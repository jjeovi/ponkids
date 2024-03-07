package com.meta.ponkids.domain.qestnar.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.ponkids.domain.qestnar.dto.QestnarGroupListDto;

public interface QestnarGroupRepositoryCustom {
	
	Page<QestnarGroupListDto> getList( QestnarGroupListDto listDto, Pageable pageable );
	
	QestnarGroupListDto findByQestnarGroupCd( String qestnarGroupCd );

}
