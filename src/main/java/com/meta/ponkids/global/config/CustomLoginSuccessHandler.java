package com.meta.ponkids.global.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
	
    
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
    public void onAuthenticationSuccess( HttpServletRequest request, HttpServletResponse response, Authentication authentication ) throws IOException, ServletException {
        log.info("2---------------------------");
        log.info("-2--------------------------");
        log.info("--2-------------------------");
        log.info("---2------------------------");
        log.info("----2-----------------------");
        System.out.println("authentication : " + authentication.getName());
//        response.sendRedirect("/"); // 인증이 성공한 후에는 root로 이동
        
    }
    
    
}
