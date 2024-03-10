package com.meta.ponkids.domain.qestnar.repository.custom;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.ponkids.domain.qestnar.dto.QestnarAnswerDetailListDto;

public interface QestnarAnswerDetailRepositoryCustom {
	
	Page<QestnarAnswerDetailListDto> getList( QestnarAnswerDetailListDto listDto, Pageable pageable );
	
	List<QestnarAnswerDetailListDto> getListByQestnarAnswerSn( Long qestnarAnswerSn );
	
	List<QestnarAnswerDetailListDto> getList( QestnarAnswerDetailListDto listDto );

}
