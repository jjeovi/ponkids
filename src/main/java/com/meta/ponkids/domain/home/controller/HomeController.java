package com.meta.ponkids.domain.home.controller;

import javax.servlet.http.HttpServletRequest;

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
    public String index( HttpServletRequest request,
                         Model model ) {
        
        // S : 필요한 객체 setting
        
        // 배// 회원가입 for SNS redirect너 메인 상단 영역 리스트
        model.addAttribute( "bannerTopList", bannerService.getMainList( BANNER_MAIN_TOP ) );
        
        // 배너 상세 분류 목록 리스트
        model.addAttribute( "bannerClDetailList", cmmnCdDetailService.getList( "BANNER_CL_DETAIL_CD" ) );
        
        // 국가 리스트 ( 회원가입 시 국가 '그 외 ' 선택시 표출되는 국가 )
        model.addAttribute( "resideAreaList", cmmnCdDetailService.getList( "RESIDE_AREA_CD" ) );
        
        // 배너 메인 클래스 영역 리스트
        model.addAttribute( "bannerClassList", bannerService.getMainList( BANNER_MAIN_CLASS ) );
        
        // home 선언하여 차별점 둠 ( layout.html > pon-contents class 삭제 )
        model.addAttribute( "mcd", "home" );
        
        // E : 필요한 객체 setting
        
        return "pon/index";
    }
    
    
    @GetMapping( "/introduce" )
    public String introduce( HttpServletRequest request,
                         Model model ) {
        
        // S : 필요한 객체 setting
        
        // E : 필요한 객체 setting
        
        return "pon/introduce/introduce";
    }
}

