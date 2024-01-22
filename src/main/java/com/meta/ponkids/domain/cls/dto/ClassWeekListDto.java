package com.meta.ponkids.domain.cls.dto;

import com.meta.ponkids.global.common.dto.CategoryDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassWeekListDto {
    
    // 수업조회 > 분류 뿌려줄때 groupBy를 하면서,
    // tb_class_week 의 class_week_sn, class_sn 컬럼을 쿼리에서 제거
    private Long    classWeekSn;    // 클래스 요일 일련번호
    
    private Long 	classSn;        // 클래스 일련번호
    
    private Long    cdDetailSn;     // 코드 상세 일련번호 (tb_cmmn_cd_detail 일련번호 사용
    
    private String 	classDayCd;     // 클래스 요일 코드
    
    private String 	classDayNm;     // 클래스 요일 명
    
    private Long 	classDaySeq;    // 클래스 요일 순서
    
    private String 	schOption;      // 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String 	schCntn;        // 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
    
    private CategoryDto category;    // 카테고리 검색 : 생성자에는 추가하지 않음!
    
    @Builder
    @QueryProjection
    public ClassWeekListDto( Long cdDetailSn, String classDayCd, String classDayNm, Long classDaySeq) {
        this.cdDetailSn = cdDetailSn;
        this.classDayCd = classDayCd;
        this.classDayNm = classDayNm;
        this.classDaySeq = classDaySeq;
    }
    
//    public ClassWeekListDto toDto( ClassWeek classWeek ) {
//        return ClassWeekListDto.builder()
//                .cdDetailSn( classWeek.getClassWeekSn())
//                .classSn( classWeek.getClassSn() )
//                .classDayCd( classWeek.getClassDayCd() )
//                .build();
//    }
    
}