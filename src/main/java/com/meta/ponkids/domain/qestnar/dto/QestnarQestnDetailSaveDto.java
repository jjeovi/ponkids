package com.meta.ponkids.domain.qestnar.dto;

import com.meta.ponkids.domain.qestnar.entity.QestnarQestnDetail;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QestnarQestnDetailSaveDto {
	
	private Long qestnarQestnDetailSn;
	
	private Long qestnarQestnSn;
	
	private Long qestnarQestnDetailSeq;
	
	private String qestnarQestnDetailCn;
	
	private String registerId;      // 등록자 id
	
	private String registerIp;      // 등록자 ip
	
	private String updusrId;      	// 수정자 id
	
	private String updusrIp;      	// 수정자 ip
	
	@Builder
	public QestnarQestnDetailSaveDto(Long qestnarQestnDetailSn, Long qestnarQestnSn, Long qestnarQestnDetailSeq,
			String qestnarQestnDetailCn, String registerId, String registerIp, String updusrId, String updusrIp) {
		this.qestnarQestnDetailSn = qestnarQestnDetailSn;
		this.qestnarQestnSn = qestnarQestnSn;
		this.qestnarQestnDetailSeq = qestnarQestnDetailSeq;
		this.qestnarQestnDetailCn = qestnarQestnDetailCn;
		this.registerId = registerId;
		this.registerIp = registerIp;
		this.updusrId = updusrId;
		this.updusrIp = updusrIp;
	}
	
	// Dto to Entity 메소드 생성
	public QestnarQestnDetail toEntity() {
		return QestnarQestnDetail.builder()
				.qestnarQestnDetailSn(qestnarQestnDetailSn)
				.qestnarQestnSn(qestnarQestnSn)
				.qestnarQestnDetailSeq(qestnarQestnDetailSeq)
				.qestnarQestnDetailCn(qestnarQestnDetailCn)
				.registerId(registerId)
				.registerIp(registerIp)
				.updusrId(updusrId)
				.updusrIp(updusrIp)
				.build();
	}


}
