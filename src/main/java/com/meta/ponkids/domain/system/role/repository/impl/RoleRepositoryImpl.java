package com.meta.ponkids.domain.system.role.repository.impl;


import static com.meta.ponkids.domain.system.role.entity.QRole.role;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.meta.ponkids.domain.system.role.repository.custom.RoleRepositoryCustom;
import com.meta.ponkids.global.common.dto.CategoryListDto;
import com.meta.ponkids.global.common.dto.QCategoryListDto;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepositoryCustom {

	private final JPAQueryFactory query;
	
	@Override
	public List<CategoryListDto> getList() {
		
		List<CategoryListDto> results = query
                // select
                .select( new QCategoryListDto(
                		role.roleSn.as("categorySn"),
                		role.roleDc.as("categoryNm"))
                ).from( role )
                // order by
                .orderBy( role.roleSn.asc() )
                .fetch();
		
		return results;
	}

}
