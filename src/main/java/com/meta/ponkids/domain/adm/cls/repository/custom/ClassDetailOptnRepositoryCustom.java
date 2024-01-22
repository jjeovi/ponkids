package com.meta.ponkids.domain.adm.cls.repository.custom;

import com.meta.ponkids.domain.adm.cls.dto.ClassDetailOptnListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClassDetailOptnRepositoryCustom {
	
	Page<ClassDetailOptnListDto> getList( ClassDetailOptnListDto listDto, Pageable pageable );

}
