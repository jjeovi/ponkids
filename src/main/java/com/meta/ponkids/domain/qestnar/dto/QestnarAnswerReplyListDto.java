package com.meta.ponkids.domain.qestnar.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QestnarAnswerReplyListDto {
	
	private Long qestnarAnswerReplySn;
	
	private Long qestnarAnswerSn;
	
	private Long userSn;
	
	private String	userNm;
	
	private String	userId;
	
	private String qestnarAnswerReplyCn;
	
	private Long atchFileSn;
	
    private String registerId;      // 등록자 ID
    
    private String regDt;	// 등록일자
	
	private String schOption;   	// 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String schCntn;     	// 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!

	@QueryProjection
	public QestnarAnswerReplyListDto( Long qestnarAnswerReplySn, Long qestnarAnswerSn, Long userSn, String userNm, String userId,
			String qestnarAnswerReplyCn, Long atchFileSn, String registerId, String regDt ) {
		this.qestnarAnswerReplySn = qestnarAnswerReplySn;
		this.qestnarAnswerSn = qestnarAnswerSn;
		this.userSn = userSn;
		this.userNm = userNm;
		this.userId = userId;
		this.qestnarAnswerReplyCn = qestnarAnswerReplyCn;
		this.atchFileSn = atchFileSn;
		this.registerId = registerId;
		this.regDt = regDt;
	}
	
	

}