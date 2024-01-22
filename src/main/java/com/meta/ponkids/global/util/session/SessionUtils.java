package com.meta.ponkids.global.util.session;

import com.meta.ponkids.domain.adm.system.login.dto.LoginDto;
import org.springframework.security.core.context.SecurityContextHolder;

public class SessionUtils {
    
    public static String getClientId() {
        
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LoginDto loginDto = ( LoginDto ) principal;
        
        return loginDto.getUserId();
    }
    
}
