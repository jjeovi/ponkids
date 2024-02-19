package com.meta.ponkids.domain.cls.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.ponkids.domain.cls.dto.ClassInqryListDto;

public interface ClassInqryRepositoryCustom {
	
	Page<ClassInqryListDto> getList( ClassInqryListDto listDto, Pageable pageable );

}
