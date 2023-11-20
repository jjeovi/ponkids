package com.meta.ponkids.domain.user.repository.impl;

import com.meta.ponkids.domain.user.dto.QUserListDto;
import com.meta.ponkids.domain.user.dto.UserListDto;
import com.meta.ponkids.domain.user.repository.custom.UserRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.meta.ponkids.domain.user.entity.QUser.user;

/**
 * className      : UserRepositoryImpl
 * author         : jjeoV
 * date           : 2023-11-19
 * description    : class of 회원 RepositoryImpl
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-11-19        jjeoV             최초 생성
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory query;
    
    @Override
    public Page<UserListDto> getList( UserListDto userListDto, Pageable pageable ) {
        
        // (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회
        
        // (1) 결과list (results).
        List<UserListDto> results = query
                // select
                .select( new QUserListDto(
                		user.userSn,
                		user.userId,
                		user.userNm,
                		new CaseBuilder()
                		.when( user.gender.eq("M")).then("남자")
                		.when( user.gender.eq("F")).then("여자")
                		.otherwise("")
                		.as("gender"),
                		user.brdtDate,
                		user.telNo,
                		new CaseBuilder()
                		.when(user.resideArea.eq("")).then("지역없음")
                		.otherwise( user.resideArea).as("resideArea"),
                		new CaseBuilder()
                		.when( user.mngrYn.eq("Y")).then("관리자")
                		.when( user.mngrYn.eq("N")).then("사용자")
                		.otherwise("").as("mngrYn"),
                		user.mngrConfmYn)
                ).from( user )
                // where
                .where(
                        eqGender( userListDto.getGender() ),
                        eqMngrYn( userListDto.getMngrYn() ),
                        eqMngrConfmYn( userListDto.getMngrConfmYn() ),
                        eqOption( userListDto.getSchOption(), userListDto.getSchCntn() )
                )
                // order by
                .orderBy( user.userSn.desc() )
                // paging
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
        
        // (2) count
        JPAQuery<Long> count = query.select( user.count() )
                .from( user )
                .where(
                        eqGender( userListDto.getGender() ),
                        eqMngrYn( userListDto.getMngrYn() ),
                        eqMngrConfmYn( userListDto.getMngrConfmYn() ),
                        eqOption( userListDto.getSchOption(), userListDto.getSchCntn() ) );
        
        return PageableExecutionUtils.getPage( results, pageable, count::fetchOne );
    }
    
    // -------------------------------- WHERE 검색 옵션 setting --------------------------------
    private BooleanExpression eqGender( String gender ) {
        return StringUtils.hasText( gender ) ? user.gender.eq( gender ) : null;
    }
    
    private BooleanExpression eqMngrYn( String mngrYn ) {
        return StringUtils.hasText( mngrYn ) ? user.mngrYn.eq( mngrYn ) : null;
    }
    
    private BooleanExpression eqMngrConfmYn( String mngrConfmYn ) {
        return StringUtils.hasText( mngrConfmYn ) ? user.mngrConfmYn.eq( mngrConfmYn ) : null;
    }
    
    private BooleanExpression eqOption( String schOption, String schCntn ) {
        // 검색 옵션  A : 아이디 , B : 이름
        if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
            if ( schOption.equals( "A" ) )
                return user.userId.contains( schCntn ); // LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
            else if ( schOption.equals( "B" ) )
                return user.userNm.contains( schCntn ); // LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
            else return null;
        } else {
            return null;
        }
    }
}