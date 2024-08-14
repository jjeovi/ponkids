package com.meta.ponkids.domain.cls.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.meta.ponkids.global.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.meta.ponkids.domain.cls.dto.ClassInqryListDto;
import com.meta.ponkids.domain.cls.dto.ClassListDto;
import com.meta.ponkids.domain.cls.dto.ClassReqstSaveDto;
import com.meta.ponkids.domain.cls.dto.ClassReviewListDto;
import com.meta.ponkids.domain.cls.repository.ClassReqstRepository;
import com.meta.ponkids.domain.cls.service.ClassCategoryCl01Service;
import com.meta.ponkids.domain.cls.service.ClassCategoryCl02Service;
import com.meta.ponkids.domain.cls.service.ClassDetailService;
import com.meta.ponkids.domain.cls.service.ClassInqryService;
import com.meta.ponkids.domain.cls.service.ClassReqstService;
import com.meta.ponkids.domain.cls.service.ClassReviewService;
import com.meta.ponkids.domain.cls.service.ClassService;
import com.meta.ponkids.domain.cls.service.ClassWeekService;
import com.meta.ponkids.domain.lctre.dto.LctreModDto;
import com.meta.ponkids.domain.lctre.dto.LctreReqstSaveDto;
import com.meta.ponkids.domain.lctre.repository.LctreReqstRepository;
import com.meta.ponkids.domain.lctre.service.LctreReqstService;
import com.meta.ponkids.domain.lctre.service.LctreService;
import com.meta.ponkids.domain.system.file.entity.AtchFileDetail;
import com.meta.ponkids.domain.system.file.service.AtchFileDetailService;
import com.meta.ponkids.domain.system.login.dto.LoginDto;
import com.meta.ponkids.domain.user.repository.UserChldrnRepository;
import com.meta.ponkids.global.util.session.SessionUtils;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class ClassController {
	
	private final static String BASIC_PATH = "/class";
	private final static String BASIC_VIEW_PATH = "pon/cls";
	
	private final ClassService classService;
	private final ClassWeekService classWeekService;
	private final ClassDetailService classDetailService;
	private final ClassCategoryCl01Service classCategoryCl01Service;
	private final ClassCategoryCl02Service classCategoryCl02Service;
	
	private final ClassReqstService classReqstService;
	private final ClassReqstRepository classReqstRepository;
	private final ClassInqryService classInqryService;
	private final ClassReviewService classReviewService;
	
	private final LctreService lctreService;
	
	private final LctreReqstService lctreReqstService;
	private final LctreReqstRepository lctreReqstRepository;
	
	private final AtchFileDetailService atchFileDetailService;

	private final UserChldrnRepository userChldrnRepository;

	@GetMapping( BASIC_PATH + "/{mcd}/list" )
	public String list( @ModelAttribute ClassListDto listDto,
						@PageableDefault( size = 8 ) Pageable pageable,
						@PathVariable String mcd,
						Model model ) {
		
		// S : 필요한 객체 setting
		
		// 로그인 여부 파악 하여 userSn setting 함
		listDto.setUserSn( SessionUtils.getAuthUserSn());
		// 목록 조회
		Page<ClassListDto> resultList = classService.getList( listDto, pageable );
		model.addAttribute( "resultList", resultList );
		
		// 검색 dto setting
		model.addAttribute( "mainSearchDTO", listDto );
		
		// 카테고리 리스트 ( lv1 )
		// 클래스 카테고리 분류1 list setting
		model.addAttribute( "cateLv1List", classCategoryCl01Service.findAll() );
		
		// 클래스 분류 2setting
		model.addAttribute( "cateLv2List", classCategoryCl02Service.findAllByOrderByParntsClSnAscClSeqAsc() );
		
		// E : 필요한 객체 setting
		
		// 기본 경로 setting
		model.addAttribute( "basicPath", BASIC_PATH );
		return BASIC_VIEW_PATH + "/list";
	}
	
	
	@GetMapping( BASIC_PATH + "/{mcd}/detail" )
	public String detail( 
			@RequestParam( required = true ) Long pk,	// 타입 체크
			@PathVariable String mcd,
			HttpServletRequest request,
			Model model ) {
		
		// S : 필요한 객체 setting
		
		// 1. 클래스 의 정보 : targetDto
		
		// 로그인 여부 파악 하여 userSn setting 함
		Long userSn = SessionUtils.getAuthUserSn();
		// target object 조회
		ClassListDto targetDto = classService.getByClassSn( pk , SessionUtils.getAuthUserSn());
		
		if( targetDto == null  || targetDto.getClass() == null ) {
			
			// 메시지 출력 및 url 이동 처리
			model.addAttribute( "resultMsg", "유효하지 않은 클래스 정보입니다." );
			model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
			
			return "common/alert";
		}
		
		model.addAttribute( "targetDto", targetDto );
		
		// 1-1. 클래스의 정보 > 클래스 입력 항목 리스트 : targetClsDtlList  ( ex ⭐️ 미팅장소에 10분 전 도착해주시길 바랍니다. 지각 시 환불이 어렵습니다. ⭐ (예시답변 : 네 ) 등.. )
		model.addAttribute( "targetClsDtlList", classDetailService.findByClassSnOrderByClassDetailSeq( targetDto.getClassSn() ) );
		
		// 1-2. 클래스 의 정보 > 첨부파일 (여러건) : atchFileList
		// 첨부파일 존재시 (여러건)
		if ( targetDto.getAtchFileSn() != null ) {
			List<AtchFileDetail> atchFileList = atchFileDetailService.getList( targetDto.getAtchFileSn() );
			model.addAttribute( "atchFileList", atchFileList );
		}
		
		// 2. 클래스 의 수업 정보 : classLctreList > ByClassSn
//		model.addAttribute("classLctreList", lctreService.getListByClassSn( targetDto.getClassSn() ) );		// 클래스 수업 : classSn 으로 검색
		
		// 3. 클래스 의 요일 정보 : classWeekList > ByClassSn
		model.addAttribute("classWeekList", classWeekService.getListByClassSn( targetDto.getClassSn() ) );	// 클래스 요일 classSn으로 검색
		
//		// 요일 List add
//		model.addAttribute( "day7List", cmmnCdDetailService.getList( "DAY_7_CD" ) );	// 요일리스트
		
		// 4-1.해당 클래스의 카테고리 후기 : classReviewList > ByClassSn
		Pageable customPageable = PageRequest.of(0, 4);	// 첫번째페이지 (0페이지) , 4개식 조회
		
		ClassReviewListDto ctgryReviewListDto = new ClassReviewListDto();
		ctgryReviewListDto.setCtgrySn( targetDto.getCtgrySn() );
		ctgryReviewListDto.setOpenYn( "Y" );
		model.addAttribute("ctgryReviewList", classReviewService.getList( ctgryReviewListDto, customPageable ) );	// 클래스 후기 classSn으로 검색
		// 클래스의 후기
		
		// 4-2. 해당 클래스 후기 : classReviewList > ByClassSn
		customPageable = PageRequest.of(0, 5);	// 첫번째페이지 (0페이지) , 5개식 조회
		
		ClassReviewListDto classReviewListDto = new ClassReviewListDto();
		classReviewListDto.setClassSn( targetDto.getClassSn() );
		classReviewListDto.setOpenYn( "Y" );
		model.addAttribute("classReviewList", classReviewService.getList( classReviewListDto, customPageable ) );	// 클래스 후기 classSn으로 검색
		
		// 5. 클래스 의 Q&A : classInqryList > ByClassSn
		// - 총 건수 : classInqryList.totalElements 로 구함.
		ClassInqryListDto classInqryListDto = new ClassInqryListDto();
		classInqryListDto.setClassSn( targetDto.getClassSn() );
		model.addAttribute("classInqryList", classInqryService.getList( classInqryListDto, customPageable ) );	// 클래스 후기 classSn으로 검색
		
		// 6. 클래스 가 속한 카테고리의 다른 클래스들의 정보 : otherClassList ( 본인 클래스는 제외해야함 ) 
		model.addAttribute("otherClassList", classService.getListTop10OtherClassExceptMeByCtgrySn( targetDto ) );
		
		// 7. 계정정보 get 후 자녀 list
		/// 자녀 정보 list get
		// chldrn target object 조회
		model.addAttribute( "targetChldrnDtoList", userChldrnRepository.getListByUserSn( userSn ) );
		
		// 8. 해당 클래스르 신청한 이력이 있는지 확인
		// 이력이 있다면 '해당 클래스를 신청한 이력이 존재합니다. (마이페이지로 이동)  ' 할 수 있는 버튼을 구현 할지 
		model.addAttribute( "reqstHistoryYn", classReqstRepository.existsByClassSnAndUserSn( targetDto.getClassSn(), userSn ) );
		
		// E : 필요한 객체 setting
		
		// 기본 경로 setting
		model.addAttribute( "basicPath", BASIC_PATH );
		return BASIC_VIEW_PATH + "/detail";
	}
	
	
	
	/**
	 * methodName    : insert
	 * date           : 11/17/23
	 * description    : class insert method
	 * @throws ParseException
	 */
	@Transactional
	@PostMapping( BASIC_PATH + "/{mcd}/insert" )
	public String insert(
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

		
		// 0. 유효성 체크 작업.
		// ===========================================
		
		// 0-1. classSn 체크
		if ( saveDto == null || saveDto.getClassSn() == null ) {
			// 메시지 출력 및 url 이동 처리
			model.addAttribute( "resultMsg", "등록 중 문제가 발생하였습니다. 다시 시도해주세요." );
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
			model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/detail?pk=" + saveDto.getClassSn());

			return "common/alert";
		}

		// 0-4. 이미 등록되어있는 자녀와 수업인지 체크
		// 같은자녀와수업의 내용으로는 중복등록할 수 없음.
		for (LctreReqstSaveDto lctreReqst : lctreReqstDtoList) {
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
		saveDto.setTotReqstCnt(  Long.valueOf( lctreReqstDtoList.size() ) );	

		// 1-2. 총 신청 금액 ( 신청 수업의 금액을 모두 합한 금액 ) 계산하여 setting
		int totReqstAmt = 0;
		if ( lctreReqstDtoList != null && lctreReqstDtoList.size() > 0 ) {
			
			// 수업정보 순회하며 각각 금액을 sum
			for (LctreReqstSaveDto lctreReqst : lctreReqstDtoList) {
				LctreModDto lctreModDto = lctreService.findById( lctreReqst.getLctreSn());
				
				// totReqstAmt 금액에 수업금액 합산
				totReqstAmt += lctreModDto.getLctreAmt();
			}
		}
		
		// for 문 돌면서 전체 합산한 수업금액을 saveDto 의 totReqstAmt 에 저장
		saveDto.setTotReqstAmt( Long.valueOf( totReqstAmt ) );
		
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
		// ====================
		//
		//
		// =======================
		String moveUrl = BASIC_PATH + "/" + mcd + "/detail?pk=" + saveDto.getClassSn();
		lctreReqsts = lctreReqstService.save(lctreReqsts, request, moveUrl );

		String resultMsg = "";
		// 예비인원 여부 존재시, 예비로 신청되었다는 메시지 안내
		if ( StringUtils.hasText( lctreReqsts.getPreparNmprYn() ) && "Y".equals( lctreReqsts.getPreparNmprYn() ) ) {
			resultMsg = "정상적으로 신청 되었습니다. \n" +
						"신청 수업 중 예비인원으로 신청된 수업이 존재합니다.\n" +
						"마이페이지에서 확인해주세요.";
		} else {
			resultMsg = "정상적으로 신청 되었습니다.";
		}
		
		// 메시지 출력 및 url 이동 처리
		model.addAttribute( "resultMsg", resultMsg );
		model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/detail?pk=" + saveDto.getClassSn());
		
		return "common/alert";
	}
	

    // 클래스 검색 ( 커리큘럼 일련번호로 검색 ) (Ajax)
    @ResponseBody
    @GetMapping( BASIC_PATH + "/live/getClassByIdAjax" )
    public Map<String, Object> getClassByIdAjax( @RequestParam( "pk" ) Long pk
    ) {
        
        Map<String, Object> result = new HashMap<String, Object>();
        
        result.put( "resultOne", classService.getByClassSn( pk ) );   // 커리큘럼 일련번호로 검색
        
        return result;
    }


	@ExceptionHandler( CustomException.class )
	public String handleCustomException(CustomException ex, Model model) {
		// 예외 발생 시 특정 URL로 리다이렉트하고, 모델 데이터 추가

		model.addAttribute( "resultMsg", ex.getMessage() );
		model.addAttribute( "moveUrl", ex.getRedirectUrl() );

		return "common/alert";
	}
    

}
