package com.meta.ponkids.domain.ntt.repository.impl;

import com.meta.ponkids.domain.ntt.dto.NttListDto;
import com.meta.ponkids.domain.ntt.dto.QNttListDto;
import com.meta.ponkids.domain.ntt.repository.custom.NttRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
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
import static com.meta.ponkids.domain.ntt.entity.QNtt.ntt;
import static com.meta.ponkids.domain.qestnar.entity.QQestnarGroup.qestnarGroup;
import static com.meta.ponkids.domain.bbs.entity.QBbs.bbs;
import static com.meta.ponkids.domain.user.entity.QUser.user;


/**
 * className	  : NttRepositoryImpl
 * author		 : ehlee
 * date		   : 2023-12-02
 * description	: class of 게시물 RepositoryImpl
 * ===========================================================
 * DATE			  AUTHOR			   NOTE
 * -----------------------------------------------------------
 * 2023-12-02		ehlee			 최초 생성
 */
@Repository
@RequiredArgsConstructor
public class NttRepositoryImpl implements NttRepositoryCustom {
	private final JPAQueryFactory query;
	
	@Override
	public Page<NttListDto> getList( NttListDto nttListDto, Pageable pageable ) {
		
		// (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회
		
		// (1) 결과 list (results).
		List<NttListDto> results = query
				.select( new QNttListDto(
						ntt.nttSn,
						ntt.bbsSn,
						ntt.nttNm,
						ntt.nttCn,
						ntt.atchFileSn,
						ntt.nttRdcnt,
						ntt.openYn,
						ntt.noticeSetYn,
						ntt.registerId,
						user.userId,
						user.userNm,
						Expressions.stringTemplate("to_char({0}, '{1s}')", ntt.regDt, "YYYY-MM-DD"),
						Expressions.stringTemplate("to_char({0}, '{1s}')", ntt.regDt, "YYYY-MM-DD HH24:MI:SS").as("regFullDt")
				) ).from( ntt )
				.leftJoin( user )
				.on( 
						user.userId.eq( ntt.registerId ),
						user.delYn.eq( "N" )
				)
				.where(
						eqOption( nttListDto.getSchOption(), nttListDto.getSchCntn() ),
						ntt.bbsSn.eq( nttListDto.getBbsSn() )
				)
				.orderBy( ntt.nttSn.desc() )
				.offset( pageable.getOffset() )
				.limit( pageable.getPageSize() )
				.fetch();
		
		// (2) count
		JPAQuery<Long> count = query.select( ntt.count() )
				.from( ntt )
				.where(
						eqOption( nttListDto.getSchOption(), nttListDto.getSchCntn() ),
						ntt.bbsSn.eq( nttListDto.getBbsSn() )
				);
		
		return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
	}
	
	
	@Override
	public List<NttListDto> getList(NttListDto nttListDto) {
		return query
				.select( new QNttListDto(
						ntt.nttSn,
						ntt.bbsSn,
						ntt.nttNm,
						ntt.nttCn,
						ntt.atchFileSn,
						ntt.nttRdcnt,
						ntt.openYn,
						ntt.noticeSetYn,
						ntt.registerId,
						user.userId,
						user.userNm,
						Expressions.stringTemplate("to_char({0}, '{1s}')", ntt.regDt, "YYYY-MM-DD"),
						Expressions.stringTemplate("to_char({0}, '{1s}')", ntt.regDt, "YYYY-MM-DD HH24:MI:SS").as("regFullDt")
						)
				)
				.from( ntt )
				.leftJoin( bbs )
				.on(
					bbs.bbsSn.eq( ntt.bbsSn ),
					bbs.delYn.eq( "N" )
				)
				.leftJoin( user )
				.on( 
						user.userId.eq( ntt.registerId ),
						user.delYn.eq( "N" )
				)
				.where(
						eqOption( nttListDto.getSchOption(), nttListDto.getSchCntn() ),
						eqBbsNm( nttListDto.getBbsNm() )
				)
				.orderBy( ntt.nttSn.desc() )
				.fetch();
	}

