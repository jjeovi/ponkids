package com.meta.ponkids.domain.system.banner.repository.impl;


import com.meta.ponkids.domain.system.banner.dto.BannerListDto;
import com.meta.ponkids.domain.system.banner.dto.QBannerListDto;
import com.meta.ponkids.domain.system.banner.repository.custom.BannerRepositoryCustom;
import com.meta.ponkids.domain.system.cmmnCd.entity.QCmmnCdDetail;
import com.meta.ponkids.global.common.dto.CategoryDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.List;

import static com.meta.ponkids.domain.system.banner.entity.QBanner.banner;
import static com.meta.ponkids.domain.cls.entity.QClass.class$;

@Repository
@RequiredArgsConstructor
public class BannerRepositoryImpl implements BannerRepositoryCustom {
    
    private final JPAQueryFactory query;
    
    QCmmnCdDetail join_bannerClCd       = new QCmmnCdDetail( "join_bannerClCd" );           // 배너 분류 조인용 테이블 생성
    QCmmnCdDetail join_bannerClDetailCd = new QCmmnCdDetail( "join_bannerClDetailCd" );     // 배너 상세 분류 조인용 테이블 생성
    
    @Override
    public Page<BannerListDto> getList( BannerListDto listDto, Pageable pageable ) {

        
        // (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회
        
        // TODO
        // (1) 결과list (results).
        List<BannerListDto> results = query
                // select
                .select( new QBannerListDto(
                        banner.bannerSn,
                        banner.bannerClCd,
                        join_bannerClCd.cdDetailNm.as("bannerClNm"),
                        banner.bannerClDetailCd,
                        join_bannerClDetailCd.cdDetailNm.as("bannerClDetailNm"),
                        banner.bannerExpsrSeq,
                        banner.bannerNm,
                        banner.bannerDc,
                        banner.atchFileSn,
                        banner.url,
                        banner.classMapngYn,
                        banner.classSn,
                        class$.classSj,
                        class$.classAmt,
                        class$.classDscntBfeAmt,
                        class$.thumbAtchFileSn.as("classThumbAtchFileSn"),
                        banner.useYn,
                        banner.bannerPdSetYn,
                        new CaseBuilder()
                                .when( banner.bannerPdSetYn.eq( "Y" ) ).then(
                                        "기간"
                                ).when( banner.bannerPdSetYn.eq( "N" ) ).then(
                                        "상시"
                                ).otherwise( "" )
                                .as( "bannerPdSetYnNm" ),
                        banner.bannerBeginDt,
                        banner.bannerEndDt,
                        new CaseBuilder()
                                .when( banner.bannerPdSetYn.eq( "Y" ) ).then(
                                        banner.bannerBeginDt.concat( " ~ " ).concat( banner.bannerEndDt )
                                )
                                .when( banner.bannerPdSetYn.eq( "N" ) ).then( "-" )
                                .otherwise( "" )
                                .as( "bannerExpsrPeriod" )
                ) )
                .from( banner )
                .leftJoin( class$ )
                .on (
                        class$.classSn.eq( banner.classSn),
                        class$.delYn.eq("N")
                )
                .leftJoin( join_bannerClCd )
                .on(
                        join_bannerClCd.cdDetailVal1.eq( banner.bannerClCd ),
                        join_bannerClCd.cdNm.eq("BANNER_CL_CD"),
                        join_bannerClCd.useYn.eq( "Y" ),
                        join_bannerClCd.delYn.eq( "N" )
                )
                .leftJoin( join_bannerClDetailCd )
                .on(
                        join_bannerClDetailCd.cdDetailVal1.eq( banner.bannerClDetailCd ),
                        join_bannerClDetailCd.cdNm.eq("BANNER_CL_DETAIL_CD"),
                        join_bannerClDetailCd.useYn.eq( "Y" ),
                        join_bannerClDetailCd.delYn.eq( "N" )
                )
                // where
                .where(
                        eqCateLv1( listDto.getCategory() ),
                        eqCateLv2( listDto.getCategory() ),
                        eqUseYn( listDto.getUseYn() ),
                        eqBannerPdSetYn( listDto.getBannerPdSetYn() ),
                        eqOption( listDto.getSchOption(), listDto.getSchCntn() )
                )
                .orderBy( banner.bannerSn.desc())
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
        
        
        // TODO
        // (2) count
        JPAQuery<Long> count = query.select( banner.count() )
                .from( banner )
                .where(
                        eqOption( listDto.getSchOption(), listDto.getSchCntn() )
                );
        
        
        
        return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
        
    }
    
