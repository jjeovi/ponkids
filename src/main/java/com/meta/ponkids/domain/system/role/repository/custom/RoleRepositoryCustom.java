package com.meta.ponkids.domain.system.role.repository.custom;

import java.util.List;

import com.meta.ponkids.global.common.dto.CategoryDto;

public interface RoleRepositoryCustom {
	
	// 카테고리 뿌려주는 리스트 구현
	List<CategoryDto> getCateList();
	
	List<CategoryDto> getCateList(Long roleSn);
	
}


