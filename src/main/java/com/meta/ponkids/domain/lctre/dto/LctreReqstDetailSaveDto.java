package com.meta.ponkids.domain.lctre.dto;

import java.util.List;

import com.meta.ponkids.domain.lctre.entity.LctreReqstDetail;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode( callSuper = false )
public class LctreReqstDetailSaveDto {
    
    private Long    lctreReqstDetailSn;     // 수업 신청 상세 일련번호
    
    private Long    lctreReqstSn;			// 수업 신청 일련번호
    
    private Long    classDetailSn;			// 클래스 상세 일련번호
    
    private String  classDetailAnswer;		// 클래스 상세 답변
    
    private String  registerId;             // 등록자 id
    
    private String  registerIp;             // 등록자 ip
    
    private String  updusrId;               // 수정자 id
    
    private String  updusrIp;               // 수정자 ip

    private List<LctreReqstDetailSaveDto> lctreReqstDetailSaveDtoList;	// list
    
    @Builder
    public LctreReqstDetailSaveDto( Long lctreReqstDetailSn, Long lctreReqstSn, Long classDetailSn, String classDetailAnswer, String registerId, String registerIp, String updusrId, String updusrIp ) {
        
        this.lctreReqstDetailSn     = lctreReqstDetailSn;
        this.lctreReqstSn           = lctreReqstSn;
        this.classDetailSn          = classDetailSn;
        this.classDetailAnswer      = classDetailAnswer;
        this.registerId             = registerId;
        this.registerIp             = registerIp;
        this.updusrId               = updusrId;
        this.updusrIp               = updusrIp;
    }
    
    // Dto to Entity 메소드 생성
    public LctreReqstDetail toEntity() {
        return LctreReqstDetail.builder()
                .lctreReqstDetailSn( lctreReqstDetailSn )
                .lctreReqstSn( lctreReqstSn )
                .classDetailSn( classDetailSn )
                .classDetailAnswer( classDetailAnswer )
                .registerId( registerId )
                .registerIp( registerIp )
                .updusrId( updusrId )
                .updusrIp( updusrIp )
                .build();
    }
    
}
