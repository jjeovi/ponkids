package com.meta.ponkids.domain.cls.dto;

import com.meta.ponkids.global.common.dto.CategoryDto;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassReviewListDto {
	
	private Long classReviewSn;
	
	private Long classSn;
	
	private String classSj;
	
	private Long 		thumbAtchFileSn;
	
	private Long userSn;
	
	private String userNm;
	
	private String	userId;				// 사용자 ID
	
	private String step;
	
	private String		replyYn;			// 답변 여부
	
	private String		replyYnNm;			// 답변 여부 명칭
	
	private Long		replyCnt;			// 답변 개수
	
	private Long parntsReviewSn;
	
	private String reviewCn;
	
	private String reviewGrade;
	
	private Long 	reviewGradeLong;
	
	private Long atchFileSn;
	
	private String openYn;
	
	private String		openYnNm;				// 공개 여부 ( Y / N )
	
    private String registerId;      // 등록자 ID
    
    private String regDt;	// 등록일자
    
    private String regFullDt;	// 등록일자
	
	private String schOption;   	// 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String schCntn;     	// 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
	
	private CategoryDto category;

	@Builder
	@QueryProjection
	public ClassReviewListDto( Long classReviewSn, Long classSn, String classSj, Long thumbAtchFileSn, Long userSn,
			String userNm, String userId,  String step, String replyYn, String replyYnNm, Long replyCnt,
			Long parntsReviewSn, String reviewCn, String reviewGrade, Long reviewGradeLong, Long atchFileSn,
			String openYn, String openYnNm, String registerId, String regDt, String regFullDt ) {
		this.classReviewSn = classReviewSn;
		this.classSn = classSn;
		this.classSj = classSj;
		this.thumbAtchFileSn = thumbAtchFileSn;
		this.userSn = userSn;
		this.userNm = userNm;
		this.userId = userId;
		this.step = step;
		this.replyYn = replyYn;
		this.replyYnNm = replyYnNm;
		this.replyCnt = replyCnt;
		this.parntsReviewSn = parntsReviewSn;
		this.reviewCn = reviewCn;
		this.reviewGrade = reviewGrade;
		this.reviewGradeLong = reviewGradeLong;
		this.atchFileSn = atchFileSn;
		this.openYn = openYn;
		this.openYnNm = openYnNm;
		this.registerId = registerId;
		this.regDt = regDt;
		this.regFullDt = regFullDt;
	}
	

}