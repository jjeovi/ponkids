package com.meta.ponkids.domain.payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meta.ponkids.domain.cls.dto.ClassReqstSaveDto;
import com.meta.ponkids.domain.payment.PaymentProvider;
import com.meta.ponkids.domain.payment.dto.ClassPaymentSaveDto;
import com.meta.ponkids.domain.payment.dto.TossApprReqDto;
import com.meta.ponkids.domain.payment.dto.TossApprResDto;
import com.meta.ponkids.domain.payment.entity.ClassPayment;
import com.meta.ponkids.domain.payment.repository.ClassPaymentRepository;
import com.meta.ponkids.domain.user.repository.UserRepository;
import com.meta.ponkids.global.util.date.DateUtils;
import com.meta.ponkids.global.util.generator.OrderIdGenerator;
import com.meta.ponkids.global.util.ip.IpUtils;
import com.meta.ponkids.global.util.session.SessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassPaymentService {
    private final ClassPaymentRepository classPaymentRepository;    // repository setting
    private final ClassPaymentHistoryService classPaymentHistoryService;
    private final UserRepository userRepository;
    private final UserCustomerKeyService userCustomerKeyService;
    
    private final TossApiService tossApiService;
    
    /**
     * 결제 정보 최초 저장
     *
     * @param classReqstSaveDto 수업 신청 정보 DTO
     * @param request           request 객체
     * @return ClassPaymentSaveDto 결제 정보 DTO
     */
    @Transactional
    public ClassPaymentSaveDto save( ClassReqstSaveDto classReqstSaveDto, HttpServletRequest request ) throws IOException {
        // save 시 필요 데이터
        // =================
        // classReqst
        // userSn
        // orderId
        // customerKey
        // amount
        // paymentStatus
        // paymentProvider
        // ===================
        
        ClassPaymentSaveDto classPaymentSaveDto = new ClassPaymentSaveDto();
        classPaymentSaveDto.setClassReqst( classReqstSaveDto.toEntity() );
        classPaymentSaveDto.setOrderId( OrderIdGenerator.generatorOrderId() );
        classPaymentSaveDto.setUser( userRepository.findByUserSn( classReqstSaveDto.getUserSn() ) );
        classPaymentSaveDto.setCustomerKey( userCustomerKeyService.getOrCreateCustomerKey( classReqstSaveDto.getUserSn(), PaymentProvider.TOSS ) );
//        classPaymentSaveDto.setPaymentType( null );
//        classPaymentSaveDto.setPaymentMethod( null );
//        classPaymentSaveDto.setPaymentKey( null );    // 결제 승인 전 상태에서는 NULL 가능
        classPaymentSaveDto.setPaymentProvider( PaymentProvider.TOSS );
        classPaymentSaveDto.setAmount( classReqstSaveDto.getTotReqstAmt() );
        classPaymentSaveDto.setPaymentStatus( "PENDING" );  // 최초 결제 상태는 "PENDING"
        classPaymentSaveDto.setReason( null );
        
        classPaymentSaveDto.setRegisterId( SessionUtils.getUserId() );              // Id set : regist
        classPaymentSaveDto.setRegisterIp( IpUtils.getClientIP( request ) );        // Ip set : regist
        classPaymentSaveDto.setUpdusrId( SessionUtils.getUserId() );                // Id set : update
        classPaymentSaveDto.setUpdusrIp( IpUtils.getClientIP( request ) );          // Ip set : update
        
        // 1. 결제 정보 저장
        ClassPayment newClassPayment = classPaymentSaveDto.toEntity();
        newClassPayment = classPaymentRepository.save( newClassPayment );
        // 2. 이력 저장(history)
        classPaymentHistoryService.save( newClassPayment );
        
        classPaymentSaveDto.setClassPaymentSn( newClassPayment.getClassPaymentSn() );
        return classPaymentSaveDto;
    }
    
    /**
     * 결제 승인 요청 (토스 결제 API 호출)
     *
     * @param tossApprReqDto 결제 정보 DTO
     * @return 승인 성공 여부
     */
    public ResponseEntity<JSONObject> confirmPayment( TossApprReqDto tossApprReqDto, HttpServletRequest request ) throws Exception {
        try {
            
            Map<String, Object> resultMap =  tossApiService.requestTossPayment( tossApprReqDto );    // 결제 승인 요청
            
            int     code            = resultMap.get( "code" ) == null ? 0 : ( int ) resultMap.get( "code" );                      // 결제 승인 요청 결과 코드
            boolean isSuccess       = resultMap.get( "isSuccess" ) == null ? false : ( boolean ) resultMap.get( "isSuccess" );    // 결제 승인 요청 결과 성공 여부
            JSONObject resultJson   = resultMap.get( "resultJson" ) == null ? null : ( JSONObject ) resultMap.get( "resultJson" );    // 결제 승인 요청 결과 JSON 데이터
            
            // 결제 성공 및 실패 비즈니스 로직을 구현하세요.
            // 6 결제 성공 처리
            if ( isSuccess ) {
                // 응답에서 필요한 데이터 추출
                ObjectMapper objectMapper = new ObjectMapper();
                TossApprResDto tossApprResDto = objectMapper.readValue( resultJson.toString(), TossApprResDto.class );
                
                // orderId 기준으로 조회
                Optional<ClassPayment> classPayment = classPaymentRepository.findByOrderId( tossApprResDto.getOrderId() );
                
                if ( classPayment.isPresent() ) {
                    // 기존 결제 정보 업데이트
                    ClassPayment existPayment = classPayment.get();
                    ClassPaymentSaveDto targetPaymentDto = new ClassPaymentSaveDto();
                    targetPaymentDto = targetPaymentDto.toDto( existPayment );
                    
                    // targetPaymentDto 와 tossApprResDto 를 비교하여 변경사항이 있는지 확인
                    // amount 값 비교 (targetPaymentDto의 amount는 Long, tossApprResDto의 totalAmount는 ㅣㅐㅜㅎ)
                    BigDecimal amount1 = targetPaymentDto.getAmount(); // BigDecimal
                    BigDecimal amount2 = new BigDecimal(tossApprResDto.getTotalAmount().toString()); // Integer → BigDecimal 변환
                    if ( amount1.compareTo(amount2) != 0  ) {
                        throw new IllegalArgumentException( "결제 금액이 일치하지 않습니다." );
                        // 고객에게 알림
                        // TODO
                    }
                    
                    // 결제 정보 업데이트
                    this.updateClassPayment( targetPaymentDto, tossApprResDto, request );
                    
                } else {
                    // 기존결제정보에서 orderId로 결제정보 찾지 못함
                    // TODO
                    
                }
                
                log.info( "✅ 결제 승인 완료! 주문번호: {}", tossApprReqDto.getOrderId() );
                return ResponseEntity.ok( resultJson );
            } else {
                
                
                // 7 결제 실패 처리
                // 실패 api 구조 : { "code" : [code] , "message" : [message] }
//            {
//                "code": "NOT_FOUND_PAYMENT_SESSION",
//                "message": "결제 시간이 만료되어 결제 진행 데이터가 존재하지 않습니다."
//            }
                
                String failCode = ( String ) resultJson.get( "code" );
                String errorMessage = ( String ) resultJson.get( "message" );
                log.error( "❌ 결제 실패! 주문번호: {}, 사유: {}", tossApprReqDto.getOrderId(), errorMessage );
                
                // 결제 정보 업데이트
                this.updateClassPayment( tossApprReqDto.getOrderId(), failCode, errorMessage );
                
            }
            
            return ResponseEntity.status( code ).body( resultJson );
            
        } catch ( Exception e ) {
            // 결제 승인 요청 중 오류 발생 시 처리
            // ex) 네트워크 오류 (예: 인터넷 연결 문제),
            // 잘못된 API URL 또는 API 키 오류,
            // API 요청 데이터 오류 (400 Bad Request),
            // 서버 오류 (500 Internal Server Error),
            // JSON 응답 파싱 오류 (JsonProcessingException)
            System.err.println( "Payment approval failed: " + e.getMessage() );
            log.error( "Payment approval failed: " + e.getMessage() );
            // TODO
            
            
        }
        return null;
    }
    
    
    public void updateClassPayment( ClassPaymentSaveDto targetPaymentDto, TossApprResDto tossApprResDto, HttpServletRequest request ) {
        
        // toss 승인 결과 info setting
        targetPaymentDto.setPaymentKey( tossApprResDto.getPaymentKey() );                               // paymentKey       결제 키 값      setting
        targetPaymentDto.setPaymentType( tossApprResDto.getType() );                                    // paymentType      결제 타입       setting
        targetPaymentDto.setPaymentMethod( tossApprResDto.getMethod() );                                // paymentMethod    결제 수단       setting
        targetPaymentDto.setPaymentStatus( tossApprResDto.getStatus() );                                // paymentStatus    결제 상태       setting
        targetPaymentDto.setRequestedAt( DateUtils.strToLDT( tossApprResDto.getRequestedAt() ) );       // requestedAt      결제 요청 시간   setting
        targetPaymentDto.setApprovedAt( DateUtils.strToLDT( tossApprResDto.getApprovedAt() ) );         // approvedAt       결제 승인 시간   setting
        
        // 기본 수정사항 setting
        targetPaymentDto.setUpdusrId( SessionUtils.getUserId() );                // Id set : update
        targetPaymentDto.setUpdusrIp( IpUtils.getClientIP( request ) );          // Ip set : update
        
        // update
        ClassPayment newClassPayment = targetPaymentDto.toEntity();
        newClassPayment = classPaymentRepository.save( newClassPayment );
        
        // 이력 저장
        classPaymentHistoryService.save( newClassPayment );
        
    }
    
    public void updateClassPayment( String orderId, String failCode, String errorMessage ) {
        
        // orderId 기준으로 조회
        Optional<ClassPayment> classPayment = classPaymentRepository.findByOrderId( orderId );
        
        if ( classPayment.isPresent() ) {
            // 기존 결제 정보 업데이트
            ClassPayment existPayment = classPayment.get();
            ClassPaymentSaveDto targetPaymentDto = new ClassPaymentSaveDto();
            targetPaymentDto = targetPaymentDto.toDto( existPayment );
            
            
            // fail, message 저장
            targetPaymentDto.setPaymentStatus( failCode );                                // paymentStatus    결제 상태       setting
            targetPaymentDto.setReason( errorMessage );                                   // reason           사유           setting
            
            // update
            ClassPayment newClassPayment = targetPaymentDto.toEntity();
            newClassPayment = classPaymentRepository.save( newClassPayment );
            
            // 이력 저장
            classPaymentHistoryService.save( newClassPayment );
            
        } else {
            // 기존결제정보에서 orderId로 결제정보 찾지 못함
            // TODO
            
        }
    
    }
    
    
}
