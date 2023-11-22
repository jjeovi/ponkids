package com.meta.ponkids.domain.system.ntt.repository.impl;



import com.meta.ponkids.domain.system.ntt.repository.custom.NttRepositoryCustom;
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

    public Integer MaxNttReplySeq() {
    	//String number = query.select(nttReply.parntsReplySn.max())
    			//.from(nttReply)
    			//.fetchOne();

      //  return Integer.parseInt(number);
    	return null;
    	
    	
    
    }
	
	 


}