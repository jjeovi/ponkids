package com.meta.ponkids.domain.system.banner.dto;

import com.meta.ponkids.domain.system.banner.entity.Banner;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODO Dto 항목 setting
@Data
@NoArgsConstructor
public class BannerSaveDto {
	
	private Long bannerSn;
	
	private String registerId;      // 등록자 id
	
	private String registerIp;      // 등록자 ip
	
	// TODO 생성자();
	@Builder
	public BannerSaveDto(Long bannerSn) {
		this.bannerSn = bannerSn;
	}
	
	// TODO toEntity();
	// Dto to Entity 메소드 생성
	public Banner toEntity() {
		return Banner.builder()
				.bannerSn(bannerSn)
				.build();
	}

}
