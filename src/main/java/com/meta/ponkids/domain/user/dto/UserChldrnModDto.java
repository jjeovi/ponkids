package com.meta.ponkids.domain.user.dto;

import com.meta.ponkids.domain.user.entity.User;
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
    
	private Long chldrnSn;
	
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
    public UserChldrnModDto( Long chldrnSn, Long userSn, Long userChldrnSeq, String chldrnNm, String chldrnGender, String chldrnBrdtDate, String chldrnEmail, String chldrnTelNo, Long atchFileSn, String updusrId, String updusrIp ) {
    	this.chldrnSn = chldrnSn;
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
        		.chldrnSn( chldrnSn )
                .userChldrnSeq( userChldrnSeq )
                .userSn( userSn )
                .chldrnNm( chldrnNm )
                .chldrnGender( chldrnGender )
                .chldrnBrdtDate( chldrnBrdtDate )
                .chldrnEmail( chldrnEmail )
                .chldrnTelNo( chldrnTelNo )
                .atchFileSn( atchFileSn )
                .updusrId( updusrId )
                .updusrIp( updusrIp )
                .build();
    }
    

    // Entity to Dto 메소드는 DTO 내부에서 생성.
    public UserChldrnModDto toDto( UserChldrn userChldrn ) {
    	return UserChldrnModDto.builder()
    			.chldrnSn( userChldrn.getChldrnSn() )
                .userChldrnSeq( userChldrn.getUserChldrnSeq() )
                .userSn( userChldrn.getUserSn() )
                .chldrnNm( userChldrn.getChldrnNm() )
                .chldrnGender( userChldrn.getChldrnGender() )
                .chldrnBrdtDate( userChldrn.getChldrnBrdtDate() )
                .chldrnEmail( userChldrn.getChldrnEmail() )
                .chldrnTelNo( userChldrn.getChldrnTelNo() )
                .atchFileSn( userChldrn.getAtchFileSn() )
                .updusrId( userChldrn.getUpdusrId() )
                .updusrIp( userChldrn.getUpdusrIp() )
                .build();
    			
    }
}
