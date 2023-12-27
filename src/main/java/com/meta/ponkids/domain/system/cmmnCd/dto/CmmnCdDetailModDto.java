package com.meta.ponkids.domain.system.cmmnCd.dto;

import com.meta.ponkids.domain.system.cmmnCd.entity.CmmnCdDetail;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO Dto 항목 setting
@Data
@NoArgsConstructor
public class CmmnCdDetailModDto {
    
    
    private Long cdDetailSn;
    
    private String updusrId;        // 수정자 ID
    
    private String updusrIp;        // 수정자 IP
    
    // TODO 생성자();
    //builder 생성
    @Builder
    public CmmnCdDetailModDto( Long cdDetailSn ) {
        this.cdDetailSn = cdDetailSn;
    }
    
    
    // TODO toEntity();
    // Dto to Entity 메소드 생성
    public CmmnCdDetail toEntity() {
        return CmmnCdDetail.builder()
                .cdDetailSn( cdDetailSn )
                .build();
    }
    
    
    //TODO toDto();
    // Entity to Dto 메소드는 DTO 내부에서 생성.
    public CmmnCdDetailModDto toDto( CmmnCdDetail cmmnCdDetail ) {
        return CmmnCdDetailModDto.builder()
                .cdDetailSn( cmmnCdDetail.getCdDetailSn() )
                .build();
    }
    
}
