package com.meta.ponkids.domain.lctre.dto;

import java.util.List;

import com.meta.ponkids.global.common.dto.CategoryDto;
import com.querydsl.core.annotations.QueryProjection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = false )
public class LctreReqstListDto  {
    
    private Long    	lctreReqstSn;			// 수업 신청 일련번호
    
    private Long    	classReqstSn;			// 클래스 신청 일련번호
	
	private String    	classSj;				// 클래스 제목
    
    private Long    	lctreSn;				// 수업 일련번호
    
    private Long 		lctreSeq;				// 수업 순번
	
	private String 		lctreSj;				// 수업 제목
	
	private String 		lctreDt;				// 수업 일시

	private Long		lctreAmt;				// 수업 금액
	
	private String 		lctreDc;				// 수업 설명
	
	private String 		lctreApplcntGuidance;	// 수업 신청자 안내
	
	private String 		classDayCd;				// 클래스 요일 코드
	
	private String 		classDayNm;				// 클래스 요일 명
    
    private Long		chldrnSn;				// 자녀 일련번호

	private String		chldrnNm;				// 자녀명

	private String		preparNmprYn;			// 예비 인원 여부
	
	private String		userId;					// 신청자 ID : 메일 발송 시 ID 검증하기 위함
	
	private String 		userNm;					// 신청자 명
	
	private String		telNo;					// 신청자 전화번호
	
	private String 		regDt;					// 등록일시
    
    private List<LctreReqstListDto> lctreReqsts;				// list
    
    private List<LctreReqstDetailListDto> lctreReqstDetails;	// list
    
    private String 	schOption;   			// 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String 	schCntn;     			// 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
	
	private CategoryDto category;    			// 카테고리 검색 : 생성자에는 추가하지 않음!
    
    @Builder
    @QueryProjection
	public LctreReqstListDto(	Long 	lctreReqstSn, 			Long 	classReqstSn, 			String	classSj,		Long	lctreSn,
								Long	lctreSeq,				String 	lctreSj, 				String 	lctreDt,		Long	lctreAmt,
								String	lctreDc,				String 	lctreApplcntGuidance, 	String 	classDayCd,		String 	classDayNm,
								 Long 	chldrnSn,				String	chldrnNm,				String 	preparNmprYn,	String userId,
								String	userNm,					String	telNo,					String 	regDt ) {
    	this.lctreReqstSn 			= lctreReqstSn;
		this.classReqstSn 			= classReqstSn;
		this.classSj 				= classSj;
		this.lctreSn 				= lctreSn;
		this.lctreSeq 				= lctreSeq;
		this.lctreSj 				= lctreSj;
		this.lctreDt 				= lctreDt;
		this.lctreAmt 				= lctreAmt;
		this.lctreDc 				= lctreDc;
		this.lctreApplcntGuidance 	= lctreApplcntGuidance;
		this.classDayCd 			= classDayCd;
		this.classDayNm 			= classDayNm;
		this.chldrnSn 				= chldrnSn;
		this.chldrnNm 				= chldrnNm;
		this.preparNmprYn 			= preparNmprYn;
		this.userId					= userId;
		this.userNm 				= userNm;
		this.telNo					= telNo;
		this.regDt					= regDt;
	}
    

    
}
