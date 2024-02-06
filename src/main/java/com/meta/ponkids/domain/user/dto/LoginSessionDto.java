package com.meta.ponkids.domain.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class LoginSessionDto {
    
    private Long userSn;
    
    private String userId;              // 사용자 아이디
    
    private String userNm;              // 사용자 이름
    
    private String gender;              // 성별
    
    private String brdtDate;            // 생년월일
    
    private String telNo;               // 연락처
    
    private String resideArea;          // 거주지역
    
    private String zip;                 // 우편번호
    
    private String rdnmAdr;             // 도로명 주소
    
    private String detailAdr;           // 상세 주소
    
    private Long atchFileSn;         // 첨부파일 일련번호
    
    private String mngrYn;              // 관리자 여부
    
    private String mngrConfmYn;         // 관리자 승인 여부
    
    private String cntnSnsKakao;        // 연계 SNS Kakao
    
    private String cntnSnsGoogle;       // 연계 SNS google
    
    private String cntnSnsNaver;        // 연계 SNS naver
    
    private String cntnSnsFacebook;     // 연계 SNS facebook
    
    private String cntnSnsApple;        // 연계 SNS apple
    
    private LocalDateTime lastLoginDt;  // 마지막 로그인한 일시
    
}
