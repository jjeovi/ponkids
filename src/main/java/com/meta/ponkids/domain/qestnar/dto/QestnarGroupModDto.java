package com.meta.ponkids.domain.qestnar.dto;

import java.util.List;

import com.meta.ponkids.domain.qestnar.entity.QestnarGroup;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QestnarGroupModDto {

	private Long qestnarGroupSn;

	private String qestnarGroupNm;

	private String qestnarGroupDc;

	private String privcyYn;

	private String useYn;

	private String updusrId;      	// 수정자 ID

	private String updusrIp;      	// 수정자 IP
	
	private List<QestnarQestnSaveDto>		qestnarQestns;		// 입력 항목
    
//    private List<QstnarQestnDetailSaveDto>	qestnarQestnDetails;	// 입력 항목 ( 선택형시 : 추후 개발 예정)
	
	//builder 생성
	@Builder
	public QestnarGroupModDto(Long qestnarGroupSn, String qestnarGroupNm, String qestnarGroupDc,
			String privcyYn, String useYn, String updusrId, String updusrIp) {
		this.qestnarGroupSn = qestnarGroupSn;
		this.qestnarGroupNm = qestnarGroupNm;
		this.qestnarGroupDc = qestnarGroupDc;
		this.privcyYn = privcyYn;
		this.useYn = useYn;
		this.updusrId = updusrId;
		this.updusrIp = updusrIp;
	}
	
	// Dto to Entity 메소드 생성
	public QestnarGroup toEntity() {
		return QestnarGroup.builder()
				.qestnarGroupSn(qestnarGroupSn)
				.qestnarGroupNm(qestnarGroupNm)
				.qestnarGroupDc(qestnarGroupDc)
				.privcyYn(privcyYn)
				.useYn(useYn)
				.updusrId(updusrId)
				.updusrIp(updusrIp)
				.build();
	}
	
	// Entity to Dto 메소드는 DTO 내부에서 생성.
	public QestnarGroupModDto toDto(QestnarGroup qestnarGroup) {
		return QestnarGroupModDto.builder()
				.qestnarGroupSn(qestnarGroup.getQestnarGroupSn())
				.qestnarGroupNm(qestnarGroup.getQestnarGroupNm())
				.qestnarGroupDc(qestnarGroup.getQestnarGroupDc())
				.privcyYn(qestnarGroup.getPrivcyYn())
				.useYn(qestnarGroup.getUseYn())
				.updusrId(qestnarGroup.getUpdusrId())
				.updusrIp(qestnarGroup.getUpdusrIp())
				.build();
	}

}
