package com.meta.ponkids.domain.system.cmmnCd.dto;

import com.meta.ponkids.domain.system.cmmnCd.entity.CmmnCd;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODO Dto 항목 setting
@Data
@NoArgsConstructor
public class CmmnCdSaveDto {
    
    private Long cdSn;
    
    private String registerId;      // 등록자 id
    
    private String registerIp;      // 등록자 ip
    
    private String updusrId;        // 수정자 ID
    
    private String updusrIp;        // 수정자 IP
    
    // TODO 생성자();
    @Builder
    public CmmnCdSaveDto( Long cdSn, String registerId, String registerIp, String updusrId, String updusrIp ) {
        this.cdSn = cdSn;
        this.registerId = registerId;
        this.registerIp = registerIp;
        this.updusrId = updusrId;
        this.updusrIp = updusrIp;
    }
    
    // TODO toEntity();
    // Dto to Entity 메소드 생성
    public CmmnCd toEntity() {
        return CmmnCd.builder()
                .cdSn( cdSn )
                .registerId( registerId )
                .registerIp( registerIp )
                .updusrId( updusrId )
                .updusrIp( updusrIp )
                .build();
    }
    
}
