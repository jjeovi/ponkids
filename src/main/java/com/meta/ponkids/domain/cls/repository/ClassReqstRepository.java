package com.meta.ponkids.domain.cls.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meta.ponkids.domain.cls.entity.ClassReqst;
import com.meta.ponkids.domain.cls.repository.custom.ClassReqstRepositoryCustom;

public interface ClassReqstRepository extends JpaRepository<ClassReqst, Long>, ClassReqstRepositoryCustom {
    
	
	boolean existsByClassSnAndUserSn( Long classSn, Long userSn );
}
