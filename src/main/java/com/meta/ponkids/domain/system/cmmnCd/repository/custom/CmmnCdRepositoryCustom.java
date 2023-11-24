package com.meta.ponkids.domain.system.cmmnCd.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.ponkids.domain.system.cmmnCd.dto.CmmnCdListDto;

public interface CmmnCdRepositoryCustom {
	
	Page<CmmnCdListDto> getList( CmmnCdListDto listDto, Pageable pageable );

}
