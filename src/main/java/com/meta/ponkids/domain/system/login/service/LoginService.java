package com.meta.ponkids.domain.system.login.service;

import com.meta.ponkids.domain.system.login.dto.LoginDto;
import com.meta.ponkids.domain.system.login.repository.LoginRepository;
import com.meta.ponkids.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
        
        if ( loginDto != null ) {
            return loginDto;
        }
        throw new UsernameNotFoundException( "User not exist with name :" + userId );
    }
    
    
    
    public Map<String, Object> userLogin( LoginDto loginDto, HttpServletRequest request ) {
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	
    	HttpSession session = request.getSession();
    	
    	User user = loginRepository.findByUserIdAndPassword( loginDto.getUserId(), loginDto.getPassword() ).orElse( null ) ;
    	
    	
    	if ( user != null ) {
    		// 1. 로그인 성공
    		// -> 세션에 로그인 객체 저장
    		
            // 결과코드 및 결과메시지 추가
	        result.put("flag", "S");
//	        result.put("msg", "로그인에 성공하였습니다.");
	        
	        if (session == null || !request.isRequestedSessionIdValid()) {
	            System.out.println("세션이 무효화 상태입니다.");
	        } else {
	        	session.invalidate();
	        }
	        
	        
	        session.setAttribute( "loginDto", loginDto );	// -> 세션에 로그인 객체 저장
	        
    		
    	} else {
    		
    		User user2 = loginRepository.findByUserId( loginDto.getUserId() ).orElse( null ) ;
    		
    		if ( user2 != null ) {
    			// 2. 계정(이메일)은 존재하지만 비밀번호가 맞지 않을 때
    	        
    	        // 결과코드 및 결과메시지 추가
    	        result.put("flag", "E");
    	        result.put("msg", "비밀번호를 다시 확인해주세요.");
    			
    			
    		} else {
    			// 3. 계정 조차 없을 경우
    	        
    	        // 결과코드 및 결과메시지 추가
    	        result.put("flag", "S");
    	        result.put("msg", "존재하지 않는 이메일입니다.");
    			
    		}
    		
    	}
    	
    	// 1. 계정 조차 없을 때
    	
    	
    	// 2. 비밀번호가 틀렸을 때
    	
    	
    	
    	
    	return result;
    }
}
