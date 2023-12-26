package com.meta.ponkids.domain.system.menu.repository.custom;

import java.util.List;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import com.meta.ponkids.domain.system.menu.dto.MenuListDto;
import com.meta.ponkids.domain.system.menu.entity.AdminMenuHierarchy;
import com.meta.ponkids.domain.system.menu.entity.UserMenuHierarchy;
import com.meta.ponkids.domain.system.role.dto.RoleListDto;

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
	UserMenuHierarchy findLastUpdtDtUserMenuCache( );
	
	UserMenuHierarchy findLastUpdtDtUserMenuNoCache();
	
	@CachePut("lastUpdtDtUserMenuCache")
	UserMenuHierarchy findLastUpdtDtUserMenuAgainCache();
	
}
