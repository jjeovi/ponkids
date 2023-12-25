package com.meta.ponkids.domain.cls.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.ponkids.domain.cls.dto.ClassCategoryCl02ListDto;

public interface ClassCategoryCl02RepositoryCustom {
	
	Page<ClassCategoryCl02ListDto> getList( ClassCategoryCl02ListDto listDto, Pageable pageable );

}