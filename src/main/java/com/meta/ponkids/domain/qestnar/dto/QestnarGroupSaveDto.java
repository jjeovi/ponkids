package com.meta.ponkids.domain.qestnar.dto;

import java.util.List;

import com.meta.ponkids.domain.qestnar.entity.QestnarGroup;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QestnarGroupSaveDto {
	
	private Long qestnarGroupSn;

	private String qestnarGroupNm;

	private String qestnarGroupDc;

	private String privcyYn;

	private String useYn;
	
	private List<QestnarQestnSaveDto>		qestnarQestns;		// 입력 항목
    
//    private List<QstnarQestnDetailSaveDto>	qestnarQestnDetails;	// 입력 항목 ( 선택형시 : 추후 개발 예정)
	
	private String registerId;      // 등록자 id
	
	private String registerIp;      // 등록자 ip
	
	private String updusrId;      	// 수정자 id
	
	private String updusrIp;      	// 수정자 ip
	
	// TODO 생성자();
	@Builder
	public QestnarGroupSaveDto(Long qestnarGroupSn, String qestnarGroupNm, String qestnarGroupDc,
			String privcyYn, String useYn, String registerId, String registerIp, String updusrId, String updusrIp) {
		this.qestnarGroupSn = qestnarGroupSn;
		this.qestnarGroupNm = qestnarGroupNm;
		this.qestnarGroupDc = qestnarGroupDc;
		this.privcyYn = privcyYn;
		this.useYn = useYn;
		this.registerId = registerId;
		this.registerIp = registerIp;
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
				.registerId(registerId)
				.registerIp(registerIp)
				.updusrId(updusrId)
				.updusrIp(updusrIp)
				.build();
	}

}
