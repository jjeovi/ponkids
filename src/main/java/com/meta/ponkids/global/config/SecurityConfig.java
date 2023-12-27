package com.meta.ponkids.global.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpSession;

@SuppressWarnings( "deprecation" )
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    
    @Autowired
    private CustomLoginSuccessHandler customLoginSuccessHandler;
    @Autowired
    private CustomLoginFailureHandler customLoginFailureHandler;
    
    // PasswordEncoder interface 의 구현체가 BCryptPasswordEncoder 임을 수동 빈 등록을 통해서 명시한다.
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


//    @Autowired
//    public void configureGlobal( AuthenticationManagerBuilder auth ) throws Exception {
//
//        auth.jdbcAuthentication()
//                .dataSource( dataSource )
//                .passwordEncoder( passwordEncoder() )                                // password 암호화
//                .usersByUsernameQuery(
//                        "SELECT user_id as username"	+
//                        "     , password"           	+
//                        "     , '1' as enabled "    	+
//                        "  FROM tb_user "          		+
//                        " WHERE del_yn  = 'N'"			+
//                        "   AND mngr_yn = 'Y'"			+
//                        "   AND user_id = ?")
//                .authoritiesByUsernameQuery(
//                        "SELECT tu.user_id as username\n"   +
//                        "     , tr.role_nm as authority "   +
//                        "  FROM tb_user tu "                +
//                        "  LEFT JOIN tb_user_role tur "     +
//                        "    ON tu.user_sn = tur.user_sn "  +
//                        "  LEFT JOIN TB_ROLE tr "           +
//                        "    ON TUR.role_sn = tr.role_sn "  +
//                        " WHERE tu.user_id = ?" );
//    }
    
    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        
        // 모든 접근에 대한 권한을 허용.
        // 인증에 대한 부분은 스프링 시큐리티를 사용하지 않고,
        // Interceptor (AuthInterceptor)에서 진행하기로 함.
        http.
                authorizeRequests( ( authorizeHttpRequests ) -> authorizeHttpRequests.
                        requestMatchers( new AntPathRequestMatcher( "/**" ) ).permitAll() )
                .formLogin()
                .loginPage( "/admLogin" )                        // 사용자 정의 로그인 페이지
//    	        .defaultSuccessUrl("/home11")							// 로그인 성공 후 이동 페이지
                .failureHandler( customLoginFailureHandler )
                .loginProcessingUrl( "/admin/login" )                    // 로그인 Form Action Url
                .successHandler( customLoginSuccessHandler )            // 로그인 성공 후 핸들러
//                .successHandler( customLoginSuccessHandler )    //  로그인 성공시 handle
//                .permitAll()
//                .and()
//                .logout()
                .permitAll();
        
        // 여기서부터 로그아웃 API 내용~!
        http.logout()
                .logoutUrl( "/admLogout" )   // 로그아웃 처리 URL (= form action url)
                .logoutSuccessUrl( "/admLogin?auth=pon" ) // 로그아웃 성공 후 targetUrl,
                // logoutSuccessHandler 가 있다면 효과 없으므로 주석처리.
                .addLogoutHandler( ( request, response, authentication ) -> {
                    // 사실 굳이 내가 세션 무효화하지 않아도 됨.
                    // LogoutFilter가 내부적으로 해줌.
                    HttpSession session = request.getSession();
                    if ( session != null ) {
                        session.invalidate();
                    }
                } )  // 로그아웃 핸들러 추가
                .logoutSuccessHandler( ( request, response, authentication ) -> {
                    response.sendRedirect( "/admLogin?auth=pon" );
                } ); // 로그아웃 성공 핸들러

//        http.
//                authorizeRequests( ( authorizeHttpRequests ) -> authorizeHttpRequests.
//                        requestMatchers( new AntPathRequestMatcher( "/**" ) ).permitAll() )
//                .formLogin()
//                .loginPage("/login")   						// 사용자 정의 로그인 페이지
//                .defaultSuccessUrl("/home123")							// 로그인 성공 후 이동 페이지
//    	        .failureUrl("/login.html?error=true")	    		// 로그인 실패 후 이동 페이지
//    	        .failureHandler(customLoginFailureHandler)				// 로그인 실패 후 핸들러
//    	        .usernameParameter("username")						// 아이디 파라미터명 설정
//    	        .passwordParameter("password")						// 패스워드 파라미터명 설정
//    	        .loginProcessingUrl("/admin/user/list")						// 로그인 Form Action Url
//    	        .successHandler(customLoginSuccessHandler)			// 로그인 성공 후 핸들러
//                .loginPage( "/admLogin" )
//                .defaultSuccessUrl("/dashboard")
//                .successHandler( customLoginSuccessHandler )    //  로그인 성공시 handle
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
        
        
        // 스프링 시큐리티가 항상 세션 생성 (https://cornarong.tistory.com/81)
        http.sessionManagement()
                .sessionCreationPolicy( SessionCreationPolicy.ALWAYS );
    }
    
}
