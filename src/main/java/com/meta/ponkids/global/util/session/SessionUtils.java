package com.meta.ponkids.global.util.session;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;

import com.meta.ponkids.domain.system.login.dto.LoginDto;

public class SessionUtils {

    public static String getClientId() {
    	
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	LoginDto loginDto = ( LoginDto ) principal;
    	
        return loginDto.getUserId();
    }
}
