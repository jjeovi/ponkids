package com.meta.ponkids.domain.ntt.dto;

import lombok.*;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class NttReplySaveReqDto {

    @NotNull
    private Long nttReplySn;
	
    @NotNull
	private Long nttSn;
	private int step;
	private Long parntsReplySn;
	private int nttReplySeq;
	private String nttReplyCn;
	private String openYn;

	@NotNull
	private String registerId;
	@NotNull
	private String registerIp;
	@NotNull
	private String regDt;
	private String upduserId;
	private String upduserIp;
	private String updtDt;
	private String delYn;

    // builder 생성
    @Builder
    public NttReplySaveReqDto( Long nttReplySn ,Long nttSn, int step, Long parntsReplySn, int nttReplySeq, String nttReplyCn, 
    		                  String openYn, String delYn, String registerId, String registerIp, String regDt,
    		                  String upduserId, String upduserIp, String updtDt) {
    	
        this.nttReplySn = nttReplySn;
        this.nttSn = nttSn;
        this.step = step;
        this.parntsReplySn = parntsReplySn;
        this.nttReplySeq = nttReplySeq;
        this.nttReplyCn = nttReplyCn;
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
