package com.meta.ponkids.domain.cls.repository.impl;


import static com.meta.ponkids.domain.cls.entity.QClassInqry.classInqry;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.meta.ponkids.domain.cls.dto.ClassInqryListDto;
import com.meta.ponkids.domain.cls.dto.QClassInqryListDto;
import com.meta.ponkids.domain.cls.repository.custom.ClassInqryRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ClassInqryRepositoryImpl implements ClassInqryRepositoryCustom {
	
	private final JPAQueryFactory query;
	
	@Override
	public Page<ClassInqryListDto> getList( ClassInqryListDto listDto, Pageable pageable ) {
		
		// TODO 구현
		// (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회
        
		// TODO
        // (1) 결과list (results).
		List<ClassInqryListDto> results = query
				// select
                .select( new QClassInqryListDto(
                		classInqry.classInqrySn,
                		classInqry.classSn,
                		classInqry.step,
                		classInqry.parntsInqrySn,
                		classInqry.userSn,
                		classInqry.inqrySj,
                		classInqry.inqryCn,
                		classInqry.openYn,
                		classInqry.registerId,
                		Expressions.stringTemplate("to_char({0}, '{1s}')", classInqry.regDt, "YYYY-MM-DD")
//                		new CaseBuilder()
//                		.when( user.gender.eq("M")).then("남자")
//                		.when( user.gender.eq("F")).then("여자")
//                		.otherwise("")
//                		.as("gender"),
                		) )					
                .from( classInqry )
                // where
                .where(
				eqOption( listDto.getSchOption(), listDto.getSchCntn() ),
				eqClassSn( listDto.getClassSn() ) 
				)
                .orderBy( classInqry.classInqrySn.desc())
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
		
		
		// TODO
		// (2) count
        JPAQuery<Long> count = query.select( classInqry.count() )
                .from( classInqry )									
                .where(
                        eqOption( listDto.getSchOption(), listDto.getSchCntn() )
		);
                

		return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
		
	}
	
	
	// -------------------------------- WHERE 검색 옵션 setting --------------------------------
	  
    private BooleanExpression eqClassSn( Long pk ) {
        return pk != null ? classInqry.classSn.eq(pk) : null;
    }
	
	
    private BooleanExpression eqOption( String schOption, String schCntn ) {
        // 검색 옵션  A : 아이디 , B : 이름 <- 예시 일뿐 이런식으로 커스텀하면 됨
        if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
//            if ( schOption.equals( "A" ) )
//                return classInqry.classInqrySn.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else if ( schOption.equals( "B" ) )
//                return classInqry.classInqryNm.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else return null;
        	return null;			// TODO (build한 이후에 해주세요. 안그럼 에러발생)  실제 구현시에는 해당부분지워주고 위에부분주석풀기
        } else {
            return null;
        }
    }

}
