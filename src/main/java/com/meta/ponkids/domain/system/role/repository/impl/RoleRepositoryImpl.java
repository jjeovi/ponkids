package com.meta.ponkids.domain.system.role.repository.impl;


import com.meta.ponkids.domain.system.role.repository.custom.RoleRepositoryCustom;
import com.meta.ponkids.global.common.dto.CategoryDto;
import com.meta.ponkids.global.common.dto.QCategoryDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.meta.ponkids.domain.system.role.entity.QRole.role;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepositoryCustom {
    
    private final JPAQueryFactory query;
    
    @Override
    public List<CategoryDto> getCateList() {
        
        List<CategoryDto> results = query
                // select
                .select( new QCategoryDto(
                        role.roleSn.as( "categorySn" ),
                        role.roleDc.as( "categoryNm" ) )
                ).from( role )
                // order by
                .orderBy( role.roleSn.asc() )
                .fetch();
        
        return results;
    }
    
    
    @Override
    public List<CategoryDto> getCateList( Long roleSn ) {
        
        List<CategoryDto> results = query
                // select
                .select( new QCategoryDto(
                        role.roleSn.as( "categorySn" ),
                        role.roleDc.as( "categoryNm" ) )
                ).from( role )
                .where( eqRoleSn( roleSn ) )
                // order by
                .orderBy( role.roleSn.asc() )
                .fetch();
        
        return results;
    }
    
    
    // -------------------------------- WHERE 검색 옵션 setting --------------------------------
    private BooleanExpression eqRoleSn( Long roleSn ) {
        return roleSn != null ? role.roleSn.eq( roleSn ) : null;
    }
    
}
