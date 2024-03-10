package com.meta.ponkids.domain.qestnar.dto;

import com.meta.ponkids.global.common.dto.CategoryDto;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QestnarAnswerDetailDto {
	
	private Long 	qestnarAnswerSn;
	
	private Long 	qestnarGroupSn;
	
	private String		qestnarGroupCd;
	
	private String		qestnarGroupNm;
	
	private String		replySetYn;

	private String		replyYn;			// 답변 여부
	
	private String		replyYnNm;			// 답변 여부 명칭
	
	private Long 		replyCnt;			// 답변 개수
	
	private Long 	userSn;
	
	private String 		userId;
	
	private String 		userNm;
	
	private String 		telNo;
	
    private String 	registerId;      // 등록자 ID
    
    private String 	regDt;	// 등록일자
	
	private String 	schOption;   	// 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String 	schCntn;     	// 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
    
    private CategoryDto category;    // 카테고리 검색 : 생성자에는 추가하지 않음!
	
	@QueryProjection
	public QestnarAnswerDetailDto ( Long qestnarAnswerSn, Long qestnarGroupSn, String qestnarGroupCd, String qestnarGroupNm, String replySetYn, String replyYn, String replyYnNm, Long replyCnt, Long userSn, String userId, String userNm, String telNo, String registerId, String regDt ) {
		this.qestnarAnswerSn = qestnarAnswerSn;
		this.qestnarGroupSn = qestnarGroupSn;
		this.qestnarGroupCd = qestnarGroupCd;
		this.qestnarGroupNm = qestnarGroupNm;
		this.replySetYn = replySetYn;
		this.replyYn = replyYn;
		this.replyYnNm = replyYnNm;
		this.replyCnt = replyCnt;
		this.userSn = userSn;
		this.userId = userId;
		this.userNm = userNm;
		this.telNo = telNo;
		this.registerId = registerId;
		this.regDt 		= regDt;
	}

}