package com.meta.ponkids.domain.ntt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.meta.ponkids.domain.ntt.dto.NttListDto;
import com.meta.ponkids.domain.ntt.entity.Ntt;
import com.meta.ponkids.domain.ntt.repository.custom.NttRepositoryCustom;


public interface NttRepository extends JpaRepository< Ntt, Long> , NttRepositoryCustom {

   boolean existsByNttSn(Long nttSn);
   
   public Ntt findByNttSn(Long nttSn);
   
   public int getMaxNttRdcnt(Long nttSn);
   
   public int MaxNttSeq(Long bbsSn);
   
   public int getExistsNtt(Long bbsSn);
   
   
   @Modifying( clearAutomatically = true )
   @Query( value = "UPDATE tb_ntt "
           + "      SET del_yn = 'Y'"
           + "        , updt_dt = now() "
           + "    WHERE ntt_sn = :nttSn", nativeQuery = true )
       // nativeQuery true 없으면 error
   int deleteAllByNttSn( @Param( "nttSn" ) Long bbsSn );

   public List<NttListDto> getNoticeList(Long bbsSn); 
	    
}
