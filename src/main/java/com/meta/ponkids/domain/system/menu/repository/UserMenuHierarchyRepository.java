package com.meta.ponkids.domain.system.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meta.ponkids.domain.system.menu.entity.UserMenuHierarchy;

public interface UserMenuHierarchyRepository extends JpaRepository<UserMenuHierarchy, Long> {
    
    UserMenuHierarchy findTop1ByMenuUrlAndDelYnOrderByMenuSn( String menuUrl , String delYn);
    

    UserMenuHierarchy findTop1ByMenuCdAndDelYnOrderByMenuSn( String menuCd, String delYn );
    
    UserMenuHierarchy findTop1ByMenuCdAndDelYnAndLevelNotOrderByMenuSn( String menuCd, String delYn, Long level );
    
    
    void deleteByMenuSn( Long sn );
    
}