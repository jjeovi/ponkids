package com.meta.ponkids.global.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    
    @Autowired
    private DataSource dataSource;
    @Autowired
    private CustomLoginSuccessHandler customLoginSuccessHandler;
    
    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        
        // 모든 접근에 대한 권한을 허용.
        // 인증에 대한 부분은 스프링 시큐리티를 사용하지 않고,
        // Interceptor (AuthInterceptor)에서 진행하기로 함.
        http.
                authorizeRequests( ( authorizeHttpRequests ) -> authorizeHttpRequests.
                        requestMatchers( new AntPathRequestMatcher( "/**" ) ).permitAll() )
                .formLogin()
                .loginPage( "/admin/login" )
                .successHandler( customLoginSuccessHandler )    //  로그인 성공시 handle
                .permitAll()
                .and()
                .logout()
                .permitAll();
        
        
        // 스프링 시큐리티가 항상 세션 생성 (https://cornarong.tistory.com/81)
        http.sessionManagement()
                .sessionCreationPolicy( SessionCreationPolicy.ALWAYS );
    }
    
    
    @Autowired
    public void configureGlobal( AuthenticationManagerBuilder auth ) throws Exception {
        
        auth.jdbcAuthentication()
                .dataSource( dataSource )
                .passwordEncoder( passwordEncoder() )                                // password 암호화
                .usersByUsernameQuery(
                        "SELECT user_id as username" +
                        "     , password"            +
                        "     , '1' as enabled "     +
                        "  FROM tb_user "            +
                        "where user_id = ?" )
                .authoritiesByUsernameQuery(
                        "SELECT tu.user_id as username\n"   +
                        "     , tr.role_nm as authority "   +
                        "  FROM tb_user tu "                +
                        "  LEFT JOIN tb_user_role tur "     +
                        "    ON tu.user_sn = tur.user_sn "  +
                        "  LEFT JOIN TB_ROLE tr "           +
                        "    ON TUR.role_sn = tr.role_sn "  +
                        " WHERE tu.user_id = ?" );
    }
    
    
    // PasswordEncoder interface 의 구현체가 BCryptPasswordEncoder 임을 수동 빈 등록을 통해서 명시한다.
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
