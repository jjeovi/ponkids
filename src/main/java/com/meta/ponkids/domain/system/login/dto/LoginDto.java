package com.meta.ponkids.domain.system.login.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor
@Data
public class LoginDto implements UserDetails {
    
    private Long userSn;
    
    private String userId;              // 사용자 아이디
    
    private String password;            // 비밀번호
    
    private String userNm;              // 사용자 이름
    
    private Long roleSn;
    
    private String roleNm;
    
    private String roleDc;
    
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
    
    private String confmerId;           // 승인자 ID
    
    private String confmerIp;           // 승인자 IP
    
    private LocalDateTime confmDt;      // 승인 일시
    
    private String cntnSns;             // 연계
    
    private LocalDateTime lastLoginDt;  // 마지막 로그인한 일시
    
    private String registerIp;          // 등록자 IP
    
    private String updusrId;            // 수정자 ID
    
    private String updusrIp;            // 수정자 IP
    
    private String returnUrlAfterLogin;	// 로그인 후 이동할 url 
    private String returnUrlAfterLoginFail;	// 로그인 후 이동할 url 
    private String loginType;			// 로그인 유형 구분할 타입 변수 ( 사용자 : pon , 관리자 : adm ) 
    
    @ColumnDefault( "N" )                             // del_yn 컬럼에 공통으로 추가
    @Column( insertable = false, updatable = false )  // del_yn 컬럼에 공통으로 추가 (등록 시, 수정 시 해당컬럼 신경쓰지 않음.)
    private String delYn;                           // 삭제 여부
    
    
    @QueryProjection
    public LoginDto( Long userSn, String userId, String password, Long roleSn, String roleNm, String roleDc, String userNm, String gender, String brdtDate, String telNo, String resideArea, String zip, String rdnmAdr, String detailAdr, Long atchFileSn, String mngrYn, String mngrConfmYn, String confmerId, String confmerIp, LocalDateTime confmDt, String cntnSns, LocalDateTime lastLoginDt ) {
        this.userSn = userSn;
        this.userId = userId;
        this.password = password;
        this.roleSn = roleSn;
        this.roleNm = roleNm;
        this.roleDc = roleDc;
        this.userNm = userNm;
        this.gender = gender;
        this.brdtDate = brdtDate;
        this.telNo = telNo;
        this.resideArea = resideArea;
        this.zip = zip;
        this.rdnmAdr = rdnmAdr;
        this.detailAdr = detailAdr;
        this.atchFileSn = atchFileSn;
        this.mngrYn = mngrYn;
        this.mngrConfmYn = mngrConfmYn;
        this.confmerId = confmerId;
        this.confmerIp = confmerIp;
        this.confmDt = confmDt;
        this.cntnSns = cntnSns;
        this.lastLoginDt = lastLoginDt;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        if ( roleNm == null ) {
        	return null;
        } else {
	        auth.add( new SimpleGrantedAuthority( roleNm ) );
	        return auth;
        }
    }
    
    @Override
    public String getUsername() {
        return this.userId;
    }
    
    @Override
    public String getPassword() {
        return this.password;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
}
