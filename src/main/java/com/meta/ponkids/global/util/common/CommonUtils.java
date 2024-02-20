package com.meta.ponkids.global.util.common;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.meta.ponkids.domain.cls.dto.ClassCategoryCl01ModDto;
import com.meta.ponkids.domain.cls.dto.ClassCategoryCl02ModDto;
import com.meta.ponkids.domain.cls.dto.ClassModDto;
import com.meta.ponkids.domain.cls.service.ClassCategoryCl01Service;
import com.meta.ponkids.domain.cls.service.ClassCategoryCl02Service;
import com.meta.ponkids.domain.system.cmmnCd.dto.CmmnCdDetailModDto;
import com.meta.ponkids.domain.system.cmmnCd.service.CmmnCdDetailService;
import com.meta.ponkids.global.common.dto.CategoryDto;

import lombok.RequiredArgsConstructor;

// 공통 유틸
@RequiredArgsConstructor
public class CommonUtils {
	
    public static ClassCategoryCl01Service classCategoryCl01Service;
    public static ClassCategoryCl02Service classCategoryCl02Service;
    
    public static CmmnCdDetailService cmmnCdDetailService;

	// 조회조건에 따라 url mapping 변경 작업
	public static void schConditionCombineForResetUrl( CategoryDto schCategoryDto, Long classSn, String BASIC_PATH, String mcd, Model model ) {
		
		
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
	public static CategoryDto createCategory( CategoryDto targetCategoryDto, ClassModDto classDto ) {
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
				if ( targetCategoryDto != null && targetCategoryDto.getLv4Sn() != null ) {
					
					if ( targetCategoryDto.getLv4Sn().equals( (long) 0 ) ) {
						categoryDto.setLv4Sn( targetCategoryDto.getLv4Sn() );
						categoryDto.setLv4Nm( "전체" );
						categoryNm += " > " + "전체";
						
					} else {
						categoryDto.setLv4Sn( targetCategoryDto.getLv4Sn() );
						// lv4Sn 값으로 요일명 조회
						CmmnCdDetailModDto cmmnCdDetailModDto = cmmnCdDetailService.findById( targetCategoryDto.getLv4Sn() );
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
			if ( targetCategoryDto != null && targetCategoryDto.getLv1Sn() != null ) {
				
				if ( targetCategoryDto.getLv1Sn().equals( (long) 0) ) {
					// ctgrySn(카테고리일련번호) ( == targetCategoryDto.getLv1Sn() ) 의 값이 0 일 때
					targetCategoryDto.setLv1Nm( "전체" );
					categoryNm += "전체";
					
				} else {
					// 그 외
					ClassCategoryCl01ModDto classCategoryCl01ModDto = classCategoryCl01Service.findById( targetCategoryDto.getLv1Sn() );
					targetCategoryDto.setLv1Nm( classCategoryCl01ModDto.getClNm() );
					categoryNm += classCategoryCl01ModDto.getClNm();
				}
			}
			
			// lv2 setting
			// ------- S : 분류 lv2 선택값 매핑 및 분류 lv3 li 리스트 생성 작업 : 클래스 리스트 ( lv3 )
			if ( targetCategoryDto != null && targetCategoryDto.getLv2Sn() != null ) {
				
				if ( targetCategoryDto.getLv2Sn().equals( (long) 0 ) ) {
					// ctgrySn(카테고리일련번호) ( == targetCategoryDto.getLv1Sn() ) 의 값이 0 일 때 
					targetCategoryDto.setLv2Nm( "전체" );
					categoryNm += " > " + "전체";
				} else {
					// 그 외
					
					ClassCategoryCl02ModDto classCategoryCl02ModDto = classCategoryCl02Service.findById( targetCategoryDto.getLv2Sn() );
					targetCategoryDto.setLv2Nm( classCategoryCl02ModDto.getClNm() );
					categoryNm += "> " + classCategoryCl02ModDto.getClNm();
				}
			}
			
			// lv3 setting ( 전체로 선택했을 시 (0일경우) 에만 확인 ) 
			if ( targetCategoryDto != null && targetCategoryDto.getLv3Sn() != null ) {
				
				if ( targetCategoryDto.getLv3Sn().equals( (long) 0 ) ) {
					targetCategoryDto.setLv3Nm( "전체" );
					categoryNm += " > " + "전체";
				}
			}
			
			// lv3 setting ( 전체로 선택했을 시 (0일경우) 에만 확인 ) 
			if ( targetCategoryDto != null && targetCategoryDto.getLv4Sn() != null ) {
				
				if ( targetCategoryDto.getLv4Sn().equals( (long) 0 ) ) {
					targetCategoryDto.setLv4Nm( "전체" );
					categoryNm += " > " + "전체";
				} else {
					// lv4Sn 값으로 요일명 조회
					CmmnCdDetailModDto cmmnCdDetailModDto = cmmnCdDetailService.findById( targetCategoryDto.getLv4Sn() );
					targetCategoryDto.setLv4Nm( cmmnCdDetailModDto.getCdDetailNm() );
					categoryNm += " > " + cmmnCdDetailModDto.getCdDetailNm();
					
				}
			}
			
			if(targetCategoryDto != null ) {
				targetCategoryDto.setCategoryNm( categoryNm );		// category 제목 ( 분류에 뿌리기 위함 [ lctre/list.html ] )
				return targetCategoryDto;
			} else {
				return null;
			}
		}
	}
	

}
