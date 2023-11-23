package com.meta.ponkids.domain.system.ntt.repository.impl;

import com.meta.ponkids.domain.system.ntt.dto.NttReplyListDto;
//import com.meta.ponkids.domain.system.ntt.dto.NttReplyListDto;
import com.meta.ponkids.domain.system.ntt.dto.QNttReplyListDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;


import static com.meta.ponkids.domain.system.ntt.entity.QNttReply.nttReply;

@Repository
@RequiredArgsConstructor
public class NttReplyRepositoryImpl    {
    private final JPAQueryFactory query;

    public Integer MaxNttReplySeq(int nttSn) {
    	int number = query.select(nttReply.nttReplySeq.max().coalesce(0))
    			     .from(nttReply)
    			     .where(
    			    		 eqNttSnOption( nttSn )
    		                )
    			   .fetchOne();

    	number= number +1;
    	
    	return number;
    	
    	
    
    }
    
    // -------------------------------- WHERE 검색 옵션 setting --------------------------------
    private BooleanExpression eqNttSnOption( int nttSn) {
        return  nttReply.nttSn.eq( nttSn );
    }
    
    private BooleanExpression eqStepOption(int step) {
        return  nttReply.step.eq(step);
    }
    
    
    
	
	  public List<NttReplyListDto> getList(int nttSn) {
	  
	  List<NttReplyListDto> results = query.select(new QNttReplyListDto(
													  nttReply.nttReplySn,
													  nttReply.nttSn,
													  nttReply.step,
													  nttReply.parntsReplySn, 
													  nttReply.nttReplyGroup,
													  nttReply.nttReplySeq,
													  nttReply.nttReplyCn,
													  nttReply.registerId )
													  ).from(nttReply)
			                                         .where( eqNttSnOption( nttSn ), eqStepOption(1))
			                                         .orderBy(
													  nttReply.nttReplySeq.desc()
													  ).fetch();
	  
	  
	  
	  return results; }
	 
    
    
    

    


}