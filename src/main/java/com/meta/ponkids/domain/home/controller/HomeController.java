package com.meta.ponkids.domain.home.controller;

import com.meta.ponkids.domain.system.banner.dto.BannerListDto;
import com.meta.ponkids.domain.system.banner.service.BannerService;
import com.meta.ponkids.domain.system.menu.dto.MenuListDto;
import com.meta.ponkids.domain.system.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    
    private final static String BANNER_MAIN_TOP = "MAIN_TOP";
    private final static String BANNER_MAIN_CLASS = "MAIN_CLASS";
    
    private final BannerService bannerService;
    private final MenuService menuService;
    
    @GetMapping( "/" )
    public String index( Model model ) {
        
        // S : 필요한 객체 setting
        
        // menuList
        model.addAttribute("menuList", menuService.getUserMenuList( new MenuListDto() ) );
        
        // 배너 메인 상단 영역 리스트
        model.addAttribute("bannerTopList", bannerService.getMainList( BANNER_MAIN_TOP ) );
        
        // 배너 메인 클래스 영역 리스트
        model.addAttribute( "bannerClassList", bannerService.getMainList( BANNER_MAIN_CLASS ) );
        
        
        
        // E : 필요한 객체 setting
        
        return "pon/index";
    }
    
}
