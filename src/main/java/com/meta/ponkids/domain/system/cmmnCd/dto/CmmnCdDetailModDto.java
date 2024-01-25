package com.meta.ponkids.domain.system.cmmnCd.dto;

import com.meta.ponkids.domain.system.cmmnCd.entity.CmmnCdDetail;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CmmnCdDetailModDto {
    
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
    
    private String updusrId;        // 수정자 ID
    
    private String updusrIp;        // 수정자 IP
    
    //builder 생성
    @Builder
	public CmmnCdDetailModDto(Long cdDetailSn, String cdNm, Long cdDetailSeq, String cdDetailNm, String cdDetailDc,
			String cdDetailVal1, String cdDetailVal2, String cdDetailVal3, String cdDetailVal4, String cdDetailVal5,
			String useYn, String updusrId, String updusrIp) {
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
		this.updusrId = updusrId;
		this.updusrIp = updusrIp;
	}
    
    // Dto to Entity 메소드 생성
    public CmmnCdDetail toEntity() {
        return CmmnCdDetail.builder()
                .cdDetailSn( cdDetailSn )
                .cdNm( cdNm )
                .cdDetailSeq( cdDetailSeq )
                .cdDetailNm( cdDetailNm )
                .cdDetailDc( cdDetailDc )
                .cdDetailVal1( cdDetailVal1 )
                .cdDetailVal2( cdDetailVal2 )
                .cdDetailVal3( cdDetailVal3 )
                .cdDetailVal4( cdDetailVal4 )
                .cdDetailVal5( cdDetailVal5 )
                .useYn( useYn )
                .updusrId( updusrId )
                .updusrIp( updusrIp )
                .build();
    }
    
    // Entity to Dto 메소드는 DTO 내부에서 생성.
    public CmmnCdDetailModDto toDto( CmmnCdDetail cmmnCdDetail ) {
        return CmmnCdDetailModDto.builder()
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
                .updusrId( cmmnCdDetail.getUpdusrId() )
                .updusrIp( cmmnCdDetail.getUpdusrIp() )
                .build();
    }

}
