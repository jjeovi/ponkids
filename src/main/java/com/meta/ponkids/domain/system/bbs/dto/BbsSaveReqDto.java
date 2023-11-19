package com.meta.ponkids.domain.system.bbs.dto;

import lombok.*;


import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class BbsSaveReqDto {
    @NotNull
    private int bbsSn;
	
    @NotNull
	private String bbsSeCd;
	
    @NotNull
	private String bbsNm;
	
	private String bbsGdcc;
	
	private String bbsDc;
	
	private String answerSetYn;
	
	@NotNull
	private String useYn;
	
	private String openYn;
	
	@NotNull
	private String registerId;
	
	@NotNull
	private String registerIp;
	
	@NotNull
	private String regDt;
	
	@NotNull
	private String upduserId;
	
	private String upduserIp;
	
	private String updtDt;
	
	private String delYn;

    // builder 생성
    @Builder
    public BbsSaveReqDto( int bbsSn ,String bbsSeCd, String bbsNm, String bbsGdcc,String bbsDc, String answerSetYn, String useYn,String openYn,String delYn
    		,String registerId,String registerIp,String regDt ,String upduserId,String upduserIp,String updtDt) {
        this.bbsSn = bbsSn;
        this.bbsSeCd = bbsSeCd;
        this.bbsNm = bbsNm;
        this.bbsGdcc = bbsGdcc;
        this.answerSetYn = answerSetYn;
        this.useYn = useYn;
        this.openYn = openYn;
        this.registerId = registerId;
        this.registerIp = registerIp;
        this.regDt = regDt;
        this.upduserId = upduserId;
        this.upduserIp = upduserIp;
        this.updtDt = updtDt;
        this.delYn = delYn;
        this.bbsDc = bbsDc;
    }
    
    
}
