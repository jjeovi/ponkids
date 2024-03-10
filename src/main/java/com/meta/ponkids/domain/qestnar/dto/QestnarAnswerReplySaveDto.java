package com.meta.ponkids.domain.qestnar.dto;

import com.meta.ponkids.domain.qestnar.entity.QestnarAnswerReply;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QestnarAnswerReplySaveDto {
	
	private Long qestnarAnswerReplySn;
	
	private Long qestnarAnswerSn;
	
	private Long userSn;
	
	private String qestnarAnswerReplyCn;
	
	private Long atchFileSn;
	
	private String registerId;      // 등록자 id
	
	private String registerIp;      // 등록자 ip
	
	private String updusrId;      	// 수정자 id
	
	private String updusrIp;      	// 수정자 ip
	
	@Builder
	public QestnarAnswerReplySaveDto(Long qestnarAnswerReplySn, Long qestnarAnswerSn, Long userSn,
			String qestnarAnswerReplyCn, Long atchFileSn, String registerId, String registerIp, String updusrId, String updusrIp ) {
		this.qestnarAnswerReplySn = qestnarAnswerReplySn;
		this.qestnarAnswerSn = qestnarAnswerSn;
		this.userSn = userSn;
		this.qestnarAnswerReplyCn = qestnarAnswerReplyCn;
		this.atchFileSn = atchFileSn;
		this.registerId = registerId;
		this.registerIp = registerIp;
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
				.registerId(registerId)
				.registerIp(registerIp)
				.updusrId(updusrId)
				.updusrIp(updusrIp)
				.build();
	}

}
