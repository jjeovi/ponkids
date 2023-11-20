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
                                new CaseBuilder()
                                .when( bbs.answerSetYn.eq( "Y" ) ).then( "사용" )
                                .when(bbs.answerSetYn.eq( "N" )).then( "미사용" )
                                .otherwise( "" )
                                .as("answerSetYn"),
                                new CaseBuilder()
                                .when( bbs.useYn.eq( "Y" ) ).then( "사용" )
                                .when(bbs.useYn.eq( "N" )).then( "미사용" )
                                .otherwise( "" )
                                .as("useYn"),
                                new CaseBuilder()
                                .when( bbs.openYn.eq( "Y" ) ).then( "공개" )
                                .when(bbs.openYn.eq( "N" )).then( "비공개" )
                                .otherwise( "" )
                                .as("openYn"),
                                bbs.registerId,
                                bbs.regDt      
                	    )  ).from( bbs )
                .orderBy( bbs.bbsSn.desc() )
                // where
                .where(
                		eqAnswerSetYn( bbsListDto.getAnswerSetYn() ),
                		eqUseYn( bbsListDto.getUseYn() ),
                		eqOpenYn( bbsListDto.getOpenYn() ),
                        eqOption( bbsListDto.getSchOption(), bbsListDto.getSchCntn() )
                )
                // paging
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
        
	      
        // (2) count
        JPAQuery<Long> count = query.select(bbs.count())
                .from(bbs)
                .where(
                		eqAnswerSetYn( bbsListDto.getAnswerSetYn() ),
                		eqUseYn( bbsListDto.getUseYn() ),
                		eqOpenYn( bbsListDto.getOpenYn() ),
                        eqOption( bbsListDto.getSchOption(), bbsListDto.getSchCntn() )
                );
               
    	return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
	 }
    
    
    // -------------------------------- WHERE 검색 옵션 setting --------------------------------
    private BooleanExpression eqAnswerSetYn( String answerSetYn) {
        return StringUtils.hasText( answerSetYn ) ? bbs.answerSetYn.eq( answerSetYn ) : null;
    }
    private BooleanExpression eqUseYn( String useYn) {
    	return StringUtils.hasText( useYn ) ? bbs.useYn.eq( useYn ) : null;
    }
    
    private BooleanExpression eqOpenYn( String openYn) {
    	return StringUtils.hasText( openYn ) ? bbs.openYn.eq( openYn ) : null;
    }
    

    
    private BooleanExpression eqOption(String schOption, String schCntn){
        // 검색 옵션  A : 아이디 , B : 이름
        if (StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn )){
                 if( schOption.equals("A")) return bbs.bbsNm.contains( schCntn ); // LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
            else if (schOption.equals("B")) return bbs.registerId.contains( schCntn ); // LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
            else                            return null;
        } else { return null; }
    }
	 


}