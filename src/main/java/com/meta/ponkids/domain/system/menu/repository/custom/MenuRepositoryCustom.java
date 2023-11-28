package com.meta.ponkids.domain.system.menu.repository.custom;

import com.meta.ponkids.domain.system.menu.dto.MenuListDto;
import com.meta.ponkids.domain.system.role.dto.RoleListDto;

import java.util.List;

public interface MenuRepositoryCustom {
	
	List<MenuListDto> getList( MenuListDto listDto );
	
	List<RoleListDto> getPossibleAuthListAjax( MenuListDto listDto );

}
