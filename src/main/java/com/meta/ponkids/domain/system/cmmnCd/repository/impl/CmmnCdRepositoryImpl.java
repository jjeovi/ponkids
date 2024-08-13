package com.meta.ponkids.domain.system.cmmnCd.repository.impl;


import static com.meta.ponkids.domain.system.cmmnCd.entity.QCmmnCd.cmmnCd;
import static com.meta.ponkids.domain.system.cmmnCd.entity.QCmmnCdDetail.cmmnCdDetail;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.meta.ponkids.domain.system.cmmnCd.dto.CmmnCdListDto;
import com.meta.ponkids.domain.system.cmmnCd.dto.QCmmnCdListDto;
import com.meta.ponkids.domain.system.cmmnCd.repository.custom.CmmnCdRepositoryCustom;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

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
                        cmmnCd.cdSn,
                        cmmnCd.cdNm,
                        cmmnCd.cdDc,
                        cmmnCd.cdVal1,
                        cmmnCd.cdVal2,
                        cmmnCd.cdVal3,
                        cmmnCd.cdVal4,
                        cmmnCd.cdVal5,
                        cmmnCd.useYn,
                        cmmnCd.sysEssntlCmmnYn,
                        cmmnCd.clCd.coalesce("-").as("clCd"),
                        cmmnCd.remark,
                        ExpressionUtils.as( JPAExpressions.select( cmmnCdDetail.count() )
                                .from( cmmnCdDetail )
                                .where( cmmnCdDetail.cdNm.eq( cmmnCd.cdNm ),
                                        cmmnCdDetail.delYn.eq("N" ) ), "childCnt" ),
                        cmmnCd.registerId,
                		Expressions.stringTemplate("to_char({0}, '{1s}')", cmmnCd.regDt, "YYYY-MM-DD HH24:MI:SS")
                ) )
                .from( cmmnCd )
                // where
                .where(
                		eqUseYn( listDto.getUseYn()),
                		eqOption( listDto.getSchOption(), listDto.getSchCntn() ) 
                		)
                .orderBy( cmmnCd.cdSn.desc())
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
        
        
        // TODO
        // (2) count
        JPAQuery<Long> count = query.select( cmmnCd.count() )
                .from( cmmnCd )
                .where( eqUseYn( listDto.getUseYn()),
                        eqOption( listDto.getSchOption(), listDto.getSchCntn())
                       );
        
        
        
        return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
        
    }
    
    // -------------------------------- WHERE 검색 옵션 setting --------------------------------
    
    private BooleanExpression eqUseYn( String useYn ) {
        return (  StringUtils.hasText( useYn ) )? cmmnCd.useYn.eq( useYn ) : null;
    }
    
    
    private BooleanExpression eqOption( String schOption, String schCntn ) {
        // 검색 옵션  A : 아이디 , B : 이름 <- 예시 일뿐 이런식으로 커스텀하면 됨
        if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
            if ( schOption.equals( "A" ) )
                return cmmnCd.cdNm.contains( schCntn ); 
            else if ( schOption.equals( "B" ) )
                return cmmnCd.cdDc.contains( schCntn ); 
            else if ( schOption.equals( "C" ) )
                return cmmnCd.clCd.contains( schCntn ); 
//            else return null;
            return null;            // TODO (build한 이후에 해주세요. 안그럼 에러발생)  실제 구현시에는 해당부분지워주고 위에부분주석풀기
        } else {
            return null;
        }
    }
    
}
