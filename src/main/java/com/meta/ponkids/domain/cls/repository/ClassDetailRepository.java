package com.meta.ponkids.domain.cls.repository;

import com.meta.ponkids.domain.cls.entity.ClassDetail;
import com.meta.ponkids.domain.cls.repository.custom.ClassDetailRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// PK(*ID) 체크
public interface ClassDetailRepository extends JpaRepository<ClassDetail, Long>, ClassDetailRepositoryCustom {
	
	Optional<ClassDetail> findById( Long pk );	// PK(*ID) 체크
}
