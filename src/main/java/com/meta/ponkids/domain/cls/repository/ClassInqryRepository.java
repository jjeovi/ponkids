package com.meta.ponkids.domain.cls.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meta.ponkids.domain.cls.entity.ClassInqry;
import com.meta.ponkids.domain.cls.repository.custom.ClassInqryRepositoryCustom;

// TODO PK(*ID) 체크
public interface ClassInqryRepository extends JpaRepository<ClassInqry, Long>, ClassInqryRepositoryCustom {
	
	Optional<ClassInqry> findById( Long pk );	// TODO PK(*ID) 체크
}
