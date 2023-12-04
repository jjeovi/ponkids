package com.meta.ponkids.domain.system.login.service;

import com.meta.ponkids.domain.system.login.dto.LoginDto;
import com.meta.ponkids.domain.system.login.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService implements UserDetailsService {
    private final LoginRepository loginRepository;
    @Override
    public UserDetails loadUserByUsername( String userId ) throws UsernameNotFoundException {

        // 로그인 시 저장할 객체
        LoginDto loginDto = loginRepository.getLogin( userId );
        
        if(loginDto != null){
            return loginDto;
        }
        throw new UsernameNotFoundException("User not exist with name :" + userId);
    }
}
