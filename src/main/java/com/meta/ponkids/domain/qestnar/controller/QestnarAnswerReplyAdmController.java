package com.meta.ponkids.domain.qestnar.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.meta.ponkids.domain.qestnar.dto.QestnarAnswerReplySaveDto;
import com.meta.ponkids.domain.qestnar.service.QestnarAnswerReplyService;
import com.meta.ponkids.global.util.session.SessionUtils;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class QestnarAnswerReplyAdmController {
	
	private final QestnarAnswerReplyService qestnarAnswerReplyService;
	
	private final static String BASIC_VIEW_PATH = "admin/qestnarAnswerReply";
	private final static String BASIC_PATH = "/" + BASIC_VIEW_PATH;	// BASIC_VIEW_PATH 는  앞의 "/" 를 제거해야 함.

	// 등록 insert Ajax
    @PostMapping( BASIC_PATH + "/{mcd}/insert" )
    public String insert(	@ModelAttribute QestnarAnswerReplySaveDto saveDto , 
    										@PathVariable String mcd,
    										HttpServletRequest request,
    										Model model ) {
        
    	// 저장 후 이동할 url setting
    	String moveUrl = request.getHeader("referer");
    	
        // userSn setting
        saveDto.setUserSn( SessionUtils.getAuthUserSn() );

        // save
        try {
        	qestnarAnswerReplyService.save( saveDto, request );
        }catch ( Exception e ) {
            
            // 메시지 출력 및 url 이동 처리
            model.addAttribute( "resultMsg", "등록 중 오류가 발생했습니다. " + e.getMessage() + "\n다시 시도해주세요." );
            model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
            
            return "common/alert";
            
        }
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 등록되었습니다." );
        model.addAttribute( "moveUrl", moveUrl );
        
        return "common/alert";
    }
	

}
