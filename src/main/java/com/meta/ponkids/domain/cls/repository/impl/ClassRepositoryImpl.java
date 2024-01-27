package com.meta.ponkids.domain.cls.repository.impl;


import com.meta.ponkids.domain.cls.dto.ClassListDto;
import com.meta.ponkids.domain.cls.dto.QClassListDto;
import com.meta.ponkids.domain.cls.repository.custom.ClassRepositoryCustom;
import com.meta.ponkids.global.common.dto.CategoryDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.meta.ponkids.domain.cls.entity.QClass.class$;
import static com.meta.ponkids.domain.cls.entity.QClassCategoryCl01.classCategoryCl01;
import static com.meta.ponkids.domain.cls.entity.QClassCategoryCl02.classCategoryCl02;

@Repository
@RequiredArgsConstructor
public class ClassRepositoryImpl implements ClassRepositoryCustom {
    
    private final JPAQueryFactory query;
    
    @Override
    public Page<ClassListDto> getList( ClassListDto listDto, Pageable pageable ) {
        
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
        List<ClassListDto> results = query
                // select
                .select( new QClassListDto(
                        class$.classSn,
                        class$.ctgrySn,
                        classCategoryCl01.clNm.as( "ctgryNm" ),
                        class$.crseSn,
                        classCategoryCl02.clNm.as( "crseNm" ),
                        class$.classSj,
                        class$.classSumry,
                        class$.classDc,
                        class$.classAmt,
                        class$.classDscntBfeAmt,
                        class$.classPdSetYn,
                        class$.classBeginDt,
                        class$.classEndDt,
                        class$.thumbAtchFileSn,
                        class$.atchFileSn,
                        new CaseBuilder()
                        			.when( class$.classExpsrYn.eq("Y")).then("표시")
                        			.when( class$.classExpsrYn.eq("N")).then("미표시")
                        			.otherwise("")
                        			.as("classExpsrYn"),
                        new CaseBuilder()
                        			.when( 	class$.classPdSetYn.eq("Y")).then(
                        					class$.classBeginDt.concat(" ~ ").concat(class$.classEndDt)
                        				)
                        			.when( class$.classPdSetYn.eq("N")).then("상시")
                        			.otherwise("")
                        			.as("classExpsrPeriod"),
                        class$.registerId,
                        Expressions.stringTemplate( "to_char({0}, '{1s}')", class$.regDt, "YYYY-MM-DD HH:MM:SS" )
                ) )
                .from( class$ )
                .leftJoin( classCategoryCl01 )
                // join 에는 delYn 조건 필수로 추가
                .on(    class$.ctgrySn.eq( classCategoryCl01.clSn ),
                        classCategoryCl01.delYn.eq( "N" )
                )
                .leftJoin( classCategoryCl02 )
                // join 에는 delYn 조건 필수로 추가
                .on(    class$.crseSn.eq( classCategoryCl02.clSn ),
                        classCategoryCl02.delYn.eq("N")
                )
                // where
                .where( 
                		eqCateLv1( listDto.getCategory() ),
                		eqCateLv2( listDto.getCategory() ),
                		eqOption( listDto.getSchOption(), listDto.getSchCntn() ) 
                		)
                .orderBy( class$.classSn.desc())
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
        
        // (2) count
        JPAQuery<Long> count = query.select( class$.count() )
                .from( class$ )
                .where(
                		eqCateLv1( listDto.getCategory() ),
                		eqCateLv2( listDto.getCategory() ),
                        eqOption( listDto.getSchOption(), listDto.getSchCntn() )
                       );
        
        
        return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
        
    }
    
    @Override
    public List<ClassListDto> getList( ClassListDto listDto ) {
        
        return query
                // select
                .select( new QClassListDto(
                        class$.classSn,
                        class$.ctgrySn,
                        classCategoryCl01.clNm.as( "ctgryNm" ),
                        class$.crseSn,
                        classCategoryCl02.clNm.as( "crseNm" ),
                        class$.classSj,
                        class$.classSumry,
                        class$.classDc,
                        class$.classAmt,
                        class$.classDscntBfeAmt,
                        class$.classPdSetYn,
                        class$.classBeginDt,
                        class$.classEndDt,
                        class$.thumbAtchFileSn,
                        class$.atchFileSn,
                        new CaseBuilder()
                                .when( class$.classExpsrYn.eq("Y")).then("표시")
                                .when( class$.classExpsrYn.eq("N")).then("미표시")
                                .otherwise("")
                                .as("classExpsrYn"),
                        new CaseBuilder()
                                .when( 	class$.classPdSetYn.eq("Y")).then(
                                        class$.classBeginDt.concat(" ~ ").concat(class$.classEndDt)
                                )
                                .when( class$.classPdSetYn.eq("N")).then("상시")
                                .otherwise("")
                                .as("classExpsrPeriod"),
                        class$.registerId,
                        Expressions.stringTemplate( "to_char({0}, '{1s}')", class$.regDt, "YYYY-MM-DD HH:MM:SS" )
                ) )
                .from( class$ )
                .leftJoin( classCategoryCl01 )
                // join 에는 delYn 조건 필수로 추가
                .on(    class$.ctgrySn.eq( classCategoryCl01.clSn ),
                        classCategoryCl01.delYn.eq( "N" )
                )
                .leftJoin( classCategoryCl02 )
                // join 에는 delYn 조건 필수로 추가
                .on(    class$.crseSn.eq( classCategoryCl02.clSn ),
                        classCategoryCl02.delYn.eq("N")
                )
                // where
                .where(
                        eqCateLv1( listDto.getCategory() ),
                        eqCateLv2( listDto.getCategory() ),
                        eqOption( listDto.getSchOption(), listDto.getSchCntn() )
                )
                .orderBy( class$.classSn.desc())
                .fetch();
    }
    
    
    
