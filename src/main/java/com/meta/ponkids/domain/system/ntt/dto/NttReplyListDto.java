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
     

     @NotNull
     private String registerId;
    

    		 
     @QueryProjection
     public NttReplyListDto( int nttReplySn, int nttSn, int step, int parntsReplySn ,int nttReplySeq, String nttReplyCn, String registerId) {
            this.nttReplySn = nttReplySn;
            this.nttSn = nttSn;
            this.step = step;
            this.parntsReplySn = parntsReplySn;
            this.nttReplySeq = nttReplySeq;
            this.nttReplyCn = nttReplyCn;
            this.registerId = registerId;
     }
    
}
