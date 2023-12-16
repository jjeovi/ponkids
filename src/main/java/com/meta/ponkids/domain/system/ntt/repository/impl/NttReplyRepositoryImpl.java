package com.meta.ponkids.domain.system.ntt.repository.impl;

import com.meta.ponkids.domain.system.ntt.dto.NttReplyListDto;
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

import java.time.format.DateTimeFormatter;
import java.util.List;


import static com.meta.ponkids.domain.system.ntt.entity.QNttReply.nttReply;




/**
 * className      : NttReplyRepositoryImpl
 * author         : ehlee
 * date           : 2023-12-02
 * description    : class of 게시물 댓글 RepositoryImpl
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-12-02        ehlee             최초 생성
 */
@Repository
@RequiredArgsConstructor
public class NttReplyRepositoryImpl    {
    private final JPAQueryFactory query;

    // 댓글 순번 + 1
    public Integer MaxNttReplySeq(Long nttSn) {
    	int number = query.select(nttReply.nttReplySeq.max().coalesce(0))
    			     .from(nttReply)
    			     .where(
    			    		 nttReply.nttSn.eq( nttSn )
    		                )
    			   .fetchOne();

    	number= number +1;
    	
    	return number;
    	
    	
    
    }

	  // 댓글 목록
      public List<NttReplyListDto> getList(Long nttSn) {
	  
	  List<NttReplyListDto> results = query
			                         .select(new QNttReplyListDto(
													               nttReply.nttReplySn,
													               nttReply.nttSn,
													               nttReply.step,
													               nttReply.parntsReplySn, 
													               nttReply.nttReplySeq,
													               nttReply.nttReplyCn,
													               nttReply.registerId,
													               nttReply.writerDt,
  													               nttReply.delYn
  													               ))
			                                            .from(nttReply)
			                                            .where( nttReply.nttSn.eq( nttSn ),
			                                            		nttReply.step.eq(1))
			                                            .orderBy(nttReply.nttReplySeq.desc())
			                                            .fetch();
	  
	  
	  
	       return results; 
	       
          }
	  
	  
	  // 답글 목록
	  public List<NttReplyListDto> getAnswerReplyList(Long nttReplySn) {
		  
	  List<NttReplyListDto> results = query
			                         .select(new QNttReplyListDto(
													              nttReply.nttReplySn,
													              nttReply.nttSn,
													              nttReply.step,
													              nttReply.parntsReplySn, 
													              nttReply.nttReplySeq,
													              nttReply.nttReplyCn,
													              nttReply.registerId,
													              nttReply.writerDt,
  													              nttReply.delYn
													             ))
			                                         .from(nttReply)
			                                         .where( nttReply.parntsReplySn.eq(nttReplySn))
			                                         .orderBy(nttReply.nttReplySeq.desc())
			                                         .fetch();
	  
	  
	  
	  return results; 
	  }


}