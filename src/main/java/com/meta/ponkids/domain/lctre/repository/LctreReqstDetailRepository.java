package com.meta.ponkids.domain.lctre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meta.ponkids.domain.lctre.entity.LctreReqst;
import com.meta.ponkids.domain.lctre.entity.LctreReqstDetail;

public interface LctreReqstDetailRepository extends JpaRepository<LctreReqstDetail, Long> {
	
}
