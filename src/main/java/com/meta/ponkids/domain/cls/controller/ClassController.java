package com.meta.ponkids.domain.cls.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.meta.ponkids.domain.cls.dto.ClassListDto;
import com.meta.ponkids.domain.cls.service.ClassCategoryCl01Service;
import com.meta.ponkids.domain.cls.service.ClassCategoryCl02Service;
import com.meta.ponkids.domain.cls.service.ClassDetailService;
import com.meta.ponkids.domain.cls.service.ClassService;
import com.meta.ponkids.domain.cls.service.ClassWeekService;
import com.meta.ponkids.domain.lctre.service.LctreService;
import com.meta.ponkids.domain.system.cmmnCd.service.CmmnCdDetailService;
import com.meta.ponkids.domain.system.file.entity.AtchFileDetail;
import com.meta.ponkids.domain.system.file.repository.AtchFileDetailRepository;
import com.meta.ponkids.domain.system.file.service.AtchFileDetailService;
import com.meta.ponkids.domain.system.file.service.AtchFileService;
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
	
	private final LctreService lctreService;
	
	private final AtchFileDetailService atchFileDetailService;

    private final UserChldrnRepository userChldrnRepository;

	@GetMapping( BASIC_PATH + "/{mcd}/list" )
	public String list( @ModelAttribute ClassListDto listDto,
						@PageableDefault( size = 8 ) Pageable pageable,
						@PathVariable String mcd,
						Model model ) {
		
		// S : 필요한 객체 setting
		
		// 목록 조회
		Page<ClassListDto> resultList = classService.getList( listDto, pageable );
		model.addAttribute( "resultList", resultList );
		
		// 검색 dto setting
		model.addAttribute( "searchDTO", listDto );
		
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
		
		// target object 조회
		ClassListDto targetDto = classService.getByClassSn( pk );
		
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
		model.addAttribute("classLctreList", lctreService.getListByClassSn( targetDto.getClassSn() ) );		// 클래스 수업 : classSn 으로 검색
		
		
		// 3. 클래스 의 요일 정보 : classWeekList > ByClassSn
		model.addAttribute("classWeekList", classWeekService.getListByClassSn( targetDto.getClassSn() ) );	// 클래스 요일 classSn으로 검색
		
//		// 요일 List add
//		model.addAttribute( "day7List", cmmnCdDetailService.getList( "DAY_7_CD" ) );	// 요일리스트
		
		
		// 4. 클래스 의 후기 : classReviewList > ByClassSn
		// TODO
		
		// 5. 클래스 의 Q&A : classInqryList > ByClassSn
		// TODO
		
		// 6. 클래스 가 속한 카테고리의 다른 클래스들의 정보 : otherClassList ( 본인 클래스는 제외해야함 ) 
		model.addAttribute("otherClassList", classService.getListTop10OtherClassExceptMeByCtgrySn( targetDto ) );
		
//		model.addAttribute( "classSn", targetDto.getClassSn() );
		
		// 7. 계정정보 get 후 자녀 list 
		LoginDto loginDto = SessionUtils.getAuthentication();
		if ( loginDto != null ) {
			/// 자녀 정보 list get
			// chldrn target object 조회
			model.addAttribute( "targetChldrnDtoList", userChldrnRepository.getListByUserSn( loginDto.getUserSn() ) );
			
		} else {
			
		}
//		if ()
	 
		// E : 필요한 객체 setting
		
		// 기본 경로 setting
		model.addAttribute( "basicPath", BASIC_PATH );
		
		return BASIC_VIEW_PATH + "/detail";
	}
	

}
