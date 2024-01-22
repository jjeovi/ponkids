package com.meta.ponkids.domain.system.role.repository;

import com.meta.ponkids.domain.system.role.entity.Role;
import com.meta.ponkids.domain.system.role.repository.custom.RoleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer>, RoleRepositoryCustom {
    
    List<Role> findAllByOrderByRoleSn();
    
    
}
