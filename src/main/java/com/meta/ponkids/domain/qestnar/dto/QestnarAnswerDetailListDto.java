package com.meta.ponkids.domain.qestnar.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import lombok.NoArgsConstructor;

// TODO Dto 항목 setting
@Data
@NoArgsConstructor
public class QestnarAnswerDetailListDto {
	
	private Long 	qestnarAnswerDetailSn;
	
	private Long 	qestnarAnswerSn;
	
	private Long 	qestnarQestnSn;				// 질문지 일련번호
	
	private String 		qestnarQestnItemCn;		// 질문지 내용
	
	private String 	qestnarAnswer;				// 입력유형이 주관식일 경우
	
	private Long 	qestnarQestnDetailSn;		// 입력유형이 객관식일 경우
	
	private String 		qestnarQestnDetailCn;		// 입력유형이 객관식일 경우
	
	private Long 	atchFileSn;					// 입력유형이 첨부파일일 경우
	
    private String registerId;      // 등록자 ID
    
    private String regDt;	// 등록일자
	
	private String schOption;   	// 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String schCntn;     	// 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
    
    private Long userSn;
    
    private String qestnarGroupCd;

    @QueryProjection
	public QestnarAnswerDetailListDto( Long qestnarAnswerDetailSn, Long qestnarAnswerSn, Long qestnarQestnSn, String qestnarQestnItemCn,
			String qestnarAnswer, Long qestnarQestnDetailSn, String qestnarQestnDetailCn, Long atchFileSn, String registerId, String regDt ) {
		this.qestnarAnswerDetailSn 	= qestnarAnswerDetailSn;
		this.qestnarAnswerSn 		= qestnarAnswerSn;
		this.qestnarQestnSn 		= qestnarQestnSn;
		this.qestnarQestnItemCn 	= qestnarQestnItemCn;
		this.qestnarAnswer 			= qestnarAnswer;
		this.qestnarQestnDetailSn 	= qestnarQestnDetailSn;
		this.qestnarQestnDetailCn 	= qestnarQestnDetailCn;
		this.atchFileSn 			= atchFileSn;
		this.registerId 			= registerId;
		this.regDt 					= regDt;
	}

}