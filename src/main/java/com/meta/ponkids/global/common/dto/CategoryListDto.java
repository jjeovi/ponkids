package com.meta.ponkids.global.common.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryListDto {
	
	private Long categorySn;
	
//	private int categoryIntSn;
	
	private String categoryNm;
	
	
	@QueryProjection
	public CategoryListDto (Long categorySn, String categoryNm) {
		this.categorySn = categorySn;
		this.categoryNm = categoryNm;
	}
	
//	@QueryProjection
//	public CategoryListDto (int categoryIntSn, String categoryNm) {
//		this.categoryIntSn = categoryIntSn;
//		this.categoryNm = categoryNm;
//	}

}