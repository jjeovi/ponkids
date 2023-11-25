package com.meta.ponkids.domain.system.ntt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.meta.ponkids.domain.system.ntt.entity.Ntt;
import com.meta.ponkids.domain.system.ntt.repository.custom.NttRepositoryCustom;
import com.querydsl.jpa.impl.JPAQuery;



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
   int deleteAllByNttSn( @Param( "nttSn" ) int bbsSn );



	    
}
