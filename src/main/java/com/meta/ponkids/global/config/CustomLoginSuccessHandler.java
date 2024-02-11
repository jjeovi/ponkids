package com.meta.ponkids.global.config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
	
    
    @Value( "${key.default.admin}" )
    private String TYPE_ADMIN;
	
    
    @Value( "${key.default.user}" )
    private String TYPE_USER;
    
    
    public void onAuthenticationSuccess( HttpServletRequest request, HttpServletResponse response, Authentication authentication ) throws IOException, ServletException {
        log.info( "login Success!!!!!! : " + authentication.getName() );
        
        // 로그인 성공 후 로직
        
        HttpSession session = request.getSession();
        // 로그인 후 이동 URL ( 지정되어 있을 때만 )
        String returnUrlAfterLogin = ( String ) session.getAttribute( "returnUrlAfterLogin" );
        String loginType = ( String ) session.getAttribute( "loginType" );
        
        session.removeAttribute( "returnUrlAfterLogin" );
        session.removeAttribute( "returnUrlAfterLoginFail" );
        session.removeAttribute( "loginType" );
        
        if ( returnUrlAfterLogin != null ) {
            // (1) returnUrlAfterLogin 세션값이 있으면 returnUrlAfterLogin 로 redirect
            response.sendRedirect( returnUrlAfterLogin );
        } else {
            // (2) returnUrlAfterLogin 세션값이 없으면 로그인유형에 따라 (1)사용자 (2)관리자 default page  로 redirect
        	if( loginType != null ) {
        		if ( loginType.equals(TYPE_ADMIN)) {
        			response.sendRedirect( "/admin/home" );
        		} else {
        			response.sendRedirect( "/" );
        		}
        		
        	} else {
        		response.sendRedirect( "/?loginType=notFound" );
        	}
        }
        
    }
    
    
}
