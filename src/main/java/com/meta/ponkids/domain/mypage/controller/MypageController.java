package com.meta.ponkids.domain.mypage.controller;

import com.meta.ponkids.domain.cls.dto.ClassInqryListDto;
import com.meta.ponkids.domain.cls.dto.ClassListDto;
import com.meta.ponkids.domain.cls.dto.ClassReqstListDto;
import com.meta.ponkids.domain.cls.dto.ClassReviewListDto;
import com.meta.ponkids.domain.cls.service.ClassInqryService;
import com.meta.ponkids.domain.cls.service.ClassReqstService;
import com.meta.ponkids.domain.cls.service.ClassReviewService;
import com.meta.ponkids.domain.cls.service.ClassService;
import com.meta.ponkids.domain.lctre.dto.LctreListDto;
import com.meta.ponkids.domain.lctre.dto.LctreModDto;
import com.meta.ponkids.domain.lctre.dto.LctreReqstListDto;
import com.meta.ponkids.domain.lctre.repository.LctreReqstRepository;
import com.meta.ponkids.domain.lctre.service.LctreReqstService;
import com.meta.ponkids.domain.lctre.service.LctreService;
import com.meta.ponkids.domain.system.cmmnCd.service.CmmnCdDetailService;
import com.meta.ponkids.domain.system.file.service.AtchFileService;
import com.meta.ponkids.domain.user.dto.UserChldrnListDto;
import com.meta.ponkids.domain.user.dto.UserChldrnModDto;
import com.meta.ponkids.domain.user.dto.UserChldrnSaveDto;
import com.meta.ponkids.domain.user.dto.UserModDto;
import com.meta.ponkids.domain.user.repository.UserChldrnRepository;
import com.meta.ponkids.domain.user.service.UserChldrnService;
import com.meta.ponkids.domain.user.service.UserService;
import com.meta.ponkids.global.email.EmailService;
import com.meta.ponkids.global.util.date.DateUtils;
import com.meta.ponkids.global.util.session.SessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping( "/mypage" )
@Controller
@RequiredArgsConstructor
public class MypageController {

    public static String USER_VIEW_PATH;
    private final LctreReqstRepository lctreReqstRepository;

    // path 경로 : pon
    @Value( "${key.default.directoryPath.user}" )
    public void setUserViewPath( String value ) {
        USER_VIEW_PATH = value;
    }

    private final static String BASIC_DOMAIN = "mypage";
    private final static String BASIC_PATH = "/" + BASIC_DOMAIN;    // USER_VIEW_PATH + "/" + BASIC_DOMAIN 는  앞의 "/" 를 제거해야 함.


    private final ClassService classService;

    private final ClassReqstService classReqstService;

    private final LctreService lctreService;
    private final LctreReqstService lctreReqstService;


    private final UserChldrnRepository userChldrnRepository;
    private final UserService userService;

    private final UserChldrnService userChldrnService;
    private final AtchFileService atchFileService;

    private final CmmnCdDetailService cmmnCdDetailService;

    private final ClassReviewService classReviewService;

    private final ClassInqryService classInqryService;

    private final EmailService emailService;


    // layout 관련 dataSet 처리는
    // - MypageAop.java 에서 처리 ( 관심개수.. 등 )
    // - 로그인 체크 : AuthPreInterceptor.java 에서 처리 하여 return

    @GetMapping( "/" )
    // 여러개 mapping
    public String main( Model model ) {

        return "forward:/mypage/reqstHistory/list";
    }

    @GetMapping( "/likeList" )
    public String likeList( @ModelAttribute ClassListDto listDto,
                            @PageableDefault( size = 8 ) Pageable pageable,
                            Model model ) {

        // S : 필요한 객체 setting

        listDto.setUserSn( SessionUtils.getAuthUserSn() );

        listDto.setSchOption( "M" );    // M 으로 설정시 내가 좋아요 한 클래스만 조회
        listDto.setSchCntn( "M" );


        // 목록 조회
        Page< ClassListDto > resultList = classService.getList( listDto, pageable );
        model.addAttribute( "resultList", resultList );


        // E : 필요한 객체 setting

        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        // mypage용 mcd
        model.addAttribute( "mypageMcd", "likeList" );
        return USER_VIEW_PATH + BASIC_PATH + "/likeList";
    }


