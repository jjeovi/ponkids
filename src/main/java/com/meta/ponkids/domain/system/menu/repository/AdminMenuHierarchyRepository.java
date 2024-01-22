package com.meta.ponkids.domain.system.menu.repository;

import com.meta.ponkids.domain.system.menu.entity.AdminMenuHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminMenuHierarchyRepository extends JpaRepository<AdminMenuHierarchy, Long> {
    
    AdminMenuHierarchy findTop1ByMenuUrlAndDelYnOrderByMenuSn( String menuUrl, String delYn );
    
    
    AdminMenuHierarchy findTop1ByMenuCdAndDelYnOrderByMenuSn( String menuCd, String delYn );
    
    void deleteByMenuSn( Long sn );
    
}