package com.meta.ponkids.domain.oauth2.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService{
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest ) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		
		String provider = userRequest.getClientRegistration().getClientId();
		String providerId = oAuth2User.getAttribute("sub");
		String username = provider + "_" + providerId; //중복이 발생하지 않도록 provider와 providerId를 조합
		String email = oAuth2User.getAttribute("email");
		String role = "ROLE_USER"; //일반 유저
		
		System.out.println("oAuth2User = " + oAuth2User.getAttributes());
		return super.loadUser(userRequest);
		
	}

}
