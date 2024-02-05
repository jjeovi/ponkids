package com.meta.ponkids.global.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.meta.ponkids.global.util.error.ErrorUtils;

@Component
public class ErrorInterceptor implements HandlerInterceptor {
    
    @Override
    public void postHandle( HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView ) throws Exception {
    	
    	HttpSession session = request.getSession();

        String errCd = ( String ) session.getAttribute( "errCd" );
        if ( StringUtils.hasText( errCd ) ) {
            session.removeAttribute( "errCd" );
            
            String errMsg = ErrorUtils.getErrorMessage( errCd );
            request.setAttribute( "errMsg", errMsg );
        }
        
    }
    
}
