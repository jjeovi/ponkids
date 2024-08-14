package com.meta.ponkids.domain.lctre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meta.ponkids.domain.lctre.entity.LctreReqst;
import com.meta.ponkids.domain.lctre.repository.custom.LctreReqstRepositoryCustom;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LctreReqstRepository extends JpaRepository<LctreReqst, Long>, LctreReqstRepositoryCustom {
	
	boolean existsByLctreSnAndChldrnSn( Long lctreSn, Long chldrnSn );

	@Modifying( clearAutomatically = true )
	@Query( value = "UPDATE {h-schema}tb_lctre_reqst"
			+       "   SET del_yn   = 'Y'"
			+       "     , updt_dt  = now()"
			+       " WHERE class_reqst_sn = :classReqstSn", nativeQuery = true )
	int deleteByClassReqstSn( @Param("classReqstSn") Long classReqstSn) ;

	@Modifying( clearAutomatically = true )
	@Query( value = "UPDATE {h-schema}tb_lctre_reqst"
			+       "   SET prepar_nmpr_yn   = 'N'"
			+       "     , updt_dt  = now()"
			+       " WHERE lctre_reqst_sn = :lctreReqstSn", nativeQuery = true )
	int updatePreparNmprYn( @Param("lctreReqstSn") Long lctreReqstSn) ;
}
