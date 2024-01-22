package com.meta.ponkids.domain.adm.lctre.dto;

import com.meta.ponkids.global.common.dto.CategoryDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class LctreDto {
	
	private Long 		lctreSn;				// 수업 일련번호
	
//	private Long 		ctgrySn;				// 카테고리 일련번호
//
//	private String		ctgryNm;				// 카테고리 명
//
//	private Long		crseSn;					// 커리큘럼 일련번호
//
//	private String		crseNm;					// 커리큘럼 명
	
	private Long 		classSn;				// 클래스 일련번호
	
//	private String 		classSj;				// 클래스 제목
//
//	private String 		classDayCd;				// 클래스 요일 코드
//
//	private String 		classDayNm;				// 클래스 요일 명
//
//	private Long 		lctreSeq;				// 수업 순번
//
//	private String 		lctreSj;				// 수업 제목
//
//	private String 		lctreDc;				// 수업 설명
//
//	private String 		lctreApplcntGuidance;	// 수업 신청자 안내
//
//	private String 		rcritNmprSetYn;			// 모집 인원 설정 여부
//
//	private String 		rcritNmprSetYnNm;		// 모집 인원 설정 여부
//
//	private Long 		rcritNmprCo;			// 모집 인원 수
//
//	private String 		preparRcritNmprSetYn;	// 예비 모집 인원 설정 여부
//
//	private String 		preparRcritNmprSetYnNm;	// 예비 모집 인원 설정 여부
//
//	private Long 		preparRcritNmprCo;		// 예비 모집 인원 수
//
//    private String 		registerId;      		// 등록자 ID
//
//    private String 		regDt;					// 등록일자
//
//	private String 		schOption;   			// 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
//
//    private String 		schCntn;     			// 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
//
//	private CategoryDto category;    			// 카테고리 검색 : 생성자에는 추가하지 않음!
}