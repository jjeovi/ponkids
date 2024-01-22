package com.meta.ponkids.domain.adm.lctre.repository.custom;

import com.meta.ponkids.domain.adm.lctre.dto.LctreListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LctreRepositoryCustom {
	
	Page<LctreListDto> getList( LctreListDto listDto, Pageable pageable );

}
