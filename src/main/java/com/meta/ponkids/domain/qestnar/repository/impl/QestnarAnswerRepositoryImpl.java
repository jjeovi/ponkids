package com.meta.ponkids.domain.qestnar.repository.impl;

import static com.meta.ponkids.domain.qestnar.entity.QQestnarAnswer.qestnarAnswer;
import static com.meta.ponkids.domain.qestnar.entity.QQestnarAnswerReply.qestnarAnswerReply;
import static com.meta.ponkids.domain.qestnar.entity.QQestnarGroup.qestnarGroup;
import static com.meta.ponkids.domain.user.entity.QUser.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.meta.ponkids.domain.qestnar.dto.QQestnarAnswerListDto;
import com.meta.ponkids.domain.qestnar.dto.QestnarAnswerListDto;
import com.meta.ponkids.domain.qestnar.repository.custom.QestnarAnswerRepositoryCustom;
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
public class QestnarAnswerRepositoryImpl implements QestnarAnswerRepositoryCustom {
	
	private final JPAQueryFactory query;
	
	@Override
	public Page<QestnarAnswerListDto> getList( QestnarAnswerListDto listDto, Pageable pageable ) {
		
		// (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회
        
        // (1) 결과list (results).
		List<QestnarAnswerListDto> results = query
				// select
                .select( new QQestnarAnswerListDto(
                		qestnarAnswer.qestnarAnswerSn,
                		qestnarAnswer.qestnarGroupSn,
                		qestnarGroup.qestnarGroupCd,
                		qestnarGroup.qestnarGroupNm,
                		qestnarGroup.replySetYn,
                		new CaseBuilder()
                		.when(ExpressionUtils.isNull( JPAExpressions
                											.select( qestnarAnswerReply.qestnarAnswerReplySn.sum() )
                											.from( qestnarAnswerReply )
                											.where( 
                													qestnarAnswerReply.qestnarAnswerSn.eq( qestnarAnswer.qestnarAnswerSn ),
                													qestnarAnswerReply.delYn.eq( "N" )
                											) 
                									)
                		).then("N")
            			.otherwise("Y")
            			.as("replyYn"),
            			new CaseBuilder()
            			.when(ExpressionUtils.isNull( JPAExpressions
							            					.select( qestnarAnswerReply.qestnarAnswerReplySn.sum() )
							            					.from( qestnarAnswerReply )
							            					.where( qestnarAnswerReply.qestnarAnswerSn.eq( qestnarAnswer.qestnarAnswerSn ),
																	qestnarAnswerReply.delYn.eq( "N" )
															)
							            			)
            			).then("답변대기")
            			.otherwise("답변완료")
            			.as("replyYnNm"),
            			ExpressionUtils.as( JPAExpressions.select( qestnarAnswerReply.count() )
                                .from( qestnarAnswerReply )
                                .where( qestnarAnswerReply.qestnarAnswerSn.eq( qestnarAnswer.qestnarAnswerSn ),
										qestnarAnswerReply.delYn.eq( "N" )
						), "replyCnt" ),
                		qestnarAnswer.userSn,
                		user.userId,
                		user.userNm,
                		user.telNo,
                		qestnarAnswer.registerId,
                		Expressions.stringTemplate("to_char({0}, '{1s}')", qestnarAnswer.regDt, "YYYY-MM-DD"),
                		Expressions.stringTemplate("to_char({0}, '{1s}')", qestnarAnswer.regDt, "YYYY-MM-DD HH24:MI:SS").as("regFullDt")
                		) )					
                .from( qestnarAnswer )
                // leftJoin
                .leftJoin( qestnarGroup )
                .on( 
                	qestnarGroup.qestnarGroupSn.eq( qestnarAnswer.qestnarGroupSn ),
                	qestnarGroup.delYn.eq( "N" )
                )
                .leftJoin( user )
                .on( 
                	user.userSn.eq( qestnarAnswer.userSn ),
                	user.delYn.eq( "N" )
                )
                // where
                .where(
                		eqCateLv1( listDto.getCategory() ),	// 분류 조회 : lv1Sn 값 존재시 검색
                		eqOption( listDto.getSchOption(), listDto.getSchCntn() )
				)
                .orderBy( qestnarAnswer.qestnarAnswerSn.desc())
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
		
		
		// (2) count
        JPAQuery<Long> count = query.select( qestnarAnswer.count() )
                .from( qestnarAnswer )									
                .where(
                        eqOption( listDto.getSchOption(), listDto.getSchCntn() )
		);
                

		return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
		
	}
	

