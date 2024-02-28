package com.meta.ponkids.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.meta.ponkids.global.config.interceptor.auth.AuthAdmInterceptor;
import com.meta.ponkids.global.config.interceptor.auth.AuthPreInterceptor;
import com.meta.ponkids.global.config.interceptor.auth.AuthPostInterceptor;
import com.meta.ponkids.global.config.interceptor.menu.MenuAdmInterceptor;
import com.meta.ponkids.global.config.interceptor.menu.MenuInterceptor;
import com.meta.ponkids.global.config.interceptor.message.MessageInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    

    private final AuthAdmInterceptor	authAdmInterceptor;
    private final AuthPostInterceptor	authPostInterceptor;
    private final AuthPreInterceptor	authPreInterceptor;
    private final MenuAdmInterceptor	menuAdmInterceptor;
    private final MenuInterceptor		menuInterceptor;
    private final MessageInterceptor	messageInterceptor;
    
    @Override
    public void addInterceptors( InterceptorRegistry registry ) {
        
        // admin : 관리자 부분
        // admin : 관리자 부분
        // ==========================================================
        // ==========================================================
        // ==========================================================
        
	        // 권한 체크 로직 설정
	        // 1. 체크 하는 로직은 /admin/ 하위 path 만 검사
	        // 2. 로그인 페이지는 검사하지 않음.
	        // 추후 로그인페이지에 IP 접근이 필요하다고 할 때 추가 예정
	        
	        // 권한 처리 및 세션 처리 Interceptor (preHandle)
	        registry.addInterceptor( authAdmInterceptor )
	                .addPathPatterns( "/admin/**" )             			                // 1. 체크 하는 로직은 /admin/ 하위 path 만 검사
	                .excludePathPatterns( 	"/**/*.js", 	"/**/*.css",
	                        "/**/*.png",    "/**/*.jpg", 	"/**/*.map",
	                        "/**/*.gif",    "/**/*.woff2",  "/**/*.svg",
	                        "/**/*.ico")                   									// 제외 목록 : 정적 컨텐츠
	                .excludePathPatterns( "/admLogin" , "/admin/readyLoginAjax");        	// 2. 로그인 페이지는 검사하지 않음.
	        
	        // 메뉴 mcd 값 추가하는 Interceptor ( postHandle : 메서드 호출 이후 )
	        registry.addInterceptor( menuAdmInterceptor )
	                .addPathPatterns( 		"/admin/**" )             		// 1. 체크 하는 로직은 /admin/ 하위 path 만 검사
	                .excludePathPatterns( 	"/**/*Ajax" )        			// 제외 목록 : Ajax 통신 
	                .excludePathPatterns( 	"/**/*.js", 	"/**/*.css",
	                        "/**/*.png",    "/**/*.jpg", 	"/**/*.map",
	                        "/**/*.gif",    "/**/*.woff2",  "/**/*.svg",
	                        "/**/*.ico")   									// 제외 목록 : 정적 컨텐츠
	                .excludePathPatterns( 	"/admLogin" )        			// 제외 목록 : 로그인 페이지
	        		.excludePathPatterns( 	"/getImage" );					// 제외 목록 : 첨부파일 조회시
	        
	        //  메세지성코드 감지해서 메시지 추출 (postHandle : 메서드 호출 이후 )
	        registry.addInterceptor( messageInterceptor )
	                .addPathPatterns( 		"/admin/**" )             		// 1. 체크 하는 로직은 /admin/ 하위 path 만 검사
	                .excludePathPatterns( 	"/**/*Ajax" )        			// 제외 목록 : Ajax 통신 
	                .excludePathPatterns( 	"/**/*.js", 	"/**/*.css",
	                        "/**/*.png",    "/**/*.jpg", 	"/**/*.map",
	                        "/**/*.gif",    "/**/*.woff2",  "/**/*.svg",
	                        "/**/*.ico")   									// 제외 목록 : 정적 컨텐츠
	                .excludePathPatterns( 	"/admLogin" )        			// 제외 목록 : 로그인 페이지
	        		.excludePathPatterns( 	"/getImage" );					// 제외 목록 : 첨부파일 조회시
	        
        // pon : 사용자 부분
        // pon : 사용자 부분
        // ==========================================================
        // ==========================================================
        // ==========================================================
	        
	        // 권한 처리 및 세션 처리 Interceptor ( postHandle : 메서드 호출 이후 ) -> 사용자
	        // -> 사용자. 로그인 세션 체크하여 request 추가
	        registry.addInterceptor( authPreInterceptor )
	        .addPathPatterns( "/mypage/**" )             					// 1. 체크 하는 로직은 /하위 전체 
	        .excludePathPatterns( 	"/**/*Ajax" )        			// 제외 목록 : Ajax 통신 
	        .excludePathPatterns( 	"/**/*.js", 	"/**/*.css",
	        		"/**/*.png",    "/**/*.jpg", 	"/**/*.map",
	        		"/**/*.gif",    "/**/*.woff2",  "/**/*.svg",
	        		"/**/*.ico")   									// 제외 목록 : 정적 컨텐츠
	        .excludePathPatterns( 	"/admLogin" )        			// 제외 목록 : 로그인 페이지
	        .excludePathPatterns( 	"/admin/**" )        			// 제외 목록 : 로그인 페이지
	        .excludePathPatterns( 	"/getImage" );					// 제외 목록 : 첨부파일 조회시
	        
	        
	        registry.addInterceptor( authPostInterceptor )
	        .addPathPatterns( "/**" )             					// 1. 체크 하는 로직은 /하위 전체 
	        .excludePathPatterns( 	"/**/*Ajax" )        			// 제외 목록 : Ajax 통신 
	        .excludePathPatterns( 	"/**/*.js", 	"/**/*.css",
	        		"/**/*.png",    "/**/*.jpg", 	"/**/*.map",
	        		"/**/*.gif",    "/**/*.woff2",  "/**/*.svg",
	        		"/**/*.ico")   									// 제외 목록 : 정적 컨텐츠
	        .excludePathPatterns( 	"/admLogin" )        			// 제외 목록 : 로그인 페이지
	        .excludePathPatterns( 	"/admin/**" )        			// 제외 목록 : 로그인 페이지
	        .excludePathPatterns( 	"/getImage" );					// 제외 목록 : 첨부파일 조회시
        
	        // 메뉴 mcd 값 추가하는 Interceptor ( postHandle : 메서드 호출 이후 ) -> 사용자 
	        registry.addInterceptor( menuInterceptor )
	        		.addPathPatterns( "/**" )             					// 1. 체크 하는 로직은 /하위 전체 
	        		.excludePathPatterns( 	"/**/*Ajax" )        			// 제외 목록 : Ajax 통신 
	        		.excludePathPatterns( 	"/**/*.js", 	"/**/*.css",
	                        "/**/*.png",    "/**/*.jpg", 	"/**/*.map",
	                        "/**/*.gif",    "/**/*.woff2",  "/**/*.svg",
	                        "/**/*.ico")   									// 제외 목록 : 정적 컨텐츠
	                .excludePathPatterns( 	"/admLogin" )        			// 제외 목록 : 로그인 페이지
	                .excludePathPatterns( 	"/admin/**" )        			// 제외 목록 : 로그인 페이지
	        		.excludePathPatterns( 	"/getImage" );					// 제외 목록 : 첨부파일 조회시
	        
	        //  메세지성코드 감지해서 메시지 추출 (postHandle : 메서드 호출 이후 )
	        registry.addInterceptor( messageInterceptor )
	                .addPathPatterns( "/**" )             					// 1. 체크 하는 로직은 /하위 전체
	                .excludePathPatterns( 	"/**/*Ajax" )        			// 제외 목록 : Ajax 통신
	                .excludePathPatterns( 	"/**/*.js", 	"/**/*.css",
	                        "/**/*.png",    "/**/*.jpg", 	"/**/*.map",
	                        "/**/*.gif",    "/**/*.woff2",  "/**/*.svg",
	                        "/**/*.ico")   									// 제외 목록 : 정적 컨텐츠
	                .excludePathPatterns( 	"/admLogin" )        			// 제외 목록 : 로그인 페이지
	                .excludePathPatterns( 	"/admin/**" )        			// 제외 목록 : 로그인 페이지
	                .excludePathPatterns( 	"/getImage" );					// 제외 목록 : 첨부파일 조회시
    }
    
    @Override
    public void addResourceHandlers( final ResourceHandlerRegistry registry ) {
        // templates 도 classpath 로 설정 ( /static/~ , /templates/~ 를 모두 정적으로 read 할 수 있다.
        
        registry.addResourceHandler( "/summernoteImage/**" )
                .addResourceLocations( "file:///C:/summernote_image/" );
        
        registry.addResourceHandler( "/**" )
                .addResourceLocations( "classpath:/templates/", "classpath:/static/" );
//        registry.addResourceHandler( "/**" )
//        		.addResourceLocations( "classpath:/static/" );
        
    }
    
}