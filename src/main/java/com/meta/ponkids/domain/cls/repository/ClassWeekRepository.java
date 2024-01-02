package com.meta.ponkids.domain.cls.repository;

import com.meta.ponkids.domain.cls.entity.ClassWeek;
import com.meta.ponkids.domain.cls.repository.custom.ClassWeekRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// TODO PK(*ID) 체크
public interface ClassWeekRepository extends JpaRepository<ClassWeek, Long>, ClassWeekRepositoryCustom {
	
	Optional<ClassWeek> findById( Long pk );	// TODO PK(*ID) 체크
}
