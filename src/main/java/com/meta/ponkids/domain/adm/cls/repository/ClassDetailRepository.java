package com.meta.ponkids.domain.adm.cls.repository;

import java.util.Optional;

import com.meta.ponkids.domain.adm.cls.entity.ClassDetail;
import com.meta.ponkids.domain.adm.cls.repository.custom.ClassDetailRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// PK(*ID) 체크
public interface ClassDetailRepository extends JpaRepository<ClassDetail, Long>, ClassDetailRepositoryCustom {
	
	Optional<ClassDetail> findById( Long pk );	// PK(*ID) 체크
	
    @Modifying( clearAutomatically = true )
    @Query( value = "UPDATE tb_class_detail "
            +       "   SET del_yn   = 'Y'"
            +       "     , updt_dt  = now()"
            +       " WHERE class_sn = :classSn", nativeQuery = true )
    int deleteAllByClassSn( @Param("classSn") Long pk) ;
    
}