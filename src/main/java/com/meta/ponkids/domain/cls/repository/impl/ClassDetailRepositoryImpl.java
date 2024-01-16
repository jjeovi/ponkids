package com.meta.ponkids.domain.cls.repository.impl;


import com.meta.ponkids.domain.cls.dto.ClassDetailListDto;
import com.meta.ponkids.domain.cls.dto.QClassDetailListDto;
import com.meta.ponkids.domain.cls.entity.ClassDetail;
import com.meta.ponkids.domain.cls.repository.custom.ClassDetailRepositoryCustom;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
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

import static com.meta.ponkids.domain.cls.entity.QClassDetail.classDetail;
import static com.meta.ponkids.domain.system.cmmnCd.entity.QCmmnCdDetail.cmmnCdDetail;

@Repository
@RequiredArgsConstructor
public class ClassDetailRepositoryImpl implements ClassDetailRepositoryCustom {
	
	private final JPAQueryFactory query;
	
	@Override
	public Page<ClassDetailListDto> getList( ClassDetailListDto listDto, Pageable pageable ) {
		
		// (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회
		
        // (1) 결과list (results).
		List<ClassDetailListDto> results = query
				// select
                .select( new QClassDetailListDto(
                		classDetail.classDetailSn,
                		classDetail.classSn,
                		classDetail.classDetailSeq,
                		classDetail.classDetailItemTyCd,
                		ExpressionUtils.as( JPAExpressions.select( cmmnCdDetail.cdDetailNm )
                                .from( cmmnCdDetail )
                                .where( cmmnCdDetail.parntsReplySn.eq( nttReply.nttReplySn ) ), "nttReplyCnt" )
                		classDetail.classDetailItemCn,
                		classDetail.classDetailEssntlYn,
                		new CaseBuilder()
			                        .when( classDetail.classDetailEssntlYn.eq( "Y" ) ).then( "필수" )
			                        .when( classDetail.classDetailEssntlYn.eq( "N" ) ).then( "선택" )
			                        .otherwise( "" ).as( "classDetailEssntlYnNm" ),
                		classDetail.registerId,
                		Expressions.stringTemplate("to_char({0}, '{1s}')", classDetail.regDt, "YYYY-MM-DD HH:MM:SS")
//                		new CaseBuilder()
//                		.when( user.gender.eq("M")).then("남자")
//                		.when( user.gender.eq("F")).then("여자")
//                		.otherwise("")
//                		.as("gender"),
                		) )					
                .from( classDetail )
                // where
                .where()
//                .orderBy( classDetail.classDetailSn.desc())
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
		
		// (2) count
        JPAQuery<Long> count = query.select( classDetail.count() )
                .from( classDetail )									
                .where(
                        eqOption( listDto.getSchOption(), listDto.getSchCntn() ) );
		
		return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
		
	}
	
	@Override
	public List<ClassDetail> findByClassSnOrderByClassDetailSeq( Long pk ) {
		
		 List<ClassDetail> results = query.select(
				 	new QClassDetailListDto(
				 			classDetail.
				 			)
				 )
		
	}
	
    private BooleanExpression eqOption( String schOption, String schCntn ) {
        // 검색 옵션  A : 아이디 , B : 이름 <- 예시 일뿐 이런식으로 커스텀하면 됨
        if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
//            if ( schOption.equals( "A" ) )
//                return classDetail.classDetailSn.contains( schCntn ); // LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else if ( schOption.equals( "B" ) )
//                return classDetail.classDetailNm.contains( schCntn ); // LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else return null;
        	return null;			// (build한 이후에 해주세요. 안그럼 에러발생)  실제 구현시에는 해당부분지워주고 위에부분주석풀기
        } else {
            return null;
        }
    }

}
