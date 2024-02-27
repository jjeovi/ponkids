package com.meta.ponkids.domain.lctre.controller;

import com.meta.ponkids.domain.cls.dto.*;
import com.meta.ponkids.domain.cls.service.ClassCategoryCl01Service;
import com.meta.ponkids.domain.cls.service.ClassCategoryCl02Service;
import com.meta.ponkids.domain.cls.service.ClassService;
import com.meta.ponkids.domain.cls.service.ClassWeekService;
import com.meta.ponkids.domain.lctre.dto.LctreListDto;
import com.meta.ponkids.domain.lctre.dto.LctreModDto;
import com.meta.ponkids.domain.lctre.dto.LctreSaveDto;
import com.meta.ponkids.domain.lctre.service.LctreService;
import com.meta.ponkids.domain.system.cmmnCd.dto.CmmnCdDetailModDto;
import com.meta.ponkids.domain.system.cmmnCd.service.CmmnCdDetailService;
import com.meta.ponkids.global.common.dto.CategoryDto;
import com.meta.ponkids.global.util.common.CommonUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class LctreAdmController {
	
	private final LctreService lctreService;
	
	private final ClassService classService;
	private final ClassWeekService classWeekService;
	private final ClassCategoryCl01Service classCategoryCl01Service;
	private final ClassCategoryCl02Service classCategoryCl02Service;
	
	
	private final CmmnCdDetailService cmmnCdDetailService;
	
	private final static String BASIC_VIEW_PATH = "admin/lctre";
	private final static String BASIC_PATH = "/" + BASIC_VIEW_PATH;	// BASIC_VIEW_PATH 는  앞의 "/" 를 제거해야 함.
	
	
	@GetMapping( value = { BASIC_PATH + "/{mcd}/list",
			BASIC_PATH + "/{mcd}/{classSn}/list" } )
	public String list( @ModelAttribute LctreListDto listDto,
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
					cmmnCdDetailService ) );
			
		}
		
		// 검색 조건 searchDTO 정렬하여 redirect function
		// 검색 분류 : [ 카테고리 / 커리큘럼 / 클래스 / 요일 ] 순서의 4단계:
		// 1. 카테고리, 커리큘럼 까지만 검색 (~lv2) 했을시, mapping 조건 : BASIC_PATH + "/{mcd}/list"
		// 2. 클래스 까지 검색했을 시 , : BASIC_PATH + "/{mcd}/{classSn}/list"
		CommonUtils.schConditionCombineForResetUrl( listDto.getCategory(), classSn, BASIC_PATH, mcd, model );
