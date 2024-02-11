package com.meta.ponkids.domain.user.dto;

import com.meta.ponkids.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


/**
 * className      : UserModDto
 * author         : jjeoV
 * date           : 2023-11-19
 * description    : class of 회원 수정 Dto
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-11-19        jjeoV             최초 생성
 */
@NoArgsConstructor
@Data
public class UserModDto {
    
    @NotNull
    private Long userSn;            // 회원 일련번호
    
    @NotNull
    @Email
    private String userId;          // 아이디
    
    @NotNull
    private String password;        // 비밀번호
    
    @NotNull
    private String userNm;          // 사용자 이름
    
    @NotNull
    private String gender;          // 성별
    
    private String brdtDate;        // 생년월일
    
    @NotNull
    private String telNo;           // 연락처
    
    private String resideArea;      // 거주지역
    
    private String zip;             // 우편번호
    
    private String rdnmAdr;         // 도로명 주소
    
    private String detailAdr;       // 상세 주소
    
    private Long atchFileSn;        // 첨부파일 일련번호
    
    private Long atchFileSnOri;     // 첨부파일 일련번호
    
    @NotNull
    private String mngrYn;          // 관리자 여부
    
    @NotNull
    private String mngrConfmYn;     // 관리자 승인 여부
    
    private String confmerId;       // 승인자 ID
    
    private String confmerIp;       // 승인자 IP
    
    private LocalDateTime confmDt;  // 승인일시
    
    private String          snsKakaoCntnYn;     // SNS Kakao 연계 여부
    
    private LocalDateTime   snsKakaoCntnDt;     // SNS Kakao 연계 일시
    
    private String          snsGoogleCntnYn;    // SNS Google 연계 여부
    
    private LocalDateTime   snsGoogleCntnDt;    // SNS Google 연계 일시
    
    private String          snsNaverCntnYn;     // SNS Naver 연계 여부
    
    private LocalDateTime   snsNaverCntnDt;     // SNS Naver 연계 일시
    
    private String          snsFacebookCntnYn;  // SNS Facebook 연계 여부
    
    private LocalDateTime   snsFacebookCntnDt;  // SNS Facebook 연계 일시
    
    private String          snsAppleCntnYn;     // SNS Apple 연계 여부
    
    private LocalDateTime   snsAppleCntnDt;     // SNS Apple 연계 일시
    
    private LocalDateTime   lastLoginDt;  // 마지막 로그인한 일시
    
    private String updusrId;        // 수정자 ID
    
    private String updusrIp;        // 수정자 IP
    
    private String delYn;           // 삭제여부
    
    // builder 생성
    @Builder
    public UserModDto( Long userSn, String userId, String password, String userNm, String gender, String brdtDate, String telNo, String resideArea, String zip, String rdnmAdr, String detailAdr, Long atchFileSn, String mngrYn, String mngrConfmYn, String confmerId, String confmerIp, LocalDateTime confmDt, String snsKakaoCntnYn, LocalDateTime snsKakaoCntnDt, String snsGoogleCntnYn, LocalDateTime snsGoogleCntnDt, String snsNaverCntnYn, LocalDateTime snsNaverCntnDt, String snsFacebookCntnYn, LocalDateTime snsFacebookCntnDt, String snsAppleCntnYn, LocalDateTime snsAppleCntnDt, LocalDateTime lastLoginDt, String updusrId, String updusrIp, String delYn ) {
        this.userSn = userSn;
        this.userId = userId;
        this.password = password;
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
        this.snsKakaoCntnYn =snsKakaoCntnYn;
        this.snsKakaoCntnDt =snsKakaoCntnDt;
        this.snsGoogleCntnYn = snsGoogleCntnYn;
        this.snsGoogleCntnDt = snsGoogleCntnDt;
        this.snsNaverCntnYn = snsNaverCntnYn;
        this.snsNaverCntnDt = snsNaverCntnDt;
        this.snsFacebookCntnYn = snsFacebookCntnYn;
        this.snsFacebookCntnDt = snsFacebookCntnDt;
        this.snsAppleCntnYn = snsAppleCntnYn;
        this.snsAppleCntnDt = snsAppleCntnDt;
        this.lastLoginDt = lastLoginDt;
        this.updusrId = updusrId;
        this.updusrIp = updusrIp;
        this.delYn = delYn;
    }
    
    // DTO to Entity 메소드는 DTO 내부에서 생성.
    public User toEntity() {
        return User.builder()
                .userSn( userSn )
                .userId( userId )
                .password( password )
                .userNm( userNm )
                .gender( gender )
                .brdtDate( brdtDate )
                .telNo( telNo )
                .resideArea( resideArea )
                .zip( zip )
                .rdnmAdr( rdnmAdr )
                .detailAdr( detailAdr )
                .atchFileSn( atchFileSn )
                .mngrYn( mngrYn )
                .mngrConfmYn( mngrConfmYn )
                .confmerId( confmerId )
                .confmerIp( confmerIp )
                .confmDt( confmDt )
                .snsKakaoCntnYn( snsKakaoCntnYn )
                .snsKakaoCntnDt( snsKakaoCntnDt )
                .snsGoogleCntnYn( snsGoogleCntnYn )
                .snsGoogleCntnDt( snsGoogleCntnDt )
                .snsNaverCntnYn( snsNaverCntnYn )
                .snsNaverCntnDt( snsNaverCntnDt )
                .snsFacebookCntnYn( snsFacebookCntnYn )
                .snsFacebookCntnDt( snsFacebookCntnDt )
                .snsAppleCntnYn( snsAppleCntnYn )
                .snsAppleCntnDt( snsAppleCntnDt )
                .lastLoginDt( lastLoginDt )
                .updusrId( updusrId )
                .updusrIp( updusrIp )
                .build();
    }
    
    // Entity to Dto 메소드는 DTO 내부에서 생성.
    public UserModDto toDto( User user ) {
        return UserModDto.builder()
                .userSn( user.getUserSn() )
                .userId( user.getUserId() )
                .password( user.getPassword() )
                .userNm( user.getUserNm() )
                .gender( user.getGender() )
                .brdtDate( user.getBrdtDate() )
                .telNo( user.getTelNo() )
                .resideArea( user.getResideArea() )
                .zip( user.getZip() )
                .rdnmAdr( user.getRdnmAdr() )
                .detailAdr( user.getDetailAdr() )
                .atchFileSn( user.getAtchFileSn() )
                .mngrYn( user.getMngrYn() )
                .mngrConfmYn( user.getMngrConfmYn() )
                .confmerId( user.getConfmerId() )
                .confmerIp( user.getConfmerIp() )
                .confmDt( user.getConfmDt() )
                .snsKakaoCntnYn( user.getSnsKakaoCntnYn() )
                .snsKakaoCntnDt( user.getSnsKakaoCntnDt() )
                .snsGoogleCntnYn( user.getSnsGoogleCntnYn() )
                .snsGoogleCntnDt( user.getSnsGoogleCntnDt() )
                .snsNaverCntnYn( user.getSnsNaverCntnYn() )
                .snsNaverCntnDt( user.getSnsNaverCntnDt() )
                .snsFacebookCntnYn( user.getSnsFacebookCntnYn() )
                .snsFacebookCntnDt( user.getSnsFacebookCntnDt() )
                .snsAppleCntnYn( user.getSnsAppleCntnYn() )
                .snsAppleCntnDt( user.getSnsAppleCntnDt() )
                .lastLoginDt( user.getLastLoginDt() )
                .updusrId( user.getUpdusrId() )
                .updusrIp( user.getUpdusrIp() )
                .build();
    }
    
}
