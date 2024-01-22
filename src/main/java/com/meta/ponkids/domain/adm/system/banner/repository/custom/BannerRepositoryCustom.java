package com.meta.ponkids.domain.adm.system.banner.repository.custom;

import com.meta.ponkids.domain.adm.system.banner.dto.BannerListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BannerRepositoryCustom {
    
    Page<BannerListDto> getList( BannerListDto listDto, Pageable pageable );
    
}
