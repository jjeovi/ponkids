package com.meta.ponkids.domain.system.login.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meta.ponkids.domain.system.login.dto.LoginDto;
import com.meta.ponkids.global.util.error.ErrorUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginoutAdmController {
    
    // 로그인 시 체크변수 pon 로 고정
    @Value( "${key.admin.auth}" )
    private String AUTH;
    
    
    @Value( "${key.default.admin}" )
    private String TYPE_ADMIN;
    
    @GetMapping( "/admLogin" )
    public String admLogin( @RequestParam( "auth" ) String auth,
                            HttpServletRequest request,
                            HttpSession session,
                            Model model ) {
        
        // admin 접근시 경로 : /admin/login?auth=pon
        if ( !StringUtils.hasText( auth ) ) {
            // 권한체크 실패
            return "/error/401";
        }
        
        String errCd = ( String ) session.getAttribute( "errCd" );
        if ( StringUtils.hasText( errCd ) ) {
            session.removeAttribute( "errCd" );
            
            String errMsg = ErrorUtils.getErrorMessage( errCd );
            model.addAttribute( "errMsg", errMsg );
        }
        
        model.addAttribute( "auth", auth );
        model.addAttribute( "successCode", AUTH );
        return "/admin/login/login";
        
    }

    @ResponseBody
    @PostMapping( "/admin/readyLogin" )
    public Map<String, Object> login( 	HttpServletRequest request,
    						@ModelAttribute LoginDto loginDto,
    						HttpSession session,
                            Model model ) {
    	Map<String, Object> result = new HashMap<String, Object>();
    	
    	
    	// returnUrlAfterLogin 은 관리자에서는 사용하지 않음. 세션에 값이 있을경우에만 보내고 그렇지 않으면 default로 무조건 타게 함.
//    	session.setAttribute("returnUrlAfterLogin", loginDto.getReturnUrlAfterLogin() );
    	session.setAttribute("loginType", TYPE_ADMIN );
    	session.setAttribute("returnUrlAfterLoginFail", loginDto.getReturnUrlAfterLoginFail() );
    	result.put("flag", "S");
//    	
//    	// 비밀번호 암호화
//    	loginDto.setPassword( passwordEncoder.encode( loginDto.getPassword() ) );
        
//        return loginService.userLogin( loginDto, request );
        return result;
        
    }
    
    @PostMapping( "/admLogout" )
    public String admLogout( @RequestParam( "auth" ) String auth,
                             HttpServletRequest request,
                             HttpServletResponse response,
                             HttpSession session,
                             Model model )  throws IOException  {
    	

		Authentication loginAuth = SecurityContextHolder.getContext().getAuthentication();
		
    	if ( loginAuth != null ) {
    		new SecurityContextLogoutHandler().logout( request, response, loginAuth );
    	}
    	
    	return "redirect:/admLogin?auth=" + AUTH;
    	
        
    }
    
    
}
