package com.meta.ponkids.domain.cls.repository.custom;

import com.meta.ponkids.domain.cls.dto.ClassWeekListDto;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClassWeekRepositoryCustom {
    
    Page<ClassWeekListDto> getList( ClassWeekListDto listDto, Pageable pageable );
    
    List<ClassWeekListDto> getListByClassSn( Long pk );
    
}
