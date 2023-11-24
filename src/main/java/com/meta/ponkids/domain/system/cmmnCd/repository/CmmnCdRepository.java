package com.meta.ponkids.domain.system.cmmnCd.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meta.ponkids.domain.system.cmmnCd.entity.CmmnCd;
import com.meta.ponkids.domain.system.cmmnCd.repository.custom.CmmnCdRepositoryCustom;

// TODO PK(*ID) 체크
public interface CmmnCdRepository extends JpaRepository<CmmnCd, Long>, CmmnCdRepositoryCustom {
	
	Optional<CmmnCd> findById( Long pk );	// TODO PK(*ID) 체크
	
	CmmnCd findByCdNm(String cdNm);
}
