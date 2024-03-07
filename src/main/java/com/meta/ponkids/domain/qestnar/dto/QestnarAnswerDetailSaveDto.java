package com.meta.ponkids.domain.qestnar.dto;

import com.meta.ponkids.domain.qestnar.entity.QestnarAnswerDetail;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QestnarAnswerDetailSaveDto {
	
	private Long 	qestnarAnswerDetailSn;
	
	private Long 	qestnarAnswerSn;
	
	private Long 	qestnarQestnSn;
	
	private String 	qestnarAnswer;				// 입력유형이 주관식일 경우
	
	private Long 	qestnarQestnDetailSn;		// 입력유형이 객관식일 경우
	
	private Long 	atchFileSn;					// 입력유형이 첨부파일일 경우
	
	private String registerId;      // 등록자 id
	
	private String registerIp;      // 등록자 ip
	
	private String updusrId;      	// 수정자 id
	
	private String updusrIp;      	// 수정자 ip
	
	@Builder
	public QestnarAnswerDetailSaveDto(Long qestnarAnswerDetailSn, Long qestnarAnswerSn, Long qestnarQestnSn,
			String qestnarAnswer, Long qestnarQestnDetailSn, Long atchFileSn, String registerId, String registerIp,
			String updusrId, String updusrIp) {
		this.qestnarAnswerDetailSn = qestnarAnswerDetailSn;
		this.qestnarAnswerSn = qestnarAnswerSn;
		this.qestnarQestnSn = qestnarQestnSn;
		this.qestnarAnswer = qestnarAnswer;
		this.qestnarQestnDetailSn = qestnarQestnDetailSn;
		this.atchFileSn = atchFileSn;
		this.registerId = registerId;
		this.registerIp = registerIp;
		this.updusrId = updusrId;
		this.updusrIp = updusrIp;
	}
	
	// Dto to Entity 메소드 생성
	public QestnarAnswerDetail toEntity() {
		return QestnarAnswerDetail.builder()
				.qestnarAnswerDetailSn(qestnarAnswerDetailSn)
				.qestnarAnswerSn(qestnarAnswerSn)
				.qestnarQestnSn(qestnarQestnSn)
				.qestnarAnswer(qestnarAnswer)
				.qestnarQestnDetailSn(qestnarQestnDetailSn)
				.atchFileSn(atchFileSn)
				.registerId(registerId)
				.registerIp(registerIp)
				.updusrId(updusrId)
				.updusrIp(updusrIp)
				.build();
	}


}
