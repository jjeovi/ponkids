package com.meta.ponkids.domain.system.cmmnCd.dto;

import com.meta.ponkids.domain.system.cmmnCd.entity.CmmnCd;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO Dto 항목 setting
@Data
@NoArgsConstructor
public class CmmnCdModDto {
    
    
    private Long cdSn;
    
    private String updusrId;        // 수정자 ID
    
    private String updusrIp;        // 수정자 IP
    
    // TODO 생성자();
    //builder 생성
    @Builder
    public CmmnCdModDto( Long cdSn ) {
        this.cdSn = cdSn;
    }
    
    
    // TODO toEntity();
    // Dto to Entity 메소드 생성
    public CmmnCd toEntity() {
        return CmmnCd.builder()
                .cdSn( cdSn )
                .build();
    }
    
    
    //TODO toDto();
    // Entity to Dto 메소드는 DTO 내부에서 생성.
    public CmmnCdModDto toDto( CmmnCd cmmnCd ) {
        return CmmnCdModDto.builder()
                .cdSn( cmmnCd.getCdSn() )
                .build();
    }
    
}
