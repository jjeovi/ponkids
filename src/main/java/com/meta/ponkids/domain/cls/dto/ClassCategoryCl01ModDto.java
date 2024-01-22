package com.meta.ponkids.domain.cls.dto;

import com.meta.ponkids.domain.cls.entity.ClassCategoryCl01;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassCategoryCl01ModDto {
    
    private Long clSn;            // 분류1 일련번호
    
    private String clNm;            // 분류1 이름
    
    private Long clSeq;            // 분류1 순번
    
    private String updusrId;        // 수정자 ID
    
    private String updusrIp;        // 수정자 IP
    
    //builder 생성
    @Builder
    public ClassCategoryCl01ModDto( Long clSn, String clNm, Long clSeq, String updusrId, String updusrIp ) {
        this.clSn = clSn;
        this.clNm = clNm;
        this.clSeq = clSeq;
        this.updusrId = updusrId;
        this.updusrIp = updusrIp;
    }
    
    // Dto to Entity 메소드 생성
    public ClassCategoryCl01 toEntity() {
        return ClassCategoryCl01.builder()
                .clSn( clSn )
                .clNm( clNm )
                .clSeq( clSeq )
                .updusrId( updusrId )
                .updusrIp( updusrIp )
                .build();
    }
    
    // Entity to Dto 메소드는 DTO 내부에서 생성.
    public ClassCategoryCl01ModDto toDto( ClassCategoryCl01 classCategoryCl01 ) {
        return ClassCategoryCl01ModDto.builder()
                .clSn( classCategoryCl01.getClSn() )
                .clNm( classCategoryCl01.getClNm() )
                .clSeq( classCategoryCl01.getClSeq() )
                .updusrId( classCategoryCl01.getUpdusrId() )
                .updusrIp( classCategoryCl01.getUpdusrIp() )
                .build();
    }
    
}
