package com.meta.ponkids.domain.system.ntt.repository.custom;

import com.meta.ponkids.domain.system.ntt.dto.NttListDto;
import com.querydsl.jpa.impl.JPAQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface NttRepositoryCustom {
	
	Page<NttListDto> getList( NttListDto nttListDto, Pageable pageable );
	int getExistsNtt( Long bbsSn);

}
