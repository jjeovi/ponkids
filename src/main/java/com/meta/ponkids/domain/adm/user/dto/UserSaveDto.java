package com.meta.ponkids.domain.adm.user.dto;

import com.meta.ponkids.domain.adm.user.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * className      : UserSaveDto
 * author         : jjeoV
 * date           : 2023-11-19
 * description    : class of 회원 등록 Dto
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-11-19        jjeoV             최초 생성
 */
@NoArgsConstructor
@Data
public class UserSaveDto {
    
    private Long userSn;
    
    @NotNull
    private String userId;            // 회원 일련번호
    
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
    
    private Long atchFileSn;      // 첨부파일 일련번호
    
    @NotNull
    private String mngrYn;          // 관리자 여부
    
    @NotNull
    private String mngrConfmYn;     // 관리자 승인 여부
    
    private String confmerId;       // 승인자 ID
    
    private String confmerIp;       // 승인자 IP
    
    private LocalDateTime confmDt;  // 승인일시
    
    private String cntnSns;         // 연계SNS
    
    private String registerIp;      // 등록자 IP
    
    private String updusrId;        // 수정자 ID
    
    private String updusrIp;        // 수정자 IP
    
    private String delYn;           // 삭제여부
    
    // builder 생성
    @Builder
    public UserSaveDto( String userId, String password, String userNm, String gender, String brdtDate, String telNo, String resideArea, String zip, String rdnmAdr, String detailAdr, Long atchFileSn, String mngrYn, String mngrConfmYn, String confmerId, String confmerIp, LocalDateTime confmDt, String cntnSns, String registerIp, String updusrId, String updusrIp, String delYn ) {
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
        this.cntnSns = cntnSns;
        this.registerIp = registerIp;
        this.updusrId = updusrId;
        this.updusrIp = updusrIp;
        this.delYn = delYn;
    }
    
    // DTO to Entity 메소드는 DTO 내부에서 생성.
    public User toEntity() {
        return User.builder()
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
                .cntnSns( cntnSns )
                .registerIp( registerIp )
                .build();
    }
    
}
