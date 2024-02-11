package com.meta.ponkids.global.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.meta.ponkids.global.util.error.ErrorUtils;
import com.meta.ponkids.global.util.message.MessageUtils;

@Component
public class MessageInterceptor implements HandlerInterceptor {
    
    @Override
    public void postHandle( HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView ) throws Exception {
    	
    	HttpSession session = request.getSession();
    	
    	// 1. errCd 값으로 에러 메시지를 조회하여 model 에 추가
    	// 2. infoCd 값으로 안내성 메시지를 조회하여 model에 추가
    	
    	
    	// 1. errCd 값으로 에러 메시지를 조회하여 model 에 추가
    	// errCd
    	// 1. session 
    	// 2. model
    	// 2군데 모두 탐색하여 errCd 값을 찾고, 없으면 return

        String errCd = ( String ) session.getAttribute( "errCd" ) ;
        if ( !StringUtils.hasText( errCd ) && modelAndView != null ) errCd = ( String ) modelAndView.getModel().get( "errCd" );
        
        if ( StringUtils.hasText( errCd ) ) {
            session.removeAttribute( "errCd" );
            
            String errMsg = MessageUtils.getMessageFromCmmnCd( "ERR_MSG_CD", errCd );
            request.setAttribute( "errMsg", errMsg );
        }
        
        // 2. infoCd 값으로 안내성 메시지를 조회하여 model에 추가
        // infoCd
        // 1. session 
        // 2. model
        // 2군데 모두 탐색하여 infoCd 값을 찾고, 없으면 return
        
        String infoCd = ( String ) session.getAttribute( "infoCd" ) ;
        if ( !StringUtils.hasText( infoCd ) && modelAndView != null ) infoCd = ( String ) modelAndView.getModel().get( "infoCd" );
        
        if ( StringUtils.hasText( infoCd ) ) {
        	session.removeAttribute( "infoCd" );
        	
        	String infoMsg = MessageUtils.getMessageFromCmmnCd( "INFO_MSG_CD", infoCd );
        	request.setAttribute( "infoMsg", infoMsg );
        }
        
    }
    
}
