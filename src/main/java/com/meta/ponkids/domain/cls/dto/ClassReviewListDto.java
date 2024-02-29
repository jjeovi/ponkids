package com.meta.ponkids.domain.cls.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassReviewListDto {
	
	private Long classReviewSn;
	
	private Long classSn;
	
	private String classSj;
	
	private Long userSn;
	
	private String userNm;
	
	private String step;
	
	private Long parntsReviewSn;
	
	private String reviewCn;
	
	private String reviewGrade;
	
	private Long atchFileSn;
	
    private String registerId;      // 등록자 ID
    
    private String regDt;	// 등록일자
	
	private String schOption;   	// 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String schCntn;     	// 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!


	@QueryProjection
	public ClassReviewListDto(Long classReviewSn, Long classSn, String classSj, Long userSn, String userNm, String step, Long parntsReviewSn,
			String reviewCn, String reviewGrade, Long atchFileSn, String registerId, String regDt) {
		this.classReviewSn = classReviewSn;
		this.classSn = classSn;
		this.classSj = classSj;
		this.userSn = userSn;
		this.userNm = userNm;
		this.step = step;
		this.parntsReviewSn = parntsReviewSn;
		this.reviewCn = reviewCn;
		this.reviewGrade = reviewGrade;
		this.atchFileSn = atchFileSn;
		this.registerId = registerId;
		this.regDt = regDt;
	}
	
	
	
	

}