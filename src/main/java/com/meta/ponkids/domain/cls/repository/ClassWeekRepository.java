package com.meta.ponkids.domain.cls.repository;

import com.meta.ponkids.domain.cls.entity.ClassWeek;
import com.meta.ponkids.domain.cls.repository.custom.ClassWeekRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClassWeekRepository extends JpaRepository<ClassWeek, Long>, ClassWeekRepositoryCustom {
    
    Optional<ClassWeek> findById( Long pk );
    
    List<ClassWeek> findByClassSnOrderByClassWeekSn( Long pk );
}
