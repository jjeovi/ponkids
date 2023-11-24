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
				 .where(
	                   eqOption( nttListDto.getSchOption(), nttListDto.getSchCntn() )
	                )
				   .orderBy( ntt.nttSn.desc() )
				   .offset( pageable.getOffset() )
	               .limit( pageable.getPageSize() )
	               .fetch();
		 
		   // (2) count
	        JPAQuery<Long> count = query.select(ntt.count())
	                .from(ntt)
	                .where(
	 	                   eqOption( nttListDto.getSchOption(), nttListDto.getSchCntn() )
	 	                );
	             
	               
	    	return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
	}
	
    
    private BooleanExpression eqOption(String schOption, String schCntn){
        // 검색 옵션  A : 아이디 , B : 이름
        if (StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn )){
                 if( schOption.equals("A")) return ntt.nttNm.contains( schCntn ); // LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
            else if (schOption.equals("B")) return ntt.registerId.contains( schCntn ); // LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
            else                            return null;
        } else { return null; }
    }
    
    
    
    public Integer getMaxNttRdcnt(int nttSn) {
    	int number = query.select(ntt.nttRdcnt.max().coalesce(0))
    			     .from(ntt)
    			     .where(
    			    		 eqNttSnOption( nttSn )
    		                )
    			   .fetchOne();

    	number= number +1;
    	
    	return number;
    	
    }
    
    
    
    // -------------------------------- WHERE 검색 옵션 setting --------------------------------
    private BooleanExpression eqNttSnOption( int nttSn) {
        return  ntt.nttSn.eq( nttSn );
    }
    


}