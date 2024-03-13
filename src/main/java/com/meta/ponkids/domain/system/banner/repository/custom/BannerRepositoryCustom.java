package com.meta.ponkids.domain.system.banner.repository.custom;

import com.meta.ponkids.domain.system.banner.dto.BannerListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BannerRepositoryCustom {
    
    Page<BannerListDto> getList( BannerListDto listDto, Pageable pageable );
    
    // 사용자 화면에 표출할 list
    List<BannerListDto> getPonList( String bannerClCd , Long userSn );
    
}