	@Override
	public List<QestnarAnswerListDto> getListByUserSn( QestnarAnswerListDto listDto ) {
		
		return null;
	}
	
	
	@Override
	public QestnarAnswerListDto getByClassInqrySn( Long pk ) {
		return query
				// select
                .select( new QQestnarAnswerListDto(
                		qestnarAnswer.qestnarAnswerSn,
                		qestnarAnswer.qestnarGroupSn,
                		qestnarGroup.qestnarGroupCd,
                		qestnarGroup.qestnarGroupNm,
                		qestnarGroup.replySetYn,
                		new CaseBuilder()
                		.when(ExpressionUtils.isNull( JPAExpressions
                											.select( qestnarAnswerReply.qestnarAnswerReplySn.sum() )
                											.from( qestnarAnswerReply )
                											.where( 
                													qestnarAnswerReply.qestnarAnswerSn.eq( qestnarAnswer.qestnarAnswerSn ),
                													qestnarAnswerReply.delYn.eq( "N" )
                											) 
                									)
                		).then("N")
            			.otherwise("Y")
            			.as("replyYn"),
            			new CaseBuilder()
            			.when(ExpressionUtils.isNull( JPAExpressions
							            					.select( qestnarAnswerReply.qestnarAnswerReplySn.sum() )
							            					.from( qestnarAnswerReply )
							            					.where( qestnarAnswerReply.qestnarAnswerSn.eq( qestnarAnswer.qestnarAnswerSn ),
																	qestnarAnswerReply.delYn.eq( "N" )
															)
							            			)
            			).then("답변대기")
            			.otherwise("답변완료")
            			.as("replyYnNm"),
            			ExpressionUtils.as( JPAExpressions.select( qestnarAnswerReply.count() )
                                .from( qestnarAnswerReply )
                                .where( qestnarAnswerReply.qestnarAnswerSn.eq( qestnarAnswer.qestnarAnswerSn ),
										qestnarAnswerReply.delYn.eq( "N" )
						), "replyCnt" ),
                		qestnarAnswer.userSn,
                		user.userId,
                		user.userNm,
                		user.telNo,
                		qestnarAnswer.registerId,
                		Expressions.stringTemplate("to_char({0}, '{1s}')", qestnarAnswer.regDt, "YYYY-MM-DD"),
                		Expressions.stringTemplate("to_char({0}, '{1s}')", qestnarAnswer.regDt, "YYYY-MM-DD HH24:MI:SS").as("regFullDt")
                		) )					
                .from( qestnarAnswer )
                // leftJoin
                .leftJoin( qestnarGroup )
                .on( 
                	qestnarGroup.qestnarGroupSn.eq( qestnarAnswer.qestnarGroupSn ),
                	qestnarGroup.delYn.eq( "N" )
                )
                .leftJoin( user )
                .on( 
                	user.userSn.eq( qestnarAnswer.userSn ),
                	user.delYn.eq( "N" )
                )
                // where
                .where(
                		qestnarAnswer.qestnarAnswerSn.eq( pk )
				)
                .orderBy( qestnarAnswer.qestnarAnswerSn.desc())
                .fetchFirst();
	}
	
