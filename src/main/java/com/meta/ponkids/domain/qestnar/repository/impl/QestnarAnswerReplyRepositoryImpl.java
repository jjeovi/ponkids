package com.meta.ponkids.domain.qestnar.repository.impl;


import static com.meta.ponkids.domain.cls.entity.QClassReview.classReview;
import static com.meta.ponkids.domain.qestnar.entity.QQestnarAnswerReply.qestnarAnswerReply;
import static com.meta.ponkids.domain.user.entity.QUser.user;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.meta.ponkids.domain.qestnar.dto.QQestnarAnswerReplyListDto;
import com.meta.ponkids.domain.qestnar.dto.QestnarAnswerReplyListDto;
import com.meta.ponkids.domain.qestnar.repository.custom.QestnarAnswerReplyRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QestnarAnswerReplyRepositoryImpl implements QestnarAnswerReplyRepositoryCustom {
	
	private final JPAQueryFactory query;
	
//	@Override
//	public Page<QestnarAnswerReplyListDto> getList( QestnarAnswerReplyListDto listDto, Pageable pageable ) {
//		
//		// (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회
//        
//        // (1) 결과list (results).
//		List<QestnarAnswerReplyListDto> results = query
//				// select
//                .select( new QQestnarAnswerReplyListDto(
//                		qestnarAnswerReply.qestnarAnswerReplySn,
//                		qestnarAnswerReply.qestnarAnswerSn,
//                		qestnarAnswerReply.userSn,
//                		qestnarAnswerReply.qestnarAnswerReplyCn,
//                		qestnarAnswerReply.atchFileSn,
//                		qestnarAnswerReply.registerId,
//                		Expressions.stringTemplate("to_char({0}, '{1s}')", qestnarAnswerReply.regDt, "YYYY-MM-DD HH24:MI:SS")
//                		) )					
//                .from( qestnarAnswerReply )
//                // where
//                .where(
//		eqOption( listDto.getSchOption(), listDto.getSchCntn() )
//		)
////                .orderBy( qestnarAnswerReply.qestnarAnswerReplySn.desc())
//                .offset( pageable.getOffset() )
//                .limit( pageable.getPageSize() )
//                .fetch();
//		
//		// (2) count
//        JPAQuery<Long> count = query.select( qestnarAnswerReply.count() )
//                .from( qestnarAnswerReply )									
//                .where(
//                        eqOption( listDto.getSchOption(), listDto.getSchCntn() )
//		);
//                
//
//		return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
//	}
	
	@Override
	public List<QestnarAnswerReplyListDto> getListByQestnarAnswerSn( Long qestnarAnswerSn ) {
		return query
				// select
                .select( new QQestnarAnswerReplyListDto(
                		qestnarAnswerReply.qestnarAnswerReplySn,
                		qestnarAnswerReply.qestnarAnswerSn,
                		qestnarAnswerReply.userSn,
                		user.userNm,
                		user.userId,
                		qestnarAnswerReply.qestnarAnswerReplyCn,
                		qestnarAnswerReply.atchFileSn,
                		qestnarAnswerReply.registerId,
                		Expressions.stringTemplate("to_char({0}, '{1s}')", qestnarAnswerReply.regDt, "YYYY-MM-DD"),
                		Expressions.stringTemplate("to_char({0}, '{1s}')", qestnarAnswerReply.regDt, "YYYY-MM-DD HH24:MI:SS").as("regFullDt")
                		) )					
                .from( qestnarAnswerReply )
                // where
                .leftJoin( user)
				.on( 	user.userSn.eq( qestnarAnswerReply.userSn ),
						user.delYn.eq("N")
						)
                .where(
                		qestnarAnswerReply.qestnarAnswerSn.eq( qestnarAnswerSn )
				)
                .orderBy( qestnarAnswerReply.qestnarAnswerReplySn.asc() )
                .fetch();
	}
	
	
	@Override
	public List<QestnarAnswerReplyListDto> getListByUserSn( Long userSn ) {
		return query
				// select
				.select( new QQestnarAnswerReplyListDto(
						qestnarAnswerReply.qestnarAnswerReplySn,
						qestnarAnswerReply.qestnarAnswerSn,
						qestnarAnswerReply.userSn,
						user.userNm,
						user.userId,
						qestnarAnswerReply.qestnarAnswerReplyCn,
						qestnarAnswerReply.atchFileSn,
						qestnarAnswerReply.registerId,
						Expressions.stringTemplate("to_char({0}, '{1s}')", qestnarAnswerReply.regDt, "YYYY-MM-DD"),
                		Expressions.stringTemplate("to_char({0}, '{1s}')", qestnarAnswerReply.regDt, "YYYY-MM-DD HH24:MI:SS").as("regFullDt")
						) )					
				.from( qestnarAnswerReply )
				// where
				.leftJoin( user)
				.on( 	user.userSn.eq( qestnarAnswerReply.userSn ),
						user.delYn.eq("N")
						)
				.where(
						qestnarAnswerReply.userSn.eq( userSn )
						)
				.orderBy( qestnarAnswerReply.qestnarAnswerReplySn.asc() )
				.fetch();
	}
	
	
    private BooleanExpression eqOption( String schOption, String schCntn ) {
        // 검색 옵션  A : 아이디 , B : 이름 <- 예시 일뿐 이런식으로 커스텀하면 됨
        if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
//            if ( schOption.equals( "A" ) )
//                return qestnarAnswerReply.qestnarAnswerReplySn.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else if ( schOption.equals( "B" ) )
//                return qestnarAnswerReply.qestnarAnswerReplyNm.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else return null;
        	return null;			// TODO (build한 이후에 해주세요. 안그럼 에러발생)  실제 구현시에는 해당부분지워주고 위에부분주석풀기
        } else {
            return null;
        }
    }

}
