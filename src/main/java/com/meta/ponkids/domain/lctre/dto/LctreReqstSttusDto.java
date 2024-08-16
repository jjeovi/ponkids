package com.meta.ponkids.domain.lctre.dto;

import com.meta.ponkids.global.common.dto.CategoryDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = false )
public class LctreReqstSttusDto {

    private Long    	lctreReqstSn;			// 수업 신청 일련번호

	private String		userId;					// 사용자 ID

	private String		userNm;					// 사용자명

	private String 		telNo;					// 전화번호

	private String 		regDt;					// 등록일자

	private Long		chldrnSn;				// 자녀 일련번호

	private String		chldrnNm;				// 자녀명

	private String		preparNmprYn;			// 예비 인원 여부

    private List<LctreReqstDetailListDto> lctreReqstDetails;	// list


    @Builder
    @QueryProjection
	public LctreReqstSttusDto( Long 	lctreReqstSn, 	String userId, String userNm, 	String telNo,
							   String 	regDt, 			Long chldrnSn, String chldrnNm, String preparNmprYn ) {
    	this.lctreReqstSn 			= lctreReqstSn;
		this.userId 				= userId;
		this.userNm 				= userNm;
		this.telNo 					= telNo;
		this.regDt 					= regDt;
		this.chldrnSn 				= chldrnSn;
		this.chldrnNm 				= chldrnNm;
		this.preparNmprYn 			= preparNmprYn;
	}
    

    
}
