package com.meta.ponkids.domain.qestnar.repository.impl;


import static com.meta.ponkids.domain.qestnar.entity.QQestnarGroup.qestnarGroup;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.meta.ponkids.domain.qestnar.dto.QQestnarGroupListDto;
import com.meta.ponkids.domain.qestnar.dto.QestnarGroupListDto;
import com.meta.ponkids.domain.qestnar.repository.custom.QestnarGroupRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QestnarGroupRepositoryImpl implements QestnarGroupRepositoryCustom {
	
	private final JPAQueryFactory query;
	
	@Override
	public Page<QestnarGroupListDto> getList( QestnarGroupListDto listDto, Pageable pageable ) {
		
		// TODO 구현
		// (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회
        
		// TODO
        // (1) 결과list (results).
		List<QestnarGroupListDto> results = query
				// select
                .select( new QQestnarGroupListDto(
                		qestnarGroup.qestnarGroupSn,
                		qestnarGroup.qestnarGroupCd,
                		qestnarGroup.qestnarGroupNm,
                		qestnarGroup.qestnarGroupDc,
                		qestnarGroup.upendGdccSetYn,
                		qestnarGroup.upendGdcc,
                		qestnarGroup.lptGdccSetYn,
                		qestnarGroup.lptGdcc,
                		qestnarGroup.privcyYn,
                		new CaseBuilder()
                		.when( qestnarGroup.privcyYn.eq("Y")).then("공개")
                		.when( qestnarGroup.privcyYn.eq("N")).then("비공개")
                		.otherwise("")
                		.as("privcyYnNm"),
                		qestnarGroup.useYn,
                		new CaseBuilder()
                		.when( qestnarGroup.useYn.eq("Y")).then("사용")
                		.when( qestnarGroup.useYn.eq("N")).then("미사용")
                		.otherwise("")
                		.as("useYnNm"),
                		qestnarGroup.registerId,
                		Expressions.stringTemplate("to_char({0}, '{1s}')", qestnarGroup.regDt, "YYYY-MM-DD HH24:MI:SS")
                		) )					
                .from( qestnarGroup )
                // where
                .where(
		eqOption( listDto.getSchOption(), listDto.getSchCntn() )
		)
//                .orderBy( qestnarGroup.qestnarGroupSn.desc())
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
		
		
		// TODO
		// (2) count
        JPAQuery<Long> count = query.select( qestnarGroup.count() )
                .from( qestnarGroup )									
                .where(
                        eqOption( listDto.getSchOption(), listDto.getSchCntn() )
		);
                

		return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
		
	}
	
	
    private BooleanExpression eqOption( String schOption, String schCntn ) {
        // 검색 옵션  A : 아이디 , B : 이름 <- 예시 일뿐 이런식으로 커스텀하면 됨
        if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
//            if ( schOption.equals( "A" ) )
//                return qestnarGroup.qestnarGroupSn.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else if ( schOption.equals( "B" ) )
//                return qestnarGroup.qestnarGroupNm.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else return null;
        	return null;			// TODO (build한 이후에 해주세요. 안그럼 에러발생)  실제 구현시에는 해당부분지워주고 위에부분주석풀기
        } else {
            return null;
        }
    }

}
