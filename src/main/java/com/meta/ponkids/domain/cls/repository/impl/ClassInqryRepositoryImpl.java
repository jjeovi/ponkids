package com.meta.ponkids.domain.cls.repository.impl;


import static com.meta.ponkids.domain.cls.entity.QClass.class$;
import static com.meta.ponkids.domain.cls.entity.QClassCategoryCl01.classCategoryCl01;
import static com.meta.ponkids.domain.cls.entity.QClassCategoryCl02.classCategoryCl02;
import static com.meta.ponkids.domain.cls.entity.QClassInqry.classInqry;
import static com.meta.ponkids.domain.user.entity.QUser.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.meta.ponkids.domain.cls.dto.ClassInqryListDto;
import com.meta.ponkids.domain.cls.dto.QClassInqryListDto;
import com.meta.ponkids.domain.cls.entity.QClassInqry;
import com.meta.ponkids.domain.cls.repository.custom.ClassInqryRepositoryCustom;
import com.meta.ponkids.global.common.dto.CategoryDto;
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
public class ClassInqryRepositoryImpl implements ClassInqryRepositoryCustom {
	
	private final JPAQueryFactory query;
	
	
	// 답변관련컬럼 조회용 객체 
	QClassInqry classInqry_Reply = new QClassInqry( "classInqry_Reply" );
	
	@Override
	public Page<ClassInqryListDto> getList( ClassInqryListDto listDto, Pageable pageable ) {
		
		// TODO 구현
		// (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회
        
		// TODO
        // (1) 결과list (results).
		List<ClassInqryListDto> results = query
				// select
                .select( new QClassInqryListDto(
                		classInqry.classInqrySn,
                		classInqry.classSn,
                		class$.classSj,
                		class$.thumbAtchFileSn.as( "thumbAtchFileSn" ),
                		classCategoryCl01.clNm.as( "ctgryNm" ),
                		classCategoryCl02.clNm.as( "crseNm" ),
                		classInqry.step,
//                		new CaseBuilder()
//                			.when( ExpressionUtils.as( JPAExpressions.select( classInqry_Reply.count() )
//                                    .from( classInqry_Reply )
//                                    .where( classInqry_Reply.parntsInqrySn.eq( classInqry.classInqrySn ) ).gt(0), "nttReplyCnt" ) ).then
                		new CaseBuilder()
                		.when(	ExpressionUtils.isNull( JPAExpressions
                											.select( classInqry_Reply.classInqrySn.sum() )
                											.from( classInqry_Reply )
                											.where( 
                													classInqry_Reply.parntsInqrySn.eq( classInqry.classInqrySn ),
                													classInqry_Reply.delYn.eq( "N" )
                											) 
                										)
                		).then("N")
            			.otherwise("Y")
            			.as("replyYn"),
            			new CaseBuilder()
            			.when(	ExpressionUtils.isNull( JPAExpressions
						            					.select( classInqry_Reply.classInqrySn.sum() )
						            					.from( classInqry_Reply )
						            					.where(
						            							classInqry_Reply.parntsInqrySn.eq( classInqry.classInqrySn ),
						            							classInqry_Reply.delYn.eq( "N" )
						            					)
						            				)
            			).then("답변대기")
            			.otherwise("답변완료")
            			.as("replyYnNm"),
            			ExpressionUtils.as( JPAExpressions.select( classInqry_Reply.count() )
                                .from( classInqry_Reply )
                                .where( classInqry_Reply.parntsInqrySn.eq( classInqry.classInqrySn ) ), "replyCnt" ),
                		classInqry.parntsInqrySn,
                		classInqry.userSn,
                		user.userNm,
                		user.userId,
                		classInqry.inqrySj,
                		classInqry.inqryCn,
                		classInqry.openYn,
                		new CaseBuilder()
                			.when( classInqry.openYn.eq("Y") ).then("공개")
                			.when( classInqry.openYn.eq("N") ).then("비공개")
                			.otherwise("")
                			.as("openYnNm"),
                		classInqry.registerId,
                		Expressions.stringTemplate("to_char({0}, '{1s}')", classInqry.regDt, "YYYY-MM-DD").as("regDt"),
                		Expressions.stringTemplate("to_char({0}, '{1s}')", classInqry.regDt, "YYYY-MM-DD HH24:MI:SS").as("regFullDt")
//                		new CaseBuilder()
//                		.when( user.gender.eq("M")).then("남자")
//                		.when( user.gender.eq("F")).then("여자")
//                		.otherwise("")
//                		.as("gender"),
                		) )					
                .from( classInqry )
                // leftJoin
				.leftJoin( user )
                .on( 	user.userSn.eq( classInqry.userSn ),
                		user.delYn.eq("N")
                )
                .leftJoin( class$ )
				// join 에는 delYn 조건 필수로 추가
				.on(    class$.classSn.eq( classInqry.classSn ),
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
                		eqCateLv1( listDto.getCategory() ),	// 분류 조회 : lv1Sn 값 존재시 검색
						eqCateLv2( listDto.getCategory() ),	// 분류 조회 : lv2Sn 값 존재시 검색
						eqCateLv3( listDto.getCategory() ), // 분류 조회 : lv3Sn 값 존재시 검색
						eqOption( listDto.getSchOption(), listDto.getSchCntn() ),
						eqOpenYn( listDto.getOpenYn() ),
						eqReplyYn( listDto.getReplyYn() ),
						eqClassSn( listDto.getClassSn() ),
						classInqry.parntsInqrySn.isNull(),
						classInqry.step.eq("1"),
						eqUserSn( listDto.getUserSn() )
				)
                .orderBy( classInqry.classInqrySn.desc())
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
		
		// TODO
		// (2) count
        JPAQuery<Long> count = query.select( classInqry.count() )
                .from( classInqry )									
                .where(
						eqCateLv1( listDto.getCategory() ),	// 분류 조회 : lv1Sn 값 존재시 검색
						eqCateLv2( listDto.getCategory() ),	// 분류 조회 : lv2Sn 값 존재시 검색
						eqCateLv3( listDto.getCategory() ), // 분류 조회 : lv3Sn 값 존재시 검색
						eqOption( listDto.getSchOption(), listDto.getSchCntn() ),
						eqOpenYn( listDto.getOpenYn() ),
						eqReplyYn( listDto.getReplyYn() ),
						eqClassSn( listDto.getClassSn() ),
						classInqry.parntsInqrySn.isNull(),
						classInqry.step.eq("1"),
						eqUserSn( listDto.getUserSn() )
				);

		return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
		
	}
	

