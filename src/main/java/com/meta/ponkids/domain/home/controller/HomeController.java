package com.meta.ponkids.domain.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.meta.ponkids.domain.system.banner.service.BannerService;
import com.meta.ponkids.domain.system.cmmnCd.service.CmmnCdDetailService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
    
    private final static String BANNER_MAIN_TOP = "MAIN_TOP";
    private final static String BANNER_MAIN_CLASS = "MAIN_CLASS";
    
    private final BannerService bannerService;
    
    private final CmmnCdDetailService cmmnCdDetailService;
    
    @GetMapping( "/" )
    public String index( Model model ) {
        
        // S : 필요한 객체 setting
        
        // menuList
//        model.addAttribute("menuList", menuService.getUserMenuList( new MenuListDto() ) );	// 메뉴 리스트는 interceptor 에서 처리
        
        // 배너 메인 상단 영역 리스트
        model.addAttribute("bannerTopList", bannerService.getMainList( BANNER_MAIN_TOP ) );
        
        // 배너 상세 분류 목록 리스트
        model.addAttribute("bannerClDetailList", cmmnCdDetailService.getList( "BANNER_CL_DETAIL_CD" ));
        
        // 배너 메인 클래스 영역 리스트
        model.addAttribute( "bannerClassList", bannerService.getMainList( BANNER_MAIN_CLASS ) );
        
        // home 선언하여 차별점 둠 ( layout.html > pon-contents class 삭제 )
        model.addAttribute( "mcd", "home" );
        
        
        // E : 필요한 객체 setting
        
        return "pon/index";
    }
    
    
    // 작업 비교용 ( 퍼블리싱 작업중.. 작업 완료되면 해당 메서드 삭제 ) 
    @GetMapping( "/compare" )
    public String compare( Model model ) {
    	
    	// S : 필요한 객체 setting
    	
    	
    	// E : 필요한 객체 setting
    	
    	return "pon/index_bak";
    }
    
}
