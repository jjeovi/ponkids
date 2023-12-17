package com.meta.ponkids.domain.cls.dto;

import com.meta.ponkids.global.common.dto.CategoryDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassListDto {
    
    private Long classSn;                // 클래스일련번호
    
    private String ctgryCd;              // 카테고리코드
    
    private String crseCd;               // 커리큘럼코드
    
    private String classSj;              // 클래스제목
    
    private String classSumry;           // 클래스요약
    
    private String classDc;              // 클래스설명
    
    private String classAmt;             // 클래스금액
    
    private String classDscntBfeAmt;     // 클래스할인전금액
    
    private String classPdSetYn;         // 클래스기간설정여부
    
    private String classBeginDt;         // 클래스시작일시
    
    private String classEndDt;           // 클래스종료일시
    
    private Long thumbAtchFileSn;      // 썸네일첨부파일일련번호
    
    private Long atchFileSn;           // 첨부파일일련번호
    
    private String classExpsrYn;         // 클래스표시여부
    
    private String schOption;   // 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String schCntn;     // 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
    
    private CategoryDto category;    // 카테고리 검색 : 생성자에는 추가하지 않음!
    
    @QueryProjection
    public ClassListDto( Long classSn, String ctgryCd, String crseCd, String classSj, String classSumry, String classDc, String classAmt, String classDscntBfeAmt, String classPdSetYn, String classBeginDt, String classEndDt, Long thumbAtchFileSn, Long atchFileSn, String classExpsrYn ) {
        this.classSn = classSn;
        this.ctgryCd = ctgryCd;
        this.crseCd = crseCd;
        this.classSj = classSj;
        this.classSumry = classSumry;
        this.classDc = classDc;
        this.classAmt = classAmt;
        this.classDscntBfeAmt = classDscntBfeAmt;
        this.classPdSetYn = classPdSetYn;
        this.classBeginDt = classBeginDt;
        this.classEndDt = classEndDt;
        this.thumbAtchFileSn = thumbAtchFileSn;
        this.atchFileSn = atchFileSn;
        this.classExpsrYn = classExpsrYn;
    }
}