package com.meta.ponkids.global.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@Component
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {
    
    
    @Value( "${key.admin.auth}" )
    private String AUTH;
    // 로그인 시 체크변수 pon 로 고정

//    @Override
//    public void onAuthenticationSuccess( HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication ) throws IOException, ServletException {
//        log.info("1---------------------------");
//        log.info("-1--------------------------");
//        log.info("--1-------------------------");
//        log.info("---1------------------------");
//        log.info("----1-----------------------");
//        AuthenticationSuccessHandler.super.onAuthenticationSuccess( request, response, chain, authentication );
//    }
    
    @Override
    public void onAuthenticationFailure( HttpServletRequest request, HttpServletResponse response, AuthenticationException exception ) throws IOException, ServletException {
        log.info( "2---------------------------" );
        log.info( "-2--------------------------" );
        log.info( "--2-------------------------" );
        log.info( "---2------------------------" );
        log.info( "----2-----------------------" );
        
        String errCd = "";
        if ( exception instanceof BadCredentialsException ) {
            errCd = "E1";
//            errorMsg = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해 주세요.";
        } else if ( exception instanceof InternalAuthenticationServiceException ) {
            errCd = "E2";
//            errorMsg = "내부적으로 발생한 시스템 문제로 인해 요청을 처리할 수 없습니다. 관리자에게 문의하세요.";
        } else if ( exception instanceof UsernameNotFoundException ) {
            errCd = "E3";
//            errorMsg = "계정이 존재하지 않습니다. 회원가입 진행 후 로그인 해주세요.";
        } else if ( exception instanceof AuthenticationCredentialsNotFoundException ) {
            errCd = "E4";
//            errorMsg = "인증 요청이 거부되었습니다. 관리자에게 문의하세요.";
        } else {
            errCd = "E5";
//            errorMsg = "알 수 없는 이유로 로그인에 실패하였습니다 관리자에게 문의하세요.";
        }
        
        HttpSession session = request.getSession();
        session.setAttribute( "errCd", errCd );
        
        String returnUrlAfterLoginFail = ( String ) session.getAttribute( "returnUrlAfterLoginFail" );
        
        session.removeAttribute( "returnUrlAfterLogin" );
        session.removeAttribute( "returnUrlAfterLoginFail" );
        session.removeAttribute( "loginType" );
        
        response.sendRedirect( returnUrlAfterLoginFail ); // 인증이 성공한 후에는 root로 이동
        
    }
}
