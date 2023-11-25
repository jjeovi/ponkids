package com.meta.ponkids.domain.system.bbs.repository.custom;


import com.meta.ponkids.domain.system.bbs.dto.BbsListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BbsRepositoryCustom {
	
	Page<BbsListDto> getList( BbsListDto bbsListDto, Pageable pageable );
	public String getSetReplySetYn(Long BbsSn);
}
