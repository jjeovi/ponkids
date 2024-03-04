package com.meta.ponkids.domain.qestnar.dto;

import com.meta.ponkids.domain.qestnar.entity.QestnarQestn;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QestnarQestnModDto {
	
	private Long qestnarQestnSn;
	
	private Long qestnarGroupSn;
	
	private Long qestnarQestnSeq;
	
	private String qestnarQestnItemTyCd;
	
	private String qestnarQestnItemCn;
	
	private String qestnarQestnEssntlYn;
	
	private Long atchFileSn;
	
    private String updusrId;      	// 수정자 ID
    
    private String updusrIp;      	// 수정자 IP
	
	//builder 생성
	@Builder
	public QestnarQestnModDto(Long qestnarQestnSn, Long qestnarGroupSn, Long qestnarQestnSeq,
			String qestnarQestnItemTyCd, String qestnarQestnItemCn, String qestnarQestnEssntlYn, Long atchFileSn, String updusrId,
			String updusrIp) {
		this.qestnarQestnSn = qestnarQestnSn;
		this.qestnarGroupSn = qestnarGroupSn;
		this.qestnarQestnSeq = qestnarQestnSeq;
		this.qestnarQestnItemTyCd = qestnarQestnItemTyCd;
		this.qestnarQestnItemCn = qestnarQestnItemCn;
		this.qestnarQestnEssntlYn = qestnarQestnEssntlYn;
		this.atchFileSn = atchFileSn;
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
				.updusrId(updusrId)
				.updusrIp(updusrIp)
				.build();
	}
	
	// Entity to Dto 메소드는 DTO 내부에서 생성.
	public QestnarQestnModDto toDto(QestnarQestn qestnarQestn) {
		return QestnarQestnModDto.builder()
				.qestnarQestnSn(qestnarQestn.getQestnarQestnSn())
				.qestnarGroupSn(qestnarQestn.getQestnarGroupSn())
				.qestnarQestnSeq(qestnarQestn.getQestnarQestnSeq())
				.qestnarQestnItemTyCd(qestnarQestn.getQestnarQestnItemTyCd())
				.qestnarQestnItemCn(qestnarQestn.getQestnarQestnItemCn())
				.qestnarQestnEssntlYn(qestnarQestn.getQestnarQestnEssntlYn())
				.atchFileSn(qestnarQestn.getAtchFileSn())
				.updusrId(qestnarQestn.getUpdusrId())
				.updusrIp(qestnarQestn.getUpdusrIp())
				.build();
	}



}
