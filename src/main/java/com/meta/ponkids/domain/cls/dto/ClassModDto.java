package com.meta.ponkids.domain.cls.dto;

import com.meta.ponkids.domain.cls.entity.Class;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = false )
public class ClassModDto extends ClassDto {
    
    private Long 	classSn;            	// 클래스일련번호
    
    private Long 	ctgrySn;                // 카테고리코드
    
    private Long	crseSn;                 // 커리큘럼코드
    
    private String 	classSj;                // 클래스제목
    
    private String 	classSumry;             // 클래스요약
    
    private String 	classDc;                // 클래스설명
    
    private Long 	classAmt;               // 클래스금액
    
    private Long 	classDscntBfeAmt;       // 클래스할인전금액
    
    private String 	classTrgtCd;           	// 클래스 대상 코드 
    
    private String 	classPdSetYn;           // 클래스기간설정여부
    
    private String 	classBeginDt;           // 클래스시작일시
    
    private String 	classEndDt;             // 클래스종료일시
    
    private Long 	thumbAtchFileSn;        // 썸네일첨부파일일련번호
    
    private Long 	thumbAtchFileSnOri;     // 썸네일첨부파일일련번호(비교용)
    
    private Long 	atchFileSn;             // 첨부파일일련번호
    
    private String 	classExpsrYn;           // 클래스표시여부
    
    private String 	updusrId;               // 수정자 ID
    
    private String 	updusrIp;               // 수정자 IP
    
    //builder 생성
    @Builder
    public ClassModDto( Long classSn, Long ctgrySn, Long crseSn, String classSj, String classSumry, String classDc, Long classAmt, Long classDscntBfeAmt, String classTrgtCd, String classPdSetYn, String classBeginDt, String classEndDt, Long thumbAtchFileSn, Long atchFileSn, String classExpsrYn, String updusrId, String updusrIp ) {
        this.classSn = classSn;
        this.ctgrySn = ctgrySn;
        this.crseSn = crseSn;
        this.classSj = classSj;
        this.classSumry = classSumry;
        this.classDc = classDc;
        this.classAmt = classAmt;
        this.classDscntBfeAmt = classDscntBfeAmt;
        this.classTrgtCd = classTrgtCd;
        this.classPdSetYn = classPdSetYn;
        this.classBeginDt = classBeginDt;
        this.classEndDt = classEndDt;
        this.thumbAtchFileSn = thumbAtchFileSn;
        this.atchFileSn = atchFileSn;
        this.classExpsrYn = classExpsrYn;
        this.updusrId = updusrId;
        this.updusrIp = updusrIp;
    }
    
    // Dto to Entity 메소드 생성
    public Class toEntity() {
        return Class.builder()
                .classSn( classSn )
                .ctgrySn( ctgrySn )
                .crseSn( crseSn )
                .classSj( classSj )
                .classSumry( classSumry )
                .classDc( classDc )
                .classAmt( classAmt )
                .classDscntBfeAmt( classDscntBfeAmt )
                .classTrgtCd( classTrgtCd )
                .classPdSetYn( classPdSetYn )
                .classBeginDt( classBeginDt )
                .classEndDt( classEndDt )
                .thumbAtchFileSn( thumbAtchFileSn )
                .atchFileSn( atchFileSn )
                .classExpsrYn( classExpsrYn )
                .updusrId( updusrId )
                .updusrIp( updusrIp )
                .build();
    }
    
    // Entity to Dto 메소드는 DTO 내부에서 생성.
    public ClassModDto toDto( Class clas ) {
        return ClassModDto.builder()
                .classSn( clas.getClassSn() )
                .ctgrySn( clas.getCtgrySn() )
                .crseSn( clas.getCrseSn() )
                .classSj( clas.getClassSj() )
                .classSumry( clas.getClassSumry() )
                .classDc( clas.getClassDc() )
                .classAmt( clas.getClassAmt() )
                .classDscntBfeAmt( clas.getClassDscntBfeAmt() )
                .classTrgtCd( clas.getClassTrgtCd() )
                .classPdSetYn( clas.getClassPdSetYn() )
                .classBeginDt( clas.getClassBeginDt() )
                .classEndDt( clas.getClassEndDt() )
                .thumbAtchFileSn( clas.getThumbAtchFileSn() )
                .atchFileSn( clas.getAtchFileSn() )
                .classExpsrYn( clas.getClassExpsrYn() )
                .updusrId( clas.getUpdusrId() )
                .updusrIp( clas.getUpdusrIp() )
                .build();
    }
    
}
