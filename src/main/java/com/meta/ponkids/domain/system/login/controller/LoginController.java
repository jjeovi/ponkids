package com.meta.ponkids.domain.system.login.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    
    // 로그인 시 체크변수 pon 로 고정
    @Value( "${key.admin.auth}" )
    private String AUTH;
    
    @GetMapping( "/admLogin" )
    public String admLogin( @RequestParam( "auth" ) String auth,
                            HttpServletRequest request,
                            HttpSession session,
                            Model model ) {
        
        // admin 접근시 경로 : /admin/login?auth=pon
        if ( !StringUtils.hasText( auth ) ) {
            // 권한체크 실패
            return "/error/401";
        }
        
        String errCd = ( String ) session.getAttribute( "errCd" );
        if ( StringUtils.hasText( errCd ) ) {
            session.removeAttribute( "errCd" );
            
            String errMsg = getErrorMessage( errCd );
            model.addAttribute( "errMsg", errMsg );
        }
        
        model.addAttribute( "auth", auth );
        model.addAttribute( "successCode", AUTH );
        return "/admin/login/login";
        
    }
    
    @GetMapping( "/admLogout" )
    public String admLogout( @RequestParam( "auth" ) String auth,
                             HttpServletRequest request,
                             HttpSession session,
                             Model model ) {
        
        // admin 접근시 경로 : /admin/login?auth=pon
        if ( !StringUtils.hasText( auth ) ) {
            // 권한체크 실패
            return "/error/401";
        }
        
        String errCd = ( String ) session.getAttribute( "errCd" );
        if ( StringUtils.hasText( errCd ) ) {
//            session.removeAttribute( "errCd" );
            
            String errMsg = getErrorMessage( errCd );
            model.addAttribute( "errMsg", errMsg );
        }
        
        model.addAttribute( "auth", auth );
        model.addAttribute( "successCode", AUTH );
        return "/admin/login/login";
        
    }
    
    
    private String getErrorMessage( String errCd ) {
        String resultMsg = "";
        
        if ( StringUtils.hasText( errCd ) ) {
            if ( errCd.equals( "E1" ) ) {
                resultMsg = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해 주세요.";
            } else if ( errCd.equals( "E2" ) ) {
                resultMsg = "내부적으로 발생한 시스템 문제로 인해 요청을 처리할 수 없습니다. 관리자에게 문의하세요.";
            } else if ( errCd.equals( "E3" ) ) {
                resultMsg = "계정이 존재하지 않습니다. 회원가입 진행 후 로그인 해주세요.";
            } else if ( errCd.equals( "E4" ) ) {
                resultMsg = "인증 요청이 거부되었습니다. 관리자에게 문의하세요.";
            } else if ( errCd.equals( "E5" ) ) {
                resultMsg = "알 수 없는 이유로 로그인에 실패하였습니다 관리자에게 문의하세요.";
            } else if ( errCd.equals( "E6" ) ) {
                resultMsg = "로그인이 필요합니다.";
            }
        }
        
        return resultMsg;
    }
}
