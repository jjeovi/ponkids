package com.meta.ponkids.domain.system.cmmnCd.dto;

import com.meta.ponkids.domain.system.cmmnCd.entity.CmmnCd;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODO Dto 항목 setting
@Data
@NoArgsConstructor
public class CmmnCdSaveDto {
    
    private Long    cdSn;
    
    private String  cdNm;
    
    private String  cdDc;
    
    private String  cdVal1;
    
    private String  cdVal2;
    
    private String  cdVal3;
    
    private String  cdVal4;
    
    private String  cdVal5;
    
    private String  useYn;
    
    private String  sysEssntlCmmnYn;
    
    private String  clCd;
    
    private String  remark;
    
    private String registerId;      // 등록자 id
    
    private String registerIp;      // 등록자 ip
    
    private String updusrId;        // 수정자 ID
    
    private String updusrIp;        // 수정자 IP
    
    @Builder
    public CmmnCdSaveDto( Long cdSn, String cdNm, String cdDc, String cdVal1, String cdVal2, String cdVal3, String cdVal4, String cdVal5, String useYn, String sysEssntlCmmnYn, String clCd, String remark, String registerId, String registerIp, String updusrId, String updusrIp ) {
        this.cdSn = cdSn;
        this.cdNm = cdNm;
        this.cdDc = cdDc;
        this.cdVal1 = cdVal1;
        this.cdVal2 = cdVal2;
        this.cdVal3 = cdVal3;
        this.cdVal4 = cdVal4;
        this.cdVal5 = cdVal5;
        this.useYn = useYn;
        this.sysEssntlCmmnYn = sysEssntlCmmnYn;
        this.clCd = clCd;
        this.remark = remark;
        this.registerId = registerId;
        this.registerIp = registerIp;
        this.updusrId = updusrId;
        this.updusrIp = updusrIp;
    }
    
    // Dto to Entity 메소드 생성
    public CmmnCd toEntity() {
        return CmmnCd.builder()
                .cdSn( cdSn )
                .cdNm( cdNm )
                .cdDc( cdDc )
                .cdVal1( cdVal1 )
    			.cdVal2( cdVal2 )
    			.cdVal3( cdVal3 )
    			.cdVal4( cdVal4 )
    			.cdVal5( cdVal5 )
                .useYn( useYn )
                .sysEssntlCmmnYn( sysEssntlCmmnYn )
                .clCd( clCd )
                .remark( remark )
                .registerId( registerId )
                .registerIp( registerIp )
                .updusrId( updusrId )
                .updusrIp( updusrIp )
                .build();
    }
    
}