	@Override
	public List<ClassInqryListDto> findByStepAndParntsInqrySn( String step, Long parntsInqrySn ) {
		return query
				// select
                .select( new QClassInqryListDto(
                		classInqry.classInqrySn,
                		classInqry.classSn,
                		class$.classSj,
                		class$.thumbAtchFileSn.as( "thumbAtchFileSn" ),
                		classCategoryCl01.clNm.as( "ctgryNm" ),
                		classCategoryCl02.clNm.as( "crseNm" ),
                		classInqry.step,
//                		new CaseBuilder()
//                			.when( ExpressionUtils.as( JPAExpressions.select( classInqry_Reply.count() )
//                                    .from( classInqry_Reply )
//                                    .where( classInqry_Reply.parntsInqrySn.eq( classInqry.classInqrySn ) ).gt(0), "nttReplyCnt" ) ).then
                		new CaseBuilder()
                		.when(ExpressionUtils.isNull( JPAExpressions
                											.select( classInqry_Reply.classInqrySn.sum() )
                											.from( classInqry_Reply )
                											.where( classInqry_Reply.parntsInqrySn.eq( classInqry.classInqrySn )) 
                									)).then("N")
            			.otherwise("Y")
            			.as("replyYn"),
            			new CaseBuilder()
            			.when(ExpressionUtils.isNull( JPAExpressions
            					.select( classInqry_Reply.classInqrySn.sum() )
            					.from( classInqry_Reply )
            					.where( classInqry_Reply.parntsInqrySn.eq( classInqry.classInqrySn )) 
            					)).then("답변대기")
            			.otherwise("답변완료")
            			.as("replyYnNm"),
            			ExpressionUtils.as( JPAExpressions.select( classInqry_Reply.count() )
                                .from( classInqry_Reply )
                                .where( classInqry_Reply.parntsInqrySn.eq( classInqry.classInqrySn ) ), "replyCnt" ),
                		classInqry.parntsInqrySn,
                		classInqry.userSn,
                		user.userNm,
                		user.userId,
                		classInqry.inqrySj,
                		classInqry.inqryCn,
                		classInqry.openYn,
                		new CaseBuilder()
                			.when( classInqry.openYn.eq("Y") ).then("공개")
                			.when( classInqry.openYn.eq("N") ).then("비공개")
                			.otherwise("")
                			.as("openYnNm"),
                		classInqry.registerId,
                		Expressions.stringTemplate("to_char({0}, '{1s}')", classInqry.regDt, "YYYY-MM-DD").as("regDt"),
                		Expressions.stringTemplate("to_char({0}, '{1s}')", classInqry.regDt, "YYYY-MM-DD HH24:MI:SS").as("regFullDt")
//                		new CaseBuilder()
//                		.when( user.gender.eq("M")).then("남자")
//                		.when( user.gender.eq("F")).then("여자")
//                		.otherwise("")
//                		.as("gender"),
                		) )					
                .from( classInqry )
                // leftJoin
                .leftJoin( user)
                .on( 	user.userSn.eq( classInqry.userSn ),
                		user.delYn.eq("N")
                )
                .leftJoin( class$ )
				// join 에는 delYn 조건 필수로 추가
				.on(    class$.classSn.eq( classInqry.classSn ),
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
						classInqry.parntsInqrySn.eq( parntsInqrySn ),
						classInqry.step.eq( step )
				)
                .orderBy( classInqry.classInqrySn.desc() )
                .fetch();
	}
	
