package com.meta.ponkids.domain.system.menu.repository;

import com.meta.ponkids.domain.system.menu.entity.Menu;
import com.meta.ponkids.domain.system.menu.repository.custom.MenuRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// TODO PK(*ID) 체크
public interface MenuRepository extends JpaRepository<Menu, Long>, MenuRepositoryCustom {
	
	Optional<Menu> findById( Long pk );	// TODO PK(*ID) 체크
	
}
