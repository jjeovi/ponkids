package com.meta.ponkids.domain.cls.repository.impl;


import com.meta.ponkids.domain.cls.dto.ClassReviewListDto;
import com.meta.ponkids.domain.cls.dto.QClassReviewListDto;
import com.meta.ponkids.domain.cls.entity.QClassReview;
import com.meta.ponkids.domain.cls.repository.custom.ClassReviewRepositoryCustom;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
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

import static com.meta.ponkids.domain.cls.entity.QClass.class$;
import static com.meta.ponkids.domain.cls.entity.QClassCategoryCl01.classCategoryCl01;
import static com.meta.ponkids.domain.cls.entity.QClassCategoryCl02.classCategoryCl02;
import static com.meta.ponkids.domain.cls.entity.QClassReview.classReview;
import static com.meta.ponkids.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class ClassReviewRepositoryImpl implements ClassReviewRepositoryCustom {
	
	private final JPAQueryFactory query;
	
	QClassReview classReview_Reply = new QClassReview( "classReview_Reply" );
	
	@Override
	public Page<ClassReviewListDto> getList( ClassReviewListDto listDto, Pageable pageable ) {
		
		// (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회
        
        // (1) 결과list (results).
		List<ClassReviewListDto> results = query
				// select
                .select( new QClassReviewListDto(
                		classReview.classReviewSn,
                		classReview.classSn,
						classCategoryCl01.clNm.as( "ctgryNm" ),
						classCategoryCl02.clNm.as( "crseNm" ),
						class$.classSj,
						class$.thumbAtchFileSn.as( "thumbAtchFileSn" ),
                		classReview.userSn,
                		user.userNm,
						user.userId,
                		classReview.step,
						new CaseBuilder()
								.when(	ExpressionUtils.isNull( JPAExpressions
												.select( classReview_Reply.classReviewSn.sum() )
												.from( classReview_Reply )
												.where(
														classReview_Reply.parntsReviewSn.eq( classReview.classReviewSn ),
														classReview_Reply.delYn.eq( "N" )
												)
										)
								).then("N")
								.otherwise("Y")
								.as("replyYn"),
						new CaseBuilder()
								.when(	ExpressionUtils.isNull( JPAExpressions
												.select( classReview_Reply.classReviewSn.sum() )
												.from( classReview_Reply )
												.where(
														classReview_Reply.parntsReviewSn.eq( classReview.classReviewSn ),
														classReview_Reply.step.eq("2"),
														classReview_Reply.delYn.eq( "N" )
												)
										)
								).then("답변대기")
								.otherwise("답변완료")
								.as("replyYnNm"),
						ExpressionUtils.as( JPAExpressions.select( classReview_Reply.count() )
								.from( classReview_Reply )
								.where( classReview_Reply.parntsReviewSn.eq( classReview.classReviewSn ),
										classReview_Reply.step.eq( "2" )
										), "replyCnt" ),
                		classReview.parntsReviewSn,
                		classReview.reviewCn,
                		classReview.reviewGrade,
						classReview.reviewGrade.castToNum( Long.class ).as("reviewGradeLong"),
                		classReview.atchFileSn,
                		classReview.openYn,
						new CaseBuilder()
								.when( classReview.openYn.eq("Y") ).then("공개")
								.when( classReview.openYn.eq("N") ).then("비공개")
								.otherwise("")
								.as("openYnNm"),
                		classReview.registerId,
                		Expressions.stringTemplate("to_char({0}, '{1s}')", classReview.regDt, "YYYY-MM-DD"),
                		Expressions.stringTemplate("to_char({0}, '{1s}')", classReview.regDt, "YYYY-MM-DD HH24:MI:SS").as("regFullDt")
                		) )					
                .from( classReview )
                //leftJoin
                .leftJoin( user )
                .on( 
                		user.userSn.eq( classReview.userSn ),
                		user.delYn.eq( "N" )
                )
                .leftJoin( class$ )
                .on( 
                		class$.classSn.eq( classReview.classSn ),
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
                		eqClassSn( listDto.getClassSn() ),
						classReview.step.eq("1")
				)
                .orderBy(
						orderByOption( listDto.getSchOption(), listDto.getSchCntn() ),
//						null
						classReview.classReviewSn.desc()
				)
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
		  
		// (2) count
        JPAQuery<Long> count = query.select( classReview.count() )
                .from( classReview );

		return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
	}
	
	
	@Override
	public ClassReviewListDto getByClassReviewSn( Long pk ) {
		
		return query
				.select(
						new QClassReviewListDto(
								classReview.classReviewSn,
								classReview.classSn,
								classCategoryCl01.clNm.as( "ctgryNm" ),
								classCategoryCl02.clNm.as( "crseNm" ),
								class$.classSj,
								class$.thumbAtchFileSn.as( "thumbAtchFileSn" ),
								classReview.userSn,
								user.userNm,
								user.userId,
								classReview.step,
								new CaseBuilder()
										.when(	ExpressionUtils.isNull( JPAExpressions
														.select( classReview_Reply.classReviewSn.sum() )
														.from( classReview_Reply )
														.where(
																classReview_Reply.parntsReviewSn.eq( classReview.classReviewSn ),
																classReview_Reply.delYn.eq( "N" )
														)
												)
										).then("N")
										.otherwise("Y")
										.as("replyYn"),
								new CaseBuilder()
										.when(	ExpressionUtils.isNull( JPAExpressions
														.select( classReview_Reply.classReviewSn.sum() )
														.from( classReview_Reply )
														.where(
																classReview_Reply.parntsReviewSn.eq( classReview.classReviewSn ),
																classReview_Reply.delYn.eq( "N" )
														)
												)
										).then("답변대기")
										.otherwise("답변완료")
										.as("replyYnNm"),
								ExpressionUtils.as( JPAExpressions.select( classReview_Reply.count() )
										.from( classReview_Reply )
										.where( classReview_Reply.parntsReviewSn.eq( classReview.classReviewSn ) ), "replyCnt" ),
								classReview.parntsReviewSn,
								classReview.reviewCn,
								classReview.reviewGrade,
								classReview.reviewGrade.castToNum( Long.class ).as("reviewGradeLong"),
								classReview.atchFileSn,
								classReview.openYn,
								new CaseBuilder()
										.when( classReview.openYn.eq("Y") ).then("공개")
										.when( classReview.openYn.eq("N") ).then("비공개")
										.otherwise("")
										.as("openYnNm"),
								classReview.registerId,
								Expressions.stringTemplate("to_char({0}, '{1s}')", classReview.regDt, "YYYY-MM-DD"),
								Expressions.stringTemplate("to_char({0}, '{1s}')", classReview.regDt, "YYYY-MM-DD HH24:MI:SS").as("regFullDt")
						)
				)
				.from(
						classReview
				)
				.leftJoin( user )
				.on(
						user.userSn.eq( classReview.userSn ),
						user.delYn.eq( "N" )
				)
				.leftJoin( class$ )
				.on(
						class$.classSn.eq( classReview.classSn ),
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
				.where(
						classReview.classReviewSn.eq( pk )
				)
				.orderBy(
						classReview.classReviewSn.desc()
				)
				.fetchFirst();
	}
	
	// 답변 조회 ( 리스트 )
	@Override
	public List<ClassReviewListDto> getListByStepAndParntsReviewSn( String step, Long parntsReviewSn) {
		
		return query
				.select(
						new QClassReviewListDto(
								classReview.classReviewSn,
								classReview.classSn,
								classCategoryCl01.clNm.as( "ctgryNm" ),
								classCategoryCl02.clNm.as( "crseNm" ),
								class$.classSj,
								class$.thumbAtchFileSn.as( "thumbAtchFileSn" ),
								classReview.userSn,
								user.userNm,
								user.userId,
								classReview.step,
								new CaseBuilder()
										.when(	ExpressionUtils.isNull( JPAExpressions
														.select( classReview_Reply.classReviewSn.sum() )
														.from( classReview_Reply )
														.where(
																classReview_Reply.parntsReviewSn.eq( classReview.classReviewSn ),
																classReview_Reply.delYn.eq( "N" )
														)
												)
										).then("N")
										.otherwise("Y")
										.as("replyYn"),
								new CaseBuilder()
										.when(	ExpressionUtils.isNull( JPAExpressions
														.select( classReview_Reply.classReviewSn.sum() )
														.from( classReview_Reply )
														.where(
																classReview_Reply.parntsReviewSn.eq( classReview.classReviewSn ),
																classReview_Reply.delYn.eq( "N" )
														)
												)
										).then("답변대기")
										.otherwise("답변완료")
										.as("replyYnNm"),
								ExpressionUtils.as( JPAExpressions.select( classReview_Reply.count() )
										.from( classReview_Reply )
										.where( classReview_Reply.parntsReviewSn.eq( classReview.classReviewSn ) ), "replyCnt" ),
								classReview.parntsReviewSn,
								classReview.reviewCn,
								classReview.reviewGrade,
								classReview.reviewGrade.castToNum( Long.class ).as("reviewGradeLong"),
								classReview.atchFileSn,
								classReview.openYn,
								new CaseBuilder()
										.when( classReview.openYn.eq("Y") ).then("공개")
										.when( classReview.openYn.eq("N") ).then("비공개")
										.otherwise("")
										.as("openYnNm"),
								classReview.registerId,
								Expressions.stringTemplate("to_char({0}, '{1s}')", classReview.regDt, "YYYY-MM-DD"),
								Expressions.stringTemplate("to_char({0}, '{1s}')", classReview.regDt, "YYYY-MM-DD HH24:MI:SS").as("regFullDt")
						)
				)
				.from(
						classReview
				)
				.leftJoin( user )
				.on(
						user.userSn.eq( classReview.userSn ),
						user.delYn.eq( "N" )
				)
				.leftJoin( class$ )
				.on(
						class$.classSn.eq( classReview.classSn ),
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
				.where(
						classReview.step.eq( step ),
						classReview.parntsReviewSn.eq( parntsReviewSn )
				)
				.orderBy(
						classReview.classReviewSn.desc()
				)
				.fetch();
	}
	
	
	// -------------------------------- WHERE 검색 옵션 setting --------------------------------
	
	    
	    private BooleanExpression eqOption( String schOption, String schCntn ) {
	        // 검색 옵션  A : 아이디 , B : 이름 <- 예시 일뿐 이런식으로 커스텀하면 됨
	        if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
	            if ( schOption.equals( "A" ) )
	                return classReview.reviewCn.contains( schCntn ); //  LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
	            else if ( schOption.equals( "B" ) )
	                return user.userNm.contains( schCntn ); //  LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
	            else if ( schOption.equals( "C" ) )
	                return user.userId.contains( schCntn ); //  LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
	            else return null;
//	        	return null;			//  (build한 이후에 해주세요. 안그럼 에러발생)  실제 구현시에는 해당부분지워주고 위에부분주석풀기
	        } else {
	            return null;
	        }
	    }
	    
	    private BooleanExpression eqClassSn( Long pk ) {
	    	return pk != null ? classReview.classSn.eq(pk) : null;
	    }
		
	
	
    private OrderSpecifier orderByOption( String schOption, String schCntn ) {
        // 검색 옵션  A : 아이디 , B : 이름 <- 예시 일뿐 이런식으로 커스텀하면 됨
        if ( StringUtils.hasText( schOption ) ) {
            if ( schOption.equals( "A" ) )
                return classReview.classReviewSn.desc();
            else if ( schOption.equals( "B" ) )
                return classReview.reviewGrade.desc();
			else if ( schOption.equals( "C" ) )
                return classReview.reviewGrade.asc();
            else return classReview.classReviewSn.desc();
        } else {
            return classReview.classReviewSn.desc();
        }
    }
    

}
