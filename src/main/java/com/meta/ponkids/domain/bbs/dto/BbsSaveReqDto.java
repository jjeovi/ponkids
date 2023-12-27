package com.meta.ponkids.domain.bbs.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class BbsSaveReqDto {
    @NotNull
    private Long bbsSn;
    
    @NotNull
    private String bbsSeCd;
    
    @NotNull
    private String bbsNm;
    
    private String bbsGdcc;
    
    private String bbsDc;
    
    private String replySetYn;
    
    @NotNull
    private String useYn;
    
    private String openYn;
    
    @NotNull
    private String registerId;
    
    @NotNull
    private String registerIp;
    
    @NotNull
    private LocalDateTime regDt;
    
    @NotNull
    private String updusrId;
    
    private String updusrIp;
    
    private LocalDateTime updtDt;
    
    private String delYn;
    
    // builder 생성
    @Builder
    public BbsSaveReqDto( Long bbsSn, String bbsSeCd, String bbsNm, String bbsGdcc, String bbsDc, String replySetYn, String useYn, String openYn,
                          String delYn, String registerId, String registerIp, LocalDateTime regDt, String updusrId, String updusrIp, LocalDateTime updtDt ) {
        this.bbsSn = bbsSn;
        this.bbsSeCd = bbsSeCd;
        this.bbsNm = bbsNm;
        this.bbsGdcc = bbsGdcc;
        this.replySetYn = replySetYn;
        this.useYn = useYn;
        this.openYn = openYn;
        this.registerId = registerId;
        this.registerIp = registerIp;
        this.regDt = regDt;
        this.updusrId = updusrId;
        this.updusrIp = updusrIp;
        this.updtDt = updtDt;
        this.delYn = delYn;
        this.bbsDc = bbsDc;
    }
    
    
}
