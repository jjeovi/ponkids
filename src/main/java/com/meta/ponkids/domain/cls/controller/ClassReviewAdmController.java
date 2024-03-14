package com.meta.ponkids.domain.cls.controller;

import com.meta.ponkids.domain.cls.dto.*;
import com.meta.ponkids.domain.cls.service.*;
import com.meta.ponkids.domain.system.cmmnCd.service.CmmnCdDetailService;
import com.meta.ponkids.global.util.common.CommonUtils;
import com.meta.ponkids.global.util.session.SessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ClassReviewAdmController {
	
	private final ClassReviewService classReviewService;
	
	private final ClassService classService;
	private final ClassWeekService classWeekService;
	
	private final ClassCategoryCl01Service classCategoryCl01Service;
	private final ClassCategoryCl02Service classCategoryCl02Service;
	private final CmmnCdDetailService cmmnCdDetailService;
	
	private final static String BASIC_VIEW_PATH = "admin/classReview";
	private final static String BASIC_PATH = "/" + BASIC_VIEW_PATH;	// BASIC_VIEW_PATH 는  앞의 "/" 를 제거해야 함.
	
	
	@GetMapping( value = { BASIC_PATH + "/{mcd}/list",
					BASIC_PATH + "/{mcd}/{classSn}/list" } )
	public String list( @ModelAttribute ClassReviewListDto listDto,
						@PathVariable String mcd,
						@PathVariable( required = false ) Long classSn,
						@PageableDefault( size = 10 ) Pageable pageable,
						Model model ) {
		
		// S : 필요한 객체 setting
		
		 
		// url 에 classSn 담겨 있을 시 classSn 유효성 체크
		if ( classSn != null && classSn != 0 ) {
			// 1. class 정보가 있을 경우 : classDto 의 정보로 categoryhDto 의 lv1~lv3 까지 setting . ( lv1 : 카테고리, lv2 : 커리큘럼, lv3 : 클래스명 ) , lv4 는 listDto 에서 존재여부 체크하여 있으면 setting

			// 공통 유효성 체크 함수
			Map<String, Object> classValidCheck = classValidCheck( classSn, mcd, model );
			boolean validResult = ( boolean ) classValidCheck.get( "validResult" ); // 체크 결과
			
			if ( !validResult ) {
				model.addAttribute( "resultMsg", classValidCheck.get( "resultMsg" ) );
				model.addAttribute( "moveUrl", classValidCheck.get( "moveUrl" ) );
				
				return "common/alert";
			} else {
				ClassModDto classDto = ( ClassModDto ) classValidCheck.get( "classDto" );
				
				if ( classDto != null ) {
					// 클래스가 존재할 경우 분류 (lv1,lv2..) 값 세팅을 미리 해줌
//					listDto.setCategory( createCategory(listDto,classDto) );
					listDto.setCategory( CommonUtils.createCategory( listDto.getCategory(), classDto, 
							classCategoryCl01Service, 
							classCategoryCl02Service,
							cmmnCdDetailService ) );
				}
			}
		} else {
			// 2. class 정보가 없을 경우 : listDto의 lv1,lv2만 체크하면 됨 (lv3 or lv4 가 만약 있다면 classDto 가 있는 url 로 redirect 되었을 테니, 이 경우는 생각하지 않아도 됨.)
			//	listDto의 lv1,lv2 값이 있다면 체크하여 categoryDto 에 setting
			
//			listDto.setCategory( createCategory(listDto, null ) );
			listDto.setCategory( CommonUtils.createCategory( listDto.getCategory(), null,
					classCategoryCl01Service, 
					classCategoryCl02Service,
					cmmnCdDetailService) );
			
		}
		
		
		CommonUtils.schConditionCombineForResetUrl( listDto.getCategory(), classSn, BASIC_PATH, mcd, model );
		
		// 카테고리 ( lv1, lv2, lv3, lv4 setting 후 model addattribute 까지 진행 method )
		cateLvListSetAndModelAdd(listDto, model);
		
		
		// 목록 조회
		Page<ClassReviewListDto> resultList = classReviewService.getList( listDto, pageable );
		model.addAttribute( "resultList", resultList );
		
		// 검색 dto setting
		model.addAttribute( "searchDTO", listDto );
		
		// E : 필요한 객체 setting
		
		
		// 기본 경로 setting
		model.addAttribute( "basicPath", BASIC_PATH );
		
		return BASIC_VIEW_PATH + "/list";
	}
	
	@GetMapping( BASIC_PATH + "/{mcd}/regist" )
	public String regist( @PathVariable String mcd, Model model ) {
		
		// S : 필요한 객체 setting
		
		// 가입 object 생성
		model.addAttribute( new ClassReviewSaveDto() );
		
		// E : 필요한 객체 setting
		
		// 기본 경로 setting
		model.addAttribute( "basicPath", BASIC_PATH );
		
		return BASIC_VIEW_PATH + "/regist";
	}
	
	@Transactional
	@PostMapping( BASIC_PATH + "/{mcd}/insertReply" )
	public String insertReply (
			@ModelAttribute ClassReviewSaveDto saveDto,
//			@ModelAttribute ClassReviewRoleSaveDto classReviewRoleSaveDto,  // required false
			@PathVariable String mcd,
			HttpServletRequest request,
			Model model ) throws IOException {
		
		// S : 필요한 객체 setting
		// 부모targetDto 조회
		ClassReviewModDto targetParntsDto = classReviewService.findById( saveDto.getParntsReviewSn() );
		
		// 부모targetDto를 기준으로 classReviewSaveDto 에 필요한 값 setting
		// - classSn setting
		// - reviewSj setting
		// - openYn setting
		saveDto.setClassSn( targetParntsDto.getClassSn() );
		saveDto.setOpenYn( targetParntsDto.getOpenYn() );				//
		saveDto.setStep("2");											// 답변 : 2로 setting 
		
		// - userSn setting
		saveDto.setUserSn( SessionUtils.getAuthUserSn() );
		
		// E : 필요한 객체 setting
		
		// 등록 처리
		
		// save
		classReviewService.save( saveDto, request );
//		classReviewService.save( saveDto, classReviewRoleSaveDto, request );
		
		// 메시지 출력 및 url 이동 처리
		model.addAttribute( "resultMsg", "정상적으로 등록되었습니다." );
		model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
		
		return "common/alert";
	}
	
	@GetMapping( value = { 
			BASIC_PATH + "/{mcd}/detail",
			BASIC_PATH + "/{mcd}/modify" } )
	public String detailOrModify(
			@RequestParam( required = true ) Long pk,	// 타입 체크
			@PathVariable String mcd,
			HttpServletRequest request,
			Model model ) {
		
		// S : 필요한 객체 setting
		
		// target object 조회
		model.addAttribute( "targetDto", classReviewService.findById( pk ) );
		
		// E : 필요한 객체 setting
		
		
		// 기본 경로 setting
		model.addAttribute( "basicPath", BASIC_PATH );
		
		String urlPath = request.getServletPath();
		String remainPath = "";
		if ( urlPath.split( BASIC_PATH )[ 1 ].endsWith( "/detail" ) ) remainPath = "detail";
		if ( urlPath.split( BASIC_PATH )[ 1 ].endsWith( "/modify" ) ) remainPath = "modify";
		
		return BASIC_VIEW_PATH + "/" + remainPath;
	}
	
	@Transactional
	@PostMapping( BASIC_PATH + "/{mcd}/update" )
	public String update(
			@RequestParam("file") MultipartFile files,		// 첨부파일 필요시
			@ModelAttribute ClassReviewModDto modDto,
//			@ModelAttribute ClassReviewRoleModDto classReviewRoleModDto,  // required false
			@PathVariable String mcd,
			HttpServletRequest request,
			Model model ) throws IOException {
		
		// S : 필요한 객체 setting
		
		// E : 필요한 객체 setting
		
		
		// update 구현
		classReviewService.update( modDto, request );
//		classReviewService.update( modDto, classReviewRoleModDto, request );
		
		// 메시지 출력 및 url 이동 처리
		model.addAttribute( "resultMsg", "정상적으로 수정되었습니다." );
		model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
		
		return "common/alert";
	}
	
	
	@Transactional
	@PostMapping( BASIC_PATH + "/{mcd}/delete" )
	public String delete(
			@RequestParam( required = true ) Long pk,
			@PathVariable String mcd,
			Model model ) {
		
		// 삭제 처리
		classReviewService.deleteAllById( pk );		// By 뒤에는 custom
		
		// 메시지 출력 및 url 이동 처리
		model.addAttribute( "resultMsg", "정상적으로 삭제되었습니다." );
		model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
		
		return "common/alert";
	}
	

	// 상세 조회 Ajax
    @ResponseBody
    @GetMapping( BASIC_PATH + "/live/detailByIdAjax" )
    public Map<String, Object> detailByIdAjax( @ModelAttribute ClassReviewListDto listDto ) {
        // 해당 권한에 맞는 menuList 가져온 뒤 drawMenuTree 로 메뉴를 그린다.
        Map<String, Object> result = new HashMap<String, Object>();
        
        // 답변대상 (target)
		// TODO
//        result.put( "target", classReviewService.findById( listDto ) );
//
//        // 답변 댓글 리스트 ( targetReplyList )
//        // step 2 / parntsReviewSn 로 검색
//        listDto.setStep("2");
//        result.put("targetReplyList", classReviewService.findReplyByStepAndParntsReviewSn( listDto ) );
        
        return result;
    }
	
	
	// ========================= Util method =========================
	// ========================= Util method =========================
	// ========================= Util method =========================
	
	
	// url 변수 classSn 유효성 체크 로직
	private Map<String, Object> classValidCheck( Long classSn, String mcd, Model model ) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if ( classSn != null ) {
			// 클래스 정보 add ( classSn으로 검색 )
			ClassModDto classDto = classService.findById( classSn );
			// 클래스 요일 list 정보 add ( classSn으로 검색 )
			List<ClassWeekListDto> classWeekListDtos = classWeekService.getListByClassSn( classSn );
			
			// 클래스가 존재 하지 않을 시
			if ( classDto == null ) {
				// 메시지 출력 및 url 이동 처리
				result.put( "validResult", false );
				
				result.put( "resultMsg", "클래스의 정보를 확인해주세요." );
				result.put( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
				
				return result;
			} else if ( classWeekListDtos.size() == 0 ) {
				// 메시지 출력 및 url 이동 처리
				result.put( "validResult", false );
				
				result.put( "resultMsg", "클래스에 요일 등록이 되어있지 않습니다.\n적어도 1개의 요일을 선택해 주세요." );
				result.put( "moveUrl", "/admin/class/" + mcd + "/modify?pk=" + classSn );
				
				return result;
			} else {
				result.put( "validResult", true );
				
				// 클래스 정보 add
				result.put( "classDto", classDto );
			}
		}
		return result;
	}
	
	
	// 카테고리list setting ( lv1, lv2, lv3, lv4 setting 후 model addattribute 까지 진행 method )
	// list.html 에 카테고리 분류 부분 리스트 뿌리기 위함
	private void cateLvListSetAndModelAdd( ClassReviewListDto listDto, Model model ) {
		
		// 클래스 카테고리 분류1 list setting : 필요하다면 주석 해제 후 구현
		// 구현 부분 ======================================================================================
		model.addAttribute( "cateLv1List", classCategoryCl01Service.findAll() );	// TODO 
		// 구현 부분 ======================================================================================

		// 클래스 카테고리 분류2,3,4 list setting 
		if ( listDto.getCategory() != null ) {
			
			// 클래스 카테고리 분류2 list setting : 필요하다면 주석 해제 후 구현
			if ( listDto.getCategory().getLv1Sn() != null ) {
				// lv2 li 리스트를 미리 만들어 뿌림
				// 구현 부분 ======================================================================================
				model.addAttribute( "cateLv2List", classCategoryCl02Service.findByParntsClSnOrderByClSeq( listDto.getCategory().getLv1Sn() ) );   // lv2 list 생성
				// 구현 부분 ======================================================================================
			}
			
			// 클래스 카테고리 분류3 list setting : 필요하다면 주석 해제 후 구현
			if ( listDto.getCategory().getLv2Sn() != null ) {
				// lv3 li 리스트를 미리 만들어 뿌림
				ClassListDto classListDto = new ClassListDto();
				classListDto.setCategory( listDto.getCategory() );
				// 구현 부분 ======================================================================================
				model.addAttribute( "cateLv3List", classService.getList( classListDto ) );  // lv3 list 생성
				// 구현 부분 ======================================================================================
			}
			
			// 클래스 카테고리 분류4 list setting : 필요하다면 주석 해제 후 구현
//			if ( listDto.getCategory().getLv3Sn() != null ) {
//				// lv4 li 리스트를 미리 만들어 뿌림
//				// 구현 부분 ======================================================================================
//				model.addAttribute( "cateLv4List", classWeekService.getListByClassSn( listDto.getCategory().getLv3Sn() ) );   // lv4 list 생성 (클래스 요일 classSn으로 검색 )
//				// 구현 부분 ======================================================================================
//			}
		}
		
	}
	

}
