package com.meta.ponkids.domain.ntt.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class NttSaveReqDto {
    
    @NotNull
    private Long nttSn;
    
    @NotNull
    private Long bbsSn;
    private String nttSeq;
    private String nttNm;
    private String nttCn;
    private Long atchFileSn;
    private Long cnAtchFileSn;
    private int nttRdcnt;
    private String openYn;
    private String noticeSetYn;
    private String noticeSeq;
    
    @NotNull
    private String registerId;
    @NotNull
    private String registerIp;
    @NotNull
    private LocalDateTime regDt;
    
    private String upduserId;
    private String upduserIp;
    private String updtDt;
    private String delYn;
    
    // builder 생성
    @Builder
    public NttSaveReqDto( Long nttSn, Long bbsSn, String nttSeq, String nttNm, String nttCn, Long atchFileSn, Long cnAtchFileSn, int nttRdcnt, String noticeSetYn,
                          String noticeSeq, String openYn, String delYn, String registerId, String registerIp, LocalDateTime regDt, String upduserId,
                          String upduserIp, String updtDt ) {
        
        
        this.nttSn = nttSn;
        this.bbsSn = bbsSn;
        this.nttSeq = nttSeq;
        this.nttNm = nttNm;
        this.nttCn = nttCn;
        this.atchFileSn = atchFileSn;
        this.cnAtchFileSn = cnAtchFileSn;
        this.nttRdcnt = nttRdcnt;
        this.noticeSetYn = noticeSetYn;
        this.noticeSeq = noticeSeq;
        this.openYn = openYn;
        this.registerId = registerId;
        this.registerIp = registerIp;
        this.regDt = regDt;
        this.upduserId = upduserId;
        this.upduserIp = upduserIp;
        this.updtDt = updtDt;
        this.delYn = delYn;
        
    }
    
    
}
