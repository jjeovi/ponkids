package com.meta.ponkids.domain.system.cmmnCd.dto;

import com.meta.ponkids.domain.system.cmmnCd.entity.CmmnCd;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CmmnCdModDto {
    
    private Long    cdSn;
    
    private String  cdNm;
    
    private String  cdDc;
    
    private String  cdDetailVal1;
    
    private String  cdDetailVal2;
    
    private String  cdDetailVal3;
    
    private String  cdDetailVal4;
    
    private String  cdDetailVal5;
    
    private String  useYn;
    
    private String  sysEssntlCmmnYn;
    
    private String  clCd;
    
    private String  remark;
    
    private String updusrId;        // 수정자 ID
    
    private String updusrIp;        // 수정자 IP
    
    
    //builder 생성
    @Builder
    public CmmnCdModDto( Long cdSn, String cdNm, String cdDc, String cdDetailVal1, String cdDetailVal2, String cdDetailVal3, String cdDetailVal4, String cdDetailVal5, String useYn, String sysEssntlCmmnYn, String clCd, String remark, String updusrId, String updusrIp ) {
        this.cdSn = cdSn;
        this.cdNm = cdNm;
        this.cdDc = cdDc;
        this.cdDetailVal1 = cdDetailVal1;
        this.cdDetailVal2 = cdDetailVal2;
        this.cdDetailVal3 = cdDetailVal3;
        this.cdDetailVal4 = cdDetailVal4;
        this.cdDetailVal5 = cdDetailVal5;
        this.useYn = useYn;
        this.sysEssntlCmmnYn = sysEssntlCmmnYn;
        this.clCd = clCd;
        this.remark = remark;
        this.updusrId = updusrId;
        this.updusrIp = updusrIp;
    }
    
    // Dto to Entity 메소드 생성
    public CmmnCd toEntity() {
        return CmmnCd.builder()
                .cdSn( cdSn )
                .cdNm( cdNm )
                .cdDc( cdDc )
                .cdDetailVal1( cdDetailVal1 )
                .cdDetailVal2( cdDetailVal2 )
                .cdDetailVal3( cdDetailVal3 )
                .cdDetailVal4( cdDetailVal4 )
                .cdDetailVal5( cdDetailVal5 )
                .useYn( useYn )
                .sysEssntlCmmnYn( sysEssntlCmmnYn )
                .clCd( clCd )
                .remark( remark )
                .updusrId( updusrId )
                .updusrIp( updusrIp )
                .build();
    }
    
    // Entity to Dto 메소드는 DTO 내부에서 생성.
    public CmmnCdModDto toDto( CmmnCd cmmnCd ) {
        return CmmnCdModDto.builder()
                .cdSn( cmmnCd.getCdSn() )
                .cdNm( cmmnCd.getCdNm() )
                .cdDc( cmmnCd.getCdDc() )
                .cdDetailVal1( cmmnCd.getCdDetailVal1() )
                .cdDetailVal2( cmmnCd.getCdDetailVal2() )
                .cdDetailVal3( cmmnCd.getCdDetailVal3() )
                .cdDetailVal4( cmmnCd.getCdDetailVal4() )
                .cdDetailVal5( cmmnCd.getCdDetailVal5() )
                .useYn( cmmnCd.getUseYn() )
                .sysEssntlCmmnYn( cmmnCd.getSysEssntlCmmnYn() )
                .clCd( cmmnCd.getClCd() )
                .remark( cmmnCd.getRemark() )
                .updusrId( cmmnCd.getUpdusrId() )
                .updusrIp( cmmnCd.getUpdusrIp() )
                .build();
    }
    
}
