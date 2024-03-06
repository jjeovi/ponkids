package com.meta.ponkids.domain.qestnar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meta.ponkids.domain.qestnar.entity.QestnarAnswer;
import com.meta.ponkids.domain.qestnar.repository.custom.QestnarAnswerRepositoryCustom;

// TODO PK(*ID) 체크
public interface QestnarAnswerRepository extends JpaRepository<QestnarAnswer, Long>, QestnarAnswerRepositoryCustom {
	
	Optional<QestnarAnswer> findById( Long pk );	// TODO PK(*ID) 체크
}
