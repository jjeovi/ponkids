package com.meta.ponkids.domain.system.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meta.ponkids.domain.system.menu.entity.MenuRole;
import com.meta.ponkids.domain.system.menu.repository.custom.MenuRoleRepositoryCustom;

public interface MenuRoleRepository extends JpaRepository<MenuRole, Long>, MenuRoleRepositoryCustom {
	
	void deleteAllByMenuSn( Long sn );
	

}
