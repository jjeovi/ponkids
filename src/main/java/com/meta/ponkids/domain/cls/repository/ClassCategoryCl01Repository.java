package com.meta.ponkids.domain.cls.repository;

import com.meta.ponkids.domain.cls.entity.ClassCategoryCl01;
import com.meta.ponkids.domain.cls.repository.custom.ClassCategoryCl01RepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClassCategoryCl01Repository extends JpaRepository<ClassCategoryCl01, Long>, ClassCategoryCl01RepositoryCustom {
    
    Optional<ClassCategoryCl01> findById( Long pk );
    
    List<ClassCategoryCl01> findAll();
    
    List<ClassCategoryCl01> findAllByOrderByClSeq();
    
}