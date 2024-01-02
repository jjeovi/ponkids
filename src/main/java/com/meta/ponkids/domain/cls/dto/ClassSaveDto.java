package com.meta.ponkids.domain.cls.dto;

import com.meta.ponkids.domain.cls.entity.Class;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ClassSaveDto {
    
    private Long classSn;                   // 클래스일련번호
    
    private String ctgryCd;                 // 카테고리코드
    
    private String crseCd;                  // 커리큘럼코드
    
    private String classSj;                 // 클래스제목
    
    private String classSumry;              // 클래스요약
    
    private String classDc;                 // 클래스설명
    
    private String classAmt;                // 클래스금액
    
    private String classDscntBfeAmt;        // 클래스할인전금액
    
    private String classPdSetYn;            // 클래스기간설정여부
    
    private String classBeginDt;            // 클래스시작일시
    
    private String classEndDt;              // 클래스종료일시
    
    private Long thumbAtchFileSn;           // 썸네일첨부파일일련번호
    
    private Long atchFileSn;                // 첨부파일일련번호
    
    private String classExpsrYn;            // 클래스표시여부
    
    private String[] classWeek;   			// 요일
    
    private String registerId;              // 등록자 id
    
    private String registerIp;              // 등록자 ip
    
    private String updusrId;                // 수정자 ID
    
    private String updusrIp;                // 수정자 IP
    
    @Builder
    public ClassSaveDto( Long classSn, String ctgryCd, String crseCd, String classSj, String classSumry, String classDc, String classAmt, String classDscntBfeAmt, String classPdSetYn, String classBeginDt, String classEndDt, Long thumbAtchFileSn, Long atchFileSn, String classExpsrYn, String registerId, String registerIp, String updusrId, String updusrIp ) {
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
        this.registerId = registerId;
        this.registerIp = registerIp;
        this.updusrId = updusrId;
        this.updusrIp = updusrIp;
    }
    
    // Dto to Entity 메소드 생성
    public Class toEntity() {
        return Class.builder()
                .classSn( classSn )
                .ctgryCd( ctgryCd )
                .crseCd( crseCd )
                .classSj( classSj )
                .classSumry( classSumry )
                .classDc( classDc )
                .classAmt( classAmt )
                .classDscntBfeAmt( classDscntBfeAmt )
                .classPdSetYn( classPdSetYn )
                .classBeginDt( classBeginDt )
                .classEndDt( classEndDt )
                .thumbAtchFileSn( thumbAtchFileSn )
                .atchFileSn( atchFileSn )
                .classExpsrYn( classExpsrYn )
                .registerId( registerId )
                .registerIp( registerIp )
                .updusrId( updusrId )
                .updusrIp( updusrIp )
                .build();
    }
}
