package com.meta.ponkids.domain.adm.cls.dto;

import com.meta.ponkids.global.common.dto.CategoryDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ClassListDto {
    
    private Long classSn;                // 클래스일련번호
    
    private Long ctgrySn;              // 카테고리코드
    
    private String ctgryNm;              // 카테고리명
    
    private Long crseSn;               // 커리큘럼코드
    
    private String crseNm;               // 커리큘럼명
    
    private String classSj;              // 클래스제목
    
    private String classSumry;           // 클래스요약
    
    private String classDc;              // 클래스설명
    
    private Long classAmt;               // 클래스금액
    
    private Long classDscntBfeAmt;     // 클래스할인전금액
    
    private String classPdSetYn;         // 클래스기간설정여부
    
    private String classBeginDt;         // 클래스시작일시
    
    private String classEndDt;           // 클래스종료일시
    
    private Long thumbAtchFileSn;        // 썸네일첨부파일일련번호
    
    private Long atchFileSn;            // 첨부파일일련번호
    
    private String classExpsrYn;         // 클래스표시여부
    
    private String classExpsrPeriod;     // 클래스표시기간
    
    private String registerId;      // 등록자 ID
    
    private String regDt;    // 등록일자
    
    private String schOption;   // 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String schCntn;     // 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
    
    private CategoryDto category;    // 카테고리 검색 : 생성자에는 추가하지 않음!
    
    @QueryProjection
    public ClassListDto( Long classSn, Long ctgrySn, String ctgryNm, Long crseSn, String crseNm, String classSj, String classSumry, String classDc, Long classAmt, Long classDscntBfeAmt, String classPdSetYn, String classBeginDt, String classEndDt, Long thumbAtchFileSn, Long atchFileSn, String classExpsrYn, String classExpsrPeriod, String registerId, String regDt ) {
        this.classSn = classSn;
        this.ctgrySn = ctgrySn;
        this.ctgryNm = ctgryNm;
        this.crseSn = crseSn;
        this.crseNm = crseNm;
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
        this.classExpsrPeriod = classExpsrPeriod;
        this.registerId = registerId;
        this.regDt = regDt;
    }
}