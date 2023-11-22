package com.meta.ponkids.domain.system.menu.dto;

import com.meta.ponkids.domain.system.menu.entity.Menu;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO Dto 항목 setting
@Data
@NoArgsConstructor
public class MenuModDto {
	
	
	private Long menuSn;
	
    private String updusrId;      	// 수정자 ID
    
    private String updusrIp;      	// 수정자 IP
	
	// TODO 생성자();
	//builder 생성
	@Builder
	public MenuModDto (Long menuSn) {
		this.menuSn = menuSn;
	}
	
	
	// TODO toEntity();
	// Dto to Entity 메소드 생성
	public Menu toEntity() {
		return Menu.builder()
				.menuSn(menuSn)
				.build();
	}
	
	
	//TODO toDto();
	// Entity to Dto 메소드는 DTO 내부에서 생성.
	public MenuModDto toDto(Menu menu) {
		return MenuModDto.builder()
				.menuSn(menu.getMenuSn())
				.build();
	}

}
