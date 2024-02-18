package com.meta.ponkids.domain.cls.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meta.ponkids.domain.cls.entity.ClassReqst;

public interface ClassReqstRepository extends JpaRepository<ClassReqst, Long> {
    
}
