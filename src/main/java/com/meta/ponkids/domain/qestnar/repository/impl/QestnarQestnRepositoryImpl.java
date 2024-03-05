package com.meta.ponkids.domain.qestnar.repository.impl;


import static com.meta.ponkids.domain.qestnar.entity.QQestnarQestn.qestnarQestn;
import static com.meta.ponkids.domain.system.cmmnCd.entity.QCmmnCdDetail.cmmnCdDetail;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.meta.ponkids.domain.qestnar.dto.QQestnarQestnListDto;
import com.meta.ponkids.domain.qestnar.dto.QestnarQestnListDto;
import com.meta.ponkids.domain.qestnar.repository.custom.QestnarQestnRepositoryCustom;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QestnarQestnRepositoryImpl implements QestnarQestnRepositoryCustom {
	
	private final JPAQueryFactory query;
	
	@Override
	public Page<QestnarQestnListDto> getList( QestnarQestnListDto listDto, Pageable pageable ) {
		
		// (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회
        
        // (1) 결과list (results).
		List<QestnarQestnListDto> results = query
				// select
                .select( new QQestnarQestnListDto(
                		qestnarQestn.qestnarQestnSn,
                		qestnarQestn.qestnarGroupSn,
                		qestnarQestn.qestnarQestnSeq,
                		qestnarQestn.qestnarQestnItemTyCd,
                		ExpressionUtils.as( JPAExpressions.select( cmmnCdDetail.cdDetailNm )
                                .from( cmmnCdDetail )
                                .where( 
                                		cmmnCdDetail.cdNm.eq("QESTNAR_QESTN_ITEM_TY_CD"),
                                		cmmnCdDetail.cdDetailVal1.eq( qestnarQestn.qestnarQestnItemTyCd ),
                                		cmmnCdDetail.useYn.eq( "Y" ),
                                		cmmnCdDetail.delYn.eq( "N" ) ), "qestnarQestnItemTyNm" ),
                		qestnarQestn.qestnarQestnItemCn,
                		qestnarQestn.qestnarQestnEssntlYn,
                		new CaseBuilder()
			                        .when( qestnarQestn.qestnarQestnEssntlYn.eq( "Y" ) ).then( "필수" )
			                        .when( qestnarQestn.qestnarQestnEssntlYn.eq( "N" ) ).then( "선택" )
			                        .otherwise( "" )
			                        .as( "qestnarQestnEssntlYnNm" ),
                		qestnarQestn.atchFileSn,
                		qestnarQestn.registerId,
                		Expressions.stringTemplate("to_char({0}, '{1s}')", qestnarQestn.regDt, "YYYY-MM-DD HH:MM:SS")
                		) )					
                .from( qestnarQestn )
                // where
                .where(
                		eqOption( listDto.getSchOption(), listDto.getSchCntn() )
				)
//                .orderBy( qestnarQestn.qestnarQestnSn.desc())
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
		
		
		// TODO
		// (2) count
        JPAQuery<Long> count = query.select( qestnarQestn.count() )
                .from( qestnarQestn )									
                .where(
                        eqOption( listDto.getSchOption(), listDto.getSchCntn() )
		);
                

		return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
		
	}
	
	@Override
	public List<QestnarQestnListDto> findByQestnarGroupSn( Long qestnarGroupSn ) {
		return query
			.select(
					 new QQestnarQestnListDto(
		                		qestnarQestn.qestnarQestnSn,
		                		qestnarQestn.qestnarGroupSn,
		                		qestnarQestn.qestnarQestnSeq,
		                		qestnarQestn.qestnarQestnItemTyCd,
		                		ExpressionUtils.as( JPAExpressions.select( cmmnCdDetail.cdDetailNm )
		                                .from( cmmnCdDetail )
		                                .where( 
		                                		cmmnCdDetail.cdNm.eq("QESTNAR_QESTN_ITEM_TY_CD"),
		                                		cmmnCdDetail.cdDetailVal1.eq( qestnarQestn.qestnarQestnItemTyCd ),
		                                		cmmnCdDetail.useYn.eq( "Y" ),
		                                		cmmnCdDetail.delYn.eq( "N" ) ), "qestnarQestnItemTyNm" ),
		                		qestnarQestn.qestnarQestnItemCn,
		                		qestnarQestn.qestnarQestnEssntlYn,
		                		new CaseBuilder()
						                        .when( qestnarQestn.qestnarQestnEssntlYn.eq( "Y" ) ).then( "필수" )
						                        .when( qestnarQestn.qestnarQestnEssntlYn.eq( "N" ) ).then( "선택" )
						                        .otherwise( "" )
						                        .as( "qestnarQestnEssntlYnNm" ),
		                		qestnarQestn.atchFileSn,
		                		qestnarQestn.registerId,
		                		Expressions.stringTemplate("to_char({0}, '{1s}')", qestnarQestn.regDt, "YYYY-MM-DD HH:MM:SS")
		                		)
					
			)
			.from( qestnarQestn )
			.where(
					eqQestnarGroupSn( qestnarGroupSn )
			)
			.orderBy( qestnarQestn.qestnarQestnSeq.asc() )
			.fetch();
			
	}
	
	
	// -------------------------------- WHERE 검색 옵션 setting --------------------------------
	// -------------------------------- WHERE 검색 옵션 setting --------------------------------
	
	  
    private BooleanExpression eqQestnarGroupSn( Long qestnarGroupSn ) {
        return qestnarGroupSn != null ? qestnarQestn.qestnarGroupSn.eq( qestnarGroupSn ) : null;
    }
	
	
    private BooleanExpression eqOption( String schOption, String schCntn ) {
        // 검색 옵션  A : 아이디 , B : 이름 <- 예시 일뿐 이런식으로 커스텀하면 됨
        if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
//            if ( schOption.equals( "A" ) )
//                return qestnarQestn.qestnarQestnSn.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else if ( schOption.equals( "B" ) )
//                return qestnarQestn.qestnarQestnNm.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else return null;
        	return null;			// TODO (build한 이후에 해주세요. 안그럼 에러발생)  실제 구현시에는 해당부분지워주고 위에부분주석풀기
        } else {
            return null;
        }
    }

}
