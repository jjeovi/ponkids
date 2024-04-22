package com.meta.ponkids.domain.user.repository.impl;

import com.meta.ponkids.domain.user.dto.QUserChldrnListDto;
import com.meta.ponkids.domain.user.dto.UserChldrnListDto;
import com.meta.ponkids.domain.user.dto.UserChldrnModDto;
import com.meta.ponkids.domain.user.repository.custom.UserChldrnRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.meta.ponkids.domain.user.entity.QUserChldrn.userChldrn;


/**
 * className      : UserChldrnRepositoryImpl
 * author         : jjeoV
 * date           : 2023-11-20
 * description    : class of 자녀 RepositoryImpl
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-11-19        jjeoV             최초 생성
 */
@Repository
@RequiredArgsConstructor
public class UserChldrnRepositoryImpl implements UserChldrnRepositoryCustom {
    private final JPAQueryFactory query;
    
    @Override
    public List<UserChldrnListDto> getListByUserSn( Long userSn ) {
        
        List<UserChldrnListDto> results = query
                .select( new QUserChldrnListDto(
                				userChldrn.chldrnSn,
                                userChldrn.userSn,
                                userChldrn.userChldrnSeq,
                                userChldrn.chldrnNm,
                                new CaseBuilder()
                                        .when( userChldrn.chldrnGender.eq( "M" ) ).then( "남자" )
                                        .when( userChldrn.chldrnGender.eq( "F" ) ).then( "여자" )
                                        .otherwise( "" ).as( "chldrnGender" ),
                                userChldrn.chldrnBrdtDate,
                                userChldrn.chldrnEmail,
                                userChldrn.chldrnTelNo,
                                userChldrn.atchFileSn,
                                userChldrn.registerId,
                                userChldrn.registerIp
                        )
                
                )
                .from( userChldrn )
                .where(
                        eqUserSn( userSn )
                )
                .orderBy( userChldrn.userChldrnSeq.asc() )
                .fetch();
        
        
        return results;
    }
    

	@Override
	public UserChldrnListDto getByUserSnAndUserChldrnSeq( Long userSn, Long userChldrnSeq ) {
		
		return query
                .select( new QUserChldrnListDto(
                				userChldrn.chldrnSn,
                                userChldrn.userSn,
                                userChldrn.userChldrnSeq,
                                userChldrn.chldrnNm,
                                new CaseBuilder()
                                        .when( userChldrn.chldrnGender.eq( "M" ) ).then( "남자" )
                                        .when( userChldrn.chldrnGender.eq( "F" ) ).then( "여자" )
                                        .otherwise( "" ).as( "chldrnGender" ),
                                userChldrn.chldrnBrdtDate,
                                userChldrn.chldrnEmail,
                                userChldrn.chldrnTelNo,
                                userChldrn.atchFileSn,
                                userChldrn.registerId,
                                userChldrn.registerIp
                        )
                
                )
                .from( userChldrn )
                .where(
                        eqUserSn( userSn ),
                        userChldrn.userChldrnSeq.eq( userChldrnSeq )
                )
                .orderBy( userChldrn.userChldrnSeq.asc() )
                .fetchFirst();
	}
    
    
    private BooleanExpression eqUserSn( Long userSn ) {
        return userSn != null ? userChldrn.userSn.eq( userSn ) : null;
    }


    
    
}

