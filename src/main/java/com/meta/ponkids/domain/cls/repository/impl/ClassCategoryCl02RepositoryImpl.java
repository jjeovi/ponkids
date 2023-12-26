package com.meta.ponkids.domain.cls.repository.impl;


import static com.meta.ponkids.domain.cls.entity.QClassCategoryCl01.classCategoryCl01;
import static com.meta.ponkids.domain.cls.entity.QClassCategoryCl02.classCategoryCl02;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.meta.ponkids.domain.cls.dto.ClassCategoryCl02ListDto;
import com.meta.ponkids.domain.cls.dto.QClassCategoryCl02ListDto;
import com.meta.ponkids.domain.cls.repository.custom.ClassCategoryCl02RepositoryCustom;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ClassCategoryCl02RepositoryImpl implements ClassCategoryCl02RepositoryCustom {
	
	private final JPAQueryFactory query;
	
	@Override
	public Page<ClassCategoryCl02ListDto> getList( ClassCategoryCl02ListDto listDto, Pageable pageable ) {
		
		// (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회
//		this.classSn = classSn;
//		this.ctgryCd = ctgryCd;
//		this.crseCd = crseCd;
//		this. = classSj;
//		this.classSumry = classSumry;
//		this. = classDc;
//		this. = classAmt;
//		this. = classDscntBfeAmt;
//		this. = classPdSetYn;
//		this. = classBeginDt;
//		this. = classEndDt;
//		this. = thumbAtchFileSn;
//		this. = atchFileSn;
//		this. = classExpsrYn;
		
        // (1) 결과list (results).
		List<ClassCategoryCl02ListDto> results = query
				// select
                .select( new QClassCategoryCl02ListDto(
                		classCategoryCl02.clSn,
                		classCategoryCl02.parntsClSn,
                		ExpressionUtils.as( JPAExpressions.select(classCategoryCl01.clNm)
								.from(classCategoryCl01)
								.where( classCategoryCl01.clSn.eq(classCategoryCl02.parntsClSn) ) , "parntsClNm" ), 
                		// 서브쿼리 적용 = ( select from tb_class_category_cl01 tccc where tccc.cl_sn = tb_class_category_cl02.parnts_cl_sn and tccc.del_yn = 'N' ) as parntsClNm
                		classCategoryCl02.clNm,
                		classCategoryCl02.clSeq,
                		classCategoryCl02.registerId,
                		classCategoryCl02.regDt
                		) )
                .from( classCategoryCl02 )
                // where
                .where(
                		eqParntsClSn( listDto.getParntsClSn() ),
                		eqOption(listDto.getSchOption(), listDto.getSchCntn())
                		)
                // order by 
                .orderBy( classCategoryCl02.clSn.desc())
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
		
		// (2) count
        JPAQuery<Long> count = query.select( classCategoryCl02.count() )
                .from( classCategoryCl02 )
                .where(
                		eqParntsClSn( listDto.getParntsClSn() ),
                        eqOption( listDto.getSchOption(), listDto.getSchCntn() ) );
                
		return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
		
	}
	
    private BooleanExpression eqParntsClSn( Long parntsClSn ) {
    	// 0 일 경우는 검색에서 제외 ( 0 은 전체검색 조건 ) 
        return ( parntsClSn != null && parntsClSn != 0  ) ? classCategoryCl02.parntsClSn.eq( parntsClSn ) : null;
    }
	
    private BooleanExpression eqOption( String schOption, String schCntn ) {
        // 검색 옵션  A : 아이디 , B : 이름 <- 예시 일뿐 이런식으로 커스텀하면 됨
        if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
            if ( schOption.equals( "A" ) )
//            	return classCategoryCl02.clNm.contains( schCntn );								// 기존
                return classCategoryCl02.clNm.toUpperCase().contains( schCntn.toUpperCase() );	// 변경
        	return null;			// TODO (build한 이후에 해주세요. 안그럼 에러발생)  실제 구현시에는 해당부분지워주고 위에부분주석풀기
        } else {
            return null;
        }
    }

}
