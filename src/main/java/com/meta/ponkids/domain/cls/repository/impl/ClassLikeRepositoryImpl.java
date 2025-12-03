package com.meta.ponkids.domain.cls.repository.impl;


import com.meta.ponkids.domain.cls.repository.custom.ClassLikeRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.meta.ponkids.domain.cls.entity.QClassLike.classLike;

@Repository
@RequiredArgsConstructor
public class ClassLikeRepositoryImpl implements ClassLikeRepositoryCustom {
	
	private final JPAQueryFactory query;
	
	@Override
	public int countByUserSn( Long userSn ) {
		Long count = query
				.select( classLike.count() )
				.from( classLike )
				.where(
						classLike.userSn.eq( userSn )
				)
				.fetchOne();
		
		return count != null ? count.intValue() : 0;
	}
	
	// -------------------------------- WHERE 검색 옵션 setting --------------------------------
	

}
