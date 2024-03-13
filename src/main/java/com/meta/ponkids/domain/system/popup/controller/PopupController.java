package com.meta.ponkids.domain.system.popup.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.meta.ponkids.domain.system.popup.service.PopupService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PopupController {
	
	private final PopupService popupService;
	
	private final static String BASIC_VIEW_PATH = "popup";
	private final static String BASIC_PATH = "/" + BASIC_VIEW_PATH;	// BASIC_VIEW_PATH 는  앞의 "/" 를 제거해야 함.
	
	@GetMapping( value = { 
			BASIC_PATH + "/detail" } )
	public String detailOrModify (
			@RequestParam( required = true ) Long pk,	// 타입 체크
			HttpServletRequest request,
			Model model ) {
		
		// S : 필요한 객체 setting
		
		// target object 조회
		model.addAttribute( "targetDto", popupService.findById( pk ) );
		
		// E : 필요한 객체 setting
		
		// 기본 경로 setting
		model.addAttribute( "basicPath", BASIC_PATH );
		
		String urlPath = request.getServletPath();
		String remainPath = "";
		if ( urlPath.split( BASIC_PATH )[ 1 ].endsWith( "/detail" ) ) remainPath = "detail";
		if ( urlPath.split( BASIC_PATH )[ 1 ].endsWith( "/modify" ) ) remainPath = "modify";
		
		return "pon/layout/popup"; 
	}
	

}
