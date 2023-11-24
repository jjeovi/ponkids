package com.meta.ponkids.domain.system.cmmnCd.dto;

import com.meta.ponkids.domain.system.cmmnCd.entity.CmmnCdDetail;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODO Dto 항목 setting
@Data
@NoArgsConstructor
public class CmmnCdDetailSaveDto {
	
	private Long cdDetailSn;
	
	private String registerId;      // 등록자 id
	
	private String registerIp;      // 등록자 ip
	
	// TODO 생성자();
	@Builder
	public CmmnCdDetailSaveDto(Long cdDetailSn) {
		this.cdDetailSn = cdDetailSn;
	}
	
	// TODO toEntity();
	// Dto to Entity 메소드 생성
	public CmmnCdDetail toEntity() {
		return CmmnCdDetail.builder()
				.cdDetailSn(cdDetailSn)
				.build();
	}

}
