package com.meta.ponkids.domain.qestnar.dto;

import java.util.List;

import com.meta.ponkids.domain.qestnar.entity.QestnarQestn;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QestnarQestnSaveDto {
	
	private Long qestnarQestnSn;
	
	private Long qestnarGroupSn;
	
	private Long qestnarQestnSeq;
	
	private String qestnarQestnItemTyCd;
	
	private String qestnarQestnItemCn;
	
	private String qestnarQestnEssntlYn;
	
	private Long atchFileSn;
	
	private String registerId;      // 등록자 id
	
	private String registerIp;      // 등록자 ip
	
	private String updusrId;      	// 수정자 id
	
	private String updusrIp;      	// 수정자 ip
	
	private List<QestnarQestnDetailSaveDto>	qestnarQestnDetails;	// 입력 항목 ( 선택형시 : 추후 개발 예정)
	
	@Builder
	public QestnarQestnSaveDto(Long qestnarQestnSn, Long qestnarGroupSn, Long qestnarQestnSeq,
			String qestnarQestnItemTyCd, String qestnarQestnItemCn, String qestnarQestnEssntlYn, Long atchFileSn, String registerId,
			String registerIp, String updusrId, String updusrIp) {
		this.qestnarQestnSn = qestnarQestnSn;
		this.qestnarGroupSn = qestnarGroupSn;
		this.qestnarQestnSeq = qestnarQestnSeq;
		this.qestnarQestnItemTyCd = qestnarQestnItemTyCd;
		this.qestnarQestnItemCn = qestnarQestnItemCn;
		this.qestnarQestnEssntlYn = qestnarQestnEssntlYn;
		this.atchFileSn = atchFileSn;
		this.registerId = registerId;
		this.registerIp = registerIp;
		this.updusrId = updusrId;
		this.updusrIp = updusrIp;
	}
	
	// Dto to Entity 메소드 생성
	public QestnarQestn toEntity() {
		return QestnarQestn.builder()
				.qestnarQestnSn(qestnarQestnSn)
				.qestnarGroupSn(qestnarGroupSn)
				.qestnarQestnSeq(qestnarQestnSeq)
				.qestnarQestnItemTyCd(qestnarQestnItemTyCd)
				.qestnarQestnItemCn(qestnarQestnItemCn)
				.qestnarQestnEssntlYn(qestnarQestnEssntlYn)
				.atchFileSn(atchFileSn)
				.registerId(registerId)
				.registerIp(registerIp)
				.updusrId(updusrId)
				.updusrIp(updusrIp)
				.build();
	}

}