	@Override
	public List<QestnarAnswerListDto> getList( QestnarAnswerListDto listDto ) {
		return query
				// select
                .select( new QQestnarAnswerListDto(
                		qestnarAnswer.qestnarAnswerSn,
                		qestnarAnswer.qestnarGroupSn,
                		qestnarGroup.qestnarGroupCd,
                		qestnarGroup.qestnarGroupNm,
                		qestnarGroup.replySetYn,
                		new CaseBuilder()
                		.when(ExpressionUtils.isNull( JPAExpressions
                											.select( qestnarAnswerReply.qestnarAnswerReplySn.sum() )
                											.from( qestnarAnswerReply )
                											.where( 
                													qestnarAnswerReply.qestnarAnswerSn.eq( qestnarAnswer.qestnarAnswerSn ),
                													qestnarAnswerReply.delYn.eq( "N" )
                											) 
                									)
                		).then("N")
            			.otherwise("Y")
            			.as("replyYn"),
            			new CaseBuilder()
            			.when(ExpressionUtils.isNull( JPAExpressions
							            					.select( qestnarAnswerReply.qestnarAnswerReplySn.sum() )
							            					.from( qestnarAnswerReply )
							            					.where( qestnarAnswerReply.qestnarAnswerSn.eq( qestnarAnswer.qestnarAnswerSn ),
																	qestnarAnswerReply.delYn.eq( "N" )
															)
							            			)
            			).then("답변대기")
            			.otherwise("답변완료")
            			.as("replyYnNm"),
            			ExpressionUtils.as( JPAExpressions.select( qestnarAnswerReply.count() )
                                .from( qestnarAnswerReply )
                                .where( qestnarAnswerReply.qestnarAnswerSn.eq( qestnarAnswer.qestnarAnswerSn ),
										qestnarAnswerReply.delYn.eq( "N" )
						), "replyCnt" ),
                		qestnarAnswer.userSn,
                		user.userId,
                		user.userNm,
                		user.telNo,
                		qestnarAnswer.registerId,
                		Expressions.stringTemplate("to_char({0}, '{1s}')", qestnarAnswer.regDt, "YYYY-MM-DD"),
                		Expressions.stringTemplate("to_char({0}, '{1s}')", qestnarAnswer.regDt, "YYYY-MM-DD HH24:MI:SS").as("regFullDt")
                		) )					
                .from( qestnarAnswer )
                // leftJoin
                .leftJoin( qestnarGroup )
                .on( 
                	qestnarGroup.qestnarGroupSn.eq( qestnarAnswer.qestnarGroupSn ),
                	qestnarGroup.delYn.eq( "N" )
                )
                .leftJoin( user )
                .on( 
                	user.userSn.eq( qestnarAnswer.userSn ),
                	user.delYn.eq( "N" )
                )
                // where
                .where(
                		eqUserSn( listDto.getUserSn() ),
                		eqQestnarGroupCd( listDto.getQestnarGroupCd() )
				)
                .orderBy( qestnarAnswer.qestnarAnswerSn.desc())
                .fetch();
	}
	
	// -------------------------------- WHERE 검색 옵션 setting --------------------------------
	// -------------------------------- WHERE 검색 옵션 setting --------------------------------
	

	private BooleanExpression eqUserSn( Long userSn ) {
		return ( userSn != null )  ? qestnarAnswer.userSn.eq( userSn ) : null;
	}
	
	private BooleanExpression eqQestnarGroupCd( String qestnarGroupCd ) {
		return ( StringUtils.hasText(qestnarGroupCd) )  ? qestnarGroup.qestnarGroupCd.eq( qestnarGroupCd ) : null;
	}

	// 카테고리 lv 1 검색 옵션
	// lv1Sn == qestnarAnswer.qestnarGroupSn
	private BooleanExpression eqCateLv1( CategoryDto categoryDto ) {
		return ( categoryDto != null && categoryDto.getLv1Sn() != null
				&& categoryDto.getLv1Sn() != 0 /* 0이 아닌 것은  검색 제외 */ ) ? qestnarAnswer.qestnarGroupSn.eq( categoryDto.getLv1Sn() ) : null;
	}
	
    private BooleanExpression eqOption( String schOption, String schCntn ) {
        // 검색 옵션  A : 아이디 , B : 이름 <- 예시 일뿐 이런식으로 커스텀하면 됨
        if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
//            if ( schOption.equals( "A" ) )
//                return qestnarAnswer.qestnarAnswerSn.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else if ( schOption.equals( "B" ) )
//                return qestnarAnswer.qestnarAnswerNm.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else return null;
        	return null;			// TODO (build한 이후에 해주세요. 안그럼 에러발생)  실제 구현시에는 해당부분지워주고 위에부분주석풀기
        } else {
            return null;
        }
    }

}
