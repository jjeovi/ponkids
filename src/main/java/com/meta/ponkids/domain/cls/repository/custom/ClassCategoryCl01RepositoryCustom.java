package com.meta.ponkids.domain.cls.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.ponkids.domain.cls.dto.ClassCategoryCl01ListDto;

public interface ClassCategoryCl01RepositoryCustom {
	
	Page<ClassCategoryCl01ListDto> getList( ClassCategoryCl01ListDto listDto, Pageable pageable );

}