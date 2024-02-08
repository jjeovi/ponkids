package com.meta.ponkids.domain.oauth2.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.meta.ponkids.domain.system.login.dto.LoginDto;
import com.meta.ponkids.domain.user.dto.UserModDto;
import com.meta.ponkids.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SnsLoginCallbackController {
	

	private final UserService userService;
	
	@RequestMapping("/login/oauth2/code/{snsType}")
	public void snsLogin(HttpServletRequest request,
    						@ModelAttribute LoginDto loginDto,
    						@PathVariable String snsType,
    						HttpSession session,
                            Model model ) {
		
		System.out.println("tttt");
		System.out.println("snsType : " + snsType);
	}
	
	
	// 계정 통합 페이지로 redirect
	@RequestMapping("/login/oauth2/{snsType}/userIntegrated")
	public String userIntegrated(HttpServletRequest request,
			@ModelAttribute String userId,
			@PathVariable String snsType,
			RedirectAttributes rttr,
			HttpSession session,
			Model model ) {
		
		String oAuthStatus = (String) session.getAttribute("oAuthStatus");
		if( !StringUtils.hasText( oAuthStatus ) && !oAuthStatus.equals("loginIntegrated") ) {
			session.setAttribute( "errCd", "AAPEMCD001" );	// 비정상적인 접근입니다.
			return "/";
		}
		
		UserModDto targetDto = userService.findByUserId( userId );
		
		rttr.addFlashAttribute("user", 		targetDto );
		rttr.addFlashAttribute("snsType", 	snsType );
		rttr.addFlashAttribute("infoCd", 	"UILIMCD001" );	// 이미 가입되어있는 계정이 존재합니다. 해당 SNS로그인을 사용하시려면 기존 계정의 비밀번호를 입력 후 계정통합을 한 뒤, 재로그인 해주세요.
		
		return "redirect:/?lgStatus=userIntegrated";		// 메인페이지 로드 후 계정통합 레이어 호출
		
	}
	
	// 회원가입 for SNS redirect
	@RequestMapping("/login/oauth2/{snsType}/joinForSns")
	public String joinForSns(HttpServletRequest request,
			@ModelAttribute String userId,
			@PathVariable String snsType,
			RedirectAttributes rttr,
			HttpSession session,
			Model model ) {
		
		String oAuthStatus = (String) session.getAttribute("oAuthStatus");
		if( !StringUtils.hasText( oAuthStatus ) && !oAuthStatus.equals("joinForSns")  ) {
			session.setAttribute( "errCd", "AAPEMCD001" );	// 비정상적인 접근입니다.
			return "/";
		}
		
		
		rttr.addFlashAttribute("userId", 		userId );
		rttr.addFlashAttribute("snsType", 	snsType );
		rttr.addFlashAttribute("infoCd", 	"JFSIMCD001" );	// 최초 로그인 시 회원 정보 등록이 필요합니다. 회원정보 등록 후 재로그인 해주세요. (추후 일반로그인으로도 로그인이 가능합니다.)
		
		return "redirect:/?lgStatus=joinForSns";			// 메인페이지 로드 후 계정통합 레이어 호출
		
	}

}
