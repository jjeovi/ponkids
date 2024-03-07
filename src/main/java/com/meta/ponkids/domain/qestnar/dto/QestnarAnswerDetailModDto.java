package com.meta.ponkids.domain.qestnar.dto;

import com.meta.ponkids.domain.qestnar.entity.QestnarAnswerDetail;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QestnarAnswerDetailModDto {
	
	private Long 	qestnarAnswerDetailSn;
	
	private Long 	qestnarAnswerSn;
	
	private Long 	qestnarQestnSn;
	
	private String 	qestnarAnswer;				// 입력유형이 주관식일 경우
	
	private Long 	qestnarQestnDetailSn;		// 입력유형이 객관식일 경우
	
	private Long 	atchFileSn;					// 입력유형이 첨부파일일 경우
	
    private String updusrId;      	// 수정자 ID
    
    private String updusrIp;      	// 수정자 IP
	
	//builder 생성
	@Builder
	public QestnarAnswerDetailModDto(Long qestnarAnswerDetailSn, Long qestnarAnswerSn, Long qestnarQestnSn,
			String qestnarAnswer, Long qestnarQestnDetailSn, Long atchFileSn, String updusrId, String updusrIp) {
		this.qestnarAnswerDetailSn = qestnarAnswerDetailSn;
		this.qestnarAnswerSn = qestnarAnswerSn;
		this.qestnarQestnSn = qestnarQestnSn;
		this.qestnarAnswer = qestnarAnswer;
		this.qestnarQestnDetailSn = qestnarQestnDetailSn;
		this.atchFileSn = atchFileSn;
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
				.updusrId(updusrId)
				.updusrIp(updusrIp)
				.build();
	}
	
	// Entity to Dto 메소드는 DTO 내부에서 생성.
	public QestnarAnswerDetailModDto toDto(QestnarAnswerDetail qestnarAnswerDetail) {
		return QestnarAnswerDetailModDto.builder()
				.qestnarAnswerDetailSn(qestnarAnswerDetail.getQestnarAnswerDetailSn())
				.qestnarAnswerSn(qestnarAnswerDetail.getQestnarAnswerSn())
				.qestnarQestnSn(qestnarAnswerDetail.getQestnarQestnSn())
				.qestnarAnswer(qestnarAnswerDetail.getQestnarAnswer())
				.qestnarQestnDetailSn(qestnarAnswerDetail.getQestnarQestnDetailSn())
				.atchFileSn(qestnarAnswerDetail.getAtchFileSn())
				.updusrId(qestnarAnswerDetail.getUpdusrId())
				.updusrIp(qestnarAnswerDetail.getUpdusrIp())
				.build();
	}

	

}
