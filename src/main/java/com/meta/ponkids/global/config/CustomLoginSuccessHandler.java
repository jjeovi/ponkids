package com.meta.ponkids.global.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    public void onAuthenticationSuccess( HttpServletRequest request, HttpServletResponse response, Authentication authentication ) throws IOException, ServletException {
        log.info( "login Success!!!!!! : " + authentication.getName() );
        
        // 로그인 성공 후 로직
        
        HttpSession session = request.getSession();
        // 로그인 후 이동 URL ( 지정되어 있을 때만 )
        String loginAfterMoveUrl = ( String ) session.getAttribute( "loginAfterMoveUrl" );
        
        if ( loginAfterMoveUrl != null ) {
            // (1) loginAfterMoveUrl 세션값이 있으면 loginAfterMoveUrl 로 redirect
            session.removeAttribute( "loginAfterMoveUrl"  );;
            response.sendRedirect( loginAfterMoveUrl );
        } else {
            // (2) loginAfterMoveUrl 세션값이 없으면 / 로 redirect
            response.sendRedirect( "/admin/home" );
        }
        
    }
    
    
}