//		schConditionCombineForResetUrl( listDto.getCategory(), classSn, mcd, model );
		
		// 카테고리 ( lv1, lv2, lv3, lv4 setting 후 model addattribute 까지 진행 method )
	 	cateLvListSetAndModelAdd(listDto, model);
		
		// 검색 dto setting
		model.addAttribute( "searchDTO", listDto );
		
		// 목록 조회
		Page<LctreListDto> resultList = lctreService.getList( listDto, pageable );
		model.addAttribute( "resultList", resultList );
		
		// E : 필요한 객체 setting
		
		// 기본 경로 setting
		model.addAttribute( "basicPath", BASIC_PATH );
		
		return BASIC_VIEW_PATH + "/list";
	}
	
	@GetMapping( BASIC_PATH + "/{mcd}/{classSn}/regist" )
	public String regist( @PathVariable String mcd,
						  @PathVariable Long classSn,
						  Model model ) {
		
		// S : 필요한 객체 setting
		
		// url 에 classSn 담겨 있을 시 classSn 유효성 체크
		Map<String, Object> classValidCheck = classValidCheck( classSn, mcd, model );
		boolean validResult = ( boolean ) classValidCheck.get( "validResult" );
		if ( !validResult ) {
			model.addAttribute( "resultMsg", classValidCheck.get( "resultMsg" ) );
			model.addAttribute( "moveUrl", classValidCheck.get( "moveUrl" ) );
			
			return "common/alert";
		}
		
		ClassModDto classDto = ( ClassModDto ) classValidCheck.get( "classDto" );
		
		// 등록 classSn 정보 add
		model.addAttribute( "classDto", classDto );
		
		// 가입 object 생성
		model.addAttribute( new LctreSaveDto() );
		
		// 클래스 요일 List add
		model.addAttribute( "classWeekList", classWeekService.getListByClassSn( classSn ) );	// 요일리스트
		
		// E : 필요한 객체 setting
		
		// 기본 경로 setting
		model.addAttribute( "basicPath", BASIC_PATH );
		
		return BASIC_VIEW_PATH + "/regist";
	}
	
	@Transactional
	@PostMapping( BASIC_PATH + "/{mcd}/{classSn}/insert" )
	public String insert(
			@ModelAttribute LctreSaveDto saveDto,
//			@ModelAttribute LctreRoleSaveDto lctreRoleSaveDto,  // required false
			@PathVariable String mcd,
			@PathVariable Long classSn,
			HttpServletRequest request,
			Model model ) throws IOException {
		
		// S : 필요한 객체 setting
		// url 에 classSn 담겨 있을 시 classSn 유효성 체크
		Map<String, Object> classValidCheck = classValidCheck( classSn, mcd, model );
		boolean validResult = ( boolean ) classValidCheck.get( "validResult" );
		if ( !validResult ) {
			model.addAttribute( "resultMsg", classValidCheck.get( "resultMsg" ) );
			model.addAttribute( "moveUrl", classValidCheck.get( "moveUrl" ) );
			
			return "common/alert";
		}
		
		ClassModDto classDto = ( ClassModDto ) classValidCheck.get( "classDto" );
		
		// 등록 classSn 정보 add
		model.addAttribute( "classDto", classDto );
		
		// E : 필요한 객체 setting
		
		// 등록 처리
		// 수업 순번 없을 경우, 최대값으로 설정
		if ( saveDto.getLctreSeq() == null ) {
			LctreModDto lctreModDto = lctreService.findTop1ByClassSnOrderByLctreSeqDesc( saveDto.getClassSn() );
			if ( lctreModDto == null ) {
				saveDto.setLctreSeq( ( long ) 1 );
			} else {
				saveDto.setLctreSeq( lctreModDto.getLctreSeq() + 1 );
			}
		}
		
		// save
		lctreService.save( saveDto, request );
		
		// 메시지 출력 및 url 이동 처리
		model.addAttribute( "resultMsg", "정상적으로 등록되었습니다." );
		model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/" + classSn + "/list" );
		
		return "common/alert";
	}
	
	@GetMapping( value = {
			BASIC_PATH + "/{mcd}/{classSn}/detail",
			BASIC_PATH + "/{mcd}/{classSn}/modify" } )
	public String detailOrModify(
			@RequestParam( required = true ) Long pk,	// 타입 체크
			@PathVariable String mcd,
			@PathVariable Long classSn,
			HttpServletRequest request,
			Model model ) {
		
		// S : 필요한 객체 setting
		// url 에 classSn 담겨 있을 시 classSn 유효성 체크
		Map<String, Object> classValidCheck = classValidCheck( classSn, mcd, model );
		boolean validResult = ( boolean ) classValidCheck.get( "validResult" );
		if ( !validResult ) {
			model.addAttribute( "resultMsg", classValidCheck.get( "resultMsg" ) );
			model.addAttribute( "moveUrl", classValidCheck.get( "moveUrl" ) );
			
			return "common/alert";
		}
		
		ClassModDto classDto = ( ClassModDto ) classValidCheck.get( "classDto" );
		
		// 등록 classSn 정보 add
		model.addAttribute( "classDto", classDto );
		
		// 클래스 요일 List add
		model.addAttribute( "classWeekList", classWeekService.getListByClassSn( classSn ) );	// 요일리스트
		
		// target object 조회
		LctreModDto targetDto = lctreService.findById( pk );
		
		// lctreSn 으로 조회한 classSn 값과 url Param의 classSn 같은 값인지 비교
		if ( !targetDto.getClassSn().equals( classSn ) ) {
			model.addAttribute( "resultMsg", "클래스 일련번호 값을 확인해주세요." );
			model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
			
			return "common/alert";
		}
		
		model.addAttribute( "targetDto", targetDto );
		
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
	@PostMapping( BASIC_PATH + "/{mcd}/{classSn}/update" )
	public String update(
			@ModelAttribute LctreModDto modDto,
//			@ModelAttribute LctreRoleModDto lctreRoleModDto,  // required false
			@PathVariable String mcd,
			@PathVariable Long classSn,
			HttpServletRequest request,
			Model model ) throws IOException {
		
		// S : 필요한 객체 setting
		// url 에 classSn 담겨 있을 시 classSn 유효성 체크
		Map<String, Object> classValidCheck = classValidCheck( classSn, mcd, model );
		boolean validResult = ( boolean ) classValidCheck.get( "validResult" );
		if ( !validResult ) {
			model.addAttribute( "resultMsg", classValidCheck.get( "resultMsg" ) );
			model.addAttribute( "moveUrl", classValidCheck.get( "moveUrl" ) );
			
			return "common/alert";
		}
		
		ClassModDto classDto = ( ClassModDto ) classValidCheck.get( "classDto" );
		
		// 등록 classSn 정보 add
		model.addAttribute( "classDto", classDto );
		
		// E : 필요한 객체 setting
		
		
		// update 구현
		lctreService.update( modDto, request );
//		lctreService.update( modDto, lctreRoleModDto, request );
		
		// 메시지 출력 및 url 이동 처리
		model.addAttribute( "resultMsg", "정상적으로 수정되었습니다." );
		model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/" + classSn + "/list" );
		
		return "common/alert";
	}
	
	
	@Transactional
	@PostMapping( value = { BASIC_PATH + "/{mcd}/delete",
			BASIC_PATH + "/{mcd}/{classSn}/delete" } )
	public String delete(
			@RequestParam( required = true ) Long pk,
			@PathVariable String mcd,
			@PathVariable Long classSn,
			Model model ) {
		
		// url 에 classSn 담겨 있을 시 classSn 유효성 체크
		if ( classSn != null ) {
			Map<String, Object> classValidCheck = classValidCheck( classSn, mcd, model );
			boolean validResult = ( boolean ) classValidCheck.get( "validResult" );
			if ( !validResult ) {
				model.addAttribute( "resultMsg", classValidCheck.get( "resultMsg" ) );
				model.addAttribute( "moveUrl", classValidCheck.get( "moveUrl" ) );
				
				return "common/alert";
			}
			
			ClassModDto classDto = ( ClassModDto ) classValidCheck.get( "classDto" );
			
			// 등록 classSn 정보 add
			model.addAttribute( "classDto", classDto );
		}
		
		
		// 삭제 처리
		lctreService.deleteAllById( pk );		// By 뒤에는 custom
		
		// 메시지 출력 및 url 이동 처리
		model.addAttribute( "resultMsg", "정상적으로 삭제되었습니다." );
		if ( classSn != null ) { 
			model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/" + classSn + "/list" );
		} else {
			model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
		}
		return "common/alert";
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
	
	// 조회조건에 따라 url mapping 변경 작업
	// CommonUtils. 로 옮기면서 삭제 예정
	// 충분한 테스트 후 추후 삭제 
	private void schConditionCombineForResetUrl( LctreListDto listDto, Long classSn, String mcd, Model model ) {
		
		CategoryDto schCategoryDto = listDto.getCategory();
		
		if ( schCategoryDto != null && schCategoryDto.getLv3Sn() != null ) {   // schCategoryDto.getLv3Sn() != null : 클래스 검색 값이 있을시,
			
			// 0. schCategoryDto.getLv3Sn 이 0 일때
			//  0-1. classSn 이 있을 때, :
			//  0-2. classSn 이 없을 때, : return
			// 1. classSn 이 있을 때,
			//  1-1. classSn과 schCategoryDto.getLv3Sn() 값이 같은 경우 : lv4 존재여부 확인 후, return
			//  1-2. classSn과 schCategoryDto.getLv3Sn() 값이 다른 경우 : BASIC_PATH + "/{mcd}/{classSn}/list" 경로로 redirect 한다.
			// 2. classSn 이 없을 때 : BASIC_PATH + "/{mcd}/{classSn}/list" 경로로 redirect 한다.
			
			if ( schCategoryDto.getLv3Sn() == 0 ) {
				// 0. schCategoryDto.getLv3Sn 이 0 일때
				
				if ( classSn != null ) {
					String makeUrlParam = "";
					if ( schCategoryDto.getLv1Sn() != null )
						makeUrlParam += "category.lv1Sn=" + schCategoryDto.getLv1Sn() + "&";
					if ( schCategoryDto.getLv2Sn() != null )
						makeUrlParam += "category.lv2Sn=" + schCategoryDto.getLv2Sn() + "&";
					if ( schCategoryDto.getLv3Sn() != null )
						makeUrlParam += "category.lv3Sn=" + schCategoryDto.getLv3Sn() + "&";
					if ( schCategoryDto.getLv4Sn() != null )
						makeUrlParam += "category.lv4Sn=" + schCategoryDto.getLv4Sn() + "&";
					try {
						// response 선언
						HttpServletResponse response = ( ( ServletRequestAttributes ) RequestContextHolder.currentRequestAttributes() ).getResponse();
						response.sendRedirect( BASIC_PATH + "/" + mcd + "/list?" + makeUrlParam );
						
					} catch ( IOException e ) {
						throw new RuntimeException( e );
					}
					
				} else {
					return;
				}
			}
			
			// classSn으로 class 정보 조회
			if ( schCategoryDto.getLv3Sn().equals( classSn ) ) {
				//  1-1. classSn과 schCategoryDto.getLv3Sn() 값이 같은 경우 : 바로 return
			} else if ( classSn != null && ! (schCategoryDto.getLv3Sn().equals(classSn)) ) {
				//  1-2. classSn과 schCategoryDto.getLv3Sn() 값이 다른 경우 : BASIC_PATH + "/{mcd}/{classSn}/list" 경로로 redirect 한다.
				
				try {
					// response 선언
					HttpServletResponse response = ( ( ServletRequestAttributes ) RequestContextHolder.currentRequestAttributes() ).getResponse();
					if ( schCategoryDto.getLv4Sn() != null ) {
						response.sendRedirect( BASIC_PATH + "/" + mcd + "/" + classSn + "/list?category.lv4Sn=" + schCategoryDto.getLv4Sn() );
					} else {
						response.sendRedirect( BASIC_PATH + "/" + mcd + "/" + classSn + "/list" );
					}
					
				} catch ( IOException e ) {
					throw new RuntimeException( e );
				}
			} else if ( classSn == null ) {
				// 2. classSn 이 없을 때 : BASIC_PATH + "/{mcd}/{classSn}/list" 경로로 redirect 한다.
				try {
					// response 선언
					HttpServletResponse response = ( ( ServletRequestAttributes ) RequestContextHolder.currentRequestAttributes() ).getResponse();
					if ( schCategoryDto.getLv4Sn() != null ) {
						response.sendRedirect( BASIC_PATH + "/" + mcd + "/" + schCategoryDto.getLv3Sn() + "/list?category.lv4Sn=" + schCategoryDto.getLv4Sn() );
					} else {
						response.sendRedirect( BASIC_PATH + "/" + mcd + "/" + schCategoryDto.getLv3Sn() + "/list" );
					}
					
				} catch ( IOException e ) {
					throw new RuntimeException( e );
				}
				
			} else {
			}
			
		} else if ( schCategoryDto != null ) {
			// lv3 조회조건이 null 일 경우
			
			if ( classSn != null ) {
				// redirect
				String makeUrlParam = "";
				if ( schCategoryDto.getLv1Sn() != null )
					makeUrlParam += "category.lv1Sn=" + schCategoryDto.getLv1Sn() + "&";
				if ( schCategoryDto.getLv2Sn() != null )
					makeUrlParam += "category.lv2Sn=" + schCategoryDto.getLv2Sn() + "&";
				
				try {
					// response 선언
					HttpServletResponse response = ( ( ServletRequestAttributes ) RequestContextHolder.currentRequestAttributes() ).getResponse();
					response.sendRedirect( BASIC_PATH + "/" + mcd + "/list?" + makeUrlParam );
					
				} catch ( IOException e ) {
					throw new RuntimeException( e );
				}
				
			} else {
			}
		}
	}
	
	
	// create Category 함수
	// CommonUtils. 로 옮기면서 삭제 예정
	// 충분한 테스트 후 추후 삭제 
	private CategoryDto createCategory( LctreListDto listDto, ClassModDto classDto ) {
		// 1. class 정보가 있을 경우 : classDto 의 정보로 categoryhDto 의 lv1~lv3 까지 setting . ( lv1 : 카테고리, lv2 : 커리큘럼, lv3 : 클래스명 ) , lv4 는 listDto 에서 존재여부 체크하여 있으면 setting
		// 2. class 정보가 없을 경우 : listDto의 lv1,lv2만 체크하면 됨 (lv3 or lv4 가 만약 있다면 classDto 가 있는 url 로 redirect 되었을 테니, 이 경우는 생각하지 않아도 됨.)
		//	listDto의 lv1,lv2 값이 있다면 체크하여 categoryDto 에 setting
		
		CategoryDto categoryDto = new CategoryDto();
		
		// 분류 제목 설정 변수 선언
		String categoryNm = "";
		
		if ( classDto != null ) {
			// 1. class 정보가 있을 경우 : classDto 의 정보로 categoryhDto 의 lv1~lv3 까지 setting . ( lv1 : 카테고리, lv2 : 커리큘럼, lv3 : 클래스명 ) , lv4 는 listDto 에서 존재여부 체크하여 있으면 setting
			
			 // 분류 선택값 set 및 리스트 미리 setting 작업
			if ( classDto.getCtgrySn() != null ) {
				
				// ------- S : 분류 lv1 선택값 매핑 및 분류 lv2 li 리스트 생성 작업 : 커리큘럼 리스트 ( lv2 )
				categoryDto.setLv1Sn( classDto.getCtgrySn() );	// searchDTO 에 lv1 Sn 매칭
				// ctgrySn 값으로 카테고리명 조회
				ClassCategoryCl01ModDto classCategoryCl01ModDto = classCategoryCl01Service.findById( categoryDto.getLv1Sn() );
				categoryDto.setLv1Nm( classCategoryCl01ModDto.getClNm() );
				categoryNm += classCategoryCl01ModDto.getClNm();
				
				
				// ------- S : 분류 lv2 선택값 매핑 및 분류 lv3 li 리스트 생성 작업 : 클래스 리스트 ( lv3 )
				if ( classDto.getCrseSn() == null ) {
					// 커리큘럼Sn 이 null 일 경우, 0 으로 setting
					categoryDto.setLv2Sn( ( long ) 0 );
					categoryDto.setLv2Nm( "전체" );
					categoryNm += " > " + "전체";
				} else {
					categoryDto.setLv2Sn( classDto.getCrseSn() );	 // searchDTO 에 lv2 Sn 매칭
					// crseSn 값으로 커리큘럼명 조회
					ClassCategoryCl02ModDto classCategoryCl02ModDto = classCategoryCl02Service.findById( categoryDto.getLv2Sn() );
					categoryDto.setLv2Nm( classCategoryCl02ModDto.getClNm() );
					categoryNm += "> " + classCategoryCl02ModDto.getClNm();
				}
				
				// ------- S : 분류 lv3 선택값 매핑 및 분류 lv4 li 리스트 생성 작업 : 요일 리스트 ( lv4 )
				categoryDto.setLv3Sn( classDto.getClassSn() );
				categoryDto.setLv3Nm( classDto.getClassSj() );
				categoryNm += " > " + classDto.getClassSj();
				
				// lv4 setting
				if ( listDto.getCategory() != null && listDto.getCategory().getLv4Sn() != null ) {
					
					if ( listDto.getCategory().getLv4Sn().equals( (long) 0 ) ) {
						categoryDto.setLv4Sn( listDto.getCategory().getLv4Sn() );
						categoryDto.setLv4Nm( "전체" );
						categoryNm += " > " + "전체";
						
					} else {
						categoryDto.setLv4Sn( listDto.getCategory().getLv4Sn() );
						// lv4Sn 값으로 요일명 조회
						CmmnCdDetailModDto cmmnCdDetailModDto = cmmnCdDetailService.findById( listDto.getCategory().getLv4Sn() );
						categoryDto.setLv4Nm( cmmnCdDetailModDto.getCdDetailNm() );
						categoryNm += " > " + cmmnCdDetailModDto.getCdDetailNm();
						
					}
					
				}
				
				categoryDto.setCategoryNm( categoryNm );		// category 제목 ( 분류에 뿌리기 위함 [ lctre/list.html ] )
			}
			
			return categoryDto;
			
		} else {
			// 2. class 정보가 없을 경우 : listDto의 lv1,lv2만 체크하면 됨 (lv3 or lv4 가 만약 있다면 classDto 가 있는 url 로 redirect 되었을 테니, 이 경우는 생각하지 않아도 됨.)
			//	listDto의 lv1,lv2 값이 있다면 체크하여 categoryDto 에 setting
			
			// lv1 setting
			// ------- S : 분류 lv1 선택값 매핑 및 분류 lv2 li 리스트 생성 작업 : 커리큘럼 리스트 ( lv2 )
			if ( listDto.getCategory() != null && listDto.getCategory().getLv1Sn() != null ) {
				
				if ( listDto.getCategory().getLv1Sn().equals( (long) 0) ) {
					// ctgrySn(카테고리일련번호) ( == listDto.getCategory().getLv1Sn() ) 의 값이 0 일 때
					listDto.getCategory().setLv1Nm( "전체" );
					categoryNm += "전체";
					
				} else {
					// 그 외
					ClassCategoryCl01ModDto classCategoryCl01ModDto = classCategoryCl01Service.findById( listDto.getCategory().getLv1Sn() );
					listDto.getCategory().setLv1Nm( classCategoryCl01ModDto.getClNm() );
					categoryNm += classCategoryCl01ModDto.getClNm();
				}
			}
			
			// lv2 setting
			// ------- S : 분류 lv2 선택값 매핑 및 분류 lv3 li 리스트 생성 작업 : 클래스 리스트 ( lv3 )
			if ( listDto.getCategory() != null && listDto.getCategory().getLv2Sn() != null ) {
				
				if ( listDto.getCategory().getLv2Sn().equals( (long) 0 ) ) {
					// ctgrySn(카테고리일련번호) ( == listDto.getCategory().getLv1Sn() ) 의 값이 0 일 때 
					listDto.getCategory().setLv2Nm( "전체" );
					categoryNm += " > " + "전체";
				} else {
					// 그 외
					
					ClassCategoryCl02ModDto classCategoryCl02ModDto = classCategoryCl02Service.findById( listDto.getCategory().getLv2Sn() );
					listDto.getCategory().setLv2Nm( classCategoryCl02ModDto.getClNm() );
					categoryNm += "> " + classCategoryCl02ModDto.getClNm();
				}
			}
			
			// lv3 setting ( 전체로 선택했을 시 (0일경우) 에만 확인 ) 
			if ( listDto.getCategory() != null && listDto.getCategory().getLv3Sn() != null ) {
				
				if ( listDto.getCategory().getLv3Sn().equals( (long) 0 ) ) {
					listDto.getCategory().setLv3Nm( "전체" );
					categoryNm += " > " + "전체";
				}
			}
			
			// lv3 setting ( 전체로 선택했을 시 (0일경우) 에만 확인 ) 
			if ( listDto.getCategory() != null && listDto.getCategory().getLv4Sn() != null ) {
				
				if ( listDto.getCategory().getLv4Sn().equals( (long) 0 ) ) {
					listDto.getCategory().setLv4Nm( "전체" );
					categoryNm += " > " + "전체";
				} else {
					// lv4Sn 값으로 요일명 조회
					CmmnCdDetailModDto cmmnCdDetailModDto = cmmnCdDetailService.findById( listDto.getCategory().getLv4Sn() );
					listDto.getCategory().setLv4Nm( cmmnCdDetailModDto.getCdDetailNm() );
					categoryNm += " > " + cmmnCdDetailModDto.getCdDetailNm();
					
				}
			}
			
			if(listDto.getCategory() != null ) {
				listDto.getCategory().setCategoryNm( categoryNm );		// category 제목 ( 분류에 뿌리기 위함 [ lctre/list.html ] )
				return listDto.getCategory();
			} else {
				return null;
			}
		}
	}
	
	
 // 카테고리list setting ( lv1, lv2, lv3, lv4 setting 후 model addattribute 까지 진행 method )
 	// list.html 에 카테고리 분류 부분 리스트 뿌리기 위함
 	private void cateLvListSetAndModelAdd( LctreListDto listDto, Model model ) {
 		
 	// 카테고리 리스트 ( lv1 )
		// 클래스 카테고리 분류1 list setting
// 		구현 부분 ======================================================================================
		model.addAttribute( "cateLv1List", classCategoryCl01Service.findAll() );
//	  구현 부분 ======================================================================================
			
		// 클래스 카테고리 분류2,3,4 list setting
		if ( listDto.getCategory() != null ) {
			
			// 클래스 카테고리 분류2 list setting
			if ( listDto.getCategory().getLv1Sn() != null ) {
				// lv2 li 리스트를 미리 만들어 뿌림
//				구현 부분 ======================================================================================
				model.addAttribute( "cateLv2List", classCategoryCl02Service.findByParntsClSnOrderByClSeq( listDto.getCategory().getLv1Sn() ) );   // lv2 list 생성
//				구현 부분 ======================================================================================
			}
			
			// 클래스 카테고리 분류3 list setting
			if ( listDto.getCategory().getLv2Sn() != null ) {
				// lv3 li 리스트를 미리 만들어 뿌림
				ClassListDto classListDto = new ClassListDto();
				classListDto.setCategory( listDto.getCategory() );
//				구현 부분 ======================================================================================
				model.addAttribute( "cateLv3List", classService.getList( classListDto ) );  // lv3 list 생성
//				구현 부분 ======================================================================================
			}
			
			// 클래스 카테고리 분류4 list setting
			if ( listDto.getCategory().getLv3Sn() != null ) {
				// lv4 li 리스트를 미리 만들어 뿌림
//				구현 부분 ======================================================================================
				model.addAttribute( "cateLv4List", classWeekService.getListByClassSn( listDto.getCategory().getLv3Sn() ) );   // lv4 list 생성 (클래스 요일 classSn으로 검색 )
//				구현 부분 ======================================================================================
			}
		}
 		
 	}
	
}
