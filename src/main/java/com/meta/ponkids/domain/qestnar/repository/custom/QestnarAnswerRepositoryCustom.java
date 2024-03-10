package com.meta.ponkids.domain.qestnar.repository.custom;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.ponkids.domain.qestnar.dto.QestnarAnswerListDto;

public interface QestnarAnswerRepositoryCustom {
	
	Page<QestnarAnswerListDto> getList( QestnarAnswerListDto listDto, Pageable pageable );
	
	
	List<QestnarAnswerListDto> getList( QestnarAnswerListDto listDto );
	
	
	List<QestnarAnswerListDto> getListByUserSn( QestnarAnswerListDto listDto );
	
	
	QestnarAnswerListDto getByClassInqrySn( Long pk ) ;

}
