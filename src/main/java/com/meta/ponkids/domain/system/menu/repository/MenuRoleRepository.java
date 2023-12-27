package com.meta.ponkids.domain.system.menu.repository;

import com.meta.ponkids.domain.system.menu.entity.MenuRole;
import com.meta.ponkids.domain.system.menu.repository.custom.MenuRoleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRoleRepository extends JpaRepository<MenuRole, Long>, MenuRoleRepositoryCustom {
    
    void deleteAllByMenuSn( Long sn );
    
    
}
