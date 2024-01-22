package com.meta.ponkids.domain.adm.cls.dto;

import com.meta.ponkids.domain.adm.cls.entity.ClassCategoryCl02;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassCategoryCl02SaveDto {
    
    private Long clSn;                // 분류2 일련번호
    
    private Long parntsClSn;        // 분류1 일련번호
    
    private String clNm;            // 분류2 이름
    
    private Long clSeq;                // 분류2 순번
    
    private String registerId;        // 등록자 id
    
    private String registerIp;        // 등록자 ip
    
    private String updusrId;            // 수정자 ID
    
    private String updusrIp;            // 수정자 IP
    
    @Builder
    public ClassCategoryCl02SaveDto( Long clSn, Long parntsClSn, String clNm, Long clSeq, String registerId, String registerIp, String updusrId, String updusrIp ) {
        this.clSn = clSn;
        this.parntsClSn = parntsClSn;
        this.clNm = clNm;
        this.clSeq = clSeq;
        this.registerId = registerId;
        this.registerIp = registerIp;
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
                .registerId( registerId )
                .registerIp( registerIp )
                .updusrId( updusrId )
                .updusrIp( updusrIp )
                .build();
    }
}
