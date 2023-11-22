package com.meta.ponkids.domain.system.menu.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.ponkids.domain.system.menu.dto.MenuListDto;

public interface MenuRepositoryCustom {
	
	Page<MenuListDto> getList( MenuListDto listDto, Pageable pageable );

}
