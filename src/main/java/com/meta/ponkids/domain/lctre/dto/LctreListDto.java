package com.meta.ponkids.domain.lctre.dto;

import com.meta.ponkids.domain.lctre.entity.Lctre;
import com.meta.ponkids.global.common.dto.CategoryDto;
import com.querydsl.core.annotations.QueryProjection;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = false )
public class LctreListDto extends LctreDto {
	
	private Long 		lctreSn;				// 수업 일련번호
	
	private Long 		ctgrySn;				// 카테고리 일련번호
	
	private String		ctgryNm;				// 카테고리 명
	
	private Long		crseSn;					// 커리큘럼 일련번호
	
	private String		crseNm;					// 커리큘럼 명
	
	private Long 		classSn;				// 클래스 일련번호
	
	private String 		classSj;				// 클래스 제목
	
	private Long 		classDaySn;				// 클래스 요일 코드
	
	private String 		classDayCd;				// 클래스 요일 코드
	
	private String 		classDayNm;				// 클래스 요일 명
	
	private Long		thumbAtchFileSn;		// 썸네일 첨부 파일 일련번호
	
	private Long 		lctreSeq;				// 수업 순번
	
	private String 		lctreSj;				// 수업 제목
	
	private String 		lctreDt;				// 수업 일시

	private Long		lctreAmt;				// 수업 금액
	
	private String 		lctreDc;				// 수업 설명
	
	private String 		lctreApplcntGuidance;	// 수업 신청자 안내
	
	private String 		rcritNmprSetYn;			// 모집 인원 설정 여부
	
	private String 		rcritNmprSetYnNm;		// 모집 인원 설정 여부명

	private Long 		rltmReqstNmprCo ;		// 실시간 신청 인원 수

	private Long 		rcritNmprCo;			// 모집 인원 수

	private String 		preparRcritNmprSetYn;	// 예비 모집 인원 설정 여부
	
	private String 		preparRcritNmprSetYnNm;	// 예비 모집 인원 설정 여부 명

	private Long 		rltmPreparReqstNmprCo;	// 실시간 예비 신청 인원 수

	private Long 		preparRcritNmprCo;		// 예비 모집 인원 수
	
    private String 		registerId;      		// 등록자 ID
    
    private String 		regDt;					// 등록일자
	
	private String 		schOption;   			// 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String 		schCntn;     			// 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
	
	private CategoryDto category;    			// 카테고리 검색 : 생성자에는 추가하지 않음!
	
	@Builder
	@QueryProjection
	public LctreListDto( Long lctreSn, Long ctgrySn, String ctgryNm, Long crseSn, String crseNm, Long classSn, String classSj, Long classDaySn, String classDayCd, String classDayNm, Long thumbAtchFileSn, Long lctreSeq, String lctreSj, String lctreDt, Long lctreAmt, String lctreDc, String lctreApplcntGuidance, String rcritNmprSetYn, String rcritNmprSetYnNm, Long rltmReqstNmprCo, Long rcritNmprCo, String preparRcritNmprSetYn, String preparRcritNmprSetYnNm, Long rltmPreparReqstNmprCo, Long preparRcritNmprCo, String registerId, String regDt ) {
		this.lctreSn = lctreSn;
		this.ctgrySn = ctgrySn;
		this.ctgryNm = ctgryNm;
		this.crseSn = crseSn;
		this.crseNm = crseNm;
		this.classSn = classSn;
		this.classSj = classSj;
		this.classDaySn = classDaySn;
		this.classDayCd = classDayCd;
		this.classDayNm = classDayNm;
		this.thumbAtchFileSn = thumbAtchFileSn;
		this.lctreSeq = lctreSeq;
		this.lctreSj = lctreSj;
		this.lctreDt = lctreDt;
		this.lctreAmt = lctreAmt;
		this.lctreDc = lctreDc;
		this.lctreApplcntGuidance = lctreApplcntGuidance;
		this.rcritNmprSetYn = rcritNmprSetYn;
		this.rcritNmprSetYnNm = rcritNmprSetYnNm;
		this.rltmReqstNmprCo = rltmReqstNmprCo;
		this.rcritNmprCo = rcritNmprCo;
		this.preparRcritNmprSetYn = preparRcritNmprSetYn;
		this.preparRcritNmprSetYnNm = preparRcritNmprSetYnNm;
		this.rltmPreparReqstNmprCo = rltmPreparReqstNmprCo;
		this.preparRcritNmprCo = preparRcritNmprCo;
		this.registerId = registerId;
		this.regDt = regDt;
	}
	
	// Entity to Dto 메소드는 DTO 내부에서 생성
	public LctreListDto toDto( Lctre lctre ) {
		return LctreListDto.builder()
				.lctreSn( lctre.getLctreSn() )
				.classSn( lctre.getClassSn() )
				.classDayCd( lctre.getClassDayCd() )
				.lctreSeq( lctre.getLctreSeq() )
				.lctreSj( lctre.getLctreSj() )
				.lctreDt( lctre.getLctreDt() )
				.lctreAmt( lctre.getLctreAmt() )
				.lctreDc( lctre.getLctreDc() )
				.lctreApplcntGuidance( lctre.getLctreApplcntGuidance() )
				.rcritNmprSetYn( lctre.getRcritNmprSetYn() )
				.rcritNmprCo( lctre.getRcritNmprCo() )
				.preparRcritNmprSetYn( lctre.getPreparRcritNmprSetYn() )
				.preparRcritNmprCo( lctre.getPreparRcritNmprCo() )
				.build();
	}
	
}