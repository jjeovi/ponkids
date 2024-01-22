package com.meta.ponkids.domain.adm.system.cmmnCd.dto;

import com.meta.ponkids.domain.adm.system.cmmnCd.entity.CmmnCdDetail;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CmmnCdDetailListDto {
    
    private Long cdDetailSn;
    
    private String cdNm;
    
    private Long cdDetailSeq;
    
    private String cdDetailNm;
    
    private String cdDetailDc;
    
    private String cdDetailVal1;
    
    private String cdDetailVal2;
    
    private String cdDetailVal3;
    
    private String cdDetailVal4;
    
    private String cdDetailVal5;
    
    private String useYn;
    
    private String schOption;   // 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String schCntn;     // 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
    
    @Builder
    @QueryProjection
    public CmmnCdDetailListDto( Long cdDetailSn, String cdNm, Long cdDetailSeq, String cdDetailNm, String cdDetailDc,
                                String cdDetailVal1, String cdDetailVal2, String cdDetailVal3, String cdDetailVal4, String cdDetailVal5,
                                String useYn ) {
        this.cdDetailSn = cdDetailSn;
        this.cdNm = cdNm;
        this.cdDetailSeq = cdDetailSeq;
        this.cdDetailNm = cdDetailNm;
        this.cdDetailDc = cdDetailDc;
        this.cdDetailVal1 = cdDetailVal1;
        this.cdDetailVal2 = cdDetailVal2;
        this.cdDetailVal3 = cdDetailVal3;
        this.cdDetailVal4 = cdDetailVal4;
        this.cdDetailVal5 = cdDetailVal5;
        this.useYn = useYn;
    }
    
    
    // Entity to Dto 메소드는 DTO 내부에서 생성.
    public CmmnCdDetailListDto toDto( CmmnCdDetail cmmnCdDetail ) {
        return CmmnCdDetailListDto.builder()
                .cdDetailSn( cmmnCdDetail.getCdDetailSn() )
                .cdNm( cmmnCdDetail.getCdNm() )
                .cdDetailSeq( cmmnCdDetail.getCdDetailSeq() )
                .cdDetailNm( cmmnCdDetail.getCdDetailNm() )
                .cdDetailDc( cmmnCdDetail.getCdDetailDc() )
                .cdDetailVal1( cmmnCdDetail.getCdDetailVal1() )
                .cdDetailVal2( cmmnCdDetail.getCdDetailVal2() )
                .cdDetailVal3( cmmnCdDetail.getCdDetailVal3() )
                .cdDetailVal4( cmmnCdDetail.getCdDetailVal4() )
                .cdDetailVal5( cmmnCdDetail.getCdDetailVal5() )
                .useYn( cmmnCdDetail.getUseYn() )
                .build();
    }
    
    
}