package com.meta.ponkids.domain.ntt.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class NttListDto {
    
    private Long nttSn;
    private Long bbsSn;
    private String nttNm;
    private String nttCn;
    private int nttRdcnt;
    private String openYn;
    private String noticeSetYn;
    private String registerId;
    private String regDt;
    
    private String schOption;   // 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    private String schCntn;     // 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
    
    
    @QueryProjection
    public NttListDto( Long nttSn, Long bbsSn, String nttNm, String nttCn, int nttRdcnt, String openYn, String noticeSetYn, String registerId, String regDt ) {
        
        this.nttSn = nttSn;
        this.bbsSn = bbsSn;
        this.nttNm = nttNm;
        this.nttCn = nttCn;
        this.nttRdcnt = nttRdcnt;
        this.openYn = openYn;
        this.noticeSetYn = noticeSetYn;
        this.registerId = registerId;
        this.regDt = regDt;
        
    }
    
    
}
