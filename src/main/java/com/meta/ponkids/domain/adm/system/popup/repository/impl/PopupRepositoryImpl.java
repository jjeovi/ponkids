package com.meta.ponkids.domain.adm.system.popup.repository.impl;


import com.meta.ponkids.domain.adm.system.popup.dto.PopupListDto;
import com.meta.ponkids.domain.adm.system.popup.dto.QPopupListDto;
import com.meta.ponkids.domain.adm.system.popup.repository.custom.PopupRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.meta.ponkids.domain.adm.system.popup.entity.QPopup.popup;

@Repository
@RequiredArgsConstructor
public class PopupRepositoryImpl implements PopupRepositoryCustom {
    
    private final JPAQueryFactory query;
    
    @Override
    public Page<PopupListDto> getList( PopupListDto listDto, Pageable pageable ) {
        
        // TODO 구현
        // (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회
        
        // TODO
        // (1) 결과list (results).
        List<PopupListDto> results = query
                // select
                .select( new QPopupListDto(
                        popup.popupSn
//                		new CaseBuilder()
//                		.when( user.gender.eq("M")).then("남자")
//                		.when( user.gender.eq("F")).then("여자")
//                		.otherwise("")
//                		.as("gender"),
                ) )
                .from( popup )
                // where
                .where()
//                .orderBy( popup.popupSn.desc())
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
        
        
        // TODO
        // (2) count
        JPAQuery<Long> count = query.select( popup.count() )
                .from( popup )
                .where(
                        eqOption( listDto.getSchOption(), listDto.getSchCntn() ) );
        
        
        
        return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
        
    }
    
    
    private BooleanExpression eqOption( String schOption, String schCntn ) {
        // 검색 옵션  A : 아이디 , B : 이름 <- 예시 일뿐 이런식으로 커스텀하면 됨
        if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
//            if ( schOption.equals( "A" ) )
//                return popup.popupSn.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else if ( schOption.equals( "B" ) )
//                return popup.popupNm.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else return null;
            return null;            // TODO (build한 이후에 해주세요. 안그럼 에러발생)  실제 구현시에는 해당부분지워주고 위에부분주석풀기
        } else {
            return null;
        }
    }
    
}
