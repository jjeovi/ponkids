package com.meta.ponkids.domain.qestnar.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QestnarAnswerListDto {
	
	private Long qestnarAnswerSn;
	
	private Long qestnarGroupSn;
	
	private Long userSn;
	
    private String registerId;      // 등록자 ID
    
    private String regDt;	// 등록일자
	
	private String schOption;   	// 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String schCntn;     	// 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
	
	@QueryProjection
	public QestnarAnswerListDto (Long qestnarAnswerSn, Long qestnarGroupSn, Long userSn, String registerId, String regDt) {
		this.qestnarAnswerSn = qestnarAnswerSn;
		this.qestnarGroupSn = qestnarGroupSn;
		this.userSn = userSn;
		this.registerId = registerId;
		this.regDt 		= regDt;
	}

}