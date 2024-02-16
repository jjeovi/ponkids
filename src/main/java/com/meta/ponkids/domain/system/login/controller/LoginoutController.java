package com.meta.ponkids.domain.system.login.controller;

import java.io.IOException;
import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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


	// id/pw 찾기

	// 아이디 찾기
	@PostMapping("/findUsername")
	public ResponseEntity<String> findUsername(@RequestBody LoginDto request) {
		// 여기에서 실제 아이디를 찾는 로직을 구현하고 결과를 반환합니다.
		if ("홍길동".equals(request.getUsername()) && "01011112222".equals(request.getTelNo())) {
			// 아이디를 찾았다고 가정
			return ResponseEntity.ok("아이디는 hongSSI 입니다.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("아이디를 찾을 수 없습니다. 입력 정보를 확인해주세요.");
		}
	}


	/* 코드 리뷰
	https://political-moth-bb9.notion.site/LoginoutController-68136941b0f0494d916acf0a50359c1c?pvs=4
	*/
	// 비밀번호 변경 뷰
//	@RequestMapping(value = "/pwUpdate", method = RequestMethod.GET)
//	public String pwUpdate() throws Exception{
//		return "/member/pwUpdate";
//	}
//
//	// 현재 비밀번호 유효성 검사
//	@RequestMapping(value = "/pwCheck",method = RequestMethod.POST)
//	@ResponseBody
//	public int pwCheck(MemberVO memberVO) throws Exception{
//		// 1. 회원의 실제 비밀번호를 데이터베이스에서 가져옵니다.
//		String password = memberService.pwCheck(memberVO.getMemberId());
//		// 2. 만약 회원이 존재하지 않거나 입력된 비밀번호가 일치하지 않으면 0을 반환합니다.
//		if(memberVO == null || !BCrypt.checkpw(memberVO.getMemberPw(),password)){
//			return 0;
//		}
//		return 1;
//	}
//	// 비밀번호 변경
//	@RequestMapping(value="/pwUpdate" , method=RequestMethod.POST)
//	public String pwUpdate(String memberId,String memberPw1,RedirectAttributes rttr,HttpSession session)throws Exception{
//		// 1. 입력된 비밀번호를 BCrypt를 사용하여 해시화합니다
//		String hashedPw = BCrypt.hashpw(memberPw1, BCrypt.gensalt());
//		// 2. 회원의 아이디와 새로운 해시화된 비밀번호를 사용하여 데이터베이스를 업데이트합니다.
//		memberService.pwUpdate(memberId, hashedPw);
//		// 3. 현재 세션을 무효화하여 로그아웃을 수행합니다.
//		session.invalidate();
//		// 4. 리다이렉트 시에 메시지를 Flash 속성에 추가하여 다음 페이지로 전달합니다.
//		rttr.addFlashAttribute("msg", "정보 수정이 완료되었습니다. 다시 로그인해주세요.");
//		// 5. 로그인 화면으로 리다이렉트합니다.
//		return "redirect:/member/loginView";
//	}



}
