package com.meta.ponkids.global.common;


import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler ) throws Exception {
        
        String requestUri = request.getRequestURI();
        
        System.out.println( "prehandle() " + requestUri );
        
        // auth check logic 검사.
        
        // 1. 현재 세션이 있는지 체크
        //   - 세션이 없다면 권한이 없음 . -> 로그인 창으로 return
        
        // 2. 현재 URI 가져옴. (정확히는 URL-path )
        // 2-1. URI 값이 해당 권한에 등록되어있는지 체크
        // 2-2. 등록되어있지 않은 URI라면 권한 없음
        return HandlerInterceptor.super.preHandle( request, response, handler );
    }
}
