package com.meta.ponkids.domain.lctre.repository.impl;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.meta.ponkids.domain.lctre.dto.QLctreListDto;
import com.meta.ponkids.domain.lctre.dto.LctreListDto;
import com.meta.ponkids.domain.lctre.repository.custom.LctreRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import static com.meta.ponkids.domain.lctre.entity.QLctre.lctre;

@Repository
@RequiredArgsConstructor
public class LctreRepositoryImpl implements LctreRepositoryCustom {
	
	private final JPAQueryFactory query;
	
	@Override
	public Page<LctreListDto> getList( LctreListDto listDto, Pageable pageable ) {
		
		// TODO 구현
		// (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회
        
		// TODO
        // (1) 결과list (results).
		List<LctreListDto> results = query
				// select
                .select( new QLctreListDto(
                		lctre.lctreSn,
						lctre.classSn,
						lctre.classWeekSn,
						lctre.lctreSeq,
						lctre.lctreSj,
						lctre.lctreDc,
						lctre.lctreApplcntGuidance,
						lctre.rcritNmprSetYn,
						lctre.rcritNmprCo,
						lctre.preparRcritNmprSetYn,
						lctre.preparRcritNmprCo,
						lctre.registerId,
                		Expressions.stringTemplate("to_char({0}, '{1s}')", lctre.regDt, "YYYY-MM-DD HH:MM:SS")
//                		new CaseBuilder()
//                		.when( user.gender.eq("M")).then("남자")
//                		.when( user.gender.eq("F")).then("여자")
//                		.otherwise("")
//                		.as("gender"),
                		) )					
                .from( lctre )
                // where
                .where()
//                .orderBy( lctre.lctreSn.desc())
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
		
		
		// TODO
		// (2) count
        JPAQuery<Long> count = query.select( lctre.count() )
                .from( lctre )									
                .where(
                        eqOption( listDto.getSchOption(), listDto.getSchCntn() ) );
                
				
		
		return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
		
	}
	
	
    private BooleanExpression eqOption( String schOption, String schCntn ) {
        // 검색 옵션  A : 아이디 , B : 이름 <- 예시 일뿐 이런식으로 커스텀하면 됨
        if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
//            if ( schOption.equals( "A" ) )
//                return lctre.lctreSn.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else if ( schOption.equals( "B" ) )
//                return lctre.lctreNm.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else return null;
        	return null;			// TODO (build한 이후에 해주세요. 안그럼 에러발생)  실제 구현시에는 해당부분지워주고 위에부분주석풀기
        } else {
            return null;
        }
    }

}
