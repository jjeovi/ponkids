package com.meta.ponkids.domain.adm.bbs.dto;

import com.meta.ponkids.domain.adm.bbs.entity.Bbs;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class BbsModDto {
    
    @NotNull
    private Long bbsSn;
    
    private String bbsSeCd;
    
    private String bbsNm;
    
    private String replySetYn;
    
    private String useYn;
    
    private String openYn;
    
    private String bbsGdcc;
    
    private String bbsDc;
    
    private String updusrId;
    
    private String updusrIp;
    
    private LocalDateTime updtDt;
    
    
    // builder 생성
    @Builder
    public BbsModDto( Long bbsSn, String bbsSeCd, String bbsNm, String replySetYn, String useYn, String openYn, String bbsGdcc,
                      String bbsDc, String updusrId, String updusrIp, LocalDateTime updtDt ) {
        
        this.bbsSn = bbsSn;
        this.bbsSeCd = bbsSeCd;
        this.bbsNm = bbsNm;
        this.replySetYn = replySetYn;
        this.useYn = useYn;
        this.openYn = openYn;
        this.bbsGdcc = bbsGdcc;
        this.bbsDc = bbsDc;
        this.updusrId = updusrId;
        this.updusrIp = updusrIp;
        this.updtDt = updtDt;
    }
    
    
    // DTO to Entity 메소드는 DTO 내부에서 생성.
    public Bbs toEntity() {
        return Bbs.builder()
                .bbsSn( bbsSn )
                .bbsSeCd( bbsSeCd )
                .bbsNm( bbsNm )
                .replySetYn( replySetYn )
                .useYn( useYn )
                .openYn( openYn )
                .bbsGdcc( bbsGdcc )
                .bbsDc( bbsDc )
                .updusrId( updusrId )
                .updusrIp( updusrIp )
                .updtDt( updtDt )
                .build();
    }
    
    
    public BbsModDto toDto( Bbs bbs ) {
        return BbsModDto.builder()
                .bbsSn( bbs.getBbsSn() )
                .bbsSeCd( bbs.getBbsSeCd() )
                .bbsNm( bbs.getBbsNm() )
                .replySetYn( bbs.getReplySetYn() )
                .useYn( bbs.getUseYn() )
                .openYn( bbs.getOpenYn() )
                .bbsGdcc( bbs.getBbsGdcc() )
                .bbsDc( bbs.getBbsDc() )
                .updusrId( bbs.getUpdusrId() )
                .updusrIp( bbs.getUpdusrIp() )
                .updtDt( bbs.getUpdtDt() )
                .build();
    }
    
    
}
