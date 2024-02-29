package com.meta.ponkids.domain.cls.dto;

import com.meta.ponkids.domain.cls.entity.ClassReqst;
import com.meta.ponkids.global.common.dto.CategoryDto;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode( callSuper = false )
public class ClassReqstListDto {
	
	private Long 	classReqstSn;			// 클래스 신청 일련번호
	
	private Long 	classSn;                // 클래스 일련번호
	
	private Long 		thumbAtchFileSn;    // 썸네일첨부파일일련번호
	
    private Long 		ctgrySn;            // 카테고리코드
    
    private String 		ctgryNm;            // 카테고리명
    
    private Long 		crseSn;             // 커리큘럼코드
    
    private String 		crseNm;             // 커리큘럼명
    
    private String 		classSj;          	// 클래스제목
    
	private Long 	userSn;                 // 사용자 일련번호
	
	private Long 	totReqstCnt;            // 총 신청 건수
	
	private Long 	totReqstAmt;            // 총 신청 금액
	
	private String 	regDt;    				// 등록일자
    
    private String 	registerId;             // 등록자 id
    
    private String 	registerIp;             // 등록자 ip
    
    private String 	updusrId;               // 수정자 ID
    
    private String 	updusrIp;               // 수정자 IP
    
    private CategoryDto category;    // 카테고리 검색 : 생성자에는 추가하지 않음!
    
    @Builder
    @QueryProjection
    public ClassReqstListDto(
    		Long classReqstSn,
			Long classSn,
			Long thumbAtchFileSn,
			Long ctgrySn,
			String ctgryNm,
			Long crseSn,
			String crseNm,
			String classSj,
			Long userSn,
			Long totReqstCnt,
			Long totReqstAmt,
			String regDt ) {
		
		this.classReqstSn	= classReqstSn;
		this.classSn		= classSn;
		this.thumbAtchFileSn= thumbAtchFileSn;
		this.ctgrySn		= ctgrySn;
		this.ctgryNm		= ctgryNm;
		this.crseSn			= crseSn;
		this.crseNm			= crseNm;
		this.classSj		= classSj;
		this.userSn			= userSn;
		this.totReqstCnt	= totReqstCnt;
		this.totReqstAmt	= totReqstAmt;
		this.regDt			= regDt;
	}
    
}
