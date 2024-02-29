package com.meta.ponkids.domain.cls.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.ponkids.domain.cls.dto.ClassReviewListDto;

public interface ClassReviewRepositoryCustom {
	
	Page<ClassReviewListDto> getList( ClassReviewListDto listDto, Pageable pageable );

}