    @GetMapping( "/reqstHistory/list" )
    public String reqstHistoryList( @ModelAttribute ClassReqstListDto listDto,
                                    @PageableDefault( size = 8 ) Pageable pageable,
                                    Model model ) {

        // S : 필요한 객체 setting

        // session userSn setting
        listDto.setUserSn( SessionUtils.getAuthUserSn() );

        // 클래스 신청내역 리스트
        model.addAttribute( "classReqstList", classReqstService.getList( listDto, pageable ) );


        // E : 필요한 객체 setting

        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        // mypage용 mcd
        model.addAttribute( "mypageMcd", "reqstHistory" );
        return USER_VIEW_PATH + BASIC_PATH + "/reqstHistory/list";
    }

    @GetMapping( "/reqstHistory/detail" )
    public String reqstHistoryDetail( @RequestParam( required = true ) Long pk,    // 타입 체크
                                      Model model ) {

        // S : 필요한 객체 setting

        // 조회 process
        // =================================================================================
        // 1. 클래스 신청 (TB_CLASS_REQST) 에서 조회  (1 건)
        // 2. 수업 신청 ( TB_LCTRE_REQST) 에서 조회  ( 여러건 가능 )
        // 3. 수업 신청 상세 ( TB_LCTRE_REQST_DETAIL ) 에서 조회 ( 2번 count 에서 추가로 여러건 또 가능 )
        // =================================================================================


        // 1. 클래스 신청 (TB_CLASS_REQST) 에서 조회  (1 건)
//		ClassReqstListDto targetDto = classReqstService.getByClassReqstSn(pk);
        model.addAttribute( "targetDto", classReqstService.getByClassReqstSn( pk ) );

        // 2. 수업 신청 ( TB_LCTRE_REQST) 에서 조회  ( 여러건 가능 )
        // 3. 수업 신청 상세 ( TB_LCTRE_REQST_DETAIL ) 에서 조회 ( 2번 count 에서 추가로 여러건 또 가능 )

        // 2,3 번 동시에 수행.
//		List<LctreReqstListDto> targetLctreReqsts = lctreReqstService.getListByClassReqstSn(pk);
        model.addAttribute( "targetLctreReqsts", lctreReqstService.getListByClassReqstSn( pk ) );


        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        // mypage용 mcd
        model.addAttribute( "mypageMcd", "reqstHistory" );
        return USER_VIEW_PATH + BASIC_PATH + "/reqstHistory/detail";
    }


