package com.meta.ponkids.domain.cls.repository.custom;

import com.meta.ponkids.domain.cls.dto.ClassListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClassRepositoryCustom {
    
    Page<ClassListDto> getList( ClassListDto listDto, Pageable pageable );
    
    
    List<ClassListDto> getList( ClassListDto listDto);
    
    
    ClassListDto getByClassSn( Long classSn ) ;
    
    
}
