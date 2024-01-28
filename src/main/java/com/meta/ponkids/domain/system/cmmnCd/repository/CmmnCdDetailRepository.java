package com.meta.ponkids.domain.system.cmmnCd.repository;

import com.meta.ponkids.domain.system.cmmnCd.entity.CmmnCdDetail;
import com.meta.ponkids.domain.system.cmmnCd.repository.custom.CmmnCdDetailRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// TODO PK(*ID) 체크
public interface CmmnCdDetailRepository extends JpaRepository<CmmnCdDetail, Long>, CmmnCdDetailRepositoryCustom {
    
    Optional<CmmnCdDetail> findById( Long pk );    // pk 로 조회 ( 고유 1 건 ) 
    
    
    Optional<CmmnCdDetail> findTop1ByCdNmAndCdDetailVal1( String cdNm, String cdDetailVal1 );    // 코드이름 (부모코드명)과 코드상세값1 로 조회 ( 고유 1건 )
    
    List<CmmnCdDetail> findByCdNmAndUseYnOrderByCdDetailSeqAsc( String cdNm, String useYn );
}
