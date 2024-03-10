package com.meta.ponkids.domain.qestnar.repository.custom;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.ponkids.domain.qestnar.dto.QestnarAnswerReplyListDto;

public interface QestnarAnswerReplyRepositoryCustom {
	
//	Page<QestnarAnswerReplyListDto> getList( QestnarAnswerReplyListDto listDto, Pageable pageable );
	
	List<QestnarAnswerReplyListDto> getListByQestnarAnswerSn( Long qestnarAnswerSn );
	
	List<QestnarAnswerReplyListDto> getListByUserSn( Long userSn );

}
