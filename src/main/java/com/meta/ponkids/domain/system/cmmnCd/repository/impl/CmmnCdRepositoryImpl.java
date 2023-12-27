package com.meta.ponkids.domain.system.cmmnCd.repository.impl;


import com.meta.ponkids.domain.system.cmmnCd.dto.CmmnCdListDto;
import com.meta.ponkids.domain.system.cmmnCd.dto.QCmmnCdListDto;
import com.meta.ponkids.domain.system.cmmnCd.repository.custom.CmmnCdRepositoryCustom;
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

import static com.meta.ponkids.domain.system.cmmnCd.entity.QCmmnCd.cmmnCd;

@Repository
@RequiredArgsConstructor
public class CmmnCdRepositoryImpl implements CmmnCdRepositoryCustom {
    
    private final JPAQueryFactory query;
    
    @Override
    public Page<CmmnCdListDto> getList( CmmnCdListDto listDto, Pageable pageable ) {
        
        // TODO 구현
        // (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회
        
        // TODO
        // (1) 결과list (results).
        List<CmmnCdListDto> results = query
                // select
                .select( new QCmmnCdListDto(
                        cmmnCd.cdSn
//                		new CaseBuilder()
//                		.when( user.gender.eq("M")).then("남자")
//                		.when( user.gender.eq("F")).then("여자")
//                		.otherwise("")
//                		.as("gender"),
                ) )
                .from( cmmnCd )
                // where
                .where()
//                .orderBy( cmmnCd.cdSn.desc())
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
        
        
        // TODO
        // (2) count
        JPAQuery<Long> count = query.select( cmmnCd.count() )
                .from( cmmnCd )
                .where(
                        eqOption( listDto.getSchOption(), listDto.getSchCntn() ) );
        
        
        
        return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
        
    }
    
    
    private BooleanExpression eqOption( String schOption, String schCntn ) {
        // 검색 옵션  A : 아이디 , B : 이름 <- 예시 일뿐 이런식으로 커스텀하면 됨
        if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
//            if ( schOption.equals( "A" ) )
//                return cmmnCd.cdSn.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else if ( schOption.equals( "B" ) )
//                return cmmnCd.cmmnCdNm.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else return null;
            return null;            // TODO (build한 이후에 해주세요. 안그럼 에러발생)  실제 구현시에는 해당부분지워주고 위에부분주석풀기
        } else {
            return null;
        }
    }
    
}
