package com.meta.ponkids.domain.qestnar.repository.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.meta.ponkids.domain.qestnar.dto.QQestnarQestnDetailListDto;
import com.meta.ponkids.domain.qestnar.dto.QestnarQestnDetailListDto;
import com.meta.ponkids.domain.qestnar.repository.custom.QestnarQestnDetailRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import static com.meta.ponkids.domain.qestnar.entity.QQestnarQestnDetail.qestnarQestnDetail;
import static com.meta.ponkids.domain.qestnar.entity.QQestnarQestn.qestnarQestn;
import static com.meta.ponkids.domain.qestnar.entity.QQestnarGroup.qestnarGroup;

@Repository
@RequiredArgsConstructor
public class QestnarQestnDetailRepositoryImpl implements QestnarQestnDetailRepositoryCustom {
	
	private final JPAQueryFactory query;
	
	@Override
	public Page<QestnarQestnDetailListDto> getList( QestnarQestnDetailListDto listDto, Pageable pageable ) {
		
		// (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회
        
        // (1) 결과list (results).
		List<QestnarQestnDetailListDto> results = query
				// select
                .select( new QQestnarQestnDetailListDto(
                		qestnarQestnDetail.qestnarQestnDetailSn,
                		qestnarQestnDetail.qestnarQestnSn,
                		qestnarQestnDetail.qestnarQestnDetailSeq,
                		qestnarQestnDetail.qestnarQestnDetailCn,
                		qestnarQestnDetail.registerId,
                		Expressions.stringTemplate("to_char({0}, '{1s}')", qestnarQestnDetail.regDt, "YYYY-MM-DD HH24:MI:SS")
                		) )					
                .from( qestnarQestnDetail )
                // where
                .where(
		eqOption( listDto.getSchOption(), listDto.getSchCntn() )
		)
                .orderBy( qestnarQestnDetail.qestnarQestnDetailSn.desc())
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
		
		
		// (2) count
        JPAQuery<Long> count = query.select( qestnarQestnDetail.count() )
                .from( qestnarQestnDetail )									
                .where(
                        eqOption( listDto.getSchOption(), listDto.getSchCntn() )
		);
                

		return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
		
	}
	
	

	@Override
	public List<QestnarQestnDetailListDto> getListByQestnarGroupSnOrderByQestnarQestnSnAsc( Long qestnarGroupSn ) {
		return query
			.select(
					new QQestnarQestnDetailListDto(
	                		qestnarQestnDetail.qestnarQestnDetailSn,
	                		qestnarQestnDetail.qestnarQestnSn,
	                		qestnarQestnDetail.qestnarQestnDetailSeq,
	                		qestnarQestnDetail.qestnarQestnDetailCn,
	                		qestnarQestnDetail.registerId,
	                		Expressions.stringTemplate("to_char({0}, '{1s}')", qestnarQestnDetail.regDt, "YYYY-MM-DD HH24:MI:SS")
	                		)
			)
			.from(qestnarQestnDetail)
			.leftJoin(qestnarQestn)
			.on(
					qestnarQestn.qestnarQestnSn.eq( qestnarQestnDetail.qestnarQestnSn ),
					qestnarQestn.delYn.eq( "N" )
					
			)
			.leftJoin(qestnarGroup)
			.on(
					qestnarGroup.qestnarGroupSn.eq( qestnarQestn.qestnarGroupSn ),
					qestnarGroup.delYn.eq( "N" )
					
			)
			.where(
					qestnarGroup.qestnarGroupSn.eq(qestnarGroupSn)
			)
			.orderBy(qestnarQestnDetail.qestnarQestnSn.asc())
			.fetch();
	}
	
	
    private BooleanExpression eqOption( String schOption, String schCntn ) {
        // 검색 옵션  A : 아이디 , B : 이름 <- 예시 일뿐 이런식으로 커스텀하면 됨
        if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
//            if ( schOption.equals( "A" ) )
//                return qestnarQestnDetail.qestnarQestnDetailSn.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else if ( schOption.equals( "B" ) )
//                return qestnarQestnDetail.qestnarQestnDetailNm.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else return null;
        	return null;			// TODO (build한 이후에 해주세요. 안그럼 에러발생)  실제 구현시에는 해당부분지워주고 위에부분주석풀기
        } else {
            return null;
        }
    }

}
