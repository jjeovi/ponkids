package com.meta.ponkids.domain.ntt.repository;

import com.meta.ponkids.domain.ntt.dto.NttListDto;
import com.meta.ponkids.domain.ntt.entity.Ntt;
import com.meta.ponkids.domain.ntt.repository.custom.NttRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface NttRepository extends JpaRepository<Ntt, Long>, NttRepositoryCustom {
    
    boolean existsByNttSn( Long nttSn );
    
    Ntt findByNttSn( Long nttSn );
    
    int getMaxNttRdcnt( Long nttSn );
    
    int MaxNttSeq( Long bbsSn );
    
    int getExistsNtt( Long bbsSn );
    
    @Modifying( clearAutomatically = true )
    @Query( value = "UPDATE {h-schema}tb_ntt "
            + "      SET del_yn = 'Y'"
            + "        , updt_dt = now() "
            + "    WHERE ntt_sn = :nttSn", nativeQuery = true )
        // nativeQuery true 없으면 error
    int deleteAllByNttSn( @Param( "nttSn" ) Long bbsSn );
    
    List<NttListDto> getNoticeList( Long bbsSn );
    
}
