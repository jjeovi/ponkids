package com.meta.ponkids.domain.system.ntt.repository.impl;

import com.meta.ponkids.domain.system.ntt.dto.NttListDto;
import com.meta.ponkids.domain.system.ntt.dto.QNttListDto;


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

import static com.meta.ponkids.domain.system.ntt.entity.QNtt.ntt;





/**
 * className      : NttRepositoryImpl
 * author         : ehlee
 * date           : 2023-12-02
 * description    : class of 게시물 RepositoryImpl
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-12-02        ehlee             최초 생성
 */
@Repository
@RequiredArgsConstructor
public class NttRepositoryImpl implements NttRepositoryCustom   {
    private final JPAQueryFactory query;


	@Override
	public Page<NttListDto> getList(NttListDto nttListDto, Pageable pageable) {
		 
    	// (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회
    	
    
    	// (1) 결과 list (results).
		 List<NttListDto> results = query
				                   .select(new QNttListDto(
				                                  ntt.nttSn,
				                                  ntt.bbsSn,
				                                  ntt.nttNm,
				                                  ntt.nttCn,
				                                  ntt.nttRdcnt,
                                                  ntt.openYn,
                                                  ntt.noticeSetYn,
                                                  ntt.registerId,
                                                  ntt.regDt
				                                  )).from(ntt)
				                         .where(
	                                            eqOption( nttListDto.getSchOption(), nttListDto.getSchCntn()),
	                                            eqBbsSnOption(nttListDto.getBbsSn())
	                                            )
				                         .orderBy( ntt.nttSn.desc() )
				                         .offset( pageable.getOffset() )
	                                     .limit( pageable.getPageSize() )
	                                     .fetch();
		 
		   // (2) count
	        JPAQuery<Long> count = query.select(ntt.count())
	                .from(ntt)
	                .where(
	 	                   eqOption( nttListDto.getSchOption(), nttListDto.getSchCntn() ),
	 	                   eqBbsSnOption(nttListDto.getBbsSn())
	 	                );
	             
	    	return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
	}
	
   
    
    // -------------------------------- WHERE 검색 옵션 setting --------------------------------
    private BooleanExpression eqNttSnOption( Long nttSn) {
        return  ntt.nttSn.eq( nttSn );
    }
    
    private BooleanExpression eqBbsSnOption( Long bbsSn) {
        return  ntt.bbsSn.eq( bbsSn );
    }
    
    private BooleanExpression eqNoticeSetYnOption( String noticeSetYn) {
    	return  ntt.noticeSetYn.eq( noticeSetYn );
    }
    
    private BooleanExpression eqOption(String schOption, String schCntn){
        // 검색 옵션  A : 아이디 , B : 이름
        if (StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn )){
                 if( schOption.equals("A")) return ntt.nttNm.contains( schCntn ); // LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
            else if (schOption.equals("B")) return ntt.registerId.contains( schCntn ); // LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
            else                            return null;
        } else { return null; }
    }

    // 조회수 +1 
    public Integer getMaxNttRdcnt(Long nttSn) {
    	int number = query.select(ntt.nttRdcnt.max().coalesce(0))
    			           .from(ntt)
    			           .where(
    			    		       eqNttSnOption( nttSn )
    		                      )
    			           .fetchOne();

    	number= number +1;
    	
    	return number;
    	
    }
    
    // 게시물 순번 + 1
    public Integer MaxNttSeq(Long bbsSn) {
    	int number = query.select(ntt.nttSeq.max().coalesce(0))
    			     .from(ntt)
    			     .where(
    			    		 eqBbsSnOption( bbsSn )
    		                )
    			   .fetchOne();

    	number= number +1;
    	
    	return number;
    
    }

    // 게시물 존재여부
	@Override
	public int getExistsNtt(Long bbsSn) {
    	int count = query.select(ntt.nttRdcnt.max().coalesce(0))
			     .from(ntt)
			     .where(
			    		 eqBbsSnOption( bbsSn )
		                )
			   .fetchOne();

	  return count;
		
	}
    
    
    // 공지 게시물 목록
    public List<NttListDto> getNoticeList(Long bbsSn) {
		  
  	  List<NttListDto> results = query
  			                     .select(new QNttListDto(
										  			     ntt.nttSn,
													     ntt.bbsSn,
													     ntt.nttNm,
													     ntt.nttCn,
													     ntt.nttRdcnt,
										                 ntt.openYn,
										                 ntt.registerId,
										                 ntt.registerId,
										                 ntt.regDt
													     ))
  			                                         .from(ntt)
  			                                         .where( eqBbsSnOption(bbsSn) ,
  			                                                 eqNoticeSetYnOption("Y"))
  			                                         .orderBy(ntt.noticeSeq.desc())
  			                                         .fetch();
  	  
  	        return results; 
  	     }
    
    


}