package com.meta.ponkids.domain.system.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    
    // 로그인 시 체크변수 pon 로 고정
    private static final String AUTH = "pon";
    
    @GetMapping( "/admin/login" )
    public String signupForm( @RequestParam( "auth" ) String auth ) {
        
        // admin 접근시 경로 : /admin/login?auth=pon
        if ( StringUtils.isEmpty( auth ) && !auth.equals( AUTH ) ) {
            // 권한체크 실패
            return "/error/401";
        }
        
        return "/admin/login/login";
        
    }
}
