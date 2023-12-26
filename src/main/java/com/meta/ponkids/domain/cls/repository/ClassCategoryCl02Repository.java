package com.meta.ponkids.domain.cls.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meta.ponkids.domain.cls.entity.ClassCategoryCl02;
import com.meta.ponkids.domain.cls.repository.custom.ClassCategoryCl02RepositoryCustom;

public interface ClassCategoryCl02Repository extends JpaRepository<ClassCategoryCl02, Long>, ClassCategoryCl02RepositoryCustom {
	
	Optional<ClassCategoryCl02> findById( Long pk );
	
	boolean existsByparntsClSn( Long pk );
	
	List<ClassCategoryCl02> findAll();
	
	List<ClassCategoryCl02> findByParntsClSnOrderByClSeq( long parntsClSn );
}