package com.meta.ponkids.domain.ntt.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class NttReplyListDto {
    
    @NotNull
    private Long nttReplySn;
    
    @NotNull
    private Long nttSn;
    private int step;
    private Long parntsReplySn;
    private int nttReplySeq;
    private String nttReplyCn;
    private String delYn;
    
    @NotNull
    private String registerId;
    private String updtDt;
    private Long nttReplyCnt;  // 답글 갯수
    
    
    @QueryProjection
    public NttReplyListDto( Long nttReplySn, Long nttSn, int step, Long parntsReplySn,
                            int nttReplySeq, String nttReplyCn, String registerId,
                            String updtDt, String delYn, Long nttReplyCnt ) {
        
        this.nttReplySn = nttReplySn;
        this.nttSn = nttSn;
        this.step = step;
        this.parntsReplySn = parntsReplySn;
        this.nttReplySeq = nttReplySeq;
        this.nttReplyCn = nttReplyCn;
        this.registerId = registerId;
        this.delYn = delYn;
        this.updtDt = updtDt;
        this.nttReplyCnt = nttReplyCnt;
        
    }
    
}
