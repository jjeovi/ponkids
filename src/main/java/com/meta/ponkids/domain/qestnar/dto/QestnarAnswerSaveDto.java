package com.meta.ponkids.domain.qestnar.dto;

import com.meta.ponkids.domain.qestnar.entity.QestnarAnswer;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QestnarAnswerSaveDto {
	
	private Long qestnarAnswerSn;
	
	private Long qestnarGroupSn;
	
	private Long userSn;
	
	private String registerId;      // 등록자 id
	
	private String registerIp;      // 등록자 ip
	
	private String updusrId;      	// 수정자 id
	
	private String updusrIp;      	// 수정자 ip
	
	@Builder
	public QestnarAnswerSaveDto(Long qestnarAnswerSn, Long qestnarGroupSn, Long userSn, String registerId, String registerIp, String updusrId, String updusrIp ) {
		this.qestnarAnswerSn = qestnarAnswerSn;
		this.qestnarGroupSn = qestnarGroupSn;
		this.userSn = userSn;
		this.registerId = registerId;
		this.registerIp = registerIp;
		this.updusrId = updusrId;
		this.updusrIp = updusrIp;
	}
	
	// Dto to Entity 메소드 생성
	public QestnarAnswer toEntity() {
		return QestnarAnswer.builder()
				.qestnarAnswerSn(qestnarAnswerSn)
				.qestnarGroupSn(qestnarGroupSn)
				.userSn(userSn)
				.registerId(registerId)
				.registerIp(registerIp)
				.updusrId(updusrId)
				.updusrIp(updusrIp)
				.build();
	}

}
