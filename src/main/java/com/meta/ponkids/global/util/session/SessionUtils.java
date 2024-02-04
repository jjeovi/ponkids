package com.meta.ponkids.global.util.session;

import com.meta.ponkids.domain.system.login.dto.LoginDto;
import org.springframework.security.core.context.SecurityContextHolder;

public class SessionUtils {
    
    public static String getClientId() {
        
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if ( principal == null || principal.equals("anonymousUser") ) { 
        	return null;
        }
        
        LoginDto loginDto = ( LoginDto ) principal;
        
    	return loginDto.getUserId();
        
    }
    
}
