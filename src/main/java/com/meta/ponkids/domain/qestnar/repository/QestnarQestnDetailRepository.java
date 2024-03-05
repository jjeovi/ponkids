package com.meta.ponkids.domain.qestnar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meta.ponkids.domain.qestnar.entity.QestnarQestnDetail;
import com.meta.ponkids.domain.qestnar.repository.custom.QestnarQestnDetailRepositoryCustom;

// TODO PK(*ID) 체크
public interface QestnarQestnDetailRepository extends JpaRepository<QestnarQestnDetail, Long>, QestnarQestnDetailRepositoryCustom {
	
	Optional<QestnarQestnDetail> findById( Long pk );	// TODO PK(*ID) 체크
}
