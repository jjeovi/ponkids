package com.meta.ponkids.domain.system.file.repository.custom;

import com.meta.ponkids.domain.system.file.entity.AtchFileDetail;

import java.util.List;

public interface AtchFileDetailRepositoryCustom {
    
    AtchFileDetail getTarget(Long atchFileSn, Long fileSeq);
    
    List<AtchFileDetail> getList( Long atchFileSn );
}
