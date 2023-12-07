package com.meta.ponkids.global.config;

import com.meta.ponkids.global.config.interceptor.AuthInterceptor;
import com.meta.ponkids.global.config.interceptor.MenuInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    
    private final AuthInterceptor authInterceptor;
    private final MenuInterceptor menuInterceptor;
    
    @Override
    public void addInterceptors( InterceptorRegistry registry ) {
        
        // 권한 체크 로직 설정
        // 1. 체크 하는 로직은 /admin/ 하위 path 만 검사
        // 2. 로그인 페이지는 검사하지 않음.
        // 추후 로그인페이지에 IP 접근이 필요하다고 할 때 추가 예정
        
        // 권한 처리 및 세션 처리 Interceptor (preHandle)
        registry.addInterceptor( authInterceptor ).
                addPathPatterns( "/admin/**" ).             // 1. 체크 하는 로직은 /admin/ 하위 path 만 검사
                excludePathPatterns( "/admLogin" );     	// 2. 로그인 페이지는 검사하지 않음.
        
        // 메뉴 mcd 값 추가하는 Interceptor (postHandle)
        registry.addInterceptor( menuInterceptor ).
                addPathPatterns( "/admin/**" ).             // 1. 체크 하는 로직은 /admin/ 하위 path 만 검사
                excludePathPatterns( "/admLogin" );      	// 2. 로그인 페이지는 검사하지 않음.
    }
    
    @Override
    public void addResourceHandlers( final ResourceHandlerRegistry registry ) {
        // templates 도 classpath 로 설정 ( /static/~ , /templates/~ 를 모두 정적으로 read 할 수 있다.
    	
    	
//    	registry.addResourceHandler( "/summernoteImage/**" )
//    	.addResourceLocations( "file:///C:/summernote_image/" );
    	
    	registry.addResourceHandler( "/**" )
    	.addResourceLocations( "classpath:/templates/", "classpath:/static/" );
    	
    }
    
}
