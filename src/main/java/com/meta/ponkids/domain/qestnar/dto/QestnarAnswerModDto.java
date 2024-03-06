package com.meta.ponkids.domain.qestnar.dto;

import com.meta.ponkids.domain.qestnar.entity.QestnarAnswer;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO Dto 항목 setting
@Data
@NoArgsConstructor
public class QestnarAnswerModDto {
	
	
	private Long qestnarAnswerSn;
	
	private Long qestnarGroupSn;
	
	private Long userSn;
	
    private String updusrId;      	// 수정자 ID
    
    private String updusrIp;      	// 수정자 IP
	
	//builder 생성
	@Builder
	public QestnarAnswerModDto (Long qestnarAnswerSn, Long qestnarGroupSn, Long userSn,  String updusrId, String updusrIp) {
		this.qestnarAnswerSn = qestnarAnswerSn;
		this.qestnarGroupSn = qestnarGroupSn;
		this.userSn = userSn;
		this.updusrId = updusrId;
		this.updusrIp = updusrIp;
	}
	
	// Dto to Entity 메소드 생성
	public QestnarAnswer toEntity() {
		return QestnarAnswer.builder()
				.qestnarAnswerSn(qestnarAnswerSn)
				.qestnarGroupSn(qestnarGroupSn)
				.userSn(userSn)
				.updusrId(updusrId)
				.updusrIp(updusrIp)
				.build();
	}
	
	// Entity to Dto 메소드는 DTO 내부에서 생성.
	public QestnarAnswerModDto toDto(QestnarAnswer qestnarAnswer) {
		return QestnarAnswerModDto.builder()
				.qestnarAnswerSn(qestnarAnswer.getQestnarAnswerSn())
				.qestnarGroupSn(qestnarAnswer.getQestnarGroupSn())
				.userSn(qestnarAnswer.getUserSn())
				.updusrId(qestnarAnswer.getUpdusrId())
				.updusrIp(qestnarAnswer.getUpdusrIp())
				.build();
	}

}
