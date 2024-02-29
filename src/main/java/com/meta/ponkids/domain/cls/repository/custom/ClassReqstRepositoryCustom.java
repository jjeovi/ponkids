package com.meta.ponkids.domain.cls.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.ponkids.domain.cls.dto.ClassReqstListDto;

public interface ClassReqstRepositoryCustom {
    
    Page<ClassReqstListDto> getList( ClassReqstListDto listDto, Pageable pageable );
    
    ClassReqstListDto getByClassReqstSn( Long classReqstSn );
    
}
