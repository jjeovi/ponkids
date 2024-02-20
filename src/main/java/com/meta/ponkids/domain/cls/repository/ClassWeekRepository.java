package com.meta.ponkids.domain.cls.repository;

import com.meta.ponkids.domain.cls.entity.ClassWeek;
import com.meta.ponkids.domain.cls.repository.custom.ClassWeekRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClassWeekRepository extends JpaRepository<ClassWeek, Long>, ClassWeekRepositoryCustom {
    
    Optional<ClassWeek> findById( Long pk );
    
    List<ClassWeek> findByClassSnOrderByClassWeekSn( Long pk );
    
    
    @Modifying( clearAutomatically = true )
    @Query( value = "UPDATE {h-schema}tb_class_week "
            +       "   SET del_yn   = 'Y'"
            +       "     , updt_dt  = now()"
            +       " WHERE class_sn = :classSn", nativeQuery = true )
    int deleteAllByClassSn( @Param("classSn") Long pk) ;
}
