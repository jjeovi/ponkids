package com.meta.ponkids.domain.system.cmmnCd.repository.custom;

import com.meta.ponkids.domain.system.cmmnCd.dto.CmmnCdListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CmmnCdRepositoryCustom {
    
    Page<CmmnCdListDto> getList( CmmnCdListDto listDto, Pageable pageable );
    
}
