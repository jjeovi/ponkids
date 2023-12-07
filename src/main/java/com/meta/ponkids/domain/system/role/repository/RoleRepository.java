package com.meta.ponkids.domain.system.role.repository;

import com.meta.ponkids.domain.system.role.entity.Role;
import com.meta.ponkids.domain.system.role.repository.custom.RoleRepositoryCustom;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> , RoleRepositoryCustom {
	
	List<Role> findAllByOrderByRoleSn();


}
