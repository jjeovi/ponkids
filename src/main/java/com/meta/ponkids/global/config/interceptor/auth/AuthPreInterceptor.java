package com.meta.ponkids.global.config.interceptor.auth;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.meta.ponkids.domain.system.login.dto.LoginDto;
import com.meta.ponkids.global.util.common.CommonUtils;

@Component
public class AuthPreInterceptor implements HandlerInterceptor {
	
	private final static String redirectUrl = "/?lgStatus=login";
	
	@Override
	public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler ) throws Exception {
		
		String fullUrl = CommonUtils.getFullURL( request );
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		// 마이페이지만 auth 적용 ( /mypage/~ )
		
		// 현재 세션이 있는지 체크하여
		// 세션이 없으면 login 페이지로 이동
    	if ( auth == null ) {
			// 로그인 페이지로 이동
			CommonUtils.goToLogin( fullUrl, request, response, "LGNEMCD006" , redirectUrl );	// 
			return false;
    	} 
			
			// 세션 체크
		if ( principal == null || principal.getClass() != LoginDto.class ) {
			// loginDto 가 없을 시
			
			// 로그인 페이지로 이동
			CommonUtils.goToLogin( fullUrl, request, response, "LGNEMCD006" , redirectUrl );	// 
			return false;
			
		} else {
			// loginDto 있을 때 ( 권한 문제 or 승인 문제 ... ) 
			
			// loginDto로 변경
			LoginDto loginDto = ( LoginDto ) principal;
			
			if ( loginDto.getMngrYn() != null && loginDto.getMngrYn().equals("Y") ) {
				// LGNEMCD011 : 관리자 계정은 마이페이지에 접근할 수 없습니다!
				CommonUtils.goToLogin( fullUrl, request, response, "LGNEMCD011", redirectUrl );
				return false;
			}
			
		}
		
		System.out.println( "AuthPreInterceptor : prehandle() " + fullUrl );
			
		return HandlerInterceptor.super.preHandle( request, response, handler );
	}
	
}
