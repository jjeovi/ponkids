package com.meta.ponkids.domain.system.menu.repository;

import com.meta.ponkids.domain.system.menu.entity.UserMenuHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMenuHierarchyRepository extends JpaRepository<UserMenuHierarchy, Long> {
    
    UserMenuHierarchy findTop1ByMenuUrlOrderByMenuSn( String menuUrl );
    
    void deleteByMenuSn( Long sn );
    
}