package com.meta.ponkids.domain.system.menu.repository;

import com.meta.ponkids.domain.system.menu.entity.AdminMenuHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminMenuHierarchyRepository extends JpaRepository<AdminMenuHierarchy, Long> {
	
	AdminMenuHierarchy findTop1ByMenuUrlOrderByMenuSn(String menuUrl);
	
	void deleteByMenuSn (Long sn);

}