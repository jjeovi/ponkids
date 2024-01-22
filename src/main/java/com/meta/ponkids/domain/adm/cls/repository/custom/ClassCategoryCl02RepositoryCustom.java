package com.meta.ponkids.domain.adm.cls.repository.custom;

import com.meta.ponkids.domain.adm.cls.dto.ClassCategoryCl02ListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClassCategoryCl02RepositoryCustom {
    
    Page<ClassCategoryCl02ListDto> getList( ClassCategoryCl02ListDto listDto, Pageable pageable );
    
}