package com.meta.ponkids.domain.cls.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.meta.ponkids.domain.cls.entity.ClassLike;

public interface ClassLikeRepository extends JpaRepository<ClassLike, Long> {
	
	void deleteByClassSnAndUserSn(Long classSn, Long userSn) ;
	
	Optional<ClassLike> findByClassSnAndUserSn( Long classSn, Long userSn ) ;
	
}
