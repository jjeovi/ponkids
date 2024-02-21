package com.meta.ponkids.domain.system.login.controller;

import java.io.IOException;
import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.meta.ponkids.domain.system.login.service.LoginService;
import com.sun.xml.messaging.saaj.soap.impl.ElementImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.meta.ponkids.domain.system.login.dto.LoginDto;

/* S: smtp 이메일 보내기. javamail 라이브러리 사용*/
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
/* E: smtp 이메일 보내기. javamail 라이브러리 사용*/
import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
public class LoginoutController {

	private final LoginService loginService;
	private final JavaMailSender javaMailSender; // SMTP 사용하여 메일 전송하기

	private final HttpSession session; // HttpSession을 멤버 변수로 선언

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
				userId = maskString(userId, Math.max(0, atIndex - 2), atIndex - 1, '*');
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


	//	S : 이메일 확인 및 인증번호 전송
	@ResponseBody
	@PostMapping("/findUseremail")
	public Map<String, Object> findUseremail( @ModelAttribute LoginDto loginDto) {

		//메소드가 실행되고 나서 클라이언트에게 반환될 데이터를 담기 위한 자료구조를 생성하는 부분
		Map<String, Object> result = new HashMap<String, Object>();

		// 여기에서 실제 로직을 구현하고 결과를 반환합니다.

		// 1. 이름/ 이메일 / (관리자여부) 로 계정을 찾음
		// 비밀번호 찾기 기능에서 실제로 사용자 정보를 데이터베이스에서 조회하는 부분
		LoginDto targetDto = loginService.findByUserNmAndUserIdAndMngrYn(loginDto);

		if ( targetDto != null ) {
			// 1-1. 해당 입력값으로 찾은 계정이 있을 떄
			result.put("flag", "S");
			result.put("msg", "인증번호가 발송되었습니다. ");

			//이메일 전송
//			sendEmail(targetDto.getUserId(),generateRandomAuthNumber());
			try{
				sendEmail(targetDto.getUserId(),generateRandomAuthNumber(), session);
			}catch(MessagingException e){
				e.printStackTrace();
				result.put("flag", "E");
				result.put("msg","이메일 전송 중 오류가 발생했습니다.");
				return result;
			}

		} else if ( targetDto == null ) {
			// 1-2. 해당 입력값으로 찾은 계정이 없을 때
			result.put("flag", "E");
			result.put("msg", "입력하신 정보와 일치하는 계정이 존재하지 않습니다. 다시 시도해주세요. ");
		}

		return result;
	}
	//	E : 이메일 확인 및 인증번호 전송



	// 이메일 전송 메서드
	private void sendEmail(String to, String authNumber , HttpSession session)throws MessagingException{
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

		helper.setTo(to);
		helper.setSubject("이메일 인증번호");
		helper.setText("인증번호: " + authNumber, true);

		javaMailSender.send(mimeMessage);

		// 세션에 인증번호 저장
		session.setAttribute("authCode", authNumber);
		// TODO : 시작 시간 체크
	}

	// 랜덤한 인증번호 생성 메서드
	private String generateRandomAuthNumber(){
		// TODO 여기에 랜덤 인증번호 생성 로직 추가 (조건 : 랜덤한 6자리 숫자)
		return "123456";
	}

	/* S: 비밀번호 찾기 - 인증번호 검증 */
	@ResponseBody
	@PostMapping("/findUserpw")
	public Map<String,Object> findUserpw(@RequestParam String authNumber, HttpSession session){
		Map<String, Object> result = new HashMap<>();
		// 세션에서 저장된 인증번호 가져오기
		String storedAuthCode = (String) session.getAttribute("authCode");

		if (storedAuthCode != null && storedAuthCode.equals(authNumber)) {
			// 인증번호 일치
			result.put("flag", "S");
			result.put("msg", "인증에 성공했습니다.");
			// 검증 시간 체크
			// TODO : 시작 시간 , 검증시간 비교하여 3분 이내인지 확인
//			if () {
//				// 3분 이내
//			result.put("flag", "S");
//			result.put("msg", "인증에 성공했습니다.");
			// 인증 성공 후 필요한 작업 수행
//			} else {
//				// 3분 넘어가면..
				// 시간초과 메시지..
//			result.put("flag", "E");
//			result.put("msg", "시간이 초과되었습니다.");
//			}

		} else {
			// 인증번호 불일치
			result.put("flag", "E");
			result.put("msg", "인증에 실패했습니다. 다시 시도해주세요.");
		}

		return result;
	}
	/* E: 비밀번호 찾기 - 인증번호 검증 */

	/* S : 비밀번호 변경 */
	@PostMapping("/changePassword")
	public ResponseEntity<String> changePassword(@RequestParam String userId,@RequestParam String newPassword){
		// 비밀번호 변경 로직 수행
		LoginDto loginDto = new LoginDto();
		loginDto.setUserId(userId);
		boolean success = loginService.changePassword(loginDto, newPassword);
		if(success){
			return ResponseEntity.ok("비밀번호 변경 되었습니다.");
		}else{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to change password");
		}

	}
	/* E : 비밀번호 변경 */


}
