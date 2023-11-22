package com.meta.ponkids.domain.system.ntt.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class NttReplyListDto {
    
	 @NotNull
	 private int nttReplySn;
		
	 @NotNull
	 private int nttSn;
	 private int step;
     private int parntsReplySn;
     private int nttReplySeq;
     private String nttReplyCn;
     private String openYn;
     @NotNull
     private String registerId;
     @NotNull
     private String registerIp;
     private LocalDateTime regDt;
     private String upduserId;
     private String upduserIp;
     private LocalDateTime updtDt;
     private String delYn;
     private String schOption;   // 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
     private String schCntn;     // 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
    
    @QueryProjection
    public NttReplyListDto( int nttReplySn ,int nttSn, int step, int parntsReplySn,int nttReplySeq, String nttReplyCn,
        		String openYn,String delYn
        		,String registerId,String registerIp,LocalDateTime regDt ,String upduserId,String upduserIp,LocalDateTime updtDt) {
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