    @Override
    public ClassListDto getByClassSn( Long classSn ) {
        
        return query
                // select
                .select( new QClassListDto(
                        class$.classSn,
                        class$.ctgrySn,
                        classCategoryCl01.clNm.as( "ctgryNm" ),
                        class$.crseSn,
                        classCategoryCl02.clNm.as( "crseNm" ),
                        class$.classSj,
                        class$.classSumry,
                        class$.classDc,
                        class$.classAmt,
                        class$.classDscntBfeAmt,
                        class$.classPdSetYn,
                        class$.classBeginDt,
                        class$.classEndDt,
                        class$.thumbAtchFileSn,
                        class$.atchFileSn,
                        new CaseBuilder()
                                .when( class$.classExpsrYn.eq("Y")).then("표시")
                                .when( class$.classExpsrYn.eq("N")).then("미표시")
                                .otherwise("")
                                .as("classExpsrYn"),
                        new CaseBuilder()
                                .when( 	class$.classPdSetYn.eq("Y")).then(
                                        class$.classBeginDt.concat(" ~ ").concat(class$.classEndDt)
                                )
                                .when( class$.classPdSetYn.eq("N")).then("상시")
                                .otherwise("")
                                .as("classExpsrPeriod"),
                        class$.registerId,
                        Expressions.stringTemplate( "to_char({0}, '{1s}')", class$.regDt, "YYYY-MM-DD HH:MM:SS" )
                ) )
                .from( class$ )
                .leftJoin( classCategoryCl01 )
                // join 에는 delYn 조건 필수로 추가
                .on(    class$.ctgrySn.eq( classCategoryCl01.clSn ),
                        classCategoryCl01.delYn.eq( "N" )
                )
                .leftJoin( classCategoryCl02 )
                // join 에는 delYn 조건 필수로 추가
                .on(    class$.crseSn.eq( classCategoryCl02.clSn ),
                        classCategoryCl02.delYn.eq("N")
                )
                // where
                .where(
                        eqClassSn(classSn)					// 고유한 1건만 조회 (pk 로 조회 ) 
                )
                .orderBy( class$.classSn.desc())
                .fetchFirst();
    }
    
    

    
    
	// -------------------------------- WHERE 검색 옵션 setting --------------------------------
    
    private BooleanExpression eqOption( String schOption, String schCntn ) {
        // 검색 옵션  A : 아이디 , B : 이름 <- 예시 일뿐 이런식으로 커스텀하면 됨
        if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
            if ( schOption.equals( "A" ) )
                return class$.classSj.contains( schCntn );
//            else if ( schOption.equals( "B" ) )
//                return clas.classNm.contains( schCntn ); // LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else return null;
            return null;            // (build한 이후에 해주세요. 안그럼 에러발생)  실제 구현시에는 해당부분지워주고 위에부분주석풀기
        } else {
            return null;
        }
    }

    // 카테고리 lv 1 검색 옵션
    // class$.ctgrySn == lv1sn 
    private BooleanExpression eqCateLv1( CategoryDto categoryDto ) {
        return ( categoryDto != null && categoryDto.getLv1Sn() != null
                && categoryDto.getLv1Sn() != 0 /* 0이 아닌 것은  검색 제외 */ ) ? class$.ctgrySn.eq( categoryDto.getLv1Sn() ) : null;
    }
    
    // 카테고리 lv 2 검색 옵션
    // class$.crseSn == lv2sn 
    private BooleanExpression eqCateLv2( CategoryDto categoryDto ) {
    	return ( categoryDto != null && categoryDto.getLv2Sn() != null
    			&& categoryDto.getLv2Sn() != 0 /* 0이 아닌 것은  검색 제외 */ ) ? class$.crseSn.eq( categoryDto.getLv2Sn() ) : null;
    }
    
    // pk 로 고유값 1건만 조회
    private BooleanExpression eqClassSn( Long classSn ) {
    	return ( classSn != null ) ? class$.classSn.eq( classSn ) : null;
    }
    
    
    
}
