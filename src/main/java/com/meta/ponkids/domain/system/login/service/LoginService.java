package com.meta.ponkids.domain.system.login.service;

import com.meta.ponkids.domain.system.login.dto.LoginDto;
import com.meta.ponkids.domain.system.login.repository.LoginRepository;
import com.meta.ponkids.domain.user.entity.User;

import com.meta.ponkids.global.util.message.MessageUtils;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RequiredArgsConstructor
@Service
public class LoginService implements UserDetailsService {

    private final LoginRepository loginRepository;
    
    @Override
    public UserDetails loadUserByUsername( String userId ) throws UsernameNotFoundException {
        
        // 로그인 시 저장할 객체
        LoginDto loginDto = loginRepository.getLogin( userId );
        
        if ( loginDto != null ) {
            return loginDto;
        }
		
        throw new UsernameNotFoundException( "User not exist with name :" + userId );
    }
    
}
