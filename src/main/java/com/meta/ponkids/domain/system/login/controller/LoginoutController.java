package com.meta.ponkids.domain.system.login.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meta.ponkids.domain.system.login.dto.LoginDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginoutController {
	
    
    @Value( "${key.default.user}" )
    private String TYPE_USER;
    	
    @ResponseBody
    @PostMapping( "/readyLogin" )
    public Map<String, Object> readyLogin( 	HttpServletRequest request,
    						@ModelAttribute LoginDto loginDto,
    						HttpSession session,
                            Model model ) {
    	Map<String, Object> result = new HashMap<String, Object>();
    	
    	session.setAttribute("loginType", TYPE_USER );
    	session.setAttribute("returnUrlAfterLogin", loginDto.getReturnUrlAfterLogin() );
    	session.setAttribute("returnUrlAfterLoginFail", loginDto.getReturnUrlAfterLoginFail() );
    	result.put("flag", "S");
//    	
//    	// 비밀번호 암호화
//    	loginDto.setPassword( passwordEncoder.encode( loginDto.getPassword() ) );
        
//        return loginService.userLogin( loginDto, request );
        return result;
        
    }
    
    @RequestMapping( "/logout" )
    public String logout( 	HttpServletRequest request,
							HttpServletResponse response,
							HttpSession session,
							Model model ) throws IOException {
    	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String returnUrlAfterLogout = request.getParameter("returnUrl");
		
//		String returnUrlAfterLogout = ( String ) session.getAttribute( "returnUrlAfterLogout" );
        
    	if ( auth != null ) {
    		new SecurityContextLogoutHandler().logout( request, response, auth );
    	}
    	
    	if ( returnUrlAfterLogout == null || returnUrlAfterLogout.equals("") ) {
    		return "redirect:/";
    	} else {
    		return "redirect:" + returnUrlAfterLogout;
    	}
        
    }

    
}
