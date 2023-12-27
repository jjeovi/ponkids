package com.meta.ponkids.domain.cls.repository.impl;


import com.meta.ponkids.domain.cls.dto.ClassListDto;
import com.meta.ponkids.domain.cls.dto.QClassListDto;
import com.meta.ponkids.domain.cls.repository.custom.ClassRepositoryCustom;
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

import static com.meta.ponkids.domain.cls.entity.QClass.class$;

@Repository
@RequiredArgsConstructor
public class ClassRepositoryImpl implements ClassRepositoryCustom {
    
    private final JPAQueryFactory query;
    
    @Override
    public Page<ClassListDto> getList( ClassListDto listDto, Pageable pageable ) {
        
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
        List<ClassListDto> results = query
                // select
                .select( new QClassListDto(
                        class$.classSn,
                        class$.ctgryCd,
                        class$.ctgryCd.as( "ctgryNm" ),
                        class$.crseCd,
                        class$.crseCd.as( "crseNm" ),
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
                        class$.classExpsrYn,
                        class$.classExpsrYn.as( "classExpsrPeriod" ),
                        class$.registerId,
                        class$.regDt
                ) )
                .from( class$ )
                // where
                .where()
//                .orderBy( clas.classSn.desc())
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
        
        // (2) count
        JPAQuery<Long> count = query.select( class$.count() )
                .from( class$ )
                .where(
                        eqOption( listDto.getSchOption(), listDto.getSchCntn() ) );
        
        
        return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
        
    }
    
    private BooleanExpression eqOption( String schOption, String schCntn ) {
        // 검색 옵션  A : 아이디 , B : 이름 <- 예시 일뿐 이런식으로 커스텀하면 됨
        if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
            if ( schOption.equals( "A" ) )
                return class$.classSj.contains( schCntn );
//            else if ( schOption.equals( "B" ) )
//                return clas.classNm.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else return null;
            return null;            // TODO (build한 이후에 해주세요. 안그럼 에러발생)  실제 구현시에는 해당부분지워주고 위에부분주석풀기
        } else {
            return null;
        }
    }
    
}