	@Override
	public ClassInqryListDto getByClassInqrySn( ClassInqryListDto listDto ) {
		
		// TODO 구현
		// (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회
		boolean passed = true;
		// TODO
		// (1) 결과list (results).
		ClassInqryListDto results = query
				// select
				.select( new QClassInqryListDto(
						classInqry.classInqrySn,
						classInqry.classSn,
						class$.classSj,
						class$.thumbAtchFileSn.as( "thumbAtchFileSn" ),
                		classCategoryCl01.clNm.as( "ctgryNm" ),
                		classCategoryCl02.clNm.as( "crseNm" ),
						classInqry.step,
						new CaseBuilder()
                		.when(ExpressionUtils.isNull( JPAExpressions
                											.select( classInqry_Reply.classInqrySn.sum() )
                											.from( classInqry_Reply )
                											.where( classInqry_Reply.parntsInqrySn.eq( classInqry.classInqrySn )) 
                									)).then("N")
            			.otherwise("Y")
            			.as("replyYn"),
            			new CaseBuilder()
            			.when(ExpressionUtils.isNull( JPAExpressions
            					.select( classInqry_Reply.classInqrySn.sum() )
            					.from( classInqry_Reply )
            					.where( classInqry_Reply.parntsInqrySn.eq( classInqry.classInqrySn )) 
            					)).then("답변대기")
            			.otherwise("답변완료")
            			.as("replyYnNm"),
            			ExpressionUtils.as( JPAExpressions.select( classInqry_Reply.count() )
                                .from( classInqry_Reply )
                                .where( classInqry_Reply.parntsInqrySn.eq( classInqry.classInqrySn ) ), "replyCnt" ),
						classInqry.parntsInqrySn,
						classInqry.userSn,
						user.userNm,
						user.userId,
						classInqry.inqrySj,
						classInqry.inqryCn,
						classInqry.openYn,
						new CaseBuilder()
	            			.when( classInqry.openYn.eq("Y") ).then("공개")
	            			.when( classInqry.openYn.eq("N") ).then("비공개")
	            			.otherwise("")
	            			.as("openYnNm"),
						classInqry.registerId,
						Expressions.stringTemplate("to_char({0}, '{1s}')", classInqry.regDt, "YYYY-MM-DD").as("regDt"),
                		Expressions.stringTemplate("to_char({0}, '{1s}')", classInqry.regDt, "YYYY-MM-DD HH24:MI:SS").as("regFullDt")
//                		new CaseBuilder()
//                		.when( user.gender.eq("M")).then("남자")
//                		.when( user.gender.eq("F")).then("여자")
//                		.otherwise("")
//                		.as("gender"),
						) )					
				.from( classInqry )
				// leftJoin
				.leftJoin( user)
				.on( 	user.userSn.eq( classInqry.userSn ),
						user.delYn.eq("N")
						)
				.leftJoin( class$ )
				// join 에는 delYn 조건 필수로 추가
				.on(    class$.classSn.eq( classInqry.classSn ),
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
						eqOption( listDto.getSchOption(), listDto.getSchCntn() ),
						eqClassSn( listDto.getClassSn() ),
						eqClassInqrySn( listDto.getClassInqrySn() )
						)
				.orderBy( classInqry.classInqrySn.desc())
				.fetchFirst();
				// fetchOne 말고 fetchFirst 를 쓰자. https://hungseong.tistory.com/87
		
		return results;
		
	}
	
	
	// -------------------------------- WHERE 검색 옵션 setting --------------------------------
	
