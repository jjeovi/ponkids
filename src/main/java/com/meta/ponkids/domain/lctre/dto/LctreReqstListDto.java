package com.meta.ponkids.domain.lctre.dto;

import com.meta.ponkids.domain.lctre.entity.LctreReqst;
import com.meta.ponkids.global.common.dto.CategoryDto;
import com.querydsl.core.annotations.QueryProjection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = false )
public class LctreReqstListDto  {
    
    private Long    lctreReqstSn;			// 수업 신청 일련번호
    
    private Long    classReqstSn;			// 클래스 신청 일련번호
    
    private Long    lctreSn;				// 수업 일련번호
    
    private Long    chldrnSn;				// 자녀 일련번호
    
    private String 		registerId;      		// 등록자 ID
    
    private String 		regDt;					// 등록일자
    
    private String 		schOption;   			// 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String 		schCntn;     			// 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
	
	private CategoryDto category;    			// 카테고리 검색 : 생성자에는 추가하지 않음!
    
    @Builder
    @QueryProjection
	public LctreReqstListDto(Long lctreReqstSn, Long classReqstSn, Long lctreSn, Long chldrnSn, String registerId,
			String regDt) {
		this.lctreReqstSn = lctreReqstSn;
		this.classReqstSn = classReqstSn;
		this.lctreSn = lctreSn;
		this.chldrnSn = chldrnSn;
		this.registerId = registerId;
		this.regDt = regDt;
	}
    
    // Entity to Dto 메소드는 DTO 내부에서 생성
    public LctreReqstListDto toDto( LctreReqst lctreReqst ) {
    	return LctreReqstListDto.builder()
    			.lctreReqstSn( lctreReqst.getLctreReqstSn())
    			.classReqstSn( lctreReqst.getClassReqstSn())
    			.lctreSn( lctreReqst.getLctreSn())
    			.chldrnSn( lctreReqst.getChldrnSn())
    			.build();
    }
    

    
}
