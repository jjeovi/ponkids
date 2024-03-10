package com.meta.ponkids.domain.qestnar.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QestnarQestnDetailListForAnswerDto {
	
	private Long qestnarQestnDetailSn;
	
	private Long qestnarQestnSn;
	
	private Long qestnarQestnDetailSeq;
	
	private String qestnarQestnDetailCn;
	
	private Long qestnarAnswerDetailSn;
	
    private String registerId;      // 등록자 ID
    
    private String regDt;	// 등록일자
	
	private String schOption;   	// 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String schCntn;     	// 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!

	@QueryProjection
	public QestnarQestnDetailListForAnswerDto(Long qestnarQestnDetailSn, Long qestnarQestnSn, Long qestnarQestnDetailSeq,
			String qestnarQestnDetailCn, Long qestnarAnswerDetailSn, String registerId, String regDt) {
		this.qestnarQestnDetailSn = qestnarQestnDetailSn;
		this.qestnarQestnSn = qestnarQestnSn;
		this.qestnarQestnDetailSeq = qestnarQestnDetailSeq;
		this.qestnarQestnDetailCn = qestnarQestnDetailCn;
		this.qestnarAnswerDetailSn = qestnarAnswerDetailSn;
		this.registerId = registerId;
		this.regDt = regDt;
	}
	
}