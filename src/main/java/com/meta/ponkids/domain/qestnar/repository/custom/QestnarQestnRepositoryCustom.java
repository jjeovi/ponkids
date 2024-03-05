package com.meta.ponkids.domain.qestnar.repository.custom;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.ponkids.domain.qestnar.dto.QestnarQestnListDto;

public interface QestnarQestnRepositoryCustom {
	
	Page<QestnarQestnListDto> getList( QestnarQestnListDto listDto, Pageable pageable );
	
	
	List<QestnarQestnListDto> findByQestnarGroupSn( Long qestnarGroupSn );

}
