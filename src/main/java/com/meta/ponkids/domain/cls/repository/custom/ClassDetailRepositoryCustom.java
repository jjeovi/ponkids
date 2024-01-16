package com.meta.ponkids.domain.cls.repository.custom;

import com.meta.ponkids.domain.cls.dto.ClassDetailListDto;
import com.meta.ponkids.domain.cls.entity.ClassDetail;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClassDetailRepositoryCustom {
	
	Page<ClassDetailListDto> getList( ClassDetailListDto listDto, Pageable pageable );
	
	List<ClassDetail> findByClassSnOrderByClassDetailSeq(Long pk);

}
