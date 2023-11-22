package com.meta.ponkids.domain.system.menu.dto;

import com.meta.ponkids.domain.system.menu.entity.Menu;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODO Dto 항목 setting
@Data
@NoArgsConstructor
public class MenuSaveDto {
	
	private Long menuSn;
	
	private String registerId;      // 등록자 id
	
	private String registerIp;      // 등록자 ip
	
	// TODO 생성자();
	@Builder
	public MenuSaveDto(Long menuSn) {
		this.menuSn = menuSn;
	}
	
	// TODO toEntity();
	// Dto to Entity 메소드 생성
	public Menu toEntity() {
		return Menu.builder()
				.menuSn(menuSn)
				.build();
	}

}
