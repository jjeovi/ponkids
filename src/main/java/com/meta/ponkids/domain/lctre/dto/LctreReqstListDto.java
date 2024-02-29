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
    
    private Long    lctreReqstSn;			// 수업 신청 일련번호
    
    private Long    classReqstSn;			// 클래스 신청 일련번호
    
    private Long    lctreSn;				// 수업 일련번호
    
    private Long 		lctreSeq;				// 수업 순번
	
	private String 		lctreSj;				// 수업 제목
	
	private String 		lctreDt;				// 수업 일시

	private Long		lctreAmt;				// 수업 금액
	
	private String 		lctreDc;				// 수업 설명
	
	private String 		lctreApplcntGuidance;	// 수업 신청자 안내
    
    private Long	chldrnSn;				// 자녀 일련번호
    
    private String		chldrnNm;				// 자녀명
    
    private List<LctreReqstListDto> lctreReqsts;				// list
    
    private List<LctreReqstDetailListDto> lctreReqstDetails;	// list
    
    private String 	schOption;   			// 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String 	schCntn;     			// 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
	
	private CategoryDto category;    			// 카테고리 검색 : 생성자에는 추가하지 않음!
    
    @Builder
    @QueryProjection
	public LctreReqstListDto(	Long 	lctreReqstSn, 			Long 	classReqstSn, 	Long	lctreSn,	Long	lctreSeq, 
								String 	lctreSj, 				String 	lctreDt,		Long	lctreAmt, 	String	lctreDc, 
								String 	lctreApplcntGuidance, 	Long 	chldrnSn,		String	chldrnNm ) {		this.lctreReqstSn 			= lctreReqstSn;
		this.classReqstSn 			= classReqstSn;
		this.lctreSn 				= lctreSn;
		this.lctreSeq 				= lctreSeq;
		this.lctreSj 				= lctreSj;
		this.lctreDt 				= lctreDt;
		this.lctreAmt 				= lctreAmt;
		this.lctreDc 				= lctreDc;
		this.lctreApplcntGuidance 	= lctreApplcntGuidance;
		this.chldrnSn 				= chldrnSn;
		this.chldrnNm 				= chldrnNm;
	}
    

    
}
