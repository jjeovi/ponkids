package com.meta.ponkids.domain.cls.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meta.ponkids.domain.cls.entity.ClassReview;
import com.meta.ponkids.domain.cls.repository.custom.ClassReviewRepositoryCustom;

// TODO PK(*ID) 체크
public interface ClassReviewRepository extends JpaRepository<ClassReview, Long>, ClassReviewRepositoryCustom {
	
	Optional<ClassReview> findById( Long pk );	// TODO PK(*ID) 체크
}
