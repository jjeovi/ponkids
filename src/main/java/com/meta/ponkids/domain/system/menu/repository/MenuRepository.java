package com.meta.ponkids.domain.system.menu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meta.ponkids.domain.system.menu.entity.Menu;
import com.meta.ponkids.domain.system.menu.repository.custom.MenuRepositoryCustom;

// TODO PK(*ID) 체크
public interface MenuRepository extends JpaRepository<Menu, Long>, MenuRepositoryCustom {
	
	Optional<Menu> findById( Long pk );	// TODO PK(*ID) 체크
}
