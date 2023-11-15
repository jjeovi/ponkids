package com.meta.ponkids.domain.user.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.meta.ponkids.domain.user.dto.QUserListDto;
import com.meta.ponkids.domain.user.dto.UserListDto;
import com.meta.ponkids.domain.user.entity.User;
import com.meta.ponkids.domain.user.repository.custom.UserRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;


import static com.meta.ponkids.domain.user.entity.QUser.user;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<UserListDto> findList(UserListDto userListDto) {
		
		return queryFactory.select(new QUserListDto(user.userId))
				.from(user)
				.where(user.delYn.eq("N"))
				.fetch();
//		return queryFactory.
//		// TODO Auto-generated method stub
//		return null;
	}
}
