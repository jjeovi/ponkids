package com.meta.ponkids.domain.cls.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meta.ponkids.domain.cls.entity.ClassInqry;
import com.meta.ponkids.domain.cls.repository.custom.ClassInqryRepositoryCustom;

public interface ClassInqryRepository extends JpaRepository<ClassInqry, Long>, ClassInqryRepositoryCustom {
	
	Optional<ClassInqry> findById( Long pk );	// TODO PK(*ID) 체크
	
	List<ClassInqry> findByStepAndParntsInqrySn(String step, Long parntsInqrySn);
}
