package com.meta.ponkids;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableCaching
@EnableJpaAuditing
@SpringBootApplication( exclude = SecurityAutoConfiguration.class )
public class PonkidsApplication {
    
    public static void main( String[] args ) {
        SpringApplication.run( PonkidsApplication.class, args );
    }
    
}
