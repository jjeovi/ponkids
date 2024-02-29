package com.meta.ponkids.domain.cls.repository.impl;


import static com.meta.ponkids.domain.cls.entity.QClassReview.classReview;
import static com.meta.ponkids.domain.cls.entity.QClass.class$;
import static com.meta.ponkids.domain.user.entity.QUser.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.meta.ponkids.domain.cls.dto.ClassReviewListDto;
import com.meta.ponkids.domain.cls.dto.QClassReviewListDto;
import com.meta.ponkids.domain.cls.repository.custom.ClassReviewRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

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
                		classReview.userSn,
                		user.userNm,
                		classReview.step,
                		classReview.parntsReviewSn,
                		classReview.reviewCn,
                		classReview.reviewGrade,
                		classReview.atchFileSn,
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
                .where(
                		eqOption( listDto.getSchOption(), listDto.getSchCntn() )
				)
                .orderBy( classReview.classReviewSn.desc())
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
		
		
		// (2) count
        JPAQuery<Long> count = query.select( classReview.count() )
                .from( classReview )									
                .where(
                        eqOption( listDto.getSchOption(), listDto.getSchCntn() )
                );

		return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
		
	}
	
	
    private BooleanExpression eqOption( String schOption, String schCntn ) {
        // 검색 옵션  A : 아이디 , B : 이름 <- 예시 일뿐 이런식으로 커스텀하면 됨
        if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
//            if ( schOption.equals( "A" ) )
//                return classReview.classReviewSn.contains( schCntn ); 
//            else if ( schOption.equals( "B" ) )
//                return classReview.classReviewNm.contains( schCntn ); 
//            else return null;
        	return null;
        } else {
            return null;
        }
    }

}
