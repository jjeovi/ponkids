package com.meta.ponkids.domain.system.cmmnCd.dto;

import com.meta.ponkids.domain.system.cmmnCd.entity.CmmnCd;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODO Dto 항목 setting
@Data
@NoArgsConstructor
public class CmmnCdSaveDto {
	
	private Long cdSn;
	
	private String registerId;      // 등록자 id
	
	private String registerIp;      // 등록자 ip
	
	// TODO 생성자();
	@Builder
	public CmmnCdSaveDto(Long cdSn) {
		this.cdSn = cdSn;
	}
	
	// TODO toEntity();
	// Dto to Entity 메소드 생성
	public CmmnCd toEntity() {
		return CmmnCd.builder()
				.cdSn(cdSn)
				.build();
	}

}
