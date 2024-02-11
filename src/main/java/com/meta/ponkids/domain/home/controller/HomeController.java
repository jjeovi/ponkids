package com.meta.ponkids.domain.home.controller;

import com.meta.ponkids.domain.system.banner.service.BannerService;
import com.meta.ponkids.domain.system.cmmnCd.service.CmmnCdDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

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
        
        // menuList
//        model.addAttribute("menuList", menuService.getUserMenuList( new MenuListDto() ) );	// 메뉴 리스트는 interceptor 에서 처리
        
        // 배너 메인 상단 영역 리스트
        model.addAttribute( "bannerTopList", bannerService.getMainList( BANNER_MAIN_TOP ) );
        
        // 배너 상세 분류 목록 리스트
        model.addAttribute( "bannerClDetailList", cmmnCdDetailService.getList( "BANNER_CL_DETAIL_CD" ) );
        
        // 국가 리스트 ( 회원가입 시 국가 '그 외 ' 선택시 표출되는 국가 )
        model.addAttribute( "resideAreaList", cmmnCdDetailService.getList( "RESIDE_AREA_CD" ) );
        
        // 배너 메인 클래스 영역 리스트
        model.addAttribute( "bannerClassList", bannerService.getMainList( BANNER_MAIN_CLASS ) );
        
        // home 선언하여 차별점 둠 ( layout.html > pon-contents class 삭제 )
        model.addAttribute( "mcd", "home" );
        
        // lgStatus  : 로그인 상태 변수
        // - login : 로그인 layer 를 호출
        // - userIntegrated : 계정통합 layer 를 호출
        // - joinForSns : sns용 회원가입 layer 를 호출
        
        String lgStatus = ( String ) request.getParameter( "lgStatus" );
        
        if ( StringUtils.hasText( lgStatus ) ) {
            switch ( lgStatus ) {
                case "login":
                    break;
                case "userIntegrated":
                    break;
                case "joinForSns":
                    model.addAttribute( "loginSnsTypeList", cmmnCdDetailService.getList( "LOGIN_SNS_CD" ) );    // 로그인 sns 코드 목록
                    break;
                default:
                    break;
            }
        }
        
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
