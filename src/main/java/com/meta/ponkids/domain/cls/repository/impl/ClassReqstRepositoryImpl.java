package com.meta.ponkids.domain.cls.repository.impl;


import static com.meta.ponkids.domain.cls.entity.QClass.class$;
import static com.meta.ponkids.domain.cls.entity.QClassCategoryCl01.classCategoryCl01;
import static com.meta.ponkids.domain.cls.entity.QClassCategoryCl02.classCategoryCl02;
import static com.meta.ponkids.domain.cls.entity.QClassReqst.classReqst;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.meta.ponkids.domain.cls.dto.ClassReqstListDto;
import com.meta.ponkids.domain.cls.dto.QClassReqstListDto;
import com.meta.ponkids.domain.cls.repository.custom.ClassReqstRepositoryCustom;
import com.meta.ponkids.global.common.dto.CategoryDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ClassReqstRepositoryImpl implements ClassReqstRepositoryCustom {
    
    private final JPAQueryFactory query;
    
    @Override
    public Page<ClassReqstListDto> getList( ClassReqstListDto listDto, Pageable pageable ) {
        
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
        List<ClassReqstListDto> results = query
                // select
                .select( new QClassReqstListDto(
                		classReqst.classReqstSn,
                		classReqst.classSn,
                		class$.thumbAtchFileSn,
                        class$.ctgrySn,
                        classCategoryCl01.clNm.as( "ctgryNm" ),
                        class$.crseSn,
                        classCategoryCl02.clNm.as( "crseNm" ),
                        class$.classSj,
                        classReqst.userSn,
                        classReqst.totReqstCnt,
                        classReqst.totReqstAmt,
                        Expressions.stringTemplate( "to_char({0}, '{1s}')", classReqst.regDt, "YYYY-MM-DD" )
                ) )
                .from( classReqst )
                // join 에는 delYn 조건 필수로 추가
                .leftJoin( class$ )
                .on( 	class$.classSn.eq( classReqst.classSn ),
                		class$.delYn.eq( "N" )
                )	
                .leftJoin( classCategoryCl01 )
                // join 에는 delYn 조건 필수로 추가
                .on(    classCategoryCl01.clSn.eq( class$.ctgrySn ),
                        classCategoryCl01.delYn.eq( "N" )
                )
                .leftJoin( classCategoryCl02 )
                // join 에는 delYn 조건 필수로 추가
                .on(    classCategoryCl02.clSn.eq( class$.crseSn ),
                        classCategoryCl02.delYn.eq("N")
                )
                // where
                .where( 
                		eqUserSn( listDto.getUserSn() ),
                		eqCateLv1( listDto.getCategory() ),
                		eqCateLv2( listDto.getCategory() )
                		)
                .orderBy( classReqst.classReqstSn.desc())
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
        
        // (2) count
        JPAQuery<Long> count = query.select( classReqst.count() )
                .from( classReqst )
                .where(
                		eqUserSn( listDto.getUserSn() ),
                		eqCateLv1( listDto.getCategory() ),
                		eqCateLv2( listDto.getCategory() )
                       );
        
        
        return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
        
    }
    @Override
    public ClassReqstListDto getByClassReqstSn( Long classReqstSn ) {
    	
    	return query
    			.select(
    					new QClassReqstListDto(
    	                		classReqst.classReqstSn,
    	                		classReqst.classSn,
    	                		class$.thumbAtchFileSn,
    	                        class$.ctgrySn,
    	                        classCategoryCl01.clNm.as( "ctgryNm" ),
    	                        class$.crseSn,
    	                        classCategoryCl02.clNm.as( "crseNm" ),
    	                        class$.classSj,
    	                        classReqst.userSn,
    	                        classReqst.totReqstCnt,
    	                        classReqst.totReqstAmt,
    	                        Expressions.stringTemplate( "to_char({0}, '{1s}')", classReqst.regDt, "YYYY-MM-DD" )
    					))
    			.from( classReqst )
                // join 에는 delYn 조건 필수로 추가
                .leftJoin( class$ )
                .on( 	class$.classSn.eq( classReqst.classSn ),
                		class$.delYn.eq( "N" )
                )	
                .leftJoin( classCategoryCl01 )
                // join 에는 delYn 조건 필수로 추가
                .on(    classCategoryCl01.clSn.eq( class$.ctgrySn ),
                        classCategoryCl01.delYn.eq( "N" )
                )
                .leftJoin( classCategoryCl02 )
                // join 에는 delYn 조건 필수로 추가
                .on(    classCategoryCl02.clSn.eq( class$.crseSn ),
                        classCategoryCl02.delYn.eq("N")
                )
                // where
                .where( 
                		eqClassReqstSn( classReqstSn )
                		)
                .orderBy( classReqst.classReqstSn.desc())
                .fetchFirst();
    			// QueryDsl을 사용하고 결과를 한 건만 조회해야 할 때, 결과가 명확하게 한 건만 조회됨이 보장되지 않는다면 fetchOne()은 NonUniqueResultException을 던질 가능성이 있다.
    			// 결과를 한 건만 조회해야 할 때, 결과가 명확하게 한 건만 조회됨이 보장되지 않는다면 내부적으로 limit(1)을 수행하는 fetchFirst()를 사용하자. 
    			// (출처) :https://hungseong.tistory.com/87
        
        
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

    // 카테고리 lv 1 검색 옵션 (카테고리)
    // class$.ctgrySn == lv1sn 
    private BooleanExpression eqCateLv1( CategoryDto categoryDto ) {
        return ( categoryDto != null && categoryDto.getLv1Sn() != null
                && categoryDto.getLv1Sn() != 0 /* 0이 아닌 것은  검색 제외 */ ) ? class$.ctgrySn.eq( categoryDto.getLv1Sn() ) : null;
    }
    
    // 카테고리 lv 2 검색 옵션 (커리큘럼)
    // class$.crseSn == lv2sn 
    private BooleanExpression eqCateLv2( CategoryDto categoryDto ) {
    	return ( categoryDto != null && categoryDto.getLv2Sn() != null
    			&& categoryDto.getLv2Sn() != 0 /* 0이 아닌 것은  검색 제외 */ ) ? class$.crseSn.eq( categoryDto.getLv2Sn() ) : null;
    }
    
    
    // userSn where 절에 조회
    private BooleanExpression eqUserSn( Long userSn ) {
        return ( userSn != null ) ? ( classReqst.userSn.eq( userSn ) ) : null;
    }

    
    
    // pk 로 고유값 1건만 조회
    private BooleanExpression eqClassReqstSn( Long classReqstSn ) {
    	return ( classReqstSn != null ) ? classReqst.classReqstSn.eq( classReqstSn ) : null;
    }
    
    
    
    
}
