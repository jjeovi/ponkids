package com.meta.ponkids.domain.adm.system.menu.repository;

import com.meta.ponkids.domain.adm.system.menu.entity.UserMenuHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMenuHierarchyRepository extends JpaRepository<UserMenuHierarchy, Long> {
    
    UserMenuHierarchy findTop1ByMenuUrlAndDelYnOrderByMenuSn( String menuUrl , String delYn);
    
    void deleteByMenuSn( Long sn );
    
}