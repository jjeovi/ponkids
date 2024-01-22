package com.meta.ponkids.domain.cls.dto;

import com.meta.ponkids.domain.cls.entity.ClassCategoryCl02;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassCategoryCl02ModDto {
    
    private Long clSn;                // 분류2 일련번호
    
    private Long parntsClSn;        // 분류1 일련번호
    
    private String clNm;            // 분류2 이름
    
    private Long clSeq;            // 분류2 순번
    
    private String updusrId;        // 수정자 ID
    
    private String updusrIp;        // 수정자 IP
    
    //builder 생성
    @Builder
    public ClassCategoryCl02ModDto( Long clSn, Long parntsClSn, String clNm, Long clSeq, String updusrId, String updusrIp ) {
        this.clSn = clSn;
        this.parntsClSn = parntsClSn;
        this.clNm = clNm;
        this.clSeq = clSeq;
        this.updusrId = updusrId;
        this.updusrIp = updusrIp;
    }
    
    // Dto to Entity 메소드 생성
    public ClassCategoryCl02 toEntity() {
        return ClassCategoryCl02.builder()
                .clSn( clSn )
                .parntsClSn( parntsClSn )
                .clNm( clNm )
                .clSeq( clSeq )
                .updusrId( updusrId )
                .updusrIp( updusrIp )
                .build();
    }
    
    // Entity to Dto 메소드는 DTO 내부에서 생성.
    public ClassCategoryCl02ModDto toDto( ClassCategoryCl02 classCategoryCl02 ) {
        return ClassCategoryCl02ModDto.builder()
                .clSn( classCategoryCl02.getClSn() )
                .parntsClSn( classCategoryCl02.getParntsClSn() )
                .clNm( classCategoryCl02.getClNm() )
                .clSeq( classCategoryCl02.getClSeq() )
                .updusrId( classCategoryCl02.getUpdusrId() )
                .updusrIp( classCategoryCl02.getUpdusrIp() )
                .build();
    }
    
}
