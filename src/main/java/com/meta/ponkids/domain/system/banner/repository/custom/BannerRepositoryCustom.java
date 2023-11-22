package com.meta.ponkids.domain.system.banner.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.ponkids.domain.system.banner.dto.BannerListDto;

public interface BannerRepositoryCustom {
	
	Page<BannerListDto> getList( BannerListDto listDto, Pageable pageable );

}
