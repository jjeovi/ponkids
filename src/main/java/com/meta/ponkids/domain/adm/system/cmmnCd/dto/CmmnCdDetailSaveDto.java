package com.meta.ponkids.domain.adm.system.cmmnCd.dto;

import com.meta.ponkids.domain.adm.system.cmmnCd.entity.CmmnCdDetail;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODO Dto 항목 setting
@Data
@NoArgsConstructor
public class CmmnCdDetailSaveDto {
    
    private Long cdDetailSn;
    
    private String registerId;      // 등록자 id
    
    private String registerIp;      // 등록자 ip
    
    private String updusrId;        // 수정자 ID
    
    private String updusrIp;        // 수정자 IP
    
    // TODO 생성자();
    @Builder
    public CmmnCdDetailSaveDto( Long cdDetailSn, String registerId, String registerIp, String updusrId, String updusrIp ) {
        this.cdDetailSn = cdDetailSn;
        this.registerId = registerId;
        this.registerIp = registerIp;
        this.updusrId = updusrId;
        this.updusrIp = updusrIp;
    }
    
    // TODO toEntity();
    // Dto to Entity 메소드 생성
    public CmmnCdDetail toEntity() {
        return CmmnCdDetail.builder()
                .cdDetailSn( cdDetailSn )
                .registerId( registerId )
                .registerIp( registerIp )
                .updusrId( updusrId )
                .updusrIp( updusrIp )
                .build();
    }
    
}
