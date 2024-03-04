package com.meta.ponkids.domain.qestnar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meta.ponkids.domain.qestnar.entity.QestnarQestn;
import com.meta.ponkids.domain.qestnar.repository.custom.QestnarQestnRepositoryCustom;

// TODO PK(*ID) 체크
public interface QestnarQestnRepository extends JpaRepository<QestnarQestn, Long>, QestnarQestnRepositoryCustom {
	
	Optional<QestnarQestn> findById( Long pk );	// TODO PK(*ID) 체크
}
