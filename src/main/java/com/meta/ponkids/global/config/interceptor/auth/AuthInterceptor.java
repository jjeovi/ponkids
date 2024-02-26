package com.meta.ponkids.global.config.interceptor.auth;


import com.meta.ponkids.domain.system.login.dto.LoginDto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    
    // 로그인 시 체크변수 pon 로 고정
    @Value( "${key.admin.auth}" )
    private String AUTH;
    
    @Override
    public void postHandle( HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView ) throws Exception {
    	
    	// 로그인 여부 확인 하여 
    	// request 에 loginYn 추가
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	
    	if ( auth == null ) {
    		request.setAttribute( "loginYn", "N" );
    		return ;
    	} 
    	
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
    	 // 세션 체크
        if ( principal == null || principal.getClass() != LoginDto.class ) {
            // loginDto 가 없을 시
            
            // 로그인 페이지로 이동

            request.setAttribute( "loginYn", "N" );
            
        } else {
            // loginDto 있을 때 ( 권한 문제 or 승인 문제 ... ) 
            
            // loginDto로 변경
            LoginDto loginDto = ( LoginDto ) principal;

            request.setAttribute( "loginYn", "Y" );
        	
        }
    	
    }
    
    void goToLogin( String fullUrl, HttpServletRequest request, HttpServletResponse response, String errCd ) throws Exception {
        
        HttpSession session = request.getSession();
        session.setAttribute( "errCd", errCd );
        session.setAttribute( "returnUrlAfterLogin", fullUrl );
        
        response.sendRedirect( "/admLogin?auth=" + AUTH ); // 인증이 성공한 후에는 root로 이동
        
    }
    
    String getFullURL( HttpServletRequest request ) {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();
        if ( queryString == null ) {
            return requestURL.toString();
        } else {
            return requestURL.append( "?" ).append( queryString ).toString();
        }
    }
}
