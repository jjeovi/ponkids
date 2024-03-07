package com.meta.ponkids.domain.qestnar.repository.impl;


import static com.meta.ponkids.domain.cls.entity.QClassCategoryCl02.classCategoryCl02;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.meta.ponkids.domain.qestnar.dto.QQestnarAnswerDetailListDto;
import com.meta.ponkids.domain.qestnar.dto.QestnarAnswerDetailListDto;
import com.meta.ponkids.domain.qestnar.repository.custom.QestnarAnswerDetailRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import static com.meta.ponkids.domain.qestnar.entity.QQestnarAnswerDetail.qestnarAnswerDetail;

@Repository
@RequiredArgsConstructor
public class QestnarAnswerDetailRepositoryImpl implements QestnarAnswerDetailRepositoryCustom {
	
	private final JPAQueryFactory query;
	
	@Override
	public Page<QestnarAnswerDetailListDto> getList( QestnarAnswerDetailListDto listDto, Pageable pageable ) {
		
		// TODO 구현
		// (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회
        
		// TODO
        // (1) 결과list (results).
		List<QestnarAnswerDetailListDto> results = query
				// select
                .select( new QQestnarAnswerDetailListDto(
                		qestnarAnswerDetail.qestnarAnswerDetailSn,
                		qestnarAnswerDetail.qestnarAnswerSn,
                		qestnarAnswerDetail.qestnarQestnSn,
                		qestnarAnswerDetail.qestnarAnswer,
                		qestnarAnswerDetail.qestnarQestnDetailSn,
                		qestnarAnswerDetail.atchFileSn,
                		qestnarAnswerDetail.registerId,
                		Expressions.stringTemplate("to_char({0}, '{1s}')", qestnarAnswerDetail.regDt, "YYYY-MM-DD HH:MM:SS")
//                		new CaseBuilder()
//                		.when( user.gender.eq("M")).then("남자")
//                		.when( user.gender.eq("F")).then("여자")
//                		.otherwise("")
//                		.as("gender"),
                		) )					
                .from( qestnarAnswerDetail )
                // where
                .where(
		eqOption( listDto.getSchOption(), listDto.getSchCntn() )
		)
//                .orderBy( qestnarAnswerDetail.qestnarAnswerDetailSn.desc())
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
		
		
		// TODO
		// (2) count
        JPAQuery<Long> count = query.select( qestnarAnswerDetail.count() )
                .from( qestnarAnswerDetail )									
                .where(
                        eqOption( listDto.getSchOption(), listDto.getSchCntn() )
		);
                

		return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
		
	}
	
	
    private BooleanExpression eqOption( String schOption, String schCntn ) {
        // 검색 옵션  A : 아이디 , B : 이름 <- 예시 일뿐 이런식으로 커스텀하면 됨
        if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
//            if ( schOption.equals( "A" ) )
//                return qestnarAnswerDetail.qestnarAnswerDetailSn.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else if ( schOption.equals( "B" ) )
//                return qestnarAnswerDetail.qestnarAnswerDetailNm.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else return null;
        	return null;			// TODO (build한 이후에 해주세요. 안그럼 에러발생)  실제 구현시에는 해당부분지워주고 위에부분주석풀기
        } else {
            return null;
        }
    }

}
