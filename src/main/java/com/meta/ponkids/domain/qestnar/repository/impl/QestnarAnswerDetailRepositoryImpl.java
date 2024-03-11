package com.meta.ponkids.domain.qestnar.repository.impl;


import static com.meta.ponkids.domain.qestnar.entity.QQestnarAnswerDetail.qestnarAnswerDetail;
import static com.meta.ponkids.domain.qestnar.entity.QQestnarQestn.qestnarQestn;
import static com.meta.ponkids.domain.cls.entity.QClassInqry.classInqry;
import static com.meta.ponkids.domain.qestnar.entity.QQestnarAnswer.qestnarAnswer;
import static com.meta.ponkids.domain.qestnar.entity.QQestnarGroup.qestnarGroup;
import static com.meta.ponkids.domain.qestnar.entity.QQestnarQestnDetail.qestnarQestnDetail;

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

@Repository
@RequiredArgsConstructor
public class QestnarAnswerDetailRepositoryImpl implements QestnarAnswerDetailRepositoryCustom {
	
	private final JPAQueryFactory query;
	
	@Override
	public Page<QestnarAnswerDetailListDto> getList( QestnarAnswerDetailListDto listDto, Pageable pageable ) {
		
		// (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회
        
        // (1) 결과list (results).
		List<QestnarAnswerDetailListDto> results = query
				// select
                .select( new QQestnarAnswerDetailListDto(
                		qestnarAnswerDetail.qestnarAnswerDetailSn,
                		qestnarAnswerDetail.qestnarAnswerSn,
                		qestnarAnswerDetail.qestnarQestnSn,
                		qestnarQestn.qestnarQestnItemCn,
                		qestnarAnswerDetail.qestnarAnswer,
                		qestnarAnswerDetail.qestnarQestnDetailSn,
                		qestnarQestnDetail.qestnarQestnDetailCn,
                		qestnarAnswerDetail.atchFileSn,
                		qestnarAnswerDetail.registerId,
                		Expressions.stringTemplate("to_char({0}, '{1s}')", qestnarAnswerDetail.regDt, "YYYY-MM-DD HH24:MI:SS")
//                		new CaseBuilder()
//                		.when( user.gender.eq("M")).then("남자")
//                		.when( user.gender.eq("F")).then("여자")
//                		.otherwise("")
//                		.as("gender"),
                		) )					
                .from( qestnarAnswerDetail )
                .leftJoin( qestnarQestnDetail )
                .on(
                		qestnarQestnDetail.qestnarQestnDetailSn.eq( qestnarAnswerDetail.qestnarQestnDetailSn ),
                		qestnarQestnDetail.delYn.eq( "N" )
                )
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
	
	
	
	// 주관식 답변만 가져온다. 
	// - 객관식 답변은 QestnarQestnDetailRepositoryImpl.java > getListForQestnarAnswerDetail 에서 선택지 가져올 때 join 으로 답을 같이 가져옴. 
	@Override
	public List<QestnarAnswerDetailListDto> getListByQestnarAnswerSn(Long qestnarAnswerSn) {
		return query
				// select
                .select( new QQestnarAnswerDetailListDto(
                		qestnarAnswerDetail.qestnarAnswerDetailSn,
                		qestnarAnswerDetail.qestnarAnswerSn,
                		qestnarAnswerDetail.qestnarQestnSn,
                		qestnarQestn.qestnarQestnItemCn,
                		qestnarAnswerDetail.qestnarAnswer,
                		qestnarAnswerDetail.qestnarQestnDetailSn,
                		qestnarQestnDetail.qestnarQestnDetailCn,
                		qestnarAnswerDetail.atchFileSn,
                		qestnarAnswerDetail.registerId,
                		Expressions.stringTemplate("to_char({0}, '{1s}')", qestnarAnswerDetail.regDt, "YYYY-MM-DD HH24:MI:SS")
                		) )					
                .from( qestnarAnswerDetail )
                // join
                .leftJoin( qestnarQestn )
                .on(
                		qestnarQestn.qestnarQestnSn.eq( qestnarAnswerDetail.qestnarQestnSn ),
                		qestnarQestn.delYn.eq( "N" )
                )
                .leftJoin( qestnarQestnDetail )
                .on(
                		qestnarQestnDetail.qestnarQestnDetailSn.eq( qestnarAnswerDetail.qestnarQestnDetailSn ),
                		qestnarQestnDetail.delYn.eq( "N" )
                )
                // where
                .where(
                		qestnarAnswerDetail.qestnarAnswerSn.eq( qestnarAnswerSn ),
                		qestnarAnswerDetail.qestnarAnswer.isNotNull()	// 주관식 답변만 가져온다.
				)
                // order by
                .orderBy( qestnarQestn.qestnarQestnSeq.asc() )
                .fetch();
	}
	
	
	
	@Override
	public List<QestnarAnswerDetailListDto> getList( QestnarAnswerDetailListDto listDto ) {
		return query
				// select
                .select( new QQestnarAnswerDetailListDto(
                		qestnarAnswerDetail.qestnarAnswerDetailSn,
                		qestnarAnswerDetail.qestnarAnswerSn,
                		qestnarAnswerDetail.qestnarQestnSn,
                		qestnarQestn.qestnarQestnItemCn,
                		qestnarAnswerDetail.qestnarAnswer,
                		qestnarAnswerDetail.qestnarQestnDetailSn,
                		qestnarQestnDetail.qestnarQestnDetailCn,
                		qestnarAnswerDetail.atchFileSn,
                		qestnarAnswerDetail.registerId,
                		Expressions.stringTemplate("to_char({0}, '{1s}')", qestnarAnswerDetail.regDt, "YYYY-MM-DD HH24:MI:SS")
                		) )					
                .from( qestnarAnswerDetail )
                // join
                .leftJoin( qestnarAnswer )
                .on(
                		qestnarAnswer.qestnarAnswerSn.eq( qestnarAnswerDetail.qestnarAnswerSn ),
                		qestnarAnswer.delYn.eq( "N" )
                )
                .leftJoin( qestnarGroup )
                .on(
                		qestnarGroup.qestnarGroupSn.eq( qestnarAnswer.qestnarGroupSn ),
                		qestnarGroup.delYn.eq( "N" )
                		)
                .leftJoin( qestnarQestn )
                .on(
                		qestnarQestn.qestnarQestnSn.eq( qestnarAnswerDetail.qestnarQestnSn ),
                		qestnarQestn.delYn.eq( "N" )
                )
                .leftJoin( qestnarQestnDetail )
                .on(
                		qestnarQestnDetail.qestnarQestnDetailSn.eq( qestnarAnswerDetail.qestnarQestnDetailSn ),
                		qestnarQestnDetail.delYn.eq( "N" )
                )
                // where
                .where(
                		eqUserSn( listDto.getUserSn()),
                		eqQestnarAnswerSn( listDto.getQestnarAnswerSn() ),
                		eqQestnarGroupCd( listDto.getQestnarGroupCd())
				)
                // order by
                .orderBy( qestnarQestn.qestnarQestnSeq.asc() )
                .fetch();
	}
	
	// -------------------------------- WHERE 검색 옵션 setting --------------------------------
	// -------------------------------- WHERE 검색 옵션 setting --------------------------------
	
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

	private BooleanExpression eqUserSn( Long userSn ) {
		return ( userSn != null )  ? qestnarAnswer.userSn.eq( userSn ) : null;
	}
	
	private BooleanExpression eqQestnarAnswerSn( Long qestnarAnswerSn ) {
		return ( qestnarAnswerSn != null )  ? qestnarAnswerDetail.qestnarAnswerSn.eq( qestnarAnswerSn ) : null;
	}
	
	private BooleanExpression eqQestnarGroupCd( String qestnarGroupCd ) {
		return ( StringUtils.hasText(qestnarGroupCd) )  ? qestnarGroup.qestnarGroupCd.eq( qestnarGroupCd ) : null;
	}
	
    
    
    

}
