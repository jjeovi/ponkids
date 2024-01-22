package com.meta.ponkids.domain.lctre.repository.impl;

import java.util.List;

import com.meta.ponkids.domain.lctre.dto.LctreListDto;
import com.meta.ponkids.domain.lctre.repository.custom.LctreRepositoryCustom;
import com.meta.ponkids.global.common.dto.CategoryDto;
import com.querydsl.core.types.dsl.CaseBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.meta.ponkids.domain.lctre.dto.QLctreListDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import static com.meta.ponkids.domain.cls.entity.QClass.class$;
import static com.meta.ponkids.domain.cls.entity.QClassCategoryCl01.classCategoryCl01;
import static com.meta.ponkids.domain.cls.entity.QClassCategoryCl02.classCategoryCl02;
import static com.meta.ponkids.domain.lctre.entity.QLctre.lctre;
import static com.meta.ponkids.domain.system.cmmnCd.entity.QCmmnCdDetail.cmmnCdDetail;

@Repository
@RequiredArgsConstructor
public class LctreRepositoryImpl implements LctreRepositoryCustom {
	
	private final JPAQueryFactory query;
	
	@Override
	public Page<LctreListDto> getList( LctreListDto listDto, Pageable pageable ) {
		
		// (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회
        
        // (1) 결과list (results).
		List<LctreListDto> results = query
				// select
                .select( new QLctreListDto(
                		lctre.lctreSn,
						class$.ctgrySn,
						classCategoryCl01.clNm.as( "ctgryNm" ),
						class$.crseSn,
						classCategoryCl02.clNm.as( "crseNm" ),
						lctre.classSn,
						class$.classSj,
						cmmnCdDetail.cdDetailSn,
						lctre.classDayCd,
						cmmnCdDetail.cdDetailNm,
						lctre.lctreSeq,
						lctre.lctreSj,
						lctre.lctreDc,
						lctre.lctreApplcntGuidance,
						lctre.rcritNmprSetYn,
						new CaseBuilder()
								.when( lctre.rcritNmprSetYn.eq( "Y" ) ).then( "설정" )
								.when( lctre.rcritNmprSetYn.eq( "N" ) ).then( "미설정" )
								.otherwise( "" )
								.as( "rcritNmprSetYnNm" ),
						lctre.rcritNmprCo,
						lctre.preparRcritNmprSetYn,
						new CaseBuilder()
								.when( lctre.preparRcritNmprSetYn.eq( "Y" ) ).then( "설정" )
								.when( lctre.preparRcritNmprSetYn.eq( "N" ) ).then( "미설정" )
								.otherwise( "" )
								.as( "preparRcritNmprSetYnNm" ),
						lctre.preparRcritNmprCo,
						lctre.registerId,
                		Expressions.stringTemplate("to_char({0}, '{1s}')", lctre.regDt, "YYYY-MM-DD HH:MM:SS")
                		) )
                .from( lctre )
				.leftJoin( class$ )
				// join 에는 delYn 조건 필수로 추가
				.on(    class$.classSn.eq( lctre.classSn ),
						class$.delYn.eq( "N" )
				)
				.leftJoin( classCategoryCl01 )
				// join 에는 delYn 조건 필수로 추가
				.on(    class$.ctgrySn.eq( classCategoryCl01.clSn ),
						classCategoryCl01.delYn.eq( "N" )
				)
				.leftJoin( classCategoryCl02 )
				// join 에는 delYn 조건 필수로 추가
				.on(    class$.crseSn.eq( classCategoryCl02.clSn ),
						classCategoryCl02.delYn.eq("N")
				)
				.leftJoin( cmmnCdDetail )
				.on(	cmmnCdDetail.cdNm.eq("DAY_7_CD"),
						cmmnCdDetail.cdDetailVal1.eq( lctre.classDayCd ),
						cmmnCdDetail.delYn.eq("N")
						)
                // where
                .where(
						eqCateLv1( listDto.getCategory() ),	// 분류 조회 : lv1Sn 값 존재시 검색
						eqCateLv2( listDto.getCategory() ),	// 분류 조회 : lv2Sn 값 존재시 검색
						eqCateLv3( listDto.getCategory() ), // 분류 조회 : lv3Sn 값 존재시 검색
						eqCateLv4( listDto.getCategory() ), // 분류 조회 : lv4Sn 값 존재시 검색
						eqClassSn( listDto.getClassSn() )
				)
//                .orderBy( lctre.lctreSn.desc())
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
		
		// (2) count
        JPAQuery<Long> count = query.select( lctre.count() )
                .from( lctre )
                .where(
                        eqOption( listDto.getSchOption(), listDto.getSchCntn() ) );
		
		return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
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
	
	// 카테고리 lv 4 검색 옵션
	// lv4sn == cmmnCdDetail.cdDetailSn
	private BooleanExpression eqCateLv4( CategoryDto categoryDto ) {
		return ( categoryDto != null && categoryDto.getLv4Sn() != null
				&& categoryDto.getLv4Sn() != 0 /* 0이 아닌 것은  검색 제외 */ ) ? cmmnCdDetail.cdDetailSn.eq( categoryDto.getLv4Sn() ) : null;
	}
	
	private BooleanExpression eqClassSn( Long pk ) {
		return ( pk != null && pk != 0 )? lctre.classSn.eq( pk ) : null;
	}
	
    private BooleanExpression eqOption( String schOption, String schCntn ) {
        // 검색 옵션  A : 아이디 , B : 이름 <- 예시 일뿐 이런식으로 커스텀하면 됨
        if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
//            if ( schOption.equals( "A" ) )
//                return lctre.lctreSn.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else if ( schOption.equals( "B" ) )
//                return lctre.lctreNm.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else return null;
        	return null;			// TODO (build한 이후에 해주세요. 안그럼 에러발생)  실제 구현시에는 해당부분지워주고 위에부분주석풀기
        } else {
            return null;
        }
    }

}
