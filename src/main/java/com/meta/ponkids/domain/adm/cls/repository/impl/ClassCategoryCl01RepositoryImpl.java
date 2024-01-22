package com.meta.ponkids.domain.adm.cls.repository.impl;


import com.meta.ponkids.domain.adm.cls.dto.ClassCategoryCl01ListDto;
import com.meta.ponkids.domain.adm.cls.repository.custom.ClassCategoryCl01RepositoryCustom;
import com.meta.ponkids.domain.adm.cls.dto.QClassCategoryCl01ListDto;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.meta.ponkids.domain.adm.cls.entity.QClassCategoryCl01.classCategoryCl01;
import static com.meta.ponkids.domain.adm.cls.entity.QClassCategoryCl02.classCategoryCl02;

@Repository
@RequiredArgsConstructor
public class ClassCategoryCl01RepositoryImpl implements ClassCategoryCl01RepositoryCustom {
    
    private final JPAQueryFactory query;
    
    @Override
    public Page<ClassCategoryCl01ListDto> getList( ClassCategoryCl01ListDto listDto, Pageable pageable ) {
        
        // (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회


//		this.classSn = classSn;
//		this.ctgrySn = ctgrySn;
//		this.crseSn = crseSn;
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
        List<ClassCategoryCl01ListDto> results = query
                // select
                .select(
                        new QClassCategoryCl01ListDto(
                                classCategoryCl01.clSn,
                                classCategoryCl01.clNm,
                                classCategoryCl01.clSeq,
                                ExpressionUtils.as( JPAExpressions.select( classCategoryCl02.count() )
                                        .from( classCategoryCl02 )
                                        .where( classCategoryCl02.parntsClSn.eq( classCategoryCl01.clSn ) ), "childCateCnt" ),
                                classCategoryCl01.registerId,
                                classCategoryCl01.regDt
                        )
                )
                .from( classCategoryCl01 )
                // where
                .where( eqOption( listDto.getSchOption(), listDto.getSchCntn() ) )
                // order by
                .orderBy( classCategoryCl01.clSeq.asc() )
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
        
        // (2) count
        JPAQuery<Long> count = query.select( classCategoryCl01.count() )
                .from( classCategoryCl01 )
                .where(
                        eqOption( listDto.getSchOption(), listDto.getSchCntn() ) );
        
        
        return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
        
    }
    
    private BooleanExpression eqOption( String schOption, String schCntn ) {
        // 검색 옵션  A : 아이디 , B : 이름 <- 예시 일뿐 이런식으로 커스텀하면 됨
        if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
            if ( schOption.equals( "A" ) )
//                return classCategoryCl01.clNm.contains( schCntn );
                return classCategoryCl01.clNm.toUpperCase().contains( schCntn.toUpperCase() );
//            else if ( schOption.equals( "B" ) )
//                return clas.classNm.contains( schCntn ); // LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else return null;
            return null;            // (build한 이후에 해주세요. 안그럼 에러발생)  실제 구현시에는 해당부분지워주고 위에부분주석풀기
        } else {
            return null;
        }
    }
    
}
