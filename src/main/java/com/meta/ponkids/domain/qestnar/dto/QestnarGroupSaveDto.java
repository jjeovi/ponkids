package com.meta.ponkids.domain.qestnar.dto;

import java.util.List;

import com.meta.ponkids.domain.cls.dto.ClassSaveDto;
import com.meta.ponkids.domain.qestnar.entity.QestnarGroup;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode( callSuper = false )
public class QestnarGroupSaveDto  extends QestnarGroupDto {
	
	private Long qestnarGroupSn;

	private String qestnarGroupCd;
	
	private String qestnarGroupNm;

	private String qestnarGroupDc;
	
	private String upendGdccSetYn;	// 상단 안내문 설정 여부
	
	private String upendGdcc;		// 상단 안내문
	
	private String lptGdccSetYn;	// 상단 안내문 설정 여부
	
	private String lptGdcc;			// 하단 안내문

	private String privcyYn;

	private String useYn;
	
	private List<QestnarQestnSaveDto>		qestnarQestns;		// 입력 항목
    
	private String registerId;      // 등록자 id
	
	private String registerIp;      // 등록자 ip
	
	private String updusrId;      	// 수정자 id
	
	private String updusrIp;      	// 수정자 ip
	
	@Builder
	public QestnarGroupSaveDto(Long qestnarGroupSn, String qestnarGroupCd, String qestnarGroupNm, String qestnarGroupDc,
			String upendGdccSetYn, String upendGdcc, String lptGdccSetYn, String lptGdcc,
			String privcyYn, String useYn, String registerId, String registerIp, String updusrId, String updusrIp) {
		this.qestnarGroupSn = qestnarGroupSn;
		this.qestnarGroupCd = qestnarGroupCd;
		this.qestnarGroupNm = qestnarGroupNm;
		this.qestnarGroupDc = qestnarGroupDc;
		this.upendGdccSetYn = upendGdccSetYn;
		this.upendGdcc = upendGdcc;
		this.lptGdccSetYn = lptGdccSetYn;
		this.lptGdcc = lptGdcc;
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
				.qestnarGroupCd(qestnarGroupCd)
				.qestnarGroupNm(qestnarGroupNm)
				.qestnarGroupDc(qestnarGroupDc)
				.upendGdccSetYn(upendGdccSetYn)
				.upendGdcc(upendGdcc)
				.lptGdccSetYn(lptGdccSetYn)
				.lptGdcc(lptGdcc)
				.privcyYn(privcyYn)
				.useYn(useYn)
				.registerId(registerId)
				.registerIp(registerIp)
				.updusrId(updusrId)
				.updusrIp(updusrIp)
				.build();
	}

}
