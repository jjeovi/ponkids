package com.meta.ponkids.domain.qestnar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meta.ponkids.domain.qestnar.entity.QestnarGroup;
import com.meta.ponkids.domain.qestnar.repository.custom.QestnarGroupRepositoryCustom;

// TODO PK(*ID) 체크
public interface QestnarGroupRepository extends JpaRepository<QestnarGroup, Long>, QestnarGroupRepositoryCustom {
	
	Optional<QestnarGroup> findById( Long pk );	// TODO PK(*ID) 체크
}
