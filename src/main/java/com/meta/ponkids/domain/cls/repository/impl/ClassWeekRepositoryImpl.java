package com.meta.ponkids.domain.cls.repository.impl;

import com.meta.ponkids.domain.cls.dto.ClassWeekListDto;
import com.meta.ponkids.domain.cls.dto.QClassWeekListDto;
import com.meta.ponkids.domain.cls.repository.custom.ClassWeekRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
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

import static com.meta.ponkids.domain.cls.entity.QClassWeek.classWeek;

@Repository
@RequiredArgsConstructor
public class ClassWeekRepositoryImpl implements ClassWeekRepositoryCustom {
    
    private final JPAQueryFactory query;
    
    @Override
    public Page<ClassWeekListDto> getList( ClassWeekListDto listDto, Pageable pageable ) {
        
        // TODO 구현
        // (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회
        
        // TODO
        // (1) 결과list (results).
        List<ClassWeekListDto> results = query
                // select
                .select( new QClassWeekListDto(
                        classWeek.classWeekSn,
                        classWeek.classSn,
                        classWeek.classDayCd,
                        classWeek.registerId,
                        Expressions.stringTemplate( "to_char({0}, '{1s}')", classWeek.regDt, "YYYY-MM-DD HH:MM:SS" )
//                		new CaseBuilder()
//                		.when( user.gender.eq("M")).then("남자")
//                		.when( user.gender.eq("F")).then("여자")
//                		.otherwise("")
//                		.as("gender"),
                ) )
                .from( classWeek )
                // where
                .where()
//                .orderBy( classWeek.classWeekSn.desc())
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
        
        
        // TODO
        // (2) count
        JPAQuery<Long> count = query.select( classWeek.count() )
                .from( classWeek )
                .where(
                        eqOption( listDto.getSchOption(), listDto.getSchCntn() ) );
        
        return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
        
    }
    
    private BooleanExpression eqOption( String schOption, String schCntn ) {
        // 검색 옵션  A : 아이디 , B : 이름 <- 예시 일뿐 이런식으로 커스텀하면 됨
        if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
//            if ( schOption.equals( "A" ) )
//                return classWeek.classWeekSn.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else if ( schOption.equals( "B" ) )
//                return classWeek.classWeekNm.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else return null;
            return null;            // TODO (build한 이후에 해주세요. 안그럼 에러발생)  실제 구현시에는 해당부분지워주고 위에부분주석풀기
        } else {
            return null;
        }
    }
    
}
