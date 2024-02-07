package com.meta.ponkids.domain.oauth2.service;

import java.io.IOException;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.meta.ponkids.domain.system.login.repository.LoginRepository;
import com.meta.ponkids.domain.user.entity.User;
import com.meta.ponkids.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService{
	
	private final UserRepository userRepository;
	
    @Value( "${key.default.user}" )
    private String TYPE_USER;
	
	@SuppressWarnings("unchecked")
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest ) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		
		HttpServletResponse response = ( ( ServletRequestAttributes ) RequestContextHolder.currentRequestAttributes() ).getResponse();
		
		  HttpSession session = request.getSession();
//		- SNS 로그인을 했을때
//		1. sns의 email 을 추출하여 해당 이메일로 가입된 계정이 있는지를 조회 
//		 1-1. 같은 이메일로 가입된 계정(A)이 있을 경우
//		    -> 해당 SNS 로 연동이 되어있는지 확인 
//		        => 1-1-(1) (최초 1회)연동이 되어 있지 않을 경우 : 기존 계정 A 의 비밀번호를 확인받은 뒤, 연동된다는 안내와 함께 연동작업 수행
//		           => "이미 가입되어있는 계정이 존재합니다. 해당 SNS로그인을 사용하시려면 기존 계정의 비밀번호를 입력 후 계정통합을 한 뒤, 재로그인 해주세요."
//		        => 1-1-(2) 연동이 되어 있는 경우 : 로그인 처리 수행
//
//		 1-2. 같은 이메일로 가입된 계정이 없는 경우
//		    -> 1-2-(1)(최초 1회)) 회원가입 진행 : 회원가입시 이메일은 disabled 처리하여 수정못하게 하고, 비밀번호와 나머지 입력은 기존 회원가입과 동일하게 수행. 
//		           => "최초 로그인 시 회원 정보 등록이 필요합니다. 회원정보 등록 후 재로그인 해주세요. (추후 일반로그인으로도 로그인이 가능합니다.)"
//		    -> 1-2-(2) 최초 1회 로그인이 아닐시 : 1-1-(2) 로직으로 이동
	
		
//		String provider = userRequest.getClientRegistration().getClientId();
//		String providerId = oAuth2User.getAttribute("sub");
//		String username = provider + "_" + providerId; //중복이 발생하지 않도록 provider와 providerId를 조합
		
		
		
//		1. sns의 email 을 추출하여 해당 이메일로 가입된 계정이 있는지를 조회 
		String userId = "";
		String snsType = "";
		
		// 구글용 email 추출
		String googleEmail = oAuth2User.getAttribute("email");		// 구글용
		
		
		// 카카오용 email 추출
		LinkedHashMap<String, String> kakaoAccount = (LinkedHashMap<String, String>) oAuth2User.getAttribute("kakao_account");
		String kakaoEmail = "";
		if ( kakaoAccount != null ) {
			kakaoEmail = (String) kakaoAccount.get("email");		// 카카오용
			
		}
		
		if ( StringUtils.hasText( googleEmail ) ) {
			
			userId = googleEmail;
			snsType = "google";
			
		} else if ( StringUtils.hasText( kakaoEmail )) {
			
			userId = kakaoEmail;
			snsType = "kakao";
		}
		
		
		boolean isExistUser = userRepository.existsByUserId( userId );
		
		if ( isExistUser ) {
//			 1-1. 같은 이메일로 가입된 계정(A)이 있을 경우
			User user = userRepository.findByUserId( userId );
			boolean snsCntnYn = false;
			
//			-> 해당 SNS 로 연동이 되어있는지 확인
			switch ( snsType )  {
			
				case "google" : 
					// 연계sns 구글 에 값이 있다면, 연계여부 는 Y 로 setting
					if( StringUtils.hasText( user.getCntnSnsGoogle() ) )	snsCntnYn = true;
					
					break;
					
				case "kakao" : 
					// 연계 sns 카카오 에 값이 있다면, 연계여부 는 Y 로 setting
					if( StringUtils.hasText( user.getCntnSnsKakao() ) )		snsCntnYn = true;
					
					break;
				default : 
					 throw new IllegalArgumentException("Invalid of Sns Type " + snsType);
			}
			
			if ( !snsCntnYn ) {
//				=> (1) (최초 1회)연동이 되어 있지 않을 경우 : 기존 계정 A 의 비밀번호를 확인받은 뒤, 연동된다는 안내와 함께 연동작업 수행
//				  => "이미 가입되어있는 계정이 존재합니다. 해당 SNS로그인을 사용하시려면 기존 계정의 비밀번호를 입력 후 계정통합을 한 뒤, 재로그인 해주세요."
				
				try {
					session.setAttribute("oAuthStatus", "loginIntegrated");										// 검증용 key
					response.sendRedirect( "login/oauth2/ " + snsType + "/userIntegrated?userId=" + userId );	// email 값 전달
				} catch (IOException e) {
					e.printStackTrace();
				} 
			} else {
//				=> (2) 연동이 되어 있는 경우 : 강제 로그인 처리 후  수행
				  
				  
				  // 강제 로그인 처리 수행. 
				  Authentication auth = new UsernamePasswordAuthenticationToken(user,null);
				  SecurityContextHolder.getContext().setAuthentication(auth);
				  
				  
			        // 로그인 후 이동 URL ( 지정되어 있을 때만 )
			        String returnUrlAfterLogin = ( String ) session.getAttribute( "returnUrlAfterLogin" );
			        String loginType = ( String ) session.getAttribute( "loginType" );
			        
			        session.removeAttribute( "returnUrlAfterLogin" );
			        session.removeAttribute( "returnUrlAfterLoginFail" );
			        session.removeAttribute( "loginType" );
				  
			        try {
						response.sendRedirect( returnUrlAfterLogin );	// email 값 전달
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				
			}
			
			
			
			
		} else  {
//			 1-2. 같은 이메일로 가입된 계정이 없는 경우
			
		}
		
		
		
		return super.loadUser(userRequest);
		
	}

}
