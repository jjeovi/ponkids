package com.meta.ponkids.domain.lctre.dto;

import java.util.List;

import com.meta.ponkids.domain.lctre.entity.LctreReqst;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode( callSuper = false )
public class LctreReqstSaveDto  {
    
    private Long    lctreReqstSn;			// 수업 신청 일련번호
    
    private Long    classReqstSn;			// 클래스 신청 일련번호
    
    private Long    lctreSn;				// 수업 일련번호
    
    private Long    chldrnSn;				// 자녀 일련번호

    private String  preparNmprYn;		    // 예비 인원 여부
    
    private String  registerId;             // 등록자 id
    
    private String  registerIp;             // 등록자 ip
    
    private String  updusrId;               // 수정자 id
    
    private String  updusrIp;               // 수정자 ip
    
    private List<LctreReqstSaveDto> lctreReqsts;				// list
    
    private List<LctreReqstDetailSaveDto> lctreReqstDetails;	// list
    
    @Builder
    public LctreReqstSaveDto( Long lctreReqstSn, Long classReqstSn, Long lctreSn, Long chldrnSn, String preparNmprYn, String registerId, String registerIp, String updusrId, String updusrIp ) {
        
        this.lctreReqstSn   = lctreReqstSn;
        this.classReqstSn   = classReqstSn;
        this.lctreSn        = lctreSn;
        this.chldrnSn       = chldrnSn;
        this.preparNmprYn   = preparNmprYn;
        this.registerId     = registerId;
        this.registerIp     = registerIp;
        this.updusrId       = updusrId;
        this.updusrIp       = updusrIp;
    }
    
    // Dto to Entity 메소드 생성
    public LctreReqst toEntity() {
        return LctreReqst.builder()
                .lctreReqstSn( lctreReqstSn )
                .classReqstSn( classReqstSn )
                .lctreSn( lctreSn )
                .chldrnSn( chldrnSn )
                .preparNmprYn( preparNmprYn )
                .registerId( registerId )
                .registerIp( registerIp )
                .updusrId( updusrId )
                .updusrIp( updusrIp )
                .build();
    }
    
}
