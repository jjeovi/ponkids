package com.meta.ponkids.domain.system.ntt.repository.impl;

import com.meta.ponkids.domain.system.ntt.dto.NttListDto;
import com.meta.ponkids.domain.system.ntt.dto.QNttListDto;
import com.meta.ponkids.domain.system.ntt.repository.custom.NttRepositoryCustom;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;


import java.util.List;

import static com.meta.ponkids.domain.system.ntt.entity.QNtt.ntt;

@Repository
@RequiredArgsConstructor
public class NttRepositoryImpl implements NttRepositoryCustom   {
    private final JPAQueryFactory query;


	@Override
	public Page<NttListDto> getList(NttListDto nttListDto, Pageable pageable) {
	
		 List<NttListDto> results = query.select(new QNttListDto(
				 ntt.nttSn,
				 ntt.bbsSn,
				 ntt.nttNm,
				 ntt.nttCn,
				 ntt.nttRdcnt,
                 ntt.openYn,
                 ntt.registerId,
                 ntt.regDt
				 ) ).from(ntt)
				   .orderBy( ntt.nttSn.desc() )
				   .offset( pageable.getOffset() )
	               .limit( pageable.getPageSize() )
	               .fetch();
		 
		   // (2) count
	        JPAQuery<Long> count = query.select(ntt.count())
	                .from(ntt);
	             
	               
	    	return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
	}


}