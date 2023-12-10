package com.meta.ponkids.domain.system.menu.repository.custom;

import com.meta.ponkids.domain.system.menu.dto.MenuListDto;
import com.meta.ponkids.domain.system.menu.entity.AdminMenuHierarchy;
import com.meta.ponkids.domain.system.menu.entity.Menu;
import com.meta.ponkids.domain.system.menu.entity.UserMenuHierarchy;
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
	List<MenuListDto> getUserMenuList( MenuListDto listDto );
	
	@CachePut("menuListCache")
	List<MenuListDto> getUserMenuListAgain( MenuListDto listDto );
	
	@Cacheable("menuListCache")
	List<MenuListDto> getAllList( MenuListDto listDto );
	
	@CachePut("menuListCache")
	List<MenuListDto> getAllListAgain( MenuListDto listDto );
	
	List<RoleListDto> getPossibleRoleListAjax( MenuListDto listDto );
	
	@Cacheable("lastUpdtDtAdminMenuCache")
	AdminMenuHierarchy findLastUpdtDtAdminMenuCache( long sn);
	
	AdminMenuHierarchy findLastUpdtDtAdminMenuNoCache(long sn);
	
	@CachePut("lastUpdtDtAdminMenuCache")
	AdminMenuHierarchy findLastUpdtDtAdminMenuAgainCache(long sn);
	
	
	@Cacheable("lastUpdtDtUserMenuCache")
	UserMenuHierarchy findLastUpdtDtUserMenuCache( long sn);
	
	UserMenuHierarchy findLastUpdtDtUserMenuNoCache(long sn);
	
	@CachePut("lastUpdtDtUserMenuCache")
	UserMenuHierarchy findLastUpdtDtUserMenuAgainCache(long sn);
	
}
