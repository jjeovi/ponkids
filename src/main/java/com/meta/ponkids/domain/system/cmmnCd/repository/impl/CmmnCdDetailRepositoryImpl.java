package com.meta.ponkids.domain.system.cmmnCd.repository.impl;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.meta.ponkids.domain.system.cmmnCd.dto.QCmmnCdDetailListDto;
import com.meta.ponkids.domain.system.cmmnCd.dto.CmmnCdDetailListDto;
import com.meta.ponkids.domain.system.cmmnCd.repository.custom.CmmnCdDetailRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import static com.meta.ponkids.domain.system.cmmnCd.entity.QCmmnCdDetail.cmmnCdDetail;

@Repository
@RequiredArgsConstructor
public class CmmnCdDetailRepositoryImpl implements CmmnCdDetailRepositoryCustom {
	
	private final JPAQueryFactory query;
	
	@Override
	public Page<CmmnCdDetailListDto> getList( CmmnCdDetailListDto listDto, Pageable pageable ) {
		
		// TODO 구현
		// (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회
        
		// TODO
        // (1) 결과list (results).
		List<CmmnCdDetailListDto> results = query
				// select
                .select( new QCmmnCdDetailListDto(
                		cmmnCdDetail.cdDetailSn,
                		cmmnCdDetail.cdSn,
                		cmmnCdDetail.cdDetailSeq,
                		cmmnCdDetail.cdDetailNm,
                		cmmnCdDetail.cdDetailDc,
                		cmmnCdDetail.cdDetailVal1,
                		cmmnCdDetail.cdDetailVal2,
                		cmmnCdDetail.cdDetailVal3,
                		cmmnCdDetail.cdDetailVal4,
                		cmmnCdDetail.cdDetailVal5,
                		cmmnCdDetail.useYn
                		) )					
                .from( cmmnCdDetail )
                // where
                .where()
//                .orderBy( cmmnCdDetail.cdDetailSn.desc())
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
		
		
		// TODO
		// (2) count
        JPAQuery<Long> count = query.select( cmmnCdDetail.count() )
                .from( cmmnCdDetail )									
                .where(
                        eqOption( listDto.getSchOption(), listDto.getSchCntn() ) );
                
				
		
		return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
		
	}
	
	
    private BooleanExpression eqOption( String schOption, String schCntn ) {
        // 검색 옵션  A : 아이디 , B : 이름 <- 예시 일뿐 이런식으로 커스텀하면 됨
        if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
//            if ( schOption.equals( "A" ) )
//                return cmmnCdDetail.cdDetailSn.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else if ( schOption.equals( "B" ) )
//                return cmmnCdDetail.cmmnCdDetailNm.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else return null;
        	return null;			// TODO (build한 이후에 해주세요. 안그럼 에러발생)  실제 구현시에는 해당부분지워주고 위에부분주석풀기
        } else {
            return null;
        }
    }

}
