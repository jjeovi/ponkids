package com.meta.ponkids.domain.adm.bbs.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class BbsListDto {
    
    private Long bbsSn;
    
    private String bbsSeCd;
    
    private String bbsNm;
    
    private String replySetYn;
    
    private String useYn;
    
    private String openYn;
    
    private String registerId;
    
    private LocalDateTime regDt;
    
    private String schOption;   // 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String schCntn;     // 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
    
    
    @QueryProjection
    public BbsListDto( Long bbsSn, String bbsSeCd, String bbsNm, String replySetYn, String useYn, String openYn, String registerId, LocalDateTime regDt ) {
        
        this.bbsSn = bbsSn;
        this.bbsSeCd = bbsSeCd;
        this.bbsNm = bbsNm;
        this.replySetYn = replySetYn;
        this.useYn = useYn;
        this.openYn = openYn;
        this.registerId = registerId;
        this.regDt = regDt;
        
    }
    
    
}
