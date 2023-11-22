package com.meta.ponkids.domain.system.banner.dto;

import com.meta.ponkids.domain.system.banner.entity.Banner;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO Dto 항목 setting
@Data
@NoArgsConstructor
public class BannerModDto {
	
	
	private Long bannerSn;
	
    private String updusrId;      	// 수정자 ID
    
    private String updusrIp;      	// 수정자 IP
	
	// TODO 생성자();
	//builder 생성
	@Builder
	public BannerModDto (Long bannerSn) {
		this.bannerSn = bannerSn;
	}
	
	
	// TODO toEntity();
	// Dto to Entity 메소드 생성
	public Banner toEntity() {
		return Banner.builder()
				.bannerSn(bannerSn)
				.build();
	}
	
	
	//TODO toDto();
	// Entity to Dto 메소드는 DTO 내부에서 생성.
	public BannerModDto toDto(Banner banner) {
		return BannerModDto.builder()
				.bannerSn(banner.getBannerSn())
				.build();
	}

}
