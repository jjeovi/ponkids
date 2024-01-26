package com.meta.ponkids.domain.cls.repository;

import com.meta.ponkids.domain.cls.entity.Class;
import com.meta.ponkids.domain.cls.repository.custom.ClassRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClassRepository extends JpaRepository<Class, Long>, ClassRepositoryCustom {
    
    Optional<Class> findById( Long pk );
    
    List<Class> findByCtgrySnOrderByClassSnDesc( Long ctgrySn );
    
    List<Class> findByCrseSnOrderByClassSnDesc( Long crseSn );
}
