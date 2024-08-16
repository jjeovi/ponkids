package com.meta.ponkids.global.util.session;

import com.meta.ponkids.domain.system.login.dto.LoginDto;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

public class SessionUtils {
    
    public static String getUserId() {
    	
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ( auth == null ) {
        	return null;
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if ( principal == null || principal.equals("anonymousUser") ) { 
        	return null;
        }
        
        LoginDto loginDto = ( LoginDto ) principal;
        
    	return loginDto.getUserId();
        
    }
    
    
    
    public static LoginDto getAuthentication() {
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ( auth == null ) {
        	return null;
        }
         
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
    	if ( principal == null || principal.equals("anonymousUser") ) { 
    		return null;
    	}
    	
    	LoginDto loginDto = ( LoginDto ) principal;
    	
    	return loginDto;
    	
    }
    
    
    public static Long getAuthUserSn() {
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ( auth == null ) {
        	return null;
        }
    	
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
    	if ( principal == null || principal.equals("anonymousUser") ) { 
    		return null;
    	}
    	
    	LoginDto loginDto = ( LoginDto ) principal;
    	
    	return loginDto.getUserSn();
    	
    }
    
    
    
}