	// 공지 게시물 목록
	@Override
	public List<NttListDto> getNoticeList( Long bbsSn ) {
		
		List<NttListDto> results = query
				.select( new QNttListDto(
						ntt.nttSn,
						ntt.bbsSn,
						ntt.nttNm,
						ntt.nttCn,
						ntt.atchFileSn,
						ntt.nttRdcnt,
						ntt.openYn,
						ntt.noticeSetYn,
						ntt.registerId,
						user.userId,
						user.userNm,
						Expressions.stringTemplate("to_char({0}, '{1s}')", ntt.regDt, "YYYY-MM-DD"),
						Expressions.stringTemplate("to_char({0}, '{1s}')", ntt.regDt, "YYYY-MM-DD HH24:MI:SS").as("regFullDt")
				) )
				.from( ntt )
				.leftJoin( user )
				.on( 
						user.userId.eq( ntt.registerId ),
						user.delYn.eq( "N" )
				)
				.where(
						ntt.bbsSn.eq( bbsSn ),
						ntt.noticeSetYn.eq( "Y" )
				)
				.orderBy( ntt.noticeSeq.desc() )
				.fetch();
		
		return results;
	}
	
	@Override
	public NttListDto detailByNttSn( Long nttSn ) {
		return query
				.select( new QNttListDto(
						ntt.nttSn,
						ntt.bbsSn,
						ntt.nttNm,
						ntt.nttCn,
						ntt.atchFileSn,
						ntt.nttRdcnt,
						ntt.openYn,
						ntt.noticeSetYn,
						ntt.registerId,
						user.userId,
						user.userNm,
						Expressions.stringTemplate("to_char({0}, '{1s}')", ntt.regDt, "YYYY-MM-DD"),
						Expressions.stringTemplate("to_char({0}, '{1s}')", ntt.regDt, "YYYY-MM-DD HH24:MI:SS").as("regFullDt")
				) )
				.from( ntt )
				.leftJoin( user )
				.on( 
						user.userId.eq( ntt.registerId ),
						user.delYn.eq( "N" )
				)
				.where(
						ntt.nttSn.eq( nttSn )
				)
				.orderBy( ntt.noticeSeq.desc() )
				.fetchFirst();
	}
	
	// -------------------------------- WHERE 검색 옵션 setting --------------------------------
	

	private BooleanExpression eqBbsNm( String bbsNm ) {
		return ( StringUtils.hasText( bbsNm ) )  ? bbs.bbsNm.eq( bbsNm ) : null;
	}

	
	
	private BooleanExpression eqOption( String schOption, String schCntn ) {
		// 검색 옵션  A : 아이디 , B : 이름  (대소문자 구분없이 조회)
		if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
			if ( schOption.equals( "A" ) )
				return ntt.nttNm.toUpperCase().contains( schCntn.toUpperCase() ); // LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
			else if ( schOption.equals( "B" ) )
				return ntt.registerId.toUpperCase().contains( schCntn.toUpperCase() ); // LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
			else return null;
		} else {
			return null;
		}
	}
	
	// 조회수 +1 
	public Integer getMaxNttRdcnt( Long nttSn ) {
		int number = query.select( ntt.nttRdcnt.max().coalesce( 0 ) )
				.from( ntt )
				.where(
						ntt.nttSn.eq( nttSn )
				)
//				.fetchOne();
				.fetchFirst();
		
		number = number + 1;
		
		return number;
		
	}
	
	// 게시물 순번 + 1
	public Integer MaxNttSeq( Long bbsSn ) {
		int number = query.select( ntt.nttSeq.max().coalesce( 0 ) )
				.from( ntt )
				.where(
						ntt.bbsSn.eq( bbsSn )
				)
//				.fetchOne();
				.fetchFirst();
		
		number = number + 1;
		
		return number;
	}
	
	// 게시물 존재여부
	@Override
	public int getExistsNtt( Long bbsSn ) {
		int count = query.select( ntt.nttRdcnt.max().coalesce( 0 ) )
				.from( ntt )
				.where(
						ntt.bbsSn.eq( bbsSn )
				)
//				.fetchOne();
				.fetchFirst();
		
		return count;
	}
	
	
}