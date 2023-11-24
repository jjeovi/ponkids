package com.meta.ponkids.domain.system.cmmnCd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meta.ponkids.domain.system.cmmnCd.entity.CmmnCdDetail;
import com.meta.ponkids.domain.system.cmmnCd.repository.custom.CmmnCdDetailRepositoryCustom;

// TODO PK(*ID) 체크
public interface CmmnCdDetailRepository extends JpaRepository<CmmnCdDetail, Long>, CmmnCdDetailRepositoryCustom {
	
	Optional<CmmnCdDetail> findById( Long pk );	// TODO PK(*ID) 체크
	
	List<CmmnCdDetail> findByCdSnAndUseYnOrderByCdDetailSeqAsc (long cdSn, String useYn);
}
