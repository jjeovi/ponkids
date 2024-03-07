package com.meta.ponkids.domain.qestnar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meta.ponkids.domain.qestnar.entity.QestnarAnswerDetail;
import com.meta.ponkids.domain.qestnar.repository.custom.QestnarAnswerDetailRepositoryCustom;

// TODO PK(*ID) 체크
public interface QestnarAnswerDetailRepository extends JpaRepository<QestnarAnswerDetail, Long>, QestnarAnswerDetailRepositoryCustom {
	
	Optional<QestnarAnswerDetail> findById( Long pk );	// TODO PK(*ID) 체크
}
