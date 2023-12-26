package com.meta.ponkids.domain.cls.dto;

import java.time.LocalDateTime;

import com.meta.ponkids.domain.cls.entity.ClassCategoryCl01;
import com.meta.ponkids.global.common.dto.CategoryDto;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassCategoryCl01ListDto{
    
	private Long clSn;			// 분류1 일련번호
	
	private String clNm;		// 분류1 이름
	
	private Long clSeq;			// 분류1 순번
	
	private Long childCateCnt;		// 하위항목개수
	
    private String registerId;      // 등록자 ID
    
    private LocalDateTime regDt;	// 등록일자
    
    private String schOption;   	// 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String schCntn;     	// 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
    
    private CategoryDto category;   // 카테고리 검색 : 생성자에는 추가하지 않음!

    @Builder
    @QueryProjection
	public ClassCategoryCl01ListDto(Long clSn, String clNm, Long clSeq, Long childCateCnt, String registerId, LocalDateTime regDt) {
		this.clSn = clSn;
		this.clNm = clNm;
		this.clSeq = clSeq;
		this.childCateCnt = childCateCnt;
		this.registerId = registerId;
		this.regDt = regDt;
	}
    
    
    // Dto to Entity 메소드 생성
    public ClassCategoryCl01 toEntity() {
        return ClassCategoryCl01.builder()
                .clSn( clSn )
                .clNm( clNm )
                .clSeq( clSeq )
                .registerId( registerId )
//                .regDt( regDt )
                .build();
    }
    
    // Entity to Dto 메소드는 DTO 내부에서 생성.
    public ClassCategoryCl01ListDto toDto( ClassCategoryCl01 classCategoryCl01 ) {
        return ClassCategoryCl01ListDto.builder()
                .clSn( classCategoryCl01.getClSn() )
                .clNm( classCategoryCl01.getClNm() )
                .clSeq( classCategoryCl01.getClSeq() )
                .registerId( classCategoryCl01.getRegisterId() )
//                .regDt( classCategoryCl01.getRegDt() )
                .build();
    }
    
    
}