    @Transactional
    @PostMapping( "/reqstHistory/cancel" )
    public String reqstHistoryCancel( @RequestParam( required = true ) Long pk,    // 타입 체크
                                      HttpServletRequest request,
                                      Model model ) {

        // S : 필요한 객체 setting


        // 삭제 process
        // =================================================================================
        // 0. 유효성 체크 작업
        // 1. 클래스 신청 (TB_CLASS_REQST) 에서 삭제  (1 건)
        // 2. 수업 신청 ( TB_LCTRE_REQST) 에서 삭제  ( 여러건 가능 )
        // 3. 수업 신청 상세 ( TB_LCTRE_REQST_DETAIL ) 에서 삭제 ( 2번 count 에서 추가로 여러건 또 가능 )
        // 4. 삭제 이후 프로세스 수행 : 삭제 한 신청건의 수업들( lctreReqsts ) 을 순회해 각 수업의 모집인원 여부와 예비모집인원여부를 체크
        //    4-1. 모집인원여부, 모집인원여부 Y, 예비인원여부 N 일 경우 :
        //         4-1-1. (실시간 신청 인원 수 + 해당수업의삭제한 예비인원N인 건 수) 와 모집 인원 수가 같다면
        //                - 예비인원 중 현재신청건이 아니면서 삭제되지않고, 가장 먼저 등록한 1명 선택
        //                - 예비인원여부를 N으로 설정
        //                - 수업대상자 전환 안내 메일 발송
        // =================================================================================

        // 저장 후 이동할 url setting
        String moveUrl = BASIC_PATH + "/reqstHistory/list";

        // 0-1. 로그인 세션 체크
        // - auth pre interceptor 에서 이미 체크함.

        // 0-2. 본인 신청건인지 체크
        // - classReqstSn 으로 userSn 조회하여 본인 신청건인지 체크
        ClassReqstListDto targetDto = classReqstService.getByClassReqstSn( pk );
        if ( targetDto == null ) {
            model.addAttribute( "resultMsg", "신청 클래스 정보가 존재하지 않습니다." );
            model.addAttribute( "moveUrl", moveUrl );

            return "common/alert";
        }
        if ( !targetDto.getUserSn().equals( SessionUtils.getAuthUserSn() ) ) {
            // 메시지 출력 및 url 이동 처리
            model.addAttribute( "resultMsg", "로그인 정보를 확인해주세요." );
            model.addAttribute( "moveUrl", moveUrl );

            return "common/alert";
        }


        // 0-3. 수업 시작일자 지났다면 취소 불가능
        // 수업 조회
        List< LctreReqstListDto > lctreReqsts = lctreReqstService.getListByClassReqstSn( pk );

        if ( lctreReqsts == null ) {
            model.addAttribute( "resultMsg", "신청 수업 정보가 존재하지 않습니다." );
            model.addAttribute( "moveUrl", moveUrl );

            return "common/alert";
        }

        // 새로운 list 선언 ( long 변수)
        // 결국 rltmDelReqstList 에서는 해당 수업의 예비인원여부가N인 삭제자의 수를 찾기 위함
        List< Long > rltmDelReqstList = new ArrayList<>();        // 현재삭제할수업건수중 수업의 sn 을 체크하기위한 list : 추후 예비인원에서 수강대상자 선정 시 계산에필요. 예비인원이 N 인 경우만 add 한다. Y인경우는 굳이 add할 필요없음

        for ( LctreReqstListDto lctreReqst : lctreReqsts ) {

            Long lctreSn = lctreReqst.getLctreSn();

            LctreModDto targetLctreDto = lctreService.findById( lctreSn );

            if ( targetLctreDto == null ) {
                model.addAttribute( "resultMsg", "신청 수업 정보가 존재하지 않습니다." );
                model.addAttribute( "moveUrl", moveUrl );

                return "common/alert";
            }

            // 수업 시작 일자와 오늘일자를 비교
            // targetLctreDto.getLctreDt();		// 수업 시작일자
            String dateTimeString = DateUtils.getCurrentDateString( DateUtils.DF_YYYYMMDDHHMMSS_DP );    // 오늘 날짜

            if ( DateUtils.isBeforeDate( targetLctreDto.getLctreDt(), dateTimeString ) ) {
                model.addAttribute( "resultMsg", "이미 시작한 수업은 취소할 수 없습니다." );
                model.addAttribute( "moveUrl", moveUrl );

                return "common/alert";
            }


            // rltmDelReqstList 에 하단 조건이 해당될경우 lctreSn 추가
            // - 해당 수업의 모집인원여부    : Y
            // - 해당 수업의 예비모집인원여부 : Y
            // - (신청자기준)예비인원여부    : N

            LctreListDto targetLctre = lctreService.getByLctreSn( lctreReqst.getLctreSn() );

            String rcritNmprSetYn = targetLctre.getRcritNmprSetYn();                // 모집인원여부		( 수업 )
            String preparRcritNmprSetYn = targetLctre.getPreparRcritNmprSetYn();    // 예비모집인원여부		( 수업 )
            String preparNmprYn = lctreReqst.getPreparNmprYn();                        // 예비인원여부 		( 신청자 )

            if ( "Y".equals( rcritNmprSetYn ) &&
                    "Y".equals( preparRcritNmprSetYn ) &&
                    "N".equals( preparNmprYn ) ) {                                                                        //    4-1. 모집인원여부, 예비모집인원여부가 Y, (신청자기준)예비인원여부 N 일 경우 :
                rltmDelReqstList.add( targetLctre.getLctreSn() );
            }

        }


        // 1. 클래스 신청 (TB_CLASS_REQST) 에서 삭제  (1 건)
        classReqstService.deleteById( pk );

        // 2. 수업 신청 ( TB_LCTRE_REQST) 에서 삭제  ( 여러건 가능 )
        // 3. 수업 신청 상세 ( TB_LCTRE_REQST_DETAIL ) 에서 삭제 ( 2번 count 에서 추가로 여러건 또 가능 )

        // 2,3 번 동시에 수행.
        lctreReqstService.deleteByClassReqstSn( pk );

        // 삭제 처리 이후 프로세스
        // 4. 삭제 이후 프로세스 수행 : 삭제 한 신청건의 수업들( lctreReqsts ) 을 순회해 각 수업의 모집인원 여부와 예비모집인원여부를 체크
        //    4-1. 모집인원여부, 예비모집인원여부가 Y, (신청자기준)예비인원여부 N 일 경우 :
        //         4-1-1. (실시간 신청 인원 수 + 해당수업의삭제한 예비인원N인 건 수) 와 모집 인원 수가 같다면 : 예비인원 중 현재신청건이 아니면서 삭제되지않고, 가장 먼저 등록한 1명의 예비인원여부를 N으로 설정

        for ( LctreReqstListDto lctreReqst : lctreReqsts ) {                                                        // 4. 삭제 이후 프로세스 수행 : 삭제 한 신청건의 수업들( lctreReqsts ) 을 순회해 각 수업의 모집인원 여부와 예비모집인원여부를 체크

            LctreListDto targetLctre = lctreService.getByLctreSn( lctreReqst.getLctreSn() );

            String rcritNmprSetYn = targetLctre.getRcritNmprSetYn();                // 모집인원여부		( 수업 )
            String preparRcritNmprSetYn = targetLctre.getPreparRcritNmprSetYn();    // 예비모집인원여부		( 수업 )
            String preparNmprYn = lctreReqst.getPreparNmprYn();                        // 예비인원여부 		( 신청자 )
            Long rltmReqstNmprCo = targetLctre.getRltmReqstNmprCo();                // 실시간 신청 인원 수	( 수업 )
            Long rcritNmprCo = targetLctre.getRcritNmprCo();                        // 모집 인원 수		( 수업 )
            Long nowDelSameLctreCo = rltmDelReqstList.stream()
                    .filter( value -> value.equals( targetLctre.getLctreSn() ) )
                    .count();                                                        // 현재 삭제중 같은 수업의 신청수 : 예비인원여부가 N인 대상자만 집계

            if ( "Y".equals( rcritNmprSetYn ) &&
                    "Y".equals( preparRcritNmprSetYn ) &&
                    "N".equals( preparNmprYn ) ) {                                                                        //    4-1. 모집인원여부, 예비모집인원여부가 Y, (신청자기준)예비인원여부 N 일 경우 :

                if ( rltmReqstNmprCo != null && nowDelSameLctreCo != null && rcritNmprCo != null && rcritNmprCo.equals( ( rltmReqstNmprCo + nowDelSameLctreCo ) ) ) {    //         4-1-1. (실시간 신청 인원 수 + 해당수업의삭제한 예비인원N인 건 수) 와 모집 인원 수가 같다면
                    // 예비인원 중 현재신청건이 아니면서 삭제되지않고, 가장 먼저 등록한 1명 선택
                    // 예비인원여부를 N으로 설정
                    // 수업대상자 선정 알림 메일 발송

                    // 예비인원 중 현재신청건이 아니면서 삭제되지않고, 가장 먼저 등록한 1명 선택
                    LctreReqstListDto frstPreparNmprLctreReqst = lctreReqstRepository.getFrstPreparNmpr( lctreReqst );

                    // 세션 유효성 체크 : 세션 정보가 수업신청 정보와 일치하지 않으면 에러처리
                    if ( frstPreparNmprLctreReqst != null && !frstPreparNmprLctreReqst.getUserId().equals( SessionUtils.getUserId() ) ) {
                        model.addAttribute( "resultMsg", "세션 정보가 수업신청 정보와 일치하지 않습니다. 재 로그인 후 이용해주세요." );
                        model.addAttribute( "moveUrl", moveUrl );

                        return "common/alert";
                    }

                    // null이 아닐 경우에만 실행
                    if ( frstPreparNmprLctreReqst != null ) {

                        // 예비인원여부를 N으로 설정
                        lctreReqstRepository.updatePreparNmprYn( frstPreparNmprLctreReqst.getLctreReqstSn() );

                        /* [START] 수업대상자 선정 알림 메일 발송 */
                        String mailUserId = frstPreparNmprLctreReqst.getUserId();                    // 이메일 주소
                        String mailSubject = "[피오니키즈] 수업대상자 전환 안내";                            // 메일 제목 setting : 수업대상자 전환 안내 메일
                        String templateName = "email_lctreTrgterCnvrsInfo";                            // 템플릿 파일명 setting : 수업대상자 전환 안내 메일

                        // 템플릿에 전달할 데이터 설정
                        Map< String, Object > variables = new HashMap<>();
                        variables.put( "classSj", frstPreparNmprLctreReqst.getClassSj() );        // 클래스 제목 설정
                        variables.put( "lctreSj", frstPreparNmprLctreReqst.getLctreSj() );        // 수업 제목 설정
                        variables.put( "lctreDt", frstPreparNmprLctreReqst.getLctreDt() );        // 수업 일시 설정
                        variables.put( "lctreAmt", frstPreparNmprLctreReqst.getLctreAmt() );    // 수업 금액 설정
                        variables.put( "chldrnNm", frstPreparNmprLctreReqst.getChldrnNm() );    // 자녀 이름 설정

                        // 인증번호 전송 (이메일)
                        emailService.sendTemplateEmail( mailUserId, mailSubject, templateName, variables );
                        /* [END] 수업대상자 선정 알림 메일 발송 */

                        // rltmDelReqstList 에서 해당 lctreSn 값 1개를 제거,
                        rltmDelReqstList.remove( targetLctre.getLctreSn() );

                    }
                }
            }
        }


        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 삭제되었습니다." );
        model.addAttribute( "moveUrl", moveUrl );

        return "common/alert";
    }

    @GetMapping( "/reviewList" )
    public String reviewList( @ModelAttribute ClassReviewListDto listDto,
                              @PageableDefault( size = 8 ) Pageable pageable,
                              Model model ) {

        // S : 필요한 객체 setting
        // userSn setting
        listDto.setUserSn( SessionUtils.getAuthUserSn() );

        // 목록 조회
        Page< ClassReviewListDto > resultList = classReviewService.getList( listDto, pageable );
        model.addAttribute( "resultList", resultList );

        // 검색 dto setting
        model.addAttribute( "searchDTO", listDto );


        // 내 리뷰 리스트 setting

        // E : 필요한 객체 setting

        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        // mypage용 mcd
        model.addAttribute( "mypageMcd", "reviewList" );
        model.addAttribute( "mcd", "myInfoModify" );        // review 삭제 할때 mcd값을 저장해야 해서, mcd값을 따로 저장
        return USER_VIEW_PATH + BASIC_PATH + "/reviewList";
    }

    @GetMapping( "/questionList" )
    public String questionList( @ModelAttribute ClassInqryListDto listDto,
                                @PageableDefault( size = 10 ) Pageable pageable,
                                Model model ) {

        // S : 필요한 객체 setting


        // 클래스 의 Q&A
        // 총 건수 : classInqryList.totalElements 로 구함.
        listDto.setUserSn( SessionUtils.getAuthUserSn() );
        model.addAttribute("resultList", classInqryService.getList( listDto, pageable ) );	// 클래스 후기 classSn으로 검색

        // 검색 dto setting
        model.addAttribute( "searchDTO", listDto );



        // E : 필요한 객체 setting

        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        // mypage용 mcd
        model.addAttribute( "mypageMcd", "questionList" );
        return USER_VIEW_PATH + BASIC_PATH + "/questionList";
    }




    @GetMapping( "/myInfoModify" )
    public String myInfoModify( Model model ) {

        // S : 필요한 객체 setting

        // 국가 리스트 ( 회원가입 시 국가 '그 외 ' 선택시 표출되는 국가 )
        model.addAttribute( "resideAreaList", cmmnCdDetailService.getList( "RESIDE_AREA_CD" ) );

        // 내 정보 targetDto setting
        model.addAttribute( "targetDto", userService.findByUserSn( SessionUtils.getAuthUserSn() ) );

        // E : 필요한 객체 setting

        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        // mypage용 mcd
        model.addAttribute( "mypageMcd", "myInfoModify" );
        return USER_VIEW_PATH + BASIC_PATH + "/myInfoModify";
    }

    @Transactional
    @PostMapping( "/myInfoModify/update" )
    public String myInfoModifyUpdate( @ModelAttribute UserModDto modDto,
                                      @RequestParam( "file" ) MultipartFile files,
                                      HttpServletRequest request,
                                      Model model ) throws IOException {


        Long userSn = SessionUtils.getAuthUserSn();

        if ( userSn == null || !userSn.equals( modDto.getUserSn() ) ) {
            model.addAttribute( "resultMsg", "수정 중 오류가 발생하였습니다. 세션을 확인해주세요." );
            model.addAttribute( "moveUrl", "/" );
            return "common/alert";
        }

        modDto.setUserSn( userSn );


        // 첨부파일 존재시 파일 저장
        if ( !files.isEmpty() ) {
            // 기존에 첨부파일 있을시 삭제
            if ( modDto.getAtchFileSnOri() != null ) {
                atchFileService.delete( modDto.getAtchFileSnOri() );
            }

            // 첨부파일 저장
            modDto.setAtchFileSn( atchFileService.save( files ) );    // 파일 save (파일 개수 1개일 때 )
        } else {
            // 첨부파일 존재하지않을 때
            // 기존 첨부파일이 있었는데 삭제됬다면 삭제처리
            if ( modDto.getAtchFileSnOri() != null && modDto.getAtchFileSn() == null ) {
                atchFileService.delete( modDto.getAtchFileSnOri() );
                modDto.setAtchFileSn( null );
            }
        }

        // 수정 처리
        userService.update( modDto, request );

        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 수정되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/myInfoModify" );

        return "common/alert";
    }

    @GetMapping( "/myChldrnInfo" )
    public String myChldrnInfo( @RequestParam( required = false ) Long userChldrnSeq,    // 자녀 순번  *기본값 1
                                @RequestParam( required = false ) String pageType,            // 타입 ( mod : 수정(기존자녀수정), add : 등록(신규자녀등록) ) *기본값 mod
                                Model model ) {

        // S : 필요한 객체 setting

        // 자녀순번 없을시 기본값 1 로 setting
        if ( userChldrnSeq == null ) {
            userChldrnSeq = ( long ) 1;
        }

        // 페이지유형 없을시 기본값 "mod"
        // type = "add" 일 경우, 자녀 등록 영역이 표시된다.
        if ( !StringUtils.hasText( pageType ) ) {
            pageType = "mod";
        }


        List< UserChldrnListDto > userChldrnListDto = userChldrnRepository.getListByUserSn( SessionUtils.getAuthUserSn() );
        UserChldrnListDto userChldrnDto = userChldrnRepository.getByUserSnAndUserChldrnSeq( SessionUtils.getAuthUserSn(), userChldrnSeq );

        if ( userChldrnListDto.size() == 0 ) {
            pageType = "add";
        } else if ( userChldrnListDto.size() > 0 && userChldrnDto == null ) {
            userChldrnSeq = userChldrnListDto.get( 0 ).getUserChldrnSeq();
        }

        userChldrnDto = userChldrnRepository.getByUserSnAndUserChldrnSeq( SessionUtils.getAuthUserSn(), userChldrnSeq );

        if ( userChldrnDto == null ) {
            pageType = "add";
        }
        model.addAttribute( "pageType", pageType );

        // default : 자녀 불러오기 ( userChldrnSeq 번째 자녀 )
        model.addAttribute( "targetDto", userChldrnDto );

        // default : 자식 list
        model.addAttribute( "userChldrnListDto", userChldrnRepository.getListByUserSn( SessionUtils.getAuthUserSn() ) );


        // E : 필요한 객체 setting

        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );

        // mypage용 mcd
        model.addAttribute( "mypageMcd", "myChldrnInfo" );

