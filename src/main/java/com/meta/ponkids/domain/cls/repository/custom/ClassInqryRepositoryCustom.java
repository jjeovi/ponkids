package com.meta.ponkids.domain.cls.repository.custom;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.ponkids.domain.cls.dto.ClassInqryListDto;
import com.meta.ponkids.domain.cls.entity.ClassInqry;

public interface ClassInqryRepositoryCustom {
	
	Page<ClassInqryListDto> getList( ClassInqryListDto listDto, Pageable pageable );
	
	List<ClassInqryListDto> findByStepAndParntsInqrySn( String step, Long parntsInqrySn );
	
	ClassInqryListDto getByClassInqrySn( ClassInqryListDto listDto );
	

}
