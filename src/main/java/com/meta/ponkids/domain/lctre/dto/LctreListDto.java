package com.meta.ponkids.domain.lctre.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LctreListDto {
	
	private Long 	lctreSn;				// 수업 일련번호
	
	private Long 	classSn;				// 클래스 일련번호
	
	private Long 	classWeekSn;			// 클래스 요일 일련번호
	
	private Long 	lctreSeq;				// 수업 순번
	
	private String 	lctreSj;				// 수업 제목
	
	private String 	lctreDc;				// 수업 설명
	
	private String 	lctreApplcntGuidance;	// 수업 신청자 안내
	
	private String 	rcritNmprSetYn;			// 모집 인원 설정 여부
	
	private Long 	rcritNmprCo;			// 모집 인원 수
	
	private String 	preparRcritNmprSetYn;	// 예비 모집 인원 설정 여부
	
	private Long 	preparRcritNmprCo;		// 예비 모집 인원 수
	
    private String 	registerId;      		// 등록자 ID
    
    private String 	regDt;					// 등록일자
	
	private String 	schOption;   			// 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String 	schCntn;     			// 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
	
	@QueryProjection
	public LctreListDto( Long lctreSn, Long classSn, Long classWeekSn, Long lctreSeq, String lctreSj, String lctreDc, String lctreApplcntGuidance, String rcritNmprSetYn, Long rcritNmprCo, String preparRcritNmprSetYn, Long preparRcritNmprCo, String registerId, String regDt ) {
		this.lctreSn = lctreSn;
		this.classSn = classSn;
		this.classWeekSn = classWeekSn;
		this.lctreSeq = lctreSeq;
		this.lctreSj = lctreSj;
		this.lctreDc = lctreDc;
		this.lctreApplcntGuidance = lctreApplcntGuidance;
		this.rcritNmprSetYn = rcritNmprSetYn;
		this.rcritNmprCo = rcritNmprCo;
		this.preparRcritNmprSetYn = preparRcritNmprSetYn;
		this.preparRcritNmprCo = preparRcritNmprCo;
		this.registerId = registerId;
		this.regDt = regDt;
	}
}