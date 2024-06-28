package com.meta.ponkids.domain.lctre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meta.ponkids.domain.lctre.entity.LctreReqstDetail;
import com.meta.ponkids.domain.lctre.repository.custom.LctreReqstDetailRepositoryCustom;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LctreReqstDetailRepository extends JpaRepository<LctreReqstDetail, Long>, LctreReqstDetailRepositoryCustom {

    @Modifying( clearAutomatically = true )
    @Query( value = "UPDATE {h-schema}tb_lctre_reqst_detail"
            +       "   SET del_yn   = 'Y'"
            +       "     , updt_dt  = now()"
            +       "  FROM tb_lctre_reqst"
            +       " WHERE 1=1"
            +       "   AND tb_lctre_reqst.lctre_reqst_sn = tb_lctre_reqst_detail.lctre_reqst_sn"
            +       "   AND tb_lctre_reqst.class_reqst_sn = :classReqstSn", nativeQuery = true )
    int deleteByClassReqstSn( @Param("classReqstSn") Long classReqstSn) ;
	
}
