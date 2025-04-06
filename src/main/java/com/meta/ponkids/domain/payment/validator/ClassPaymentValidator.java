package com.meta.ponkids.domain.payment.validator;


import com.meta.ponkids.domain.cls.dto.ClassListDto;
import com.meta.ponkids.domain.cls.dto.ClassReqstListDto;
import com.meta.ponkids.domain.cls.dto.ClassReqstSaveDto;
import com.meta.ponkids.domain.cls.service.ClassReqstService;
import com.meta.ponkids.domain.cls.service.ClassService;
import com.meta.ponkids.domain.lctre.dto.LctreModDto;
import com.meta.ponkids.domain.lctre.dto.LctreReqstListDto;
import com.meta.ponkids.domain.lctre.dto.LctreReqstSaveDto;
import com.meta.ponkids.domain.lctre.repository.LctreReqstRepository;
import com.meta.ponkids.domain.lctre.service.LctreReqstService;
import com.meta.ponkids.domain.lctre.service.LctreService;
import com.meta.ponkids.domain.payment.dto.TossApprReqDto;
import com.meta.ponkids.domain.payment.entity.ClassPayment;
import com.meta.ponkids.domain.payment.repository.ClassPaymentRepository;
import com.meta.ponkids.domain.system.login.dto.LoginDto;
import com.meta.ponkids.domain.user.entity.UserChldrn;
import com.meta.ponkids.domain.user.repository.UserChldrnRepository;
import com.meta.ponkids.global.util.date.DateUtils;
import com.meta.ponkids.global.util.session.SessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.thymeleaf.util.StringUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClassPaymentValidator {
    
    private final LctreReqstRepository lctreReqstRepository;
    private final UserChldrnRepository userChldrnRepository;
    private final LctreService lctreService;
    private final ClassService classService;
    private final ClassReqstService classReqstService;
    
    private final ClassPaymentRepository classPaymentRepository;
    private final LctreReqstService lctreReqstService;
    
    public boolean validateInsert( ClassReqstSaveDto saveDto, LctreReqstSaveDto lctreReqsts, Model model, String mcd, String basicPath ) {
        
        // 1. classSn 체크
        // 2. class의 표시기간 체크 : 상시면 상관없음. 기간설정인데 기간에 포함되어 있지 않으면 '클래스의 신청기간이 아닙니다' 메시지 출력
        // 3. userSn 체크
        // 4. 신청한 수업 존재 체크
        // 5. 수업일시 체크. 신청수업 순회(for) 하면서, 각 수업의 수업일시 보다 지났으면 신청 불가.
        //    5-1. 로그인세션의 자녀인지 체크. (도중에 로그인세션이 바뀔 수 있으므로)
        // 6. 중복 수업 체크
        
        // 1. classSn 체크
        if ( saveDto == null || saveDto.getClassSn() == null ) {
            model.addAttribute( "resultMsg", "클래스 정보를 찾을 수 없습니다." );
            model.addAttribute( "moveUrl", basicPath + "/" + mcd + "/list" );
            return false;
        }
        
        // 2. class의 표시기간 체크 : 상시면 상관없음. 기간설정인데 기간에 포함되어 있지 않으면 '클래스의 신청기간이 아닙니다' 메시지 출력
        ClassListDto targetDto = classService.getByClassSn( saveDto.getClassSn() );
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm" ); // 패턴은 형식에 맞게 수정
        
        if ( !"N".equals( targetDto.getClassPdSetYn() ) ) {
            
            if ( StringUtils.isEmpty( targetDto.getClassBeginDt() ) || StringUtils.isEmpty( targetDto.getClassEndDt() ) ) {
                model.addAttribute( "resultMsg", "클래스의 신청기간이 설정되지 않았습니다." );
                model.addAttribute( "moveUrl", basicPath + "/" + mcd + "/list" );
                return false;
            }
            
            LocalDateTime beginDt = LocalDateTime.parse( targetDto.getClassBeginDt(), formatter );
            LocalDateTime endDt = LocalDateTime.parse( targetDto.getClassEndDt(), formatter );
            
            if ( targetDto.getClassBeginDt() != null && targetDto.getClassEndDt() != null ) {
                LocalDateTime now = LocalDateTime.now();
                if ( now.isBefore( beginDt ) || now.isAfter( endDt ) ) {
                    model.addAttribute( "resultMsg", "클래스의 신청기간이 아닙니다." );
                    model.addAttribute( "moveUrl", basicPath + "/" + mcd + "/list" );
                    return false;
                }
            }
        }
        
        // 3. userSn 체크
        LoginDto loginDto = SessionUtils.getAuthentication();
        if ( loginDto == null || loginDto.getUserSn() == null ) {
            model.addAttribute( "resultMsg", "로그인 세션을 확인해주세요." );
            model.addAttribute( "moveUrl", basicPath + "/" + mcd + "/list" );
            return false;
        }
        
        // 4. 신청한 수업 존재 체크
        List<LctreReqstSaveDto> lctreReqstDtoList = lctreReqsts.getLctreReqsts();
        if ( lctreReqstDtoList == null || lctreReqstDtoList.isEmpty() ) {
            model.addAttribute( "resultMsg", "신청한 수업이 없습니다. 다시 확인해주세요" );
            model.addAttribute( "moveUrl", basicPath + "/" + mcd + "/detail?pk=" + saveDto.getClassSn() );
            return false;
        }
        
        // 5. 수업일시 체크. 신청수업 순회(for) 하면서, 각 수업의 수업일시 보다 지났으면 신청 불가.
        if ( lctreReqstDtoList != null && lctreReqstDtoList.size() > 0 ) {
            for ( LctreReqstSaveDto lctreReqst : lctreReqstDtoList ) {
                
                // 5-1. 로그인세션의 자녀인지 체크. (도중에 로그인세션이 바뀔 수 있으므로)
                UserChldrn userChldrn = userChldrnRepository.findByChldrnSnAndUserSn( lctreReqst.getChldrnSn(), loginDto.getUserSn() ).orElse( null );
                if ( userChldrn == null ) {
                    model.addAttribute( "resultMsg", "현재 로그인정보로 신청한 자녀를 찾을 수 없습니다." );
                    model.addAttribute( "moveUrl", basicPath + "/" + mcd + "/detail?pk=" + saveDto.getClassSn() );
                    return false;
                }
                
                LctreModDto lctreModDto = lctreService.findById( lctreReqst.getLctreSn() );
                try {
                    LocalDateTime lctreDt = LocalDateTime.parse( lctreModDto.getLctreDt(), formatter );
                    
                    if ( lctreDt.isBefore( LocalDateTime.now() ) ) {
                        model.addAttribute( "resultMsg", "신청하려는 수업일시가 지났습니다. 다시 확인해주세요." );
                        model.addAttribute( "moveUrl", basicPath + "/" + mcd + "/detail?pk=" + saveDto.getClassSn() );
                        return false;
                    }
                    
                } catch ( DateTimeParseException e ) {
                    model.addAttribute( "resultMsg", "수업일시 형식이 올바르지 않습니다." );
                    model.addAttribute( "moveUrl", basicPath + "/" + mcd + "/detail?pk=" + saveDto.getClassSn() );
                    return false;
                }
            }
        }
        
        // 6. 중복 수업 체크
        for ( LctreReqstSaveDto lctreReqst : lctreReqstDtoList ) {
            if ( lctreReqstRepository.existsLctreReqstWithPaidStatus( lctreReqst.getLctreSn(), lctreReqst.getChldrnSn() ) ) {
                model.addAttribute( "resultMsg", "같은 자녀로 신청된 같은수업이 존재합니다. 마이페이지에서 확인해주세요." );
                model.addAttribute( "moveUrl", basicPath + "/" + mcd + "/detail?pk=" + saveDto.getClassSn() );
                return false;
            }
        }
        
        return true;
    }
    
    public boolean validateAfterPayment( TossApprReqDto tossApprReqDto, Model model, String mcd, String basicPath ) {
        
        // 0. orderId 값으로 결제정보(주문내역정보) 조회 .
        // 1. classSn 체크
        // 2. class의 표시기간 체크 : 상시면 상관없음. 기간설정인데 기간에 포함되어 있지 않으면 '클래스의 신청기간이 아닙니다' 메시지 출력
        // 3. userSn 체크
        // 4. 신청한 수업 존재 체크
        // 5. 수업일시 체크. 신청수업 순회(for) 하면서, 각 수업의 수업일시 보다 지났으면 신청 불가.
        //    5-1. 로그인세션의 자녀인지 체크. (도중에 로그인세션이 바뀔 수 있으므로)
        // 6. 중복 수업 체크
        
        // 0. orderId 값으로 결제정보(주문내역정보) 조회 .
        ClassPayment classPayment = classPaymentRepository.findByOrderId( tossApprReqDto.getOrderId() ).orElse( null );
        if ( classPayment == null ) {
            model.addAttribute( "resultMsg", "주문내역정보를 찾을 수 없습니다." );
            model.addAttribute( "moveUrl", basicPath + "/" + mcd + "/list" );
            return false;
        }
        
        // 1. classSn 체크
        Long classSn = classPayment.getClassReqst().getClassSn();
        if ( classSn == null ) {
            model.addAttribute( "resultMsg", "클래스 정보를 찾을 수 없습니다." );
            model.addAttribute( "moveUrl", basicPath + "/" + mcd + "/list" );
            return false;
        }
        
        // 2. class의 표시기간 체크 : 상시면 상관없음. 기간설정인데 기간에 포함되어 있지 않으면 '클래스의 신청기간이 아닙니다' 메시지 출력
        ClassListDto targetDto = classService.getByClassSn( classSn );
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm" ); // 패턴은 형식에 맞게 수정
        
        if ( !"N".equals( targetDto.getClassPdSetYn() ) ) {
            
            if ( StringUtils.isEmpty( targetDto.getClassBeginDt() ) || StringUtils.isEmpty( targetDto.getClassEndDt() ) ) {
                model.addAttribute( "resultMsg", "클래스의 신청기간이 설정되지 않았습니다." );
                model.addAttribute( "moveUrl", basicPath + "/" + mcd + "/list" );
                return false;
            }
            
            LocalDateTime beginDt = LocalDateTime.parse( targetDto.getClassBeginDt(), formatter );
            LocalDateTime endDt = LocalDateTime.parse( targetDto.getClassEndDt(), formatter );
            
            if ( targetDto.getClassBeginDt() != null && targetDto.getClassEndDt() != null ) {
                LocalDateTime now = LocalDateTime.now();
                if ( now.isBefore( beginDt ) || now.isAfter( endDt ) ) {
                    model.addAttribute( "resultMsg", "클래스의 신청기간이 아닙니다." );
                    model.addAttribute( "moveUrl", basicPath + "/" + mcd + "/list" );
                    return false;
                }
            }
        }
        
        // 3. userSn 체크
        LoginDto loginDto = SessionUtils.getAuthentication();
        if ( loginDto == null || loginDto.getUserSn() == null ) {
            model.addAttribute( "resultMsg", "로그인 세션을 확인해주세요." );
            model.addAttribute( "moveUrl", basicPath + "/" + mcd + "/list" );
            return false;
        }
        
        // 4. 신청한 수업 존재 체크
        Long classReqstSn = classPayment.getClassReqst().getClassReqstSn();
        List<LctreReqstListDto> lctreReqsts = lctreReqstService.getListByClassReqstSn( classReqstSn );
        if ( lctreReqsts == null || lctreReqsts.isEmpty() ) {
            model.addAttribute( "resultMsg", "신청한 수업이 없습니다. 다시 확인해주세요" );
            model.addAttribute( "moveUrl", basicPath + "/" + mcd + "/detail?pk=" + classSn );
            return false;
        }
        
        // 5. 수업일시 체크. 신청수업 순회(for) 하면서, 각 수업의 수업일시 보다 지났으면 신청 불가.
        if ( lctreReqsts != null && lctreReqsts.size() > 0 ) {
            for ( LctreReqstListDto lctreReqst : lctreReqsts ) {
                // 5-1. 로그인세션의 자녀인지 체크. (도중에 로그인세션이 바뀔 수 있으므로)
                UserChldrn userChldrn = userChldrnRepository.findByChldrnSnAndUserSn( lctreReqst.getChldrnSn(), loginDto.getUserSn() ).orElse( null );
                if ( userChldrn == null ) {
                    model.addAttribute( "resultMsg", "현재 로그인정보로 신청한 자녀를 찾을 수 없습니다." );
                    model.addAttribute( "moveUrl", basicPath + "/" + mcd + "/detail?pk=" + classSn );
                    return false;
                }
                
                LctreModDto lctreModDto = lctreService.findById( lctreReqst.getLctreSn() );
                try {
                    LocalDateTime lctreDt = LocalDateTime.parse( lctreModDto.getLctreDt(), formatter );
                    
                    if ( lctreDt.isBefore( LocalDateTime.now() ) ) {
                        model.addAttribute( "resultMsg", "신청하려는 수업일시가 지났습니다. 다시 확인해주세요." );
                        model.addAttribute( "moveUrl", basicPath + "/" + mcd + "/detail?pk=" + classSn );
                        return false;
                    }
                    
                } catch ( DateTimeParseException e ) {
                    model.addAttribute( "resultMsg", "수업일시 형식이 올바르지 않습니다." );
                    model.addAttribute( "moveUrl", basicPath + "/" + mcd + "/detail?pk=" + classSn );
                    return false;
                }
            }
        }
        
        // 6. 중복 수업 체크
        for ( LctreReqstListDto lctreReqst : lctreReqsts ) {
            if ( lctreReqstRepository.existsLctreReqstWithPaidStatus( lctreReqst.getLctreSn(), lctreReqst.getChldrnSn() ) ) {
                model.addAttribute( "resultMsg", "같은 자녀로 신청된 같은수업이 존재합니다. 마이페이지에서 확인해주세요." );
                model.addAttribute( "moveUrl", basicPath + "/" + mcd + "/detail?pk=" + classSn );
                return false;
            }
        }
        
        return true;
    }
    
    
    public boolean validateCancel( Long classReqstSn, Model model, String basicPath, LocalDateTime cancelRequestTime ) {
        // ----------------------------------
        // 1. 로그인 세션 체크
        // 2. 본인 신청건인지 체크
        // 3. 수업 시작일자 지났다면 취소 불가능
        // ----------------------------------
        
        // 저장 후 이동할 url setting
        String moveUrl = basicPath + "/reqstHistory/list";
        
        // 1. 로그인 세션 체크
        // - auth pre interceptor 에서 이미 체크함.
        
        // 2. 본인 신청건인지 체크
        // - classReqstSn 으로 userSn 조회하여 본인 신청건인지 체크
        ClassReqstListDto targetDto = classReqstService.getByClassReqstSn( classReqstSn );
        if ( targetDto == null ) {
            model.addAttribute( "resultMsg", "신청 클래스 정보가 존재하지 않습니다." );
            model.addAttribute( "moveUrl", moveUrl );
            
            return false;
        }
        if ( !targetDto.getUserSn().equals( SessionUtils.getAuthUserSn() ) ) {
            // 메시지 출력 및 url 이동 처리
            model.addAttribute( "resultMsg", "로그인 정보를 확인해주세요." );
            model.addAttribute( "moveUrl", moveUrl );
            
            return false;
        }
        
        
        // 3. 수업 시작일자 지났다면 취소 불가능
        // 수업 조회
        List<LctreReqstListDto> lctreReqsts = lctreReqstService.getListByClassReqstSn( classReqstSn );
        
        if ( lctreReqsts == null ) {
            model.addAttribute( "resultMsg", "신청 수업 정보가 존재하지 않습니다." );
            model.addAttribute( "moveUrl", moveUrl );
            
            return false;
        }
        
        // 새로운 list 선언 ( long 변수)
        // 결국 rltmDelReqstList 에서는 해당 수업의 예비인원여부가 N인 삭제자의 수를 찾기 위함
        List<Long> rltmDelReqstList = new ArrayList<>();        // 현재삭제할수업건수중 수업의 sn 을 체크하기위한 list : 추후 예비인원에서 수강대상자 선정 시 계산에필요. 예비인원이 N 인 경우만 add 한다. Y인경우는 굳이 add할 필요없음
        
        for ( LctreReqstListDto lctreReqst : lctreReqsts ) {
            
            Long lctreSn = lctreReqst.getLctreSn();
            
            LctreModDto targetLctreDto = lctreService.findById( lctreSn );
            
            // 3-1. 수업 정보가 존재하지 않을 경우
            if ( targetLctreDto == null ) {
                model.addAttribute( "resultMsg", "신청 수업 정보가 존재하지 않습니다." );
                model.addAttribute( "moveUrl", moveUrl );
                
                return false;
            }
            
            // 수업 시작 일자와 오늘일자를 비교
            // targetLctreDto.getLctreDt();		// 수업 시작일자
            String dateTimeString = DateUtils.getCurrentDateString( DateUtils.DF_YYYYMMDDHHMMSS_DP );    // 오늘 날짜
            
            // 문자열을 LocalDateTime으로 변환
            String cancelRequestTimeStr = DateUtils.LDTToString( DateUtils.DF_YYYYMMDDHHMMSS_DP, cancelRequestTime );
            
            // DateTimeFormatter로 형식 지정 (공백 포함)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm" );
            LocalDateTime targetLctreDt = LocalDateTime.parse( targetLctreDto.getLctreDt(), formatter );
            
            // 3-2. 이미 시작한 수업일 경우
            if ( DateUtils.isBeforeDate( targetLctreDto.getLctreDt(), cancelRequestTimeStr ) ) {
                model.addAttribute( "resultMsg", "이미 시작한 수업은 취소할 수 없습니다." );
                model.addAttribute( "moveUrl", moveUrl );
                
                return false;
            }
            
            // 3-3. 수업시작일시가 24시간 이내로 남은 경우
            long hoursUntilStart = Duration.between( cancelRequestTime, targetLctreDt ).toHours();
            if ( hoursUntilStart < 24 ) {
                model.addAttribute( "resultMsg", "수업 시작 24시간 이내에는 취소할 수 없습니다." );
                model.addAttribute( "moveUrl", moveUrl );
                
                return false;
            }
            
        }
        return true;
    }
}