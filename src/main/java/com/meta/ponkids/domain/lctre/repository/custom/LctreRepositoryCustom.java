package com.meta.ponkids.domain.lctre.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.ponkids.domain.lctre.dto.LctreListDto;

public interface LctreRepositoryCustom {
	
	Page<LctreListDto> getList( LctreListDto listDto, Pageable pageable );

}
