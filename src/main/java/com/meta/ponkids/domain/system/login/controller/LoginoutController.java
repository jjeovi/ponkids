package com.meta.ponkids.domain.system.login.controller;

import java.io.IOException;
import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.meta.ponkids.domain.system.login.service.LoginService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.meta.ponkids.domain.system.login.dto.LoginDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginoutController {

	private final LoginService loginService;


    @Value( "${key.default.user}" )
    private String TYPE_USER;

    @ResponseBody
    @PostMapping( "/readyLoginAjax" )
    public Map<String, Object> readyLogin( 	HttpServletRequest request,
    						@ModelAttribute LoginDto loginDto,
    						HttpSession session,
                            Model model ) {
    	Map<String, Object> result = new HashMap<String, Object>();

    	session.setAttribute("loginType", TYPE_USER );
    	session.setAttribute("returnUrlAfterLogin", loginDto.getReturnUrlAfterLogin() );
    	session.setAttribute("returnUrlAfterLoginFail", loginDto.getReturnUrlAfterLoginFail() );
    	result.put("flag", "S");
//    	
//    	// 비밀번호 암호화
//    	loginDto.setPassword( passwordEncoder.encode( loginDto.getPassword() ) );

//        return loginService.userLogin( loginDto, request );
        return result;

    }

    @RequestMapping( "/logout" )
    public String logout( 	HttpServletRequest request,
							HttpServletResponse response,
							HttpSession session,
							Model model ) throws IOException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String returnUrlAfterLogout = request.getParameter("returnUrl");

//		String returnUrlAfterLogout = ( String ) session.getAttribute( "returnUrlAfterLogout" );

    	if ( auth != null ) {
    		new SecurityContextLogoutHandler().logout( request, response, auth );
    	}

    	if ( returnUrlAfterLogout == null || returnUrlAfterLogout.equals("") ) {
    		return "redirect:/";
    	} else {
    		return "redirect:" + returnUrlAfterLogout;
    	}

    }

	// 아이디 찾기
	@ResponseBody
	@PostMapping("/findUsername")
	public Map<String, Object> findUsername( HttpServletRequest request,
											@ModelAttribute LoginDto loginDto,
											HttpSession session,
											Model model
	) {
		//메소드가 실행되고 나서 클라이언트에게 반환될 데이터를 담기 위한 자료구조를 생성하는 부분
		Map<String, Object> result = new HashMap<String, Object>();

		// 여기에서 실제 아이디를 찾는 로직을 구현하고 결과를 반환합니다.
		// TODO 1. 1. 이름/전화번호 / (관리자여부) 로 계정을 찾음
		// 아이디 찾기 기능에서 실제로 사용자 정보를 데이터베이스에서 조회하는 부분
		LoginDto targetDto = loginService.findByUserNmAndTelNoAndMngrYn(loginDto);


		if ( targetDto != null ) {
			// 1-1. 해당 입력값으로 찾은 계정이 있을 떄
			result.put("flag", "S");
			String userId  = targetDto.getUserId(); // DTO 로 받은 userid 데이터-> userId

			// TODO masking 처리 @ 기준 왼쪽 4개 문자 마스킹 처리
			// 마스킹 처리 - '@' 문자를 기준으로 왼쪽 4개 문자를 마스킹
			int atIndex = userId.indexOf('@');  // indexOf 메서드를 이용해서 '@' 문자가 처음으로 나타나는 위치의 인덱스를 반환 -> atIndex

			if (atIndex != -1) { // '@' 문자가 존재하는 경우
				userId = maskString(userId, Math.max(0, atIndex - 4), atIndex - 1, '*');
				result.put("maskingUserId", userId );
			}else{
				result.put("msg","이메일정보가 올바르지 않습니다. 관리자에게 문의해주세요.");
			}
		} else if ( targetDto == null ) {
			// 1-2. 해당 입력값으로 찾은 계정이 없을 때
			result.put("flag", "E");
			result.put("msg", "입력하신 정보와 일치하는 계정이 존재하지 않습니다. 다시 시도해주세요. ");
		}

		return result;
	}
	// 문자열 일부를 마스킹 처리하는 함수 -> maskString
	private String maskString(String str, int start, int end, char maskChar) {
		if (str == null || start < 0 || end >= str.length()) {
			return str;
		}

		char[] chars = str.toCharArray();
		for (int i = start; i <= end; i++) {
			chars[i] = maskChar;
		}

		return new String(chars);
	}



}
