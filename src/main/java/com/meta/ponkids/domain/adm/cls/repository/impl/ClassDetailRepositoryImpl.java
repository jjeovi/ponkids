package com.meta.ponkids.domain.adm.cls.repository.impl;


import static com.meta.ponkids.domain.adm.cls.entity.QClassDetail.classDetail;
import static com.meta.ponkids.domain.adm.system.cmmnCd.entity.QCmmnCdDetail.cmmnCdDetail;

import java.util.List;

import com.meta.ponkids.domain.adm.cls.dto.ClassDetailListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.meta.ponkids.domain.adm.cls.dto.QClassDetailListDto;
import com.meta.ponkids.domain.adm.cls.repository.custom.ClassDetailRepositoryCustom;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

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
                                .where( cmmnCdDetail.cdDetailVal1.eq( classDetail.classDetailItemTyCd ),
                                		cmmnCdDetail.cdNm.eq("CLASS_DETAIL_ITEM_TY_CD") ), "classDetailItemTyNm" ),
                		classDetail.classDetailItemCn,
                		classDetail.classDetailEssntlYn,
                		new CaseBuilder()
			                        .when( classDetail.classDetailEssntlYn.eq( "Y" ) ).then( "필수" )
			                        .when( classDetail.classDetailEssntlYn.eq( "N" ) ).then( "선택" )
			                        .otherwise( "" ).as( "classDetailEssntlYnNm" ),
                		classDetail.registerId,
                		Expressions.stringTemplate("to_char({0}, '{1s}')", classDetail.regDt, "YYYY-MM-DD HH:MM:SS")
                		) )					
                .from( classDetail )
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
	public List<ClassDetailListDto> findByClassSnOrderByClassDetailSeq( Long pk ) {
		
		 List<ClassDetailListDto> results = query.select(
				 new QClassDetailListDto(
	                		classDetail.classDetailSn,
	                		classDetail.classSn,
	                		classDetail.classDetailSeq,
	                		classDetail.classDetailItemTyCd,
	                		ExpressionUtils.as( JPAExpressions.select( cmmnCdDetail.cdDetailNm )
	                                .from( cmmnCdDetail )
	                                .where( cmmnCdDetail.cdDetailVal1.eq( classDetail.classDetailItemTyCd ),
	                                		cmmnCdDetail.cdNm.eq("CLASS_DETAIL_ITEM_TY_CD") ), "classDetailItemTyNm" ),
	                		classDetail.classDetailItemCn,
	                		classDetail.classDetailEssntlYn,
	                		new CaseBuilder()
				                        .when( classDetail.classDetailEssntlYn.eq( "Y" ) ).then( "필수" )
				                        .when( classDetail.classDetailEssntlYn.eq( "N" ) ).then( "선택" )
				                        .otherwise( "" )
				                        .as( "classDetailEssntlYnNm" ),
	                		classDetail.registerId,
	                		Expressions.stringTemplate("to_char({0}, '{1s}')", classDetail.regDt, "YYYY-MM-DD HH:MM:SS")
	                		)
				 )
				 .from(classDetail)
				 .where ( eqClassSn( pk ) )
				 .orderBy( classDetail.classDetailSeq.asc() )
				 .fetch();
		
		 return results;
	}
	
	// -------------------------------- WHERE 검색 옵션 setting --------------------------------
	  
    private BooleanExpression eqClassSn( Long pk ) {
        return pk != null ? classDetail.classSn.eq(pk) : null;
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
