package com.meta.ponkids.domain.cls.repository.custom;

import com.meta.ponkids.domain.cls.dto.ClassWeekListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClassWeekRepositoryCustom {
	
	Page<ClassWeekListDto> getList( ClassWeekListDto listDto, Pageable pageable );

}
