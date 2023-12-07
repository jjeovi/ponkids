package com.meta.ponkids.domain.system.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meta.ponkids.domain.system.menu.entity.AdminMenuHierarchy;

public interface AdminMenuHierarchyRepository extends JpaRepository<AdminMenuHierarchy, Long> {
	
	AdminMenuHierarchy findTop1ByMenuUrlOrderByMenuSn(String menuUrl);
	
	void deleteByMenuSn (Long sn);

}
