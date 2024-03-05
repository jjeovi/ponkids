package com.meta.ponkids.domain.qestnar.dto;

import com.meta.ponkids.domain.qestnar.entity.QestnarQestnDetail;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QestnarQestnDetailModDto {
	
	
	private Long qestnarQestnDetailSn;
	
	private Long qestnarQestnSn;
	
	private Long qestnarQestnDetailSeq;
	
	private String qestnarQestnDetailCn;
	
    private String updusrId;      	// 수정자 ID
    
    private String updusrIp;      	// 수정자 IP
	
	//builder 생성
	@Builder
	public QestnarQestnDetailModDto (Long qestnarQestnDetailSn, Long qestnarQestnSn, Long qestnarQestnDetailSeq,
			String qestnarQestnDetailCn, String updusrId, String updusrIp) {
		this.qestnarQestnDetailSn = qestnarQestnDetailSn;
		this.qestnarQestnSn = qestnarQestnSn;
		this.qestnarQestnDetailSeq = qestnarQestnDetailSeq;
		this.qestnarQestnDetailCn = qestnarQestnDetailCn;
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
				.updusrId(updusrId)
				.updusrIp(updusrIp)
				.build();
	}
	
	// Entity to Dto 메소드는 DTO 내부에서 생성.
	public QestnarQestnDetailModDto toDto(QestnarQestnDetail qestnarQestnDetail) {
		return QestnarQestnDetailModDto.builder()
				.qestnarQestnDetailSn(qestnarQestnDetail.getQestnarQestnDetailSn())
				.qestnarQestnSn(qestnarQestnDetail.getQestnarQestnSn())
				.qestnarQestnDetailSeq(qestnarQestnDetail.getQestnarQestnDetailSeq())
				.qestnarQestnDetailCn(qestnarQestnDetail.getQestnarQestnDetailCn())
				.updusrId(qestnarQestnDetail.getUpdusrId())
				.updusrIp(qestnarQestnDetail.getUpdusrIp())
				.build();
	}

}
