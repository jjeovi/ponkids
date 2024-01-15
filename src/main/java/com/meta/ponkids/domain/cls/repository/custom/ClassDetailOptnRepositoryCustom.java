package com.meta.ponkids.domain.cls.repository.custom;

import com.meta.ponkids.domain.cls.dto.ClassDetailOptnListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClassDetailOptnRepositoryCustom {
	
	Page<ClassDetailOptnListDto> getList( ClassDetailOptnListDto listDto, Pageable pageable );

}
