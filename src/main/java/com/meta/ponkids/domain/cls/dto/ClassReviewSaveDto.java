package com.meta.ponkids.domain.cls.dto;

import com.meta.ponkids.domain.cls.entity.ClassReview;
import com.meta.ponkids.global.util.session.SessionUtils;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassReviewSaveDto {
	
	private Long classReviewSn;
	
	private Long classSn;
	
	private Long userSn;
	
	private String step;
	
	private Long parntsReviewSn;
	
	private String reviewCn;
	
	private String reviewGrade;
	
	private Long atchFileSn;
	
	private String registerId;      // 등록자 id
	
	private String registerIp;      // 등록자 ip
	
	private String updusrId;      	// 수정자 id
	
	private String updusrIp;      	// 수정자 ip
	
	@Builder
	public ClassReviewSaveDto(Long classReviewSn, Long classSn, Long userSn, String step, Long parntsReviewSn,
			String reviewCn, String reviewGrade, Long atchFileSn, String registerId, String registerIp, String updusrId,
			String updusrIp) {
		this.classReviewSn = classReviewSn;
		this.classSn = classSn;
		this.userSn = SessionUtils.getAuthUserSn();
		this.step = step;
		this.parntsReviewSn = parntsReviewSn;
		this.reviewCn = reviewCn;
		this.reviewGrade = reviewGrade;
		this.atchFileSn = atchFileSn;
		this.registerId = registerId;
		this.registerIp = registerIp;
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
				.registerId(registerId)
				.registerIp(registerIp)
				.updusrId(updusrId)
				.updusrIp(updusrIp)
				.build();
	}
	

}
