package com.meta.ponkids;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync    // 비동기 기능 활성화 : 메일 발송시 비동기처리
@EnableCaching
@EnableJpaAuditing
@SpringBootApplication( exclude = SecurityAutoConfiguration.class )
public class PonkidsApplication {
	
    @Value("${developer.motto}")
    private String developerMotto;

    public static void main( String[] args ) {
        SpringApplication.run( PonkidsApplication.class, args );
    }
    
    @PostConstruct
    private void start() {
        
        
        String rootPath = System.getProperty( "user.dir" );
        
        System.out.println("rootPath :" + rootPath );
        System.out.println("rootPath :" + rootPath );
        System.out.println("developer's motto == " + developerMotto);
        System.out.println("developer's motto == " + developerMotto);
        System.out.println("developer's motto == " + developerMotto);
        System.out.println("developer's motto == " + developerMotto);
    }
    
}
