package com.meta.ponkids.global.common.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDto {
    
    private Long lv1Sn;
    
    private String lv1Nm;
    
    private String lv1Remark;
    
    private Long lv2Sn;
    
    private String lv2Nm;
    
    private String lv2Remark;
    
    private Long lv3Sn;
    
    private String lv3Nm;
    
    private String lv3Remark;
    
    private Long lv4Sn;
    
    private String lv4Nm;
    
    private String lv4Remark;
    
    private Long lv5Sn;
    
    private String lv5Nm;
    
    private String lv5Remark;
    
    private Long categorySn;
    
    private String categoryNm;
    
    @QueryProjection
    public CategoryDto( Long categorySn, String categoryNm ) {
        this.categorySn = categorySn;
        this.categoryNm = categoryNm;
    }
    //	@QueryProjection
//	public CategoryListDto (int categoryIntSn, String categoryNm) {
//		this.categoryIntSn = categoryIntSn;
//		this.categoryNm = categoryNm;
//	}

}