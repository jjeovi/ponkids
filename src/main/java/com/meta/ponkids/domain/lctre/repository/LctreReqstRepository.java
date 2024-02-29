package com.meta.ponkids.domain.lctre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meta.ponkids.domain.lctre.entity.LctreReqst;
import com.meta.ponkids.domain.lctre.repository.custom.LctreReqstRepositoryCustom;

public interface LctreReqstRepository extends JpaRepository<LctreReqst, Long>, LctreReqstRepositoryCustom {
	
	boolean existsByLctreSnAndChldrnSn( Long lctreSn, Long chldrnSn );
}
