package com.meta.ponkids.domain.cls.dto;

import com.meta.ponkids.domain.cls.entity.ClassWeek;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassWeekListDto {
    
    private Long classWeekSn;        // 클래스 요일 일련번호
    
    private Long 	classSn;           // 클래스 일련번호
    
    private String 	classDayCd;      // 클래스 요일 코드
    
    private String 	classDayNm;      // 클래스 요일 명
    
    private Long 	classDaySeq;      // 클래스 요일 순서
    
    private String 	schOption;    // 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String 	schCntn;        // 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
    
    @Builder
    @QueryProjection
    public ClassWeekListDto( Long classWeekSn, Long classSn, String classDayCd, String classDayNm, Long classDaySeq) {
        this.classWeekSn = classWeekSn;
        this.classSn = classSn;
        this.classDayCd = classDayCd;
        this.classDayNm = classDayNm;
        this.classDaySeq = classDaySeq;
    }
    
    public ClassWeekListDto toDto( ClassWeek classWeek ) {
        return ClassWeekListDto.builder()
                .classWeekSn( classWeek.getClassWeekSn())
                .classSn( classWeek.getClassSn() )
                .classDayCd( classWeek.getClassDayCd() )
                .build();
    }
    
}