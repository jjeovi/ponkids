package com.meta.ponkids.domain.cls.repository.impl;

import static com.meta.ponkids.domain.cls.entity.QClassWeek.classWeek;
import static com.meta.ponkids.domain.system.cmmnCd.entity.QCmmnCdDetail.cmmnCdDetail;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.meta.ponkids.domain.cls.dto.ClassWeekListDto;
import com.meta.ponkids.domain.cls.dto.QClassWeekListDto;
import com.meta.ponkids.domain.cls.repository.custom.ClassWeekRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ClassWeekRepositoryImpl implements ClassWeekRepositoryCustom {
    
    private final JPAQueryFactory query;
    
    @Override
    public Page<ClassWeekListDto> getList( ClassWeekListDto listDto, Pageable pageable ) {
        
        // (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회
        
        // (1) 결과list (results).
        List<ClassWeekListDto> results = query
                // select
                .select( new QClassWeekListDto(
                        classWeek.classWeekSn,
                        classWeek.classSn,
                        classWeek.classDayCd,
                        cmmnCdDetail.cdDetailNm,
                        cmmnCdDetail.cdDetailSeq
                ) )
                .from( classWeek )
                // where
                .where()
//                .orderBy( classWeek.classWeekSn.desc())
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
        
        // (2) count
        JPAQuery<Long> count = query.select( classWeek.count() )
                .from( classWeek )
                .where(
                        eqOption( listDto.getSchOption(), listDto.getSchCntn() ) );
        
        return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
        
    }
    
    @Override
    public List<ClassWeekListDto> getListByClassSn( Long pk ){
    	List<ClassWeekListDto> results = query
    			.select( new QClassWeekListDto(
                        classWeek.classWeekSn,
                        classWeek.classSn,
                        classWeek.classDayCd,
                        cmmnCdDetail.cdDetailNm,
                        cmmnCdDetail.cdDetailSeq )
    			)
    			.from( classWeek )
    			.innerJoin(cmmnCdDetail)
    			//join 조건 시에는 delYn 조건을 명시해야 함 
    			.on( cmmnCdDetail.cdDetailVal1.eq(classWeek.classDayCd),
    				cmmnCdDetail.delYn.eq("N")
    			)
    			.where( eqClassSn(pk), 
    					cmmnCdDetail.cdNm.eq("DAY_7_CD")
    			)
    			.orderBy( cmmnCdDetail.cdDetailSeq.asc() )
    			.fetch();
    			
    			return results;
    }
	
	// -------------------------------- WHERE 검색 옵션 setting --------------------------------
	  
    private BooleanExpression eqClassSn( Long pk ) {
        return pk != null ? classWeek.classSn.eq(pk) : null;
    }
    
    private BooleanExpression eqOption( String schOption, String schCntn ) {
        // 검색 옵션  A : 아이디 , B : 이름 <- 예시 일뿐 이런식으로 커스텀하면 됨
        if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
//            if ( schOption.equals( "A" ) )
//                return classWeek.classWeekSn.contains( schCntn ); // LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else if ( schOption.equals( "B" ) )
//                return classWeek.classWeekNm.contains( schCntn ); // LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else return null;
            return null;            // (build한 이후에 해주세요. 안그럼 에러발생)  실제 구현시에는 해당부분지워주고 위에부분주석풀기
        } else {
            return null;
        }
    }
    
}
