package com.meta.ponkids.domain.lctre.repository.custom;

import java.util.List;

import com.meta.ponkids.domain.lctre.dto.LctreReqstDetailListDto;

public interface LctreReqstDetailRepositoryCustom {
    
    List<LctreReqstDetailListDto> getListByLctreReqstSn( Long lctreReqstSn );
    
}
