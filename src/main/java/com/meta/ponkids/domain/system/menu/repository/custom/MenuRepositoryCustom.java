package com.meta.ponkids.domain.system.menu.repository.custom;

import com.meta.ponkids.domain.system.menu.dto.MenuListDto;

import java.util.List;

public interface MenuRepositoryCustom {
	
	List<MenuListDto> getList( MenuListDto listDto );

}
