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
    
    private Long classSn;           // 클래스 일련번호
    
    private String classDayCd;      // 클래스 요일 코드
    
    private String registerId;      // 등록자 ID
    
    private String regDt;            // 등록일자
    
    private String schOption;    // 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String schCntn;        // 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
    
    @Builder
    @QueryProjection
    public ClassWeekListDto( Long classWeekSn, Long classSn, String classDayCd, String registerId, String regDt ) {
        this.classWeekSn = classWeekSn;
        this.classSn = classSn;
        this.classDayCd = classDayCd;
        this.registerId = registerId;
        this.regDt = regDt;
    }
    
    public ClassWeekListDto toDto( ClassWeek classWeek ) {
        return ClassWeekListDto.builder()
                .classWeekSn( classWeek.getClassWeekSn())
                .classSn( classWeek.getClassSn() )
                .classDayCd( classWeek.getClassDayCd() )
                .build();
    }
    
}