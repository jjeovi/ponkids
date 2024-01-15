package com.meta.ponkids.domain.cls.repository;

import com.meta.ponkids.domain.cls.entity.ClassDetailOptn;
import com.meta.ponkids.domain.cls.repository.custom.ClassDetailOptnRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// TODO PK(*ID) 체크
public interface ClassDetailOptnRepository extends JpaRepository<ClassDetailOptn, Long>, ClassDetailOptnRepositoryCustom {
	
	Optional<ClassDetailOptn> findById( Long pk );	// PK(*ID) 체크
}