        return USER_VIEW_PATH + BASIC_PATH + "/myChldrnInfo";
    }


    @Transactional
    @PostMapping( "/myChldrnInfo/update" )
    public String myChldrnInfoUpdate(
            @ModelAttribute UserChldrnModDto modDto,
            @RequestParam( "file" ) MultipartFile files,
            HttpServletRequest request,
            Model model ) throws IOException {


        Long userSn = SessionUtils.getAuthUserSn();

        if ( userSn == null ) {
            model.addAttribute( "resultMsg", "수정 중 오류가 발생하였습니다. 세션을 확인해주세요." );
            model.addAttribute( "moveUrl", "/" );
            return "common/alert";
        }

        modDto.setUserSn( userSn );

        // 첨부파일 존재시 파일 저장
        if ( !files.isEmpty() ) {
            // 기존에 첨부파일 있을시 삭제
            if ( modDto.getAtchFileSnOri() != null ) {
                atchFileService.delete( modDto.getAtchFileSnOri() );
            }

            // 첨부파일 저장
            modDto.setAtchFileSn( atchFileService.save( files ) );    // 파일 save (파일 개수 1개일 때 )
        } else {
            // 첨부파일 존재하지않을 때
            // 기존 첨부파일이 있었는데 삭제됬다면 삭제처리
            if ( modDto.getAtchFileSnOri() != null && modDto.getAtchFileSn() == null ) {
                atchFileService.delete( modDto.getAtchFileSnOri() );
                modDto.setAtchFileSn( null );
            }
        }

        // 수정 처리
        userChldrnService.update( modDto, request );

        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 수정되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/myChldrnInfo" );

        return "common/alert";
    }

    @Transactional
    @PostMapping( "/myChldrnInfo/insert" )
    public String myChldrnInfoInsert(
            @ModelAttribute UserChldrnSaveDto saveDto,
            @RequestParam( "file" ) MultipartFile files,
            HttpServletRequest request,
            Model model ) throws IOException {


        Long userSn = SessionUtils.getAuthUserSn();

        if ( userSn == null ) {
            model.addAttribute( "resultMsg", "등록 중 오류가 발생하였습니다. 세션을 확인해주세요." );
            model.addAttribute( "moveUrl", "/" );
            return "common/alert";
        }

        saveDto.setUserSn( userSn );


        // 첨부파일 존재시 파일 저장
        // 썸네일 이미지 존재시 파일 저장
        if ( !files.isEmpty() ) {
            saveDto.setAtchFileSn( atchFileService.save( files ) );    // 파일 save (파일 개수 1개일 때 )
        }

        // saveDto 에 userChldrnSeq =  현재 자녀들의 userChldrnSeq 최대값 + 1 로 setting . 단 최대값이 NULL일 경우를 체크해야함. NULL이라면 0으로 setting
        Long nowUserChldrnSeq = userChldrnRepository.findMaxUserChldrnSeq( userSn );
        nowUserChldrnSeq = nowUserChldrnSeq == null ? 0 : nowUserChldrnSeq;

        saveDto.setUserChldrnSeq( ( Long ) ( nowUserChldrnSeq + 1 ) );

        // 신규 자녀 등록 처리
        userChldrnService.save( saveDto, request );

        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 등록되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/myChldrnInfo" );

        return "common/alert";
    }


    @Transactional
    @PostMapping( "/myChldrnInfo/delete" )
    public String deleteChldrn(
            @RequestParam( required = true ) Long pk,
            HttpServletRequest request,
            Model model ) {


        Long userSn = SessionUtils.getAuthUserSn();

        if ( userSn == null ) {
            model.addAttribute( "resultMsg", "삭제 중 오류가 발생하였습니다. 세션을 확인해주세요." );
            model.addAttribute( "moveUrl", "/" );
            return "common/alert";
        }

        // 삭제 처리
        userChldrnRepository.deleteByChldrnSnAndUserSn( pk, userSn );

        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 삭제되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/myChldrnInfo" );

        return "common/alert";
    }


    @GetMapping( "/inqryList" )
    public String inqryList( Model model ) {

        // S : 필요한 객체 setting


        // E : 필요한 객체 setting

        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        // mypage용 mcd
        model.addAttribute( "mypageMcd", "inqryList" );
        return USER_VIEW_PATH + BASIC_PATH + "/inqryList";
    }


}
