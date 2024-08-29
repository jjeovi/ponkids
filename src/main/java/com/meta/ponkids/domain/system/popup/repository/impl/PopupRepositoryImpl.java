package com.meta.ponkids.domain.system.popup.repository.impl;


import static com.meta.ponkids.domain.cls.entity.QClass.class$;
import static com.meta.ponkids.domain.cls.entity.QClassCategoryCl02.classCategoryCl02;

import java.util.List;

import com.querydsl.core.types.dsl.CaseBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.meta.ponkids.domain.system.popup.dto.QPopupListDto;
import com.meta.ponkids.domain.system.popup.dto.PopupListDto;
import com.meta.ponkids.domain.system.popup.repository.custom.PopupRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import static com.meta.ponkids.domain.system.popup.entity.QPopup.popup;

@Repository
@RequiredArgsConstructor
public class PopupRepositoryImpl implements PopupRepositoryCustom {
	
	private final JPAQueryFactory query;
	
	@Override
	public Page<PopupListDto> getList( PopupListDto listDto, Pageable pageable ) {
		
		// (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회
		
		// (1) 결과list (results).
		List<PopupListDto> results = query
				// select
				.select( new QPopupListDto(
						popup.popupSn,
						popup.popupNm,
						popup.popupCn,
						popup.useYn,
						popup.popupBeginDt,
						popup.popupEndDt,
						popup.popupBeginDt.concat(" ~ ").concat(popup.popupEndDt).as("popupPeriod"),
						popup.atchFileSn,
						popup.url,
						popup.registerId,
						Expressions.stringTemplate("to_char({0}, '{1s}')", popup.regDt, "YYYY-MM-DD HH24:MI:SS")
						) )					
				.from( popup )
				// where
				.where(
					eqOption( listDto.getSchOption(), listDto.getSchCntn() )
				)
				.orderBy( popup.popupSn.desc())
				.offset( pageable.getOffset() )
				.limit( pageable.getPageSize() )
				.fetch();
		
		// (2) count
		JPAQuery<Long> count = query.select( popup.count() )
				.from( popup )									
				.where(
						eqOption( listDto.getSchOption(), listDto.getSchCntn() )
		);
				

		return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
		
	}
	
	
	@Override
	public List<PopupListDto> getPonList() {
		return query
				// select
				.select( new QPopupListDto(
						popup.popupSn,
						popup.popupNm,
						popup.popupCn,
						popup.useYn,
						popup.popupBeginDt,
						popup.popupEndDt,
						popup.popupBeginDt.concat(" ~ ").concat(popup.popupEndDt).as("popupPeriod"),
						popup.atchFileSn,
						popup.url,
						popup.registerId,
						Expressions.stringTemplate("to_char({0}, '{1s}')", popup.regDt, "YYYY-MM-DD HH24:MI:SS")
						) )					
				.from( popup )
				// where
				.where(
						popup.useYn.eq( "Y"),	// '사용' 인 팝업
						Expressions.stringTemplate("to_char({0}, '{1s}')", Expressions.currentTimestamp(), "YYYY-MM-DD HH24:MI:SS").between( popup.popupBeginDt, popup.popupEndDt )
				)
				.orderBy( popup.popupSn.desc())
				.fetch();
	}
	
	
	private BooleanExpression eqOption( String schOption, String schCntn ) {
		// 검색 옵션  A : 아이디 , B : 이름 <- 예시 일뿐 이런식으로 커스텀하면 됨
		if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
//			if ( schOption.equals( "A" ) )
//				return popup.popupSn.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//			else if ( schOption.equals( "B" ) )
//				return popup.popupNm.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//			else return null;
			return null;			// TODO (build한 이후에 해주세요. 안그럼 에러발생)  실제 구현시에는 해당부분지워주고 위에부분주석풀기
		} else {
			return null;
		}
	}

}
