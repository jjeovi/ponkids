package com.meta.ponkids.global.config.interceptor.auth;


import com.meta.ponkids.domain.system.login.dto.LoginDto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class AuthAdmInterceptor implements HandlerInterceptor {
    
    // 로그인 시 체크변수 pon 로 고정
    @Value( "${key.admin.auth}" )
    private String AUTH;
    
    @Override
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler ) throws Exception {
        
        String requestUri = request.getRequestURI();
        String fullUrl = getFullURL( request );
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        // 관리자 URL 인지 체크
        // 관리자 URL : ( /admin~ 으로 시작 )
        
        // 관리자 권한 일 경우
        // 현재 세션이 있는지 체크하여
        // 세션이 없으면 login 페이지로 이동
        
        if ( requestUri.startsWith( "/admin" ) ) {
            
            // 세션 체크
            if ( principal == null || principal.getClass() != LoginDto.class ) {
                // loginDto 가 없을 시
                
                // 로그인 페이지로 이동
                goToLogin( fullUrl, request, response, "LGNEMCD006" );	// 
                return false;
                
            } else {
                // loginDto 있을 때 ( 권한 문제 or 승인 문제 ... ) 
                
                // loginDto로 변경
                LoginDto loginDto = ( LoginDto ) principal;
                
                if ( loginDto.getMngrYn() != null && loginDto.getMngrYn().equals("N") ) {
                	if ( auth != null ) {
                		new SecurityContextLogoutHandler().logout(request, response, auth);
                	}
                	goToLogin( fullUrl, request, response, "LGNEMCD007" );
                	return false;
                }
                
                if ( loginDto.getMngrYn() != null && loginDto.getMngrYn().equals("Y") &&  ( loginDto.getMngrConfmYn() == null || !( loginDto.getMngrConfmYn().equals("Y")) )  ) {
                	if ( auth != null ) {
                		new SecurityContextLogoutHandler().logout(request, response, auth);
                	}
                	
                	goToLogin( fullUrl, request, response, "LGNEMCD008" );
               	 	return false;
                } 
            }
            
        }
        
        System.out.println( "AuthInterceptor : prehandle() " + fullUrl );
        
        return HandlerInterceptor.super.preHandle( request, response, handler );
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
