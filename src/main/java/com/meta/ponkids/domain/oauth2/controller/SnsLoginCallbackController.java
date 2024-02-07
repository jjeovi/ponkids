package com.meta.ponkids.domain.oauth2.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.meta.ponkids.domain.system.login.dto.LoginDto;
import com.meta.ponkids.domain.user.dto.UserModDto;
import com.meta.ponkids.domain.user.entity.User;
import com.meta.ponkids.domain.user.repository.UserRepository;
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
	
	
	@RequestMapping("/login/oauth2/{snsType}/userIntegrated")
	public void userIntegrated(HttpServletRequest request,
			@ModelAttribute String userId,
			@PathVariable String snsType,
			HttpSession session,
            Model model ) {
		
		UserModDto targetDto = userService.findByUserId( userId );
		
        model.addAttribute( "user", targetDto );
        model.addAttribute( "infoMsg", "" );
		
			
	
	System.out.println("tttt");
	System.out.println("snsType : " + snsType);
	}

}
