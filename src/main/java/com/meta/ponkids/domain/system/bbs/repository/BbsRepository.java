package com.meta.ponkids.domain.system.bbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.meta.ponkids.domain.system.bbs.entity.Bbs;
import com.meta.ponkids.domain.system.bbs.repository.custom.BbsRepositoryCustom;
import com.meta.ponkids.domain.system.ntt.entity.Ntt;



public interface BbsRepository extends JpaRepository< Bbs, Long> , BbsRepositoryCustom {

   boolean existsByBbsSn(Long bbsSn);
   
   public Bbs findByBbsSn(Long bbsSn);
   
   @Modifying( clearAutomatically = true )
   @Query( value = "UPDATE tb_bbs "
           + "      SET del_yn = 'Y'"
           + "        , updt_dt = now() "
           + "    WHERE bbs_sn = :bbsSn", nativeQuery = true )
       // nativeQuery true 없으면 error
   int deleteAllByBbsSn( @Param( "bbsSn" ) Long bbsSn );
   
   
   public String getSetReplySetYn(Long bbsSn);
   

	    
}
