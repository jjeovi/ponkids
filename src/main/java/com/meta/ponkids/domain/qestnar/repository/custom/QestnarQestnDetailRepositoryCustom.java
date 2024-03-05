package com.meta.ponkids.domain.qestnar.repository.custom;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.ponkids.domain.qestnar.dto.QestnarQestnDetailListDto;

public interface QestnarQestnDetailRepositoryCustom {
	
	Page<QestnarQestnDetailListDto> getList( QestnarQestnDetailListDto listDto, Pageable pageable );
	
	List<QestnarQestnDetailListDto> getListByQestnarGroupSnOrderByQestnarQestnSnAsc( Long qestnarGroupSn );

}
