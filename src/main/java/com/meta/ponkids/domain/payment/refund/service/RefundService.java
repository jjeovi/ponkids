package com.meta.ponkids.domain.payment.refund.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meta.ponkids.domain.payment.dto.ClassPaymentSaveDto;
import com.meta.ponkids.domain.payment.dto.TossCancelReqDto;
import com.meta.ponkids.domain.payment.dto.TossCancelResDto;
import com.meta.ponkids.domain.payment.entity.ClassPayment;
import com.meta.ponkids.domain.payment.refund.RefundPolicy;
import com.meta.ponkids.domain.payment.refund.context.RefundContext;
import com.meta.ponkids.domain.payment.refund.factory.RefundPolicyFactory;
import com.meta.ponkids.domain.payment.repository.ClassPaymentRepository;
import com.meta.ponkids.domain.payment.service.ClassPaymentHistoryService;
import com.meta.ponkids.domain.payment.service.TossApiService;
import com.meta.ponkids.global.util.date.DateUtils;
import com.meta.ponkids.global.util.ip.IpUtils;
import com.meta.ponkids.global.util.session.SessionUtils;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefundService {
    
    private static final Logger log = LoggerFactory.getLogger( RefundService.class );
    
    private final TossApiService tossApiService;
    private final ClassPaymentRepository classPaymentRepository;    // repository setting
    private final ClassPaymentHistoryService classPaymentHistoryService;  // history service setting
    
    private final RefundPolicyFactory refundPolicyFactory = new RefundPolicyFactory();
    
    public RefundContext calculateRefund( RefundContext context ) {
        RefundPolicy policy = refundPolicyFactory.getPolicy( context );
        return policy.calculateRefundAmount( context );
    }
    
    
    public ResponseEntity<JSONObject> paymentCancel( RefundContext context, HttpServletRequest request ) throws Exception {
        
        TossCancelReqDto tossCancelReqDto = new TossCancelReqDto( context );
        tossCancelReqDto.setCancelReason( "본인 취소" );
        
        Map<String, Object> resultMap = tossApiService.requestTossCancel( tossCancelReqDto );
        
        int     code            = resultMap.get( "code" ) == null ? 0 : ( int ) resultMap.get( "code" );                        // 결제 승인 요청 결과 코드
        boolean isSuccess       = resultMap.get( "isSuccess" ) == null ? false : ( boolean ) resultMap.get( "isSuccess" );      // 결제 승인 요청 결과 성공 여부
        JSONObject resultJson   = resultMap.get( "resultJson" ) == null ? null : ( JSONObject ) resultMap.get( "resultJson" );  // 결제 승인 요청 결과 JSON 데이터
        
        // db 업데이트
        if ( isSuccess ) {
            // 응답에서 필요한 데이터 추출
            ObjectMapper objectMapper = new ObjectMapper();
            TossCancelResDto tossCancelResDto = objectMapper.readValue( resultJson.toString(), TossCancelResDto.class );
            
            // orderId 기준으로 조회
            Optional<ClassPayment> classPayment = classPaymentRepository.findByOrderId( tossCancelResDto.getOrderId() );
            
            if ( classPayment.isPresent() ) {
                // 기존 결제 정보 업데이트
                ClassPayment existPayment = classPayment.get();
                ClassPaymentSaveDto targetPaymentDto = new ClassPaymentSaveDto();
                targetPaymentDto = targetPaymentDto.toDto( existPayment );
                
                // 결제 정보 업데이트
                // TODO
                this.updateClassPayment( targetPaymentDto, tossCancelResDto, context, request );
                
            } else {
                // 기존결제정보에서 orderId로 결제정보 찾지 못함
                // TODO
                
            }
            
            log.info( "✅ 결제 취소 완료! 주문번호: {}", tossCancelResDto.getOrderId() );
            return ResponseEntity.ok( resultJson );
            
        } else {
            
            
            // 7 결제 실패 처리
            // 실패 api 구조 : { "code" : [code] , "message" : [message] }
//            {
//                "code": "NOT_FOUND_PAYMENT_SESSION",
//                "message": "결제 시간이 만료되어 결제 진행 데이터가 존재하지 않습니다."
//            }
            // TODO
            
        }
        
    
        return null;
    }
    
    
    public void updateClassPayment( ClassPaymentSaveDto targetPaymentDto, TossCancelResDto tossCancelResDto, RefundContext context, HttpServletRequest request ) {
        
        // toss 승인 결과 info setting
        targetPaymentDto.setPaymentStatus( tossCancelResDto.getStatus() );                                                  // paymentStatus        결제 상태
        targetPaymentDto.setReason( context.getClassPayment().getReason() );                                                // reason               결제 사유
        targetPaymentDto.setCancelRequestedAt( context.getCancelRequestTime() );                                            // cancelRequestedAt    결제 취소 요청 시간
        targetPaymentDto.setRefundableAmount( new BigDecimal( tossCancelResDto.getCancels()[0].getRefundableAmount() ) );   // refundableAmount     결제 취소 가능 금액
        targetPaymentDto.setCanceledAt( DateUtils.strToLDT( tossCancelResDto.getCancels()[0].getCanceledAt() ) );           // canceledAt           결제 취소 시각
        targetPaymentDto.setCancelAmount( new BigDecimal( tossCancelResDto.getCancels()[0].getCancelAmount() ) );           // cancelAmount         결제 취소 금액
        targetPaymentDto.setCancelStatus( tossCancelResDto.getCancels()[0].getCancelStatus() );                             // cancelStatus         결제 취소 상태
        targetPaymentDto.setCancelReason( tossCancelResDto.getCancels()[0].getCancelReason() );                             // cancelReason         결제 취소 사유s
        targetPaymentDto.setIdempotencyKey( context.getClassPayment().getIdempotencyKey() );                                // idempotencyKey       멱등키
        targetPaymentDto.setIdempotencyCreatedAt( context.getClassPayment().getIdempotencyCreatedAt() );                    // idempotencyCreatedAt 멱등키 생성 시각
        
        // 기본 수정사항 setting
        targetPaymentDto.setUpdusrId( SessionUtils.getUserId() );                // Id set : update
        targetPaymentDto.setUpdusrIp( IpUtils.getClientIP( request ) );          // Ip set : update
        
        // update
        ClassPayment newClassPayment = targetPaymentDto.toEntity();
        newClassPayment = classPaymentRepository.save( newClassPayment );
        
        // 이력 저장
        classPaymentHistoryService.save( newClassPayment );
        
    }
    
}
