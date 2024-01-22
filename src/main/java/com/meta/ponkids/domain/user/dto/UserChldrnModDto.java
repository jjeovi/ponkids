package com.meta.ponkids.domain.user.dto;

import com.meta.ponkids.domain.user.entity.UserChldrn;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


/**
 * className      : UserChldrnModDto
 * author         : jjeoV
 * date           : 2023-11-19
 * description    : class of 자녀 수정 Dto
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-11-19        jjeoV             최초 생성
 */
@NoArgsConstructor
@Data
public class UserChldrnModDto {
    
    private Long userSn;
    
    private Long userChldrnSeq;
    
    private String chldrnNm;
    
    private String chldrnGender;
    
    private String chldrnBrdtDate;
    
    private String chldrnEmail;
    
    private String chldrnTelNo;
    
    private Long atchFileSn;
    
    private Long atchFileSnOri;
    
    private String updusrId;
    
    private String updusrIp;
    
    private MultipartFile file;
    
    
    @Builder
    public UserChldrnModDto( Long userSn, Long userChldrnSeq, String chldrnNm, String chldrnGender, String chldrnBrdtDate, String chldrnEmail, String chldrnTelNo, Long atchFileSn, String updusrId, String updusrIp ) {
        this.userSn = userSn;
        this.userChldrnSeq = userChldrnSeq;
        this.chldrnNm = chldrnNm;
        this.chldrnGender = chldrnGender;
        this.chldrnBrdtDate = chldrnBrdtDate;
        this.chldrnEmail = chldrnEmail;
        this.chldrnTelNo = chldrnTelNo;
        this.atchFileSn = atchFileSn;
        this.updusrId = updusrId;
        this.updusrIp = updusrIp;
    }
    
    // DTO to Entity 메소드는 DTO 내부에서 생성.
    public UserChldrn toEntity() {
        return UserChldrn.builder()
                .userChldrnSeq( userChldrnSeq )
                .userSn( userSn )
                .chldrnNm( chldrnNm )
                .chldrnGender( chldrnGender )
                .chldrnBrdtDate( getChldrnBrdtDate() )
                .chldrnEmail( chldrnEmail )
                .chldrnTelNo( chldrnTelNo )
                .atchFileSn( atchFileSn )
                .updusrId( updusrId )
                .updusrIp( updusrIp )
                .build();
    }
}
