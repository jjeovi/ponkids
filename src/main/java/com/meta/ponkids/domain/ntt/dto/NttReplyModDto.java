package com.meta.ponkids.domain.ntt.dto;

import com.meta.ponkids.domain.ntt.entity.NttReply;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;


@NoArgsConstructor
@Data
public class NttReplyModDto {

    
	 @NotNull
	 private Long nttReplySn;
		
	 @NotNull
	 private Long nttSn;
	 private int step;
     private Long parntsReplySn;
     private int nttReplySeq;
     private String nttReplyCn;
     private String updusrId;      
     private String updusrIp; 
     private LocalDateTime updtDt;
 	 private String writerDt;

    // builder 생성
    @Builder
    public NttReplyModDto( Long nttReplySn, Long nttSn, int step, Long parntsReplySn, int nttReplySeq, String nttReplyCn,
    		              String updusrId,  String updusrIp, LocalDateTime updtDt,String writerDt) {

        this.nttReplySn = nttReplySn;
        this.nttSn = nttSn;
        this.step = step;
        this.parntsReplySn = parntsReplySn;
        this.nttReplySeq = nttReplySeq;
        this.nttReplyCn = nttReplyCn;
        this.updusrId = updusrId;
        this.updusrIp = updusrIp;
        this.updtDt = updtDt;
        this.writerDt = writerDt;
   }
    
    
    // DTO to Entity 메소드는 DTO 내부에서 생성.
    public NttReply toEntity() {
        return NttReply.builder()
                       .nttReplySn(nttReplySn)
                       .nttSn(nttSn)
                       .step(step)
                       .parntsReplySn(parntsReplySn)
                       .nttReplySeq(nttReplySeq)
                       .nttReplyCn(nttReplyCn)
                       .updusrId(updusrId)
                       .updusrIp(updusrIp)
                       .updtDt(updtDt)
                       .writerDt(writerDt)
                       .build();
    }
    
    
    
    public NttReplyModDto toDto(NttReply nttReply) {
        return NttReplyModDto.builder()
        		             .nttReplySn(nttReply.getNttReplySn())
        		             .nttSn(nttReply.getNttSn())
                             .step(nttReply.getStep())
                             .parntsReplySn(nttReply.getParntsReplySn())
                             .nttReplySeq(nttReply.getNttReplySeq())
                             .nttReplyCn( nttReply.getNttReplyCn())
                             .updusrId(nttReply.getUpdusrId())
                             .updusrIp(nttReply.getUpdusrIp())
                             .updtDt(nttReply.getUpdtDt())
                             .writerDt(nttReply.getWriterDt())
                             .build();
    }
    

}
