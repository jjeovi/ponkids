package com.meta.ponkids.domain.qestnar.repository.custom;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.ponkids.domain.qestnar.dto.QestnarGroupListDto;

public interface QestnarGroupRepositoryCustom {
	
	Page<QestnarGroupListDto> getList( QestnarGroupListDto listDto, Pageable pageable );
	
	List<QestnarGroupListDto> getList( QestnarGroupListDto listDto );
	
	QestnarGroupListDto findByQestnarGroupCd( String qestnarGroupCd );

}
