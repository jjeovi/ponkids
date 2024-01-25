package com.meta.ponkids.domain.system.cmmnCd.dto;

import com.meta.ponkids.domain.system.cmmnCd.entity.CmmnCd;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CmmnCdListDto {
    
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
    
    private Long	childCnt;						// 자식 코드 개수 
    
    private String 	registerId;      				// 등록자 ID
    
    private String 	regDt;							// 등록일자
    
    private String schOption;   // 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String schCntn;     // 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
    
    @Builder
    @QueryProjection
    public CmmnCdListDto( Long cdSn, String cdNm, String cdDc, String cdVal1, String cdVal2, String cdVal3, String cdVal4, String cdVal5, String useYn, String sysEssntlCmmnYn, String clCd, String remark, Long childCnt, String registerId, String regDt ) {
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
        this.childCnt = childCnt;
        this.registerId = registerId;
        this.regDt = regDt;
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
    			.build();
    }
    
    // Entity to Dto 메소드는 DTO 내부에서 생성
    public CmmnCdListDto toDto ( CmmnCd cmmnCd ) {
    	return CmmnCdListDto.builder()
    			.cdSn( cmmnCd.getCdSn() )
    			.cdNm( cmmnCd.getCdNm() )
    			.cdDc( cmmnCd.getCdDc() )
    			.cdVal1( cmmnCd.getCdVal1() )
    			.cdVal2( cmmnCd.getCdVal2() )
    			.cdVal3( cmmnCd.getCdVal3() )
    			.cdVal4( cmmnCd.getCdVal4() )
    			.cdVal5( cmmnCd.getCdVal5() )
    			.useYn( cmmnCd.getUseYn() )
    			.sysEssntlCmmnYn( cmmnCd.getSysEssntlCmmnYn() )
    			.clCd( cmmnCd.getClCd() )
    			.remark( cmmnCd.getRemark() )
    			.build();
    }
    
}