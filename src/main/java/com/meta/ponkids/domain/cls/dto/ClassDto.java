package com.meta.ponkids.domain.cls.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ClassDto {
    
    private Long classSn;                   // 클래스일련번호
    
    private String ctgryCd;                 // 카테고리코드
    
    private String crseCd;                  // 커리큘럼코드
    
    private String classSj;                 // 클래스제목
    
    private String classSumry;              // 클래스요약
    
    private String classDc;                 // 클래스설명
    
    private Long classAmt;                // 클래스금액
    
    private Long classDscntBfeAmt;        // 클래스할인전금액
    
    private String classPdSetYn;            // 클래스기간설정여부
    
    private String classBeginDt;            // 클래스시작일시
    
    private String classEndDt;              // 클래스종료일시
    
    private Long thumbAtchFileSn;           // 썸네일첨부파일일련번호
    
    private Long atchFileSn;                // 첨부파일일련번호
    
    private String classExpsrYn;            // 클래스표시여부
    
    private String[] classWeek;            // 요일
    
    private String registerId;              // 등록자 id
    
    private String registerIp;              // 등록자 ip
    
    private String updusrId;                // 수정자 ID
    
    private String updusrIp;                // 수정자 IP

}
