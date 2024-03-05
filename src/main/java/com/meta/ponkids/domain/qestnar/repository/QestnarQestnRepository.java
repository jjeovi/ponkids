package com.meta.ponkids.domain.qestnar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.meta.ponkids.domain.qestnar.entity.QestnarQestn;
import com.meta.ponkids.domain.qestnar.repository.custom.QestnarQestnRepositoryCustom;

// TODO PK(*ID) 체크
public interface QestnarQestnRepository extends JpaRepository<QestnarQestn, Long>, QestnarQestnRepositoryCustom {
	
	Optional<QestnarQestn> findById( Long pk );	// TODO PK(*ID) 체크
	
	

    @Modifying( clearAutomatically = true )
    @Query( value = "UPDATE {h-schema}tb_qestnar_qestn "
            +       "   SET del_yn   = 'Y'"
            +       "     , updt_dt  = now()"
            +       " WHERE qestnar_group_sn = :qestnarGroupSn", nativeQuery = true )
    int deleteAllByQestnarGroupSn( @Param("qestnarGroupSn") Long pk) ;
}
