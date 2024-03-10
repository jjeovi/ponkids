package com.meta.ponkids.domain.qestnar.dto;

import com.meta.ponkids.domain.qestnar.entity.QestnarAnswerReply;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO Dto 항목 setting
@Data
@NoArgsConstructor
public class QestnarAnswerReplyModDto {
	
	
	private Long qestnarAnswerReplySn;
	
	private Long qestnarAnswerSn;
	
	private Long userSn;
	
	private String qestnarAnswerReplyCn;
	
	private Long atchFileSn;
	
    private String updusrId;      	// 수정자 ID
    
    private String updusrIp;      	// 수정자 IP
	
	//builder 생성
	@Builder
	public QestnarAnswerReplyModDto (Long qestnarAnswerReplySn, Long qestnarAnswerSn, Long userSn,
			String qestnarAnswerReplyCn, Long atchFileSn, String updusrId, String updusrIp) {
		this.qestnarAnswerReplySn = qestnarAnswerReplySn;
		this.qestnarAnswerSn = qestnarAnswerSn;
		this.userSn = userSn;
		this.qestnarAnswerReplyCn = qestnarAnswerReplyCn;
		this.atchFileSn = atchFileSn;
		this.updusrId = updusrId;
		this.updusrIp = updusrIp;
	}
	
	// Dto to Entity 메소드 생성
	public QestnarAnswerReply toEntity() {
		return QestnarAnswerReply.builder()
				.qestnarAnswerReplySn(qestnarAnswerReplySn)
				.qestnarAnswerSn(qestnarAnswerSn)
				.userSn(userSn)
				.qestnarAnswerReplyCn(qestnarAnswerReplyCn)
				.atchFileSn(atchFileSn)
				.updusrId(updusrId)
				.updusrIp(updusrIp)
				.build();
	}
	
	// Entity to Dto 메소드는 DTO 내부에서 생성.
	public QestnarAnswerReplyModDto toDto(QestnarAnswerReply qestnarAnswerReply) {
		return QestnarAnswerReplyModDto.builder()
				.qestnarAnswerReplySn(qestnarAnswerReply.getQestnarAnswerReplySn())
				.qestnarAnswerSn(qestnarAnswerReply.getQestnarAnswerSn())
				.userSn(qestnarAnswerReply.getUserSn())
				.qestnarAnswerReplyCn(qestnarAnswerReply.getQestnarAnswerReplyCn())
				.atchFileSn(qestnarAnswerReply.getAtchFileSn())
				.updusrId(qestnarAnswerReply.getUpdusrId())
				.updusrIp(qestnarAnswerReply.getUpdusrIp())
				.build();
	}

}
