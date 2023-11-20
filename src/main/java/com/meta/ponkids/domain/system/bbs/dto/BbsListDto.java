package com.meta.ponkids.domain.system.bbs.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BbsListDto {
    
    private int bbsSn;
    
    private String bbsSeCd;
    
    private String bbsNm;
    
    private String answerSetYn;
    
    private String useYn;
    
    private String openYn;

	private String registerId;
	

	private LocalDateTime regDt;
	
    private String schOption;   // 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String schCntn;     // 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
    
    
    @QueryProjection
    public BbsListDto( int bbsSn ,String bbsSeCd, String bbsNm,  String answerSetYn, String useYn,String openYn	 ,String registerId,LocalDateTime regDt) {

         this.bbsSn = bbsSn;
         this.bbsSeCd = bbsSeCd;
         this.bbsNm = bbsNm;
         this.answerSetYn = answerSetYn;
         this.useYn = useYn;
         this.openYn = openYn;
         this.regDt = regDt;
         this.registerId = registerId;
    }
    
    
    
}
