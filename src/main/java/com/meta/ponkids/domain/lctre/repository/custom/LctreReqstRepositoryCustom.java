package com.meta.ponkids.domain.lctre.repository.custom;

import java.util.List;

import com.meta.ponkids.domain.lctre.dto.LctreReqstListDto;

public interface LctreReqstRepositoryCustom {
    
    List<LctreReqstListDto> getListByClassReqstSn( Long classReqstSn );

    List<LctreReqstListDto> getListByLctreSn( Long lctreSn );

    LctreReqstListDto getFrstPreparNmpr( LctreReqstListDto targetDto );
    
    boolean existsLctreReqstWithPaidStatus(Long lctreSn, Long chldrnSn);
}
