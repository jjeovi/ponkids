package com.meta.ponkids.domain.adm.cls.repository.custom;

import com.meta.ponkids.domain.adm.cls.dto.ClassCategoryCl01ListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClassCategoryCl01RepositoryCustom {
    
    Page<ClassCategoryCl01ListDto> getList( ClassCategoryCl01ListDto listDto, Pageable pageable );
    
}