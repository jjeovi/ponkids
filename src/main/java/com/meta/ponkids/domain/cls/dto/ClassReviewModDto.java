package com.meta.ponkids.domain.cls.dto;

import com.meta.ponkids.domain.cls.entity.ClassReview;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO Dto 항목 setting
@Data
@NoArgsConstructor
public class ClassReviewModDto {
	
	
	private Long classReviewSn;
	
	private Long classSn;
	
	private Long userSn;
	
	private String step;
	
	private Long parntsReviewSn;
	
	private String reviewCn;
	
	private String reviewGrade;
	
	private Long atchFileSn;
	
    private String updusrId;      	// 수정자 ID
    
    private String updusrIp;      	// 수정자 IP
	
	//builder 생성
	@Builder
	public ClassReviewModDto(Long classReviewSn, Long classSn, Long userSn, String step, Long parntsReviewSn,
			String reviewCn, String reviewGrade, Long atchFileSn, String updusrId, String updusrIp) {
		this.classReviewSn = classReviewSn;
		this.classSn = classSn;
		this.userSn = userSn;
		this.step = step;
		this.parntsReviewSn = parntsReviewSn;
		this.reviewCn = reviewCn;
		this.reviewGrade = reviewGrade;
		this.atchFileSn = atchFileSn;
		this.updusrId = updusrId;
		this.updusrIp = updusrIp;
	}
	
	
	// Dto to Entity 메소드 생성
	public ClassReview toEntity() {
		return ClassReview.builder()
				.classReviewSn(classReviewSn)
				.classSn(classSn)
				.userSn(userSn)
				.step(step)
				.parntsReviewSn(parntsReviewSn)
				.reviewCn(reviewCn)
				.reviewGrade(reviewGrade)
				.atchFileSn(atchFileSn)
				.updusrId(updusrId)
				.updusrIp(updusrIp)
				.build();
	}
	
	
	// Entity to Dto 메소드는 DTO 내부에서 생성.
	public ClassReviewModDto toDto(ClassReview classReview) {
		return ClassReviewModDto.builder()
				.classReviewSn(classReview.getClassReviewSn())
				.classSn(classReview.getClassSn())
				.userSn(classReview.getUserSn())
				.step(classReview.getStep())
				.parntsReviewSn(classReview.getParntsReviewSn())
				.reviewCn(classReview.getReviewCn())
				.reviewGrade(classReview.getReviewGrade())
				.atchFileSn(classReview.getAtchFileSn())
				.updusrId(classReview.getUpdusrId())
				.updusrIp(classReview.getUpdusrIp())
				.build();
	}



}
