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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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


	// S : 회원이름 / 전화번호 / 관리자여부 3가지로 계정 찾기 (1건만) -- 아이디 찾기
	public LoginDto findByUserNmAndTelNoAndMngrYn(LoginDto loginDto) {

		// 쿼리 구현
		User target = loginRepository.findTop1ByUserNmAndTelNoAndMngrYn(loginDto.getUserNm(), loginDto.getTelNo(), "N").orElse(null);

		// null 일때
		if ( target == null ) {
			return null;
		}

		// entity 룰 dto 로 변환하여 return
		LoginDto targetDto = new LoginDto();
		return targetDto.toDto(target);
	}
	// E : 회원이름 / 전화번호 / 관리자여부 3가지로 계정 찾기 (1건만) -- 아이디 찾기

	// S : 회원이름 / 이메일 / 관리자여부 3가지로 계정 찾기(1건만)  -- 비밀번호 찾기
	public LoginDto findByUserNmAndUserIdAndMngrYn(LoginDto loginDto) {

		// 쿼리 구현
		User target = loginRepository.findByUserNmAndUserIdAndMngrYn(loginDto.getUserNm(), loginDto.getUserId(), "N").orElse(null);

		// null 일때
		if (target == null){
			return null;
		}

		// entity 룰 dto 로 변환하여 return
		LoginDto targetDto = new LoginDto();
		return targetDto.toDto(target);

	}
	// E : 회원이름 / 이메일 / 관리자여부 3가지로 계정 찾기(1건만)  -- 비밀번호 찾기


	/* S : 이메일로 인증번호 보내기 */
	private JavaMailSender javaMailSender;

	public void sendEmail(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);

		javaMailSender.send(message);
	}
	/* E : 이메일로 인증번호 보내기 */
}
