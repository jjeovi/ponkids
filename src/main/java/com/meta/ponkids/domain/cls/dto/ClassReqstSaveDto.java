package com.meta.ponkids.domain.cls.dto;

import java.util.List;

import com.meta.ponkids.domain.cls.entity.Class;
import com.meta.ponkids.domain.cls.entity.ClassReqst;
import com.meta.ponkids.domain.lctre.dto.LctreReqstSaveDto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode( callSuper = false )
public class ClassReqstSaveDto {
	
	private Long 	classReqstSn;			// 클래스 신청 일련번호
	
	private Long 	classSn;                // 클래스 일련번호
	
	private Long 	userSn;                 // 사용자 일련번호
    
    private String 	registerId;             // 등록자 id
    
    private String 	registerIp;             // 등록자 ip
    
    private String 	updusrId;               // 수정자 ID
    
    private String 	updusrIp;               // 수정자 IP

    private List<ClassReqstSaveDto> classReqstSaveDtoList;	// list
    
    @Builder
    public ClassReqstSaveDto(
    		Long classReqstSn,
			Long classSn,
			Long userSn,
			String registerId, 
			String registerIp, 
			String updusrId, 
			String updusrIp ) {
		
		this.classReqstSn 	= classReqstSn;
		this.classSn 		= classSn;
		this.userSn 		= userSn;
		this.registerId 	= registerId;
		this.registerIp 	= registerIp;
		this.updusrId 		= updusrId;
		this.updusrIp 		= updusrIp;
	}
    
    // Dto to Entity 메소드 생성
    public ClassReqst toEntity() {
        return ClassReqst.builder()
                .classReqstSn( classReqstSn )
                .classSn( classSn )
                .userSn( userSn )
                .registerId( registerId )
                .registerIp( registerIp )
                .updusrId( updusrId )
                .updusrIp( updusrIp )
                .build();
    }

}
