package com.meta.ponkids.domain.mypage.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.meta.ponkids.domain.cls.dto.ClassListDto;
import com.meta.ponkids.domain.cls.dto.ClassReqstListDto;
import com.meta.ponkids.domain.cls.service.ClassReqstService;
import com.meta.ponkids.domain.cls.service.ClassService;
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
    
    @GetMapping( "/" )
	public String main( Model model ) {
    	
    	return "forward:/mypage/reqstHistory";   
	}
    
	
	@GetMapping( "/reqstHistory" )
	public String reqstHistory( 	@ModelAttribute ClassReqstListDto listDto,
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
		return USER_VIEW_PATH + BASIC_PATH + "/reqstHistory";
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
