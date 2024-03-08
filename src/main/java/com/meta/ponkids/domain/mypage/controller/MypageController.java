package com.meta.ponkids.domain.mypage.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.meta.ponkids.domain.cls.dto.ClassReqstListDto;
import com.meta.ponkids.domain.cls.service.ClassReqstService;
import com.meta.ponkids.domain.lctre.service.LctreReqstService;
import com.meta.ponkids.global.util.session.SessionUtils;

import lombok.RequiredArgsConstructor;

@RequestMapping("/mypage")
@Controller
@RequiredArgsConstructor
public class MypageController {
	
	public static String USER_VIEW_PATH;
    // path 경로 : pon
    @Value( "${key.default.directoryPath.user}" )
    public void setUserViewPath(String value) {
    	USER_VIEW_PATH = value;
    }
	
    private final static String BASIC_DOMAIN = "mypage";
    private final static String BASIC_PATH = "/" + BASIC_DOMAIN;	// USER_VIEW_PATH + "/" + BASIC_DOMAIN 는  앞의 "/" 를 제거해야 함.
    
    
    private final ClassReqstService classReqstService;
    
    private final LctreReqstService lctreReqstService;
    
    
    // layout 관련 dataSet 처리는 
    // - MypageAop.java 에서 처리 ( 관심개수.. 등 ) 
    // - 로그인 체크 : AuthPreInterceptor.java 에서 처리 하여 return  
    
    @GetMapping( "/" )
	public String main( Model model ) {
    	
    	return "forward:/mypage/reqstHistory/list";   
	}
	
	@GetMapping( "/reqstHistory/list" )
	public String reqstHistoryList( 	@ModelAttribute ClassReqstListDto listDto,
								@PageableDefault( size = 8 ) Pageable pageable,
								Model model ) {
		
		// S : 필요한 객체 setting
		
		// session userSn setting
		listDto.setUserSn( SessionUtils.getAuthUserSn() );
		
		// 클래스 신청내역 리스트
		model.addAttribute("classReqstList", classReqstService.getList(listDto, pageable));
		
		
		// E : 필요한 객체 setting
		
		// 기본 경로 setting
		model.addAttribute( "basicPath", BASIC_PATH );
		return USER_VIEW_PATH + BASIC_PATH + "/reqstHistory/list";
	}
	
	@GetMapping( "/reqstHistory/detail" )
	public String reqstHistoryDetail( @RequestParam( required = true ) Long pk,    // 타입 체크
										Model model) {
		
		// S : 필요한 객체 setting
		
		// 조회 process 
		// =================================================================================
		// 1. 클래스 신청 (TB_CLASS_REQST) 에서 조회  (1 건)
		// 2. 수업 신청 ( TB_LCTRE_REQST) 에서 조회  ( 여러건 가능 ) 
		// 3. 수업 신청 상세 ( TB_LCTRE_REQST_DETAIL ) 에서 조회 ( 2번 count 에서 추가로 여러건 또 가능 ) 
		// =================================================================================
		
		
		// 1. 클래스 신청 (TB_CLASS_REQST) 에서 조회  (1 건)
//		ClassReqstListDto targetDto = classReqstService.getByClassReqstSn(pk);
		model.addAttribute( "targetDto", classReqstService.getByClassReqstSn(pk) );

		// 2. 수업 신청 ( TB_LCTRE_REQST) 에서 조회  ( 여러건 가능 )
		// 3. 수업 신청 상세 ( TB_LCTRE_REQST_DETAIL ) 에서 조회 ( 2번 count 에서 추가로 여러건 또 가능 )
		
		// 2,3 번 동시에 수행.
//		List<LctreReqstListDto> targetLctreReqsts = lctreReqstService.getListByClassReqstSn(pk);
		model.addAttribute( "targetLctreReqsts", lctreReqstService.getListByClassReqstSn(pk) );
		
		
		// 기본 경로 setting
		model.addAttribute( "basicPath", BASIC_PATH );
		return USER_VIEW_PATH + BASIC_PATH + "/reqstHistory/detail";
	}
	
	
	@GetMapping( BASIC_PATH + "/reviewList" )
	public String reviewList( Model model ) {
		
		// S : 필요한 객체 setting
		
		
		// E : 필요한 객체 setting
		
		// 기본 경로 setting
		model.addAttribute( "basicPath", BASIC_PATH );
		return USER_VIEW_PATH + BASIC_PATH + "/reviewList";
	}
	
	@GetMapping( BASIC_PATH + "/myInfoModify" )
	public String myInfoModify( Model model ) {
		
		// S : 필요한 객체 setting
		
		
		// E : 필요한 객체 setting
		
		// 기본 경로 setting
		model.addAttribute( "basicPath", BASIC_PATH );
		return USER_VIEW_PATH + BASIC_PATH + "/myInfoModify";
	}
	
	@GetMapping( BASIC_PATH + "/childInfoModify" )
	public String childInfoModify( Model model ) {
		
		// S : 필요한 객체 setting
		
		
		// E : 필요한 객체 setting
		
		// 기본 경로 setting
		model.addAttribute( "basicPath", BASIC_PATH );
		return USER_VIEW_PATH + BASIC_PATH + "/childInfoModify";
	}
	
	@GetMapping( BASIC_PATH + "/inqryList" )
	public String inqryList( Model model ) {
		
		// S : 필요한 객체 setting
		
		
		// E : 필요한 객체 setting
		
		// 기본 경로 setting
		model.addAttribute( "basicPath", BASIC_PATH );
		return USER_VIEW_PATH + BASIC_PATH + "/inqryList";
	}
	
	
	@GetMapping( BASIC_PATH + "/questionList" )
	public String questionList( Model model ) {
		
		// S : 필요한 객체 setting
		
		
		// E : 필요한 객체 setting
		
		// 기본 경로 setting
		model.addAttribute( "basicPath", BASIC_PATH );
		return USER_VIEW_PATH + BASIC_PATH + "/questionList";
	}
	
	
}
