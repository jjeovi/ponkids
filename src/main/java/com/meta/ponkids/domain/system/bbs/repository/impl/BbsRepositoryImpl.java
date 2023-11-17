package com.meta.ponkids.domain.system.bbs.repository.impl;



import com.meta.ponkids.domain.system.bbs.dto.BbsListDto;
import com.meta.ponkids.domain.system.bbs.dto.QBbsListDto;

import com.meta.ponkids.domain.system.bbs.repository.custom.BbsRepositoryCustom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;


import static com.meta.ponkids.domain.system.bbs.entity.QBbs.bbs;

@Repository
@RequiredArgsConstructor
public class BbsRepositoryImpl implements BbsRepositoryCustom   {
    private final JPAQueryFactory query;
	
    @Override
    public Page<BbsListDto> getList( BbsListDto bbsListDto, Pageable pageable ) { 
	  
    	  // (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회
	  
    	  // (1) 결과list (results).
        List<BbsListDto> results = query
                // select
                .select( new QBbsListDto(
                		bbs.bbsSn,
                        new CaseBuilder()
                                .when( bbs.bbsSeCd.eq( "01" ) ).then( "포토형" )
                                .when(bbs.bbsSeCd.eq( "02" )).then( "리스트형" )
                                .otherwise( "" )
                                .as("bbsSeCd"),
                                bbs.bbsNm,
                                bbs.answerSetYn,
                                bbs.useYn,   
                                bbs.openYn,
                                bbs.registerId                // from
                	    )  ).from( bbs )
                .orderBy( bbs.bbsSn.desc() )
                // paging
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
        
	      
        // (2) count
        JPAQuery<Long> count = query.select(bbs.count())
                .from(bbs);
               
    	return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
	 }
	 


}