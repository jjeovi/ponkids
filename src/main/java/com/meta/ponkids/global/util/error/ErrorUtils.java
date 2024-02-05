package com.meta.ponkids.global.util.error;

import org.springframework.util.StringUtils;

public class ErrorUtils {


    public static String getErrorMessage( String errCd ) {
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
            } else if ( errCd.equals( "E7" ) ) {
                resultMsg = "접근할 수 있는 권한이 없습니다. 관리자 계정으로 로그인해주세요.";
            } else if ( errCd.equals( "E8" ) ) {
                resultMsg = "관리자의 승인이 필요합니다.";
            }
        }
        
        return resultMsg;
    }

}
