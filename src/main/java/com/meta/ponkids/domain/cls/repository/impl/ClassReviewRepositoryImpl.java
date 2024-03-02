package com.meta.ponkids.domain.cls.repository.impl;


import com.meta.ponkids.domain.cls.dto.ClassReviewListDto;
import com.meta.ponkids.domain.cls.dto.QClassReviewListDto;
import com.meta.ponkids.domain.cls.repository.custom.ClassReviewRepositoryCustom;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
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
import static com.meta.ponkids.domain.cls.entity.QClassReview.classReview;
import static com.meta.ponkids.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class ClassReviewRepositoryImpl implements ClassReviewRepositoryCustom {
	
	private final JPAQueryFactory query;
	
	@Override
	public Page<ClassReviewListDto> getList( ClassReviewListDto listDto, Pageable pageable ) {
		
		// (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회
        
        // (1) 결과list (results).
		List<ClassReviewListDto> results = query
				// select
                .select( new QClassReviewListDto(
                		classReview.classReviewSn,
                		classReview.classSn,
                		class$.classSj,
                		class$.thumbAtchFileSn.as( "thumbAtchFileSn" ),
                		classReview.userSn,
                		user.userNm,
                		classReview.step,
                		classReview.parntsReviewSn,
                		classReview.reviewCn,
                		classReview.reviewGrade,
						classReview.reviewGrade.castToNum( Long.class ).as("reviewGradeLong"),
                		classReview.atchFileSn,
                		classReview.openYn,
                		classReview.registerId,
                		Expressions.stringTemplate("to_char({0}, '{1s}')", classReview.regDt, "YYYY-MM-DD HH:MM:SS")
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
                // where
//                .where(
//                		eqOption( listDto.getSchOption(), listDto.getSchCntn() )
//				)
                .orderBy(
						eqOption( listDto.getSchOption(), listDto.getSchCntn() ),
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
								class$.classSj,
								class$.thumbAtchFileSn.as( "thumbAtchFileSn" ),
								classReview.userSn,
								user.userNm,
								classReview.step,
								classReview.parntsReviewSn,
								classReview.reviewCn,
								classReview.reviewGrade,
								classReview.reviewGrade.castToNum( Long.class ).as("reviewGradeLong"),
								classReview.atchFileSn,
								classReview.openYn,
								classReview.registerId,
								Expressions.stringTemplate("to_char({0}, '{1s}')", classReview.regDt, "YYYY-MM-DD")
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
								class$.classSj,
								class$.thumbAtchFileSn.as( "thumbAtchFileSn" ),
								classReview.userSn,
								user.userNm,
								classReview.step,
								classReview.parntsReviewSn,
								classReview.reviewCn,
								classReview.reviewGrade,
								classReview.reviewGrade.castToNum( Long.class ).as("reviewGradeLong"),
								classReview.atchFileSn,
								classReview.openYn,
								classReview.registerId,
								Expressions.stringTemplate("to_char({0}, '{1s}')", classReview.regDt, "YYYY-MM-DD")
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
				.where(
						classReview.step.eq( step ),
						classReview.parntsReviewSn.eq( parntsReviewSn )
				)
				.orderBy(
						classReview.classReviewSn.desc()
				)
				.fetch();
	}
	
    private OrderSpecifier eqOption( String schOption, String schCntn ) {
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
