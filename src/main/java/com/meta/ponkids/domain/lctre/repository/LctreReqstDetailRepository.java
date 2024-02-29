package com.meta.ponkids.domain.lctre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meta.ponkids.domain.lctre.entity.LctreReqstDetail;
import com.meta.ponkids.domain.lctre.repository.custom.LctreReqstDetailRepositoryCustom;

public interface LctreReqstDetailRepository extends JpaRepository<LctreReqstDetail, Long>, LctreReqstDetailRepositoryCustom {
	
}
