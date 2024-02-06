package com.meta.ponkids.domain.oauth2.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.meta.ponkids.domain.system.login.dto.LoginDto;

@Controller
public class SnsLoginCallbackController {
	
	
	
	@RequestMapping("/login/oauth2/code/kakao")
	public void snsLogin(HttpServletRequest request,
    						@ModelAttribute LoginDto loginDto,
    						HttpSession session,
                            Model model ) {
		
		System.out.println("tttt");
	}

}
