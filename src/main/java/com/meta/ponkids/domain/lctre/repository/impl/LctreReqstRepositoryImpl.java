package com.meta.ponkids.domain.lctre.repository.impl;

import static com.meta.ponkids.domain.cls.entity.QClass.class$;
import static com.meta.ponkids.domain.lctre.entity.QLctre.lctre;
import static com.meta.ponkids.domain.lctre.entity.QLctreReqst.lctreReqst;
import static com.meta.ponkids.domain.system.cmmnCd.entity.QCmmnCdDetail.cmmnCdDetail;
import static com.meta.ponkids.domain.user.entity.QUserChldrn.userChldrn;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.meta.ponkids.domain.lctre.dto.LctreReqstListDto;
import com.meta.ponkids.domain.lctre.dto.QLctreReqstListDto;
import com.meta.ponkids.domain.lctre.repository.custom.LctreReqstRepositoryCustom;
import com.meta.ponkids.global.common.dto.CategoryDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LctreReqstRepositoryImpl implements LctreReqstRepositoryCustom {
	
	private final JPAQueryFactory query;
	
	@Override
	public List<LctreReqstListDto> getListByClassReqstSn( Long classReqstSn ) {
		// TODO Auto-generated method stu
		
		return query
				.select( new QLctreReqstListDto(
												lctreReqst.lctreReqstSn,
												lctreReqst.classReqstSn,
												lctreReqst.lctreSn,
												lctre.lctreSeq,
												lctre.lctreSj,
												lctre.lctreDt,
												lctre.lctreAmt,
												lctre.lctreDc,
												lctre.lctreApplcntGuidance,
												lctre.classDayCd,
												cmmnCdDetail.cdDetailNm,
												lctreReqst.chldrnSn,
												userChldrn.chldrnNm,
												lctreReqst.preparNmprYn
						)
				)
				.from( lctreReqst )
				.leftJoin( lctre )
				// join 에는 delYn 조건 필수로 추가
				.on( 	lctre.lctreSn.eq( lctreReqst.lctreSn ),
						lctre.delYn.eq( "N" )
				)
				.leftJoin( userChldrn )
				// join 에는 delYn 조건 필수로 추가
				.on( 	userChldrn.chldrnSn.eq( lctreReqst.chldrnSn ),
						userChldrn.delYn.eq( "N" )
						)
				.leftJoin( cmmnCdDetail )
				.on(	cmmnCdDetail.cdNm.eq("DAY_7_CD"),
						cmmnCdDetail.cdDetailVal1.eq( lctre.classDayCd ),
						cmmnCdDetail.useYn.eq("Y"),
						cmmnCdDetail.delYn.eq("N")
						)
				.where(
						lctreReqst.classReqstSn.eq( classReqstSn )
				)
				.orderBy(
						lctreReqst.lctreReqstSn.desc()
				)
				.fetch();
				
				
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
