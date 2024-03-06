package com.meta.ponkids.domain.cls.repository.impl;


import com.meta.ponkids.domain.cls.dto.ClassDetailOptnListDto;
import com.meta.ponkids.domain.cls.dto.QClassDetailOptnListDto;
import com.meta.ponkids.domain.cls.repository.custom.ClassDetailOptnRepositoryCustom;
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

import static com.meta.ponkids.domain.cls.entity.QClassDetailOptn.classDetailOptn;

@Repository
@RequiredArgsConstructor
public class ClassDetailOptnRepositoryImpl implements ClassDetailOptnRepositoryCustom {
	
	private final JPAQueryFactory query;
	
	@Override
	public Page<ClassDetailOptnListDto> getList( ClassDetailOptnListDto listDto, Pageable pageable ) {
		
		// (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회
        
		
        // (1) 결과list (results).
		List<ClassDetailOptnListDto> results = query
				// select
                .select( new QClassDetailOptnListDto(
                		classDetailOptn.classDetailOptnSn,
                		classDetailOptn.classDetailSn,
                		classDetailOptn.classDetailOptnSeq,
                		classDetailOptn.classsDetailOptnCn,
                		classDetailOptn.registerId,
                		Expressions.stringTemplate("to_char({0}, '{1s}')", classDetailOptn.regDt, "YYYY-MM-DD HH24:MI:SS")
//                		new CaseBuilder()
//                		.when( user.gender.eq("M")).then("남자")
//                		.when( user.gender.eq("F")).then("여자")
//                		.otherwise("")
//                		.as("gender"),
                		) )					
                .from( classDetailOptn )
                // where
                .where(
                		eqOption( listDto.getSchOption(), listDto.getSchCntn() )
                		)
//                .orderBy( classDetailOptn.classDetailOptnSn.desc())
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
		
		// (2) count
        JPAQuery<Long> count = query.select( classDetailOptn.count() )
                .from( classDetailOptn )									
                .where(
                        eqOption( listDto.getSchOption(), listDto.getSchCntn() )
                       );
                
		
		return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
		
	}
	
    private BooleanExpression eqOption( String schOption, String schCntn ) {
        // 검색 옵션  A : 아이디 , B : 이름 <- 예시 일뿐 이런식으로 커스텀하면 됨
        if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
//            if ( schOption.equals( "A" ) )
//                return classDetailOptn.classDetailOptnSn.contains( schCntn ); // LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else if ( schOption.equals( "B" ) )
//                return classDetailOptn.classDetailOptnNm.contains( schCntn ); // LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else return null;
        	return null;			// (build한 이후에 해주세요. 안그럼 에러발생)  실제 구현시에는 해당부분지워주고 위에부분주석풀기
        } else {
            return null;
        }
    }

}