	// 카테고리 lv 1 검색 옵션
	// lv1Sn == class$.ctgrySn
	private BooleanExpression eqCateLv1( CategoryDto categoryDto ) {
		return ( categoryDto != null && categoryDto.getLv1Sn() != null
				&& categoryDto.getLv1Sn() != 0 /* 0이 아닌 것은  검색 제외 */ ) ? class$.ctgrySn.eq( categoryDto.getLv1Sn() ) : null;
	}
	
	// 카테고리 lv 2 검색 옵션
	// lv2Sn == class$.crseSn
	private BooleanExpression eqCateLv2( CategoryDto categoryDto ) {
		return ( categoryDto != null && categoryDto.getLv2Sn() != null
				&& categoryDto.getLv2Sn() != 0 /* 0이 아닌 것은  검색 제외 */ ) ? class$.crseSn.eq( categoryDto.getLv2Sn() ) : null;
	}
	
	// 카테고리 lv 3 검색 옵션
	// lv3sn == class$.classSn
	
	private BooleanExpression eqCateLv3( CategoryDto categoryDto ) {
		return ( categoryDto != null && categoryDto.getLv3Sn() != null
				&& categoryDto.getLv3Sn() != 0 /* 0이 아닌 것은  검색 제외 */ ) ? class$.classSn.eq( categoryDto.getLv3Sn() ) : null;
	}
	
	private BooleanExpression eqOpenYn( String openYn ) {
		return (StringUtils.hasText(openYn) )  ? classInqry.openYn.eq(openYn) : null;
	}
	  
	private BooleanExpression eqReplyYn( String replyYn ) {
		return ( StringUtils.hasText(replyYn) ) ? (
			new CaseBuilder()
            		.when(ExpressionUtils.isNull( JPAExpressions
							.select( classInqry_Reply.classInqrySn.sum() )
							.from( classInqry_Reply )
							.where( classInqry_Reply.parntsInqrySn.eq( classInqry.classInqrySn )) 
					)).then("N")
            		.otherwise("Y")
            		.equalsIgnoreCase(replyYn)
            ) : null ;
	}
	
    private BooleanExpression eqClassSn( Long pk ) {
        return pk != null ? classInqry.classSn.eq(pk) : null;
    }

	private BooleanExpression eqUserSn( Long pk ) {
		return pk != null ? user.userSn.eq( pk ) : null;
	}
    
    private BooleanExpression eqClassInqrySn( Long pk ) {
    	return pk != null ? classInqry.classInqrySn.eq(pk) : null;
    }
	
    
    private BooleanExpression eqOption( String schOption, String schCntn ) {
        // 검색 옵션  A : 아이디 , B : 이름 <- 예시 일뿐 이런식으로 커스텀하면 됨
        if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
            if ( schOption.equals( "A" ) )
                return classInqry.inqrySj.contains( schCntn ); //  LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
            else if ( schOption.equals( "B" ) )
                return user.userNm.contains( schCntn ); //  LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
            else if ( schOption.equals( "C" ) )
                return user.userId.contains( schCntn ); //  LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
            else return null;
//        	return null;			//  (build한 이후에 해주세요. 안그럼 에러발생)  실제 구현시에는 해당부분지워주고 위에부분주석풀기
        } else {
            return null;
        }
    }

}
