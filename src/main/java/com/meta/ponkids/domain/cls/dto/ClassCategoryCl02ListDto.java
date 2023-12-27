package com.meta.ponkids.domain.cls.dto;

import com.meta.ponkids.domain.cls.entity.ClassCategoryCl02;
import com.meta.ponkids.global.common.dto.CategoryDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassCategoryCl02ListDto {
    
    private Long clSn;                // 분류2 일련번호
    
    private Long parntsClSn;        // 분류1 일련번호
    
    private String parntsClNm;        // 분류1 이름
    
    private String clNm;            // 분류2 이름
    
    private Long clSeq;                // 분류2 순번
    
    private String registerId;      // 등록자 ID
    
    private String regDt;            // 등록일자
    
    private String schOption;    // 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String schCntn;        // 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
    
    private CategoryDto category;   // 카테고리 검색 : 생성자에는 추가하지 않음!
    
    @Builder
    @QueryProjection
    public ClassCategoryCl02ListDto( Long clSn, Long parntsClSn, String parntsClNm, String clNm, Long clSeq, String registerId, String regDt ) {
        this.clSn = clSn;
        this.parntsClSn = parntsClSn;
        this.parntsClNm = parntsClNm;
        this.clNm = clNm;
        this.clSeq = clSeq;
        this.registerId = registerId;
        this.regDt = regDt;
    }
    
    // Dto to Entity 메소드 생성
    public ClassCategoryCl02 toEntity() {
        return ClassCategoryCl02.builder()
                .clSn( clSn )
                .parntsClSn( parntsClSn )
                .clNm( clNm )
                .clSeq( clSeq )
                .build();
    }
    
    // Entity to Dto 메소드는 DTO 내부에서 생성.
    public ClassCategoryCl02ListDto toDto( ClassCategoryCl02 classCategoryCl02 ) {
        return ClassCategoryCl02ListDto.builder()
                .clSn( classCategoryCl02.getClSn() )
                .parntsClSn( classCategoryCl02.getParntsClSn() )
                .clNm( classCategoryCl02.getClNm() )
                .clSeq( classCategoryCl02.getClSeq() )
                .regDt( classCategoryCl02.getRegDt().toString() )
                .build();
    }
    
}