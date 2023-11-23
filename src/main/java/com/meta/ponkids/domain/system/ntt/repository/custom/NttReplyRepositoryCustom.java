package com.meta.ponkids.domain.system.ntt.repository.custom;

import com.meta.ponkids.domain.system.ntt.dto.NttListDto;
import com.meta.ponkids.domain.system.ntt.dto.NttReplyListDto;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface NttReplyRepositoryCustom {
	List<NttReplyListDto> getList(int nttSn );
	
	

}
