package com.meta.ponkids.domain.system.menu.repository.custom;

import com.meta.ponkids.domain.system.menu.dto.MenuListDto;
import com.meta.ponkids.domain.system.menu.entity.Menu;
import com.meta.ponkids.domain.system.role.dto.RoleListDto;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface MenuRepositoryCustom {
	
	@Cacheable("menuListCache")
	List<MenuListDto> getList( MenuListDto listDto );
	
	@CachePut("menuListCache")
	List<MenuListDto> getListAgain( MenuListDto listDto );
	
	@Cacheable("menuListCache")
	List<MenuListDto> getAllList( MenuListDto listDto );
	
	@CachePut("menuListCache")
	List<MenuListDto> getAllListAgain( MenuListDto listDto );
	
	List<RoleListDto> getPossibleRoleListAjax( MenuListDto listDto );
	
	@Cacheable("lastUpdtDtMenuCache")
	Menu findLastUpdtDtMenuCache(long sn);
	
	Menu findLastUpdtDtMenuNoCache(long sn);
	
	@CachePut("lastUpdtDtMenuCache")
	Menu findLastUpdtDtMenuAgainCache(long sn);
	
	
}
