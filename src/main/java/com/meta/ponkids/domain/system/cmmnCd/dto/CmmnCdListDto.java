package com.meta.ponkids.domain.system.cmmnCd.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CmmnCdListDto {
    
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
    
    private String 	registerId;      				// 등록자 ID
    
    private String 	regDt;							// 등록일자
    
    private String schOption;   // 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String schCntn;     // 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
    
    @QueryProjection
    public CmmnCdListDto( Long cdSn, String cdNm, String cdDc, String cdDetailVal1, String cdDetailVal2, String cdDetailVal3, String cdDetailVal4, String cdDetailVal5, String useYn, String sysEssntlCmmnYn, String clCd, String remark, String registerId, String regDt ) {
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
        this.registerId = registerId;
        this.regDt = regDt;
    }
    
}