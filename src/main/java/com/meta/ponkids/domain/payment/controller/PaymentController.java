package com.meta.ponkids.domain.payment.controller;


import com.meta.ponkids.domain.cls.dto.ClassListDto;
import com.meta.ponkids.domain.cls.dto.ClassReqstSaveDto;
import com.meta.ponkids.domain.cls.service.ClassReqstService;
import com.meta.ponkids.domain.cls.service.ClassService;
import com.meta.ponkids.domain.lctre.dto.LctreModDto;
import com.meta.ponkids.domain.lctre.dto.LctreReqstSaveDto;
import com.meta.ponkids.domain.lctre.repository.LctreReqstRepository;
import com.meta.ponkids.domain.lctre.service.LctreReqstService;
import com.meta.ponkids.domain.lctre.service.LctreService;
import com.meta.ponkids.domain.payment.dto.ClassPaymentSaveDto;
import com.meta.ponkids.domain.payment.dto.TossApprReqDto;
import com.meta.ponkids.domain.payment.service.ClassPaymentService;
import com.meta.ponkids.domain.system.login.dto.LoginDto;
import com.meta.ponkids.global.util.session.SessionUtils;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PaymentController {
    
    private final static String BASIC_PATH = "/class";
    private final static String BASIC_ROOT_PATH = "/payment";
    private final static String BASIC_VIEW_PATH = "pon/payment";
    
    private final ClassService classService;
    private final ClassReqstService classReqstService;
    
    private final LctreService lctreService;
    private final LctreReqstService lctreReqstService;
    private final LctreReqstRepository lctreReqstRepository;
    
    private final ClassPaymentService classPaymentService;
    
    /**
     * methodName    : checkout
     * date           : 2025/02/12
     * description    : 주문(클래스) 신청시, 주문페이지로 이동
     *
     * @throws ParseException
     */
    @Transactional
    @PostMapping( BASIC_ROOT_PATH + "/{mcd}/orderCheckout" )
    public String orderCheckout(
            @ModelAttribute ClassReqstSaveDto saveDto,
            @ModelAttribute LctreReqstSaveDto lctreReqsts,
            @PathVariable String mcd,
            HttpServletRequest request,
            Model model ) throws IOException, ParseException {
        
        // insert process
        // ===========================================
        // 0. 유효성 체크 작업.
        // 1. TB_CLASS_REQST insert
        // 2. TB_LCTRE_REQST insert
        // 3. TB_LCTRE_REQST_DETAIL insert
        // 4. TB_CLASS_PAYMENT insert <- payment_status를 [pending] 상태로 저장
        // ===========================================
        
        // 0-1. classSn 체크
        if ( saveDto == null || saveDto.getClassSn() == null ) {
            // 메시지 출력 및 url 이동 처리
            model.addAttribute( "resultMsg", "신청 중 문제가 발생하였습니다. 다시 시도해주세요." );
            model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
            
            return "common/alert";
        }
        
        // 0-2. userSn 체크
        // 로그인 안되어 있으면 return
        LoginDto loginDto = SessionUtils.getAuthentication();
        if ( loginDto == null || loginDto.getUserSn() == null ) {
            // 메시지 출력 및 url 이동 처리
            model.addAttribute( "resultMsg", "로그인 세션을 확인해주세요." );
            model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
            
            return "common/alert";
        }
        
        // 0-3. 신청한 수업 존재 체크
        List<LctreReqstSaveDto> lctreReqstDtoList = lctreReqsts.getLctreReqsts();
        if ( lctreReqstDtoList == null ) {
            model.addAttribute( "resultMsg", "신청한 수업이 없습니다. 다시 확인해주세요" );
            model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/detail?pk=" + saveDto.getClassSn() );
            
            return "common/alert";
        }
        
        // 0-4. 이미 등록되어있는 자녀와 수업인지 체크
        // 같은자녀와수업의 내용으로는 중복등록할 수 없음.
        for ( LctreReqstSaveDto lctreReqst : lctreReqstDtoList ) {
            if ( lctreReqstRepository.existsByLctreSnAndChldrnSn( lctreReqst.getLctreSn(), lctreReqst.getChldrnSn() ) ) {
                // 메시지 출력 및 url 이동 처리
                model.addAttribute( "resultMsg", "같은 자녀로 신청된 같은수업이 존재합니다. 마이페이지에서 확인해주세요." );
                model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/detail?pk=" + saveDto.getClassSn() );
                
                return "common/alert";
            }
        }
        
        // 0-4. 수업 수강모집인원 설정 확인 및 유효성 체크
        // 수업별로 체크를 해야하기 때문에 수업신청 프로세스에서 해당 체크 진행
        
        //      - 모집인원설정여부, 예비모집인원 설졍여부 확인
        //      1. 모집인원설정여부 설정시 : 수강모집인원수, 현재수강인원수, 지금추가하는클래스의 총건수  확인
        //         (1) 수강모집인원수 > 현재수강인원수 : 그대로 insert
        //             (1-1) 수강모집인원수 - 현재수강인원수  > 지금 추가해야할
        //         (2) 수강모집인원수 < 현재수강인원수 :
        //             (2-1) [예비모집인원설정 Y 인 경우] : 수강모집인원수 + 예비모집인원수 > 현재수강인원수 : 예비인원설정 후 insert
        //                                              수강모집인원수 + 예비모집인원수 < 현재수강인원수 : 수강신청 실패 로직 (인원수초과 알림)
        //             (2-2) [예비모집인원설정 N 인 경우] : 수강신청 실패 로직 (인원수초과 알림)
        
        // 로그인 세션의 userSn 값으로 set
        saveDto.setUserSn( loginDto.getUserSn() );
        
        // 1. TB_CLASS_REQST insert
        // ===========================================
        // 총 신청 건수 ( 한 클래스 내에 몇개의 [수업&자녀] 의 조합으로 신청을 했는지 => 수업과 자녀가 여러개라면 2개이상이 가능함 ) 계산하여 setting
        // 총 신청 건수 setting  (* 신청한 수업의 size : 개수 )
        saveDto.setTotReqstCnt( Long.valueOf( lctreReqstDtoList.size() ) );
        
        // 1-2. 총 신청 금액 ( 신청 수업의 금액을 모두 합한 금액 ) 계산하여 setting
        int totReqstAmt = 0;
        // 1-3 클래스신청명 setting
        // (1) 수업건수가 1건일 경우      : [클래스명] 수업명
        // (2) 수업건수가 2건 이상일 경우  : [클래스명] 수업명 외 n-1건
        String classReqstNm = "";
        if ( lctreReqstDtoList != null && lctreReqstDtoList.size() > 0 ) {
            
            
            int i = 0;
            // 수업정보 순회하며 각각 금액을 sum
            for ( LctreReqstSaveDto lctreReqst : lctreReqstDtoList ) {
                LctreModDto lctreModDto = lctreService.findById( lctreReqst.getLctreSn() );
                
                // totReqstAmt 금액에 수업금액 합산
                totReqstAmt += lctreModDto.getLctreAmt();
                if ( i == 0 ) {
                    ClassListDto classListDto = classService.getByClassSn( lctreModDto.getClassSn() );
                    classReqstNm = "[" + classListDto.getClassSj() + "] " + lctreModDto.getLctreSj();
                }
                i++;
                
            }
            
            if ( lctreReqstDtoList.size() > 1 ) {
                classReqstNm += " 외 " + ( lctreReqstDtoList.size() - 1 ) + "건";
            }
        }
        saveDto.setClassReqstNm( classReqstNm );
        
        // for 문 돌면서 전체 합산한 수업금액을 saveDto 의 totReqstAmt 에 저장
        saveDto.setTotReqstAmt( BigDecimal.valueOf( totReqstAmt ) );
        
        // 1-3. insert
        saveDto = classReqstService.save( saveDto, request );
        
        // 2. TB_LCTRE_REQST insert
        // 1 개 이상의 multi data
        // ===========================================
        // 3. TB_LCTRE_REQST_DETAIL insert
        // ===========================================
        
        // 클래스신청일련번호 setting
        // 2-1. 클래스 신청 일련번호 (classReqstSn 값 set) set
        lctreReqsts.setClassReqstSn( saveDto.getClassReqstSn() );
        
        // 2. TB_LCTRE_REQST insert
        // 3. TB_LCTRE_REQST_DETAIL insert
        // 2번 3번 2개 모두 lctreReqstService.save() 에서 수행
        // =======================
        String moveUrl = BASIC_PATH + "/" + mcd + "/detail?pk=" + saveDto.getClassSn();
        lctreReqsts = lctreReqstService.save( lctreReqsts, request, moveUrl );
        
        
        // 4. TB_CLASS_PAYMENT insert <- : payment_status를 [pending] 상태로 저장
        ClassPaymentSaveDto classPaymentSaveDto = new ClassPaymentSaveDto();
        classPaymentSaveDto = classPaymentService.save( saveDto, request );
        
        // 주문명 setting
        // orderName
        // S : 모델에 결제 정보 추가
        model.addAttribute( "orderId", classPaymentSaveDto.getOrderId() );          // 주문ID
        model.addAttribute( "amount", classPaymentSaveDto.getAmount() );          // 결제 금액
        model.addAttribute( "customerKey", classPaymentSaveDto.getCustomerKey() );          // 고객 키
        model.addAttribute( "orderName", saveDto.getClassReqstNm() );          // 주문명
        model.addAttribute( "customerEmail", classPaymentSaveDto.getUser().getUserId() );          // 주문자 email
        model.addAttribute( "customerName", classPaymentSaveDto.getUser().getUserNm() );          // 주문자 명
        model.addAttribute( "customerMobilePhone", classPaymentSaveDto.getUser().getTelNo() );          // 예제 데이터
        model.addAttribute( "successUrl", BASIC_ROOT_PATH + "/" + mcd + "/orderSuccess" );    // 성공시 url
        model.addAttribute( "failUrl", BASIC_ROOT_PATH + "/" + mcd + "/orderFail" );          // 성공시 url
        // E : 모델에 결제 정보 추가
        
        return BASIC_VIEW_PATH + "/checkout";
    }
    
    
    /**
     * methodName    : orderSuccess
     * date           : 2025/02/12
     * description    : 결제에 성공하였을 경우, 결제 성공 프로세스 진행 후 결제성공페이지로 이동
     *
     * @throws ParseException
     */
    @Transactional
    @GetMapping( BASIC_ROOT_PATH + "/{mcd}/orderSuccess" )
    public String orderSuccess(
            @PathVariable String mcd,
//            @RequestParam( required = false ) String paymentType,
//            @RequestParam( required = false ) String orderId,
//            @RequestParam( required = false ) String paymentKey,
//            @RequestParam( required = false ) Long amount,
            @ModelAttribute TossApprReqDto tossApprReqDto,
            HttpServletRequest request,
            Model model ) throws Exception {
        
        
        classPaymentService.confirmPayment( tossApprReqDto, request );
        
        // TODO : 결제 성공 화면 으로 이동 하도록 (결제성공화면 퍼블 구현 필요)
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "결제에 성공하였습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
        
        return "common/alert";
        
//        return BASIC_VIEW_PATH + "/success";
//            if ( approvedPayment == null || !approvedPayment.get( "status" ).equals( "SUCCESS" ) ) {
//                throw new RuntimeException( "Payment approval failed" );
//            }
        
        // 승인된 결제 정보를 DB에 저장
//            classPaymentService.processPayment( approvedPayment, request );
    
    }
    
    
    /**
     * methodName    : orderFail
     * date           : 2025/02/12
     * description    : 결제에 성공하였을 경우, 결제 성공 프로세스 진행 후 결제성공페이지로 이동
     *
     * @throws ParseException
     */
    @Transactional
    @GetMapping( BASIC_ROOT_PATH + "/{mcd}/orderFail" )
    public String orderFail(
            @PathVariable String mcd,
            HttpServletRequest request,
            Model model ) throws IOException, ParseException {
        
        System.out.println( "orderFail" );
        
        // TODO : 결제 실패 화면 으로 이동 하도록 (결제실패화면 퍼블 구현 필요)
        model.addAttribute( "resultMsg", "결제에 실패하였습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
        return "";
    }
    
    
}
