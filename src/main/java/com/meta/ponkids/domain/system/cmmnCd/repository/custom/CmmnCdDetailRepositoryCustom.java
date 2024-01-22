package com.meta.ponkids.domain.system.cmmnCd.repository.custom;

import com.meta.ponkids.domain.system.cmmnCd.dto.CmmnCdDetailListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CmmnCdDetailRepositoryCustom {
    
    Page<CmmnCdDetailListDto> getList( CmmnCdDetailListDto listDto, Pageable pageable );
    
}
