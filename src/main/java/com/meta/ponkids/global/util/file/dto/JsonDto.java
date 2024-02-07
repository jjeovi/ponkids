package com.meta.ponkids.global.util.file.dto;

import com.meta.ponkids.global.common.dto.CategoryDto;

import lombok.Getter;

@Getter
public class JsonDto {

	 private Long cdDetailSn;
	    
	    private String cdNm;
	    
	    private String cdDc;
	    
	    private Long cdDetailSeq;
	    
	    private String cdDetailNm;
	    
	    private String cdDetailDc;
	    
	    private String cdDetailVal1;
	    
	    private String cdDetailVal2;
	    
	    private String cdDetailVal3;
	    
	    private String cdDetailVal4;
	    
	    private String cdDetailVal5;
	    
	    private String useYn;
	    
	    private String regDt;            // 등록일자
	    
	    private String schOption;   // 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
	    
	    private String schCntn;     // 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
	    
	    private CategoryDto category;   // 카테고리 검색 : 생성자에는 추가하지 않음!
	    
	    
}
