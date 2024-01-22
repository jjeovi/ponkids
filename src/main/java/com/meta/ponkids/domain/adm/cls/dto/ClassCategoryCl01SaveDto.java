package com.meta.ponkids.domain.adm.cls.dto;

import com.meta.ponkids.domain.adm.cls.entity.ClassCategoryCl01;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassCategoryCl01SaveDto {
    
    
    private Long clSn;            // 분류1 일련번호
    
    private String clNm;            // 분류1 이름
    
    private Long clSeq;            // 분류1 순번
    
    private String registerId;        // 등록자 id
    
    private String registerIp;        // 등록자 ip
    
    private String updusrId;            // 수정자 ID
    
    private String updusrIp;            // 수정자 IP
    
    @Builder
    public ClassCategoryCl01SaveDto( Long clSn, String clNm, Long clSeq, String registerId, String registerIp, String updusrId, String updusrIp ) {
        this.clSn = clSn;
        this.clNm = clNm;
        this.clSeq = clSeq;
        this.registerId = registerId;
        this.registerIp = registerIp;
        this.updusrId = updusrId;
        this.updusrIp = updusrIp;
    }
    
    // Dto to Entity 메소드 생성
    public ClassCategoryCl01 toEntity() {
        return ClassCategoryCl01.builder()
                .clSn( clSn )
                .clNm( clNm )
                .clSeq( clSeq )
                .registerId( registerId )
                .registerIp( registerIp )
                .updusrId( updusrId )
                .updusrIp( updusrIp )
                .build();
    }
}