    @Override
    public List<BannerListDto> getMainList( String bannerClCd ) {
        // 메인 list
        
        // - 1. 사용여부 Y
        // - 2. 표시기간이 상시
        // - 3. 표시기간이 기간일 경우 , 현재시간(now) 가 시작일~종료일 기간내에 포함되어있는 배너
        
        
        List<BannerListDto> results = query
                // select
                .select( new QBannerListDto(
                        banner.bannerSn,
                        banner.bannerClCd,
                        join_bannerClCd.cdDetailNm.as("bannerClNm"),
                        banner.bannerClDetailCd,
                        join_bannerClDetailCd.cdDetailNm.as("bannerClDetailNm"),
                        banner.bannerExpsrSeq,
                        banner.bannerNm,
                        banner.bannerDc,
                        banner.atchFileSn,
                        banner.url,
                        banner.classMapngYn,
                        banner.classSn,
                        class$.classSj,
                        class$.classAmt,
                        class$.classDscntBfeAmt,
                        class$.thumbAtchFileSn.as("classThumbAtchFileSn"),
                        banner.useYn,
                        banner.bannerPdSetYn,
                        new CaseBuilder()
                                .when( banner.bannerPdSetYn.eq( "Y" ) ).then(
                                        "기간"
                                ).when( banner.bannerPdSetYn.eq( "N" ) ).then(
                                        "상시"
                                ).otherwise( "" )
                                .as( "bannerPdSetYnNm" ),
                        banner.bannerBeginDt,
                        banner.bannerEndDt,
                        new CaseBuilder()
                                .when( banner.bannerPdSetYn.eq( "Y" ) ).then(
                                        banner.bannerBeginDt.concat( " ~ " ).concat( banner.bannerEndDt )
                                )
                                .when( banner.bannerPdSetYn.eq( "N" ) ).then( "-" )
                                .otherwise( "" )
                                .as( "bannerExpsrPeriod" )
                ) )
                .from( banner )
                .leftJoin( class$ )
                .on (
                        class$.classSn.eq( banner.classSn),
                        class$.delYn.eq("N")
                )
                .innerJoin( join_bannerClCd )
                .on(
                        join_bannerClCd.cdDetailVal1.eq( banner.bannerClCd ),
                        join_bannerClCd.cdNm.eq("BANNER_CL_CD"),
                        join_bannerClCd.useYn.eq( "Y" ),
                        join_bannerClCd.delYn.eq( "N" )
                )
                .leftJoin( join_bannerClDetailCd )
                .on(
                        join_bannerClDetailCd.cdDetailVal1.eq( banner.bannerClDetailCd ),
                        join_bannerClDetailCd.cdNm.eq("BANNER_CL_DETAIL_CD"),
                        join_bannerClDetailCd.useYn.eq( "Y" ),
                        join_bannerClDetailCd.delYn.eq( "N" )
                )
                // where
                .where(
                        eqUseYn("Y"),
                        eqBannerClCd( bannerClCd ),
                        eqBannerPdSetYn()
                )
                .orderBy(
                		banner.bannerClDetailCd.asc(),
                		banner.bannerExpsrSeq.asc(),
                		banner.regDt.asc()
                )
                .fetch();
        
        return results;
    }
    
    
    // -------------------------------- WHERE 검색 옵션 setting --------------------------------
    
    
    // 사용여부 검색
    private BooleanExpression eqUseYn( String useYn  ) {
    	return ( StringUtils.hasText( useYn ) ) ? banner.useYn.eq( useYn ) : null;
    }
    
    
    // 배너 분류 설정 여부 검색
    private BooleanExpression eqBannerClCd( String bannerClCd ) {
        return ( StringUtils.hasText( bannerClCd ) ) ? banner.bannerClCd.eq( bannerClCd ) : null;
    }
    
    // 배너 기간 설정 여부 검색
    private BooleanExpression eqBannerPdSetYn( String eqBannerPdSetYn ) {
    	return ( StringUtils.hasText( eqBannerPdSetYn ) ) ? banner.bannerPdSetYn.eq( eqBannerPdSetYn ) : null;
    }
    
    private BooleanExpression eqBannerPdSetYn() {
        return banner.bannerPdSetYn.eq("N")
                .or(
                            banner.bannerPdSetYn.eq("Y").
                       and(
                               Expressions.currentTimestamp().between(
                                    Expressions.dateTimeTemplate( Timestamp.class, "TO_TIMESTAMP({0}, 'YYYY-MM-DD HH24:MI')", banner.bannerBeginDt   ),
                                    Expressions.dateTimeTemplate( Timestamp.class, "TO_TIMESTAMP({0}, 'YYYY-MM-DD HH24:MI')", banner.bannerEndDt     )
                               )
                       )
                    );
//        return banner.bannerPdSetYn.eq("Y");
    }
    
    // 카테고리 lv 1 검색 옵션
    // 배너 분류 검색
    // join_bannerClCd.cdDetailSn == lv1sn
    private BooleanExpression eqCateLv1( CategoryDto categoryDto ) {
        return ( categoryDto != null && categoryDto.getLv1Sn() != null
                && categoryDto.getLv1Sn() != 0 /* 0이 아닌 것은  검색 제외 */ ) ? join_bannerClCd.cdDetailSn.eq( categoryDto.getLv1Sn() ) : null;
    }
    
    // 카테고리 lv 2 검색 옵션
    // 배너 상세 분류 검색
    // join_bannerClDetailCd.cdDetailSn == lv2sn
    private BooleanExpression eqCateLv2( CategoryDto categoryDto ) {
        return ( categoryDto != null && categoryDto.getLv2Sn() != null
                && categoryDto.getLv2Sn() != 0 /* 0이 아닌 것은  검색 제외 */ ) ? join_bannerClDetailCd.cdDetailSn.eq( categoryDto.getLv2Sn() ) : null;
    }
    
    
    private BooleanExpression eqOption( String schOption, String schCntn ) {
        // 검색 옵션  A : 아이디 , B : 이름 <- 예시 일뿐 이런식으로 커스텀하면 됨
        if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
            if ( schOption.equals( "A" ) )
                return banner.bannerNm.contains( schCntn );
            else if ( schOption.equals( "B" ) )
                return banner.url.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
            else return null;
        } else {
            return null;
        }
    }
    
}
