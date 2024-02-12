package com.meta.ponkids.domain.oauth2.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.meta.ponkids.domain.system.cmmnCd.dto.CmmnCdDetailModDto;
import com.meta.ponkids.domain.system.cmmnCd.service.CmmnCdDetailService;
import com.meta.ponkids.domain.system.login.dto.LoginDto;
import com.meta.ponkids.domain.user.dto.UserModDto;
import com.meta.ponkids.domain.user.service.UserService;
import com.meta.ponkids.global.util.message.MessageUtils;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SnsLoginCallbackController {
    
    @Value( "${key.admin.auth}" )
    private String ADMIN_AUTH;
    
    @Value( "${key.default.user}" )
    private String TYPE_USER;
    
    @Value( "${key.default.admin}" )
    private String TYPE_ADMIN;
    
    private final UserService userService;
    private final CmmnCdDetailService cmmnCdDetailService;
    
    // 계정 통합 페이지로 redirect
    @RequestMapping( "/login/oauth2/{snsType}/userIntegrated" )
    public String userIntegrated( HttpServletRequest request,
                                  @RequestParam( "userId" ) String userId,
                                  @PathVariable String snsType,
                                  RedirectAttributes rttr,
                                  HttpSession session,
                                  HttpServletResponse response,
                                  Model model ) {
        
        String oAuthStatus = ( String ) session.getAttribute( "oAuthStatus" );
        session.removeAttribute("oAuthStatus");
        if ( !StringUtils.hasText( oAuthStatus ) && !oAuthStatus.equals( "loginIntegrated" ) ) {
            session.setAttribute( "errCd", "AAPEMCD001" );    // 비정상적인 접근입니다.
            return "/";
        }
        
        String returnUrlAfterLogin = ( String ) session.getAttribute( "returnUrlAfterLogin" );
        String loginType = ( String ) session.getAttribute( "loginType" );
        
        session.removeAttribute( "returnUrlAfterLogin" );
        session.removeAttribute( "returnUrlAfterLoginFail" );
        session.removeAttribute( "loginType" );
        
        // 로그아웃 처리
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ( auth != null ) {
            new SecurityContextLogoutHandler().logout( request, response, auth );
        }
        
        UserModDto targetDto = userService.findByUserId( userId );
        
        rttr.addFlashAttribute( "integratedUser", targetDto );
        rttr.addFlashAttribute( "snsType", snsType );
        // 공통코드 상세 조회
        // 공통상세코드 1건 조회
        CmmnCdDetailModDto snsDto = cmmnCdDetailService.findTop1ByCdNmAndCdDetailVal1( "LOGIN_SNS_CD", snsType );
        if ( snsDto != null ) {
            rttr.addFlashAttribute( "snsDto", snsDto );
        }
        
        rttr.addFlashAttribute( "infoCd", "UILIMCD001" );    // 이미 가입되어있는 계정이 존재합니다. 해당 SNS로그인을 사용하시려면 기존 계정의 비밀번호를 입력 후 계정통합을 한 뒤, 재로그인 해주세요.
        
        
        if (returnUrlAfterLogin == null || returnUrlAfterLogin.equals("") ) {
        	
        	if( loginType != null ) {
        		if ( loginType.equals(TYPE_ADMIN)) {
        			returnUrlAfterLogin = "/admLogin?auth=" + ADMIN_AUTH;
        		} else {
        			returnUrlAfterLogin = "/" ;
        		}
        	} else {
        		returnUrlAfterLogin = "/" ;
        	}
        }
        	
        rttr.addFlashAttribute( "lgStatus", "userIntegrated" );   	
        return "redirect:" + returnUrlAfterLogin ;        // 메인페이지 로드 후 계정통합 레이어 호출
        
    }
    
    // 회원가입 for SNS redirect
    @RequestMapping( "/login/oauth2/{snsType}/joinForSns" )
    public String joinForSns( HttpServletRequest request,
                              @RequestParam( "userId" ) String userId,
                              @PathVariable String snsType,
                              RedirectAttributes rttr,
                              HttpSession session,
                              HttpServletResponse response,
                              Model model ) {
        
        String oAuthStatus = ( String ) session.getAttribute( "oAuthStatus" );
        session.removeAttribute("oAuthStatus");
        if ( !StringUtils.hasText( oAuthStatus ) && !oAuthStatus.equals( "joinForSns" ) ) {
            session.setAttribute( "errCd", "AAPEMCD001" );    // 비정상적인 접근입니다.
            return "/";
        }
        
        String returnUrlAfterLogin = ( String ) session.getAttribute( "returnUrlAfterLogin" );
        String loginType = ( String ) session.getAttribute( "loginType" );
        
        session.removeAttribute( "returnUrlAfterLogin" );
        session.removeAttribute( "returnUrlAfterLoginFail" );
        session.removeAttribute( "loginType" );
        
        // 로그아웃 처리
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ( auth != null ) {
            new SecurityContextLogoutHandler().logout( request, response, auth );
        }
        
        rttr.addFlashAttribute( "snsUserId", userId );
        rttr.addFlashAttribute( "snsType", snsType );
        // 공통코드 상세 조회
        // 공통상세코드 1건 조회
        CmmnCdDetailModDto snsDto = cmmnCdDetailService.findTop1ByCdNmAndCdDetailVal1( "LOGIN_SNS_CD", snsType );
        if ( snsDto != null ) {
            rttr.addFlashAttribute( "snsDto", snsDto );
        }
        rttr.addFlashAttribute( "lgStatus", "joinForSns" );
        rttr.addFlashAttribute( "infoCd", "JFSIMCD001" );    // 최초 로그인 시 회원 정보 등록이 필요합니다. 회원정보 등록 후 재로그인 해주세요. (추후 일반로그인으로도 로그인이 가능합니다.)
        
        rttr.addFlashAttribute( "loginSnsTypeList", cmmnCdDetailService.getList( "LOGIN_SNS_CD" ) );    // 로그인 sns 코드 목록
        
        if (returnUrlAfterLogin == null || returnUrlAfterLogin.equals("") ) {
        	
        	if( loginType != null ) {
        		if ( loginType.equals(TYPE_ADMIN)) {
        			returnUrlAfterLogin = "/admLogin?auth=" + ADMIN_AUTH;
        		} else {
        			returnUrlAfterLogin = "/" ;
        		}
        	} else {
        		returnUrlAfterLogin = "/" ;
        	}
        }
        
        rttr.addFlashAttribute( "lgStatus", "joinForSns" );
        return "redirect:" + returnUrlAfterLogin ;        // 메인페이지 로드 후 계정통합 레이어 호출
    }
    
    
    // 계정통합 ready ajax
    @ResponseBody
    @PostMapping( "/readyUserIntegratedAjax" )
    public Map<String, Object> readyUserIntegratedAjax( 	HttpServletRequest request,
                                              @ModelAttribute LoginDto loginDto,
                                              HttpSession session,
                                              Model model ) {
        Map<String, Object> result = new HashMap<String, Object>();
        
        session.setAttribute("loginType", TYPE_USER );
        session.setAttribute("returnUrlAfterLogin", loginDto.getReturnUrlAfterLogin() );
        session.setAttribute("returnUrlAfterLoginFail", loginDto.getReturnUrlAfterLoginFail() );
        
        session.setAttribute("oAuthStatus", "userIntegratedCallbackAjax" );
        result.put("flag", "S");
        
        return result;
        
    }
    
    
    // 계정 통합 callback
    @ResponseBody
    @RequestMapping( "/{snsType}/userIntegratedCallbackAjax" )
    public Map<String, Object> userIntegratedCallbackAjax( @PathVariable String snsType,
                                                       HttpSession session,
                                                       Model model) {
        Map<String, Object> result = new HashMap<String, Object>();
        
        String oAuthStatus = ( String ) session.getAttribute( "oAuthStatus" );
        session.removeAttribute("oAuthStatus");
        if ( !StringUtils.hasText( oAuthStatus ) && !oAuthStatus.equals( "userIntegratedCallbackAjax" ) ) {
            result.put( "flag", "E" );
            result.put( "msg", MessageUtils.getMessageFromCmmnCd( "ERR_MSG_CD", "AAPEMCD001" ) );   // 비정상적인 접근입니다.
            return result;
        }
        
        // 로그인 되었는지 여부 확인
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if ( principal != null && principal.getClass() == LoginDto.class  ) {
            
            // auth 의 계정에 sns 연동 작업 update
            LoginDto loginDto = (LoginDto) principal;
            try {
                userService.updateSnsCntn( loginDto.getUserSn() , snsType );
            } catch ( IOException e ) {
                // TODO : exception
                
            }
            
            result.put( "flag", "S" );
            result.put( "msg", MessageUtils.getMessageFromCmmnCd( "INFO_MSG_CD", "UILIMCD002" ) );  // 계정통합이 완료되었습니다.
        } else {
            
            result.put( "flag", "E" );
            result.put( "msg", MessageUtils.getMessageFromCmmnCd( "ERR_MSG_CD", "LGNEMCD010" ) );   // 비밀번호가 맞지 않습니다.
        }
        
        return result;
    }
    
}


