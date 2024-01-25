package com.meta.ponkids.domain.system.cmmnCd.repository;

import com.meta.ponkids.domain.system.cmmnCd.entity.CmmnCd;
import com.meta.ponkids.domain.system.cmmnCd.repository.custom.CmmnCdRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// TODO PK(*ID) 체크
public interface CmmnCdRepository extends JpaRepository<CmmnCd, Long>, CmmnCdRepositoryCustom {
    
    Optional<CmmnCd> findById( Long pk );    // TODO PK(*ID) 체크
    
    Optional<CmmnCd> findByCdNm( String cdNm );
    
    List<CmmnCd> findAllByOrderByCdSnDesc();
}
