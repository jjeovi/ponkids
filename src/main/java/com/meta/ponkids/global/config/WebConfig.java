package com.meta.ponkids.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.meta.ponkids.global.config.interceptor.AuthInterceptor;
import com.meta.ponkids.global.config.interceptor.MenuAdmInterceptor;
import com.meta.ponkids.global.config.interceptor.MenuInterceptor;
import com.meta.ponkids.global.config.interceptor.MessageInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    
    private final AuthInterceptor authInterceptor;
    private final MenuAdmInterceptor menuAdmInterceptor;
    private final MenuInterceptor menuInterceptor;
    private final MessageInterceptor messageInterceptor;
    
    @Override
    public void addInterceptors( InterceptorRegistry registry ) {
        
        // 권한 체크 로직 설정
        // 1. 체크 하는 로직은 /admin/ 하위 path 만 검사
        // 2. 로그인 페이지는 검사하지 않음.
        // 추후 로그인페이지에 IP 접근이 필요하다고 할 때 추가 예정
        
        // 권한 처리 및 세션 처리 Interceptor (preHandle)
        registry.addInterceptor( authInterceptor )
                .addPathPatterns( "/admin/**" )             			                // 1. 체크 하는 로직은 /admin/ 하위 path 만 검사
                .excludePathPatterns( 	"/**/*.js", 	"/**/*.css",
                        "/**/*.png",    "/**/*.jpg", 	"/**/*.map",
                        "/**/*.gif",    "/**/*.woff2",  "/**/*.svg" )                   // 제외 목록 : 정적 컨텐츠
                .excludePathPatterns( "/admLogin" , "/admin/readyLoginAjax");        	// 2. 로그인 페이지는 검사하지 않음.
        
        // admin : 관리자 부분
        // admin : 관리자 부분
        // ==========================================================
        // ==========================================================
        // ==========================================================
        
        // 메뉴 mcd 값 추가하는 Interceptor ( postHandle : 메서드 호출 이후 )
        registry.addInterceptor( menuAdmInterceptor )
                .addPathPatterns( 		"/admin/**" )             		// 1. 체크 하는 로직은 /admin/ 하위 path 만 검사
                .excludePathPatterns( 	"/**/*Ajax" )        			// 제외 목록 : Ajax 통신 
                .excludePathPatterns( 	"/**/*.js", 	"/**/*.css",
                        "/**/*.png",    "/**/*.jpg", 	"/**/*.map",
                        "/**/*.gif",    "/**/*.woff2",  "/**/*.svg" )   // 제외 목록 : 정적 컨텐츠
                .excludePathPatterns( 	"/admLogin" )        			// 제외 목록 : 로그인 페이지
        		.excludePathPatterns( 	"/getImage" );					// 제외 목록 : 첨부파일 조회시
        
        //  메세지성코드 감지해서 메시지 추출 (postHandle : 메서드 호출 이후 )
        registry.addInterceptor( messageInterceptor )
                .addPathPatterns( 		"/admin/**" )             		// 1. 체크 하는 로직은 /admin/ 하위 path 만 검사
                .excludePathPatterns( 	"/**/*Ajax" )        			// 제외 목록 : Ajax 통신 
                .excludePathPatterns( 	"/**/*.js", 	"/**/*.css",
                        "/**/*.png",    "/**/*.jpg", 	"/**/*.map",
                        "/**/*.gif",    "/**/*.woff2",  "/**/*.svg" )   // 제외 목록 : 정적 컨텐츠
                .excludePathPatterns( 	"/admLogin" )        			// 제외 목록 : 로그인 페이지
        		.excludePathPatterns( 	"/getImage" );					// 제외 목록 : 첨부파일 조회시
        
        // pon : 사용자 부분
        // pon : 사용자 부분
        // ==========================================================
        // ==========================================================
        // ==========================================================
        
        // 메뉴 mcd 값 추가하는 Interceptor ( postHandle : 메서드 호출 이후 ) -> 사용자 
        registry.addInterceptor( menuInterceptor )
        		.addPathPatterns( "/**" )             					// 1. 체크 하는 로직은 /하위 전체 
        		.excludePathPatterns( 	"/**/*Ajax" )        			// 제외 목록 : Ajax 통신 
                .excludePathPatterns( 	"/**/*.js", 	"/**/*.css",
                        "/**/*.png",    "/**/*.jpg", 	"/**/*.map",
                        "/**/*.gif",    "/**/*.woff2",  "/**/*.svg" )   // 제외 목록 : 정적 컨텐츠
                .excludePathPatterns( 	"/admLogin" )        			// 제외 목록 : 로그인 페이지
                .excludePathPatterns( 	"/admin/**" )        			// 제외 목록 : 로그인 페이지
        		.excludePathPatterns( 	"/getImage" );					// 제외 목록 : 첨부파일 조회시
        
        //  메세지성코드 감지해서 메시지 추출 (postHandle : 메서드 호출 이후 )
        registry.addInterceptor( messageInterceptor )
                .addPathPatterns( "/**" )             					// 1. 체크 하는 로직은 /하위 전체
                .excludePathPatterns( 	"/**/*Ajax" )        			// 제외 목록 : Ajax 통신
                .excludePathPatterns( 	"/**/*.js", 	"/**/*.css",
                        "/**/*.png",    "/**/*.jpg", 	"/**/*.map",
                        "/**/*.gif",    "/**/*.woff2",  "/**/*.svg" )   // 제외 목록 : 정적 컨텐츠
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