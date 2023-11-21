package com.meta.ponkids.domain.system.bbs.dto;

import com.meta.ponkids.domain.system.bbs.entity.Bbs;
import com.meta.ponkids.domain.user.dto.UserModDto;
import com.meta.ponkids.domain.user.entity.User;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class BbsModDto {

	@NotNull
    private int bbsSn;
    
    private String bbsSeCd;
    
    private String bbsNm;
    
    private String replySetYn;
    
    private String useYn;
    
    private String openYn;

	private String registerId;
	
	
	private String registerIp;
	
	private String bbsGdcc;
	
	private String bbsDc;
	
	
	

    // builder 생성
    @Builder
    public BbsModDto( int bbsSn ,String bbsSeCd, String bbsNm,  String replySetYn, String useYn,String openYn	 ,String registerId  ,String registerIp ,String bbsGdcc, String bbsDc) {

        this.bbsSn = bbsSn;
        this.bbsSeCd = bbsSeCd;
        this.bbsNm = bbsNm;
        this.replySetYn = replySetYn;
        this.useYn = useYn;
        this.openYn = openYn;
        this.registerId = registerId;
        this.registerIp = registerIp;
        this.bbsGdcc = bbsGdcc;
        this.bbsDc = bbsDc;
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
                .registerId( registerId )
                .registerIp( registerIp )
                .bbsGdcc( bbsGdcc )
                .bbsDc( bbsDc )
                .build();
    }
    
    
    public BbsModDto toDto(Bbs bbs) {
        return BbsModDto.builder()
        		.bbsSn( bbs.getBbsSn() )
                .bbsSeCd( bbs.getBbsSeCd() )
                .bbsNm( bbs.getBbsNm() )
                .replySetYn( bbs.getReplySetYn() )
                .useYn( bbs.getUseYn() )
                .openYn( bbs.getOpenYn() )
                .registerId( bbs.getRegisterId() )
                .registerIp( bbs.getRegisterIp() )
                .bbsGdcc( bbs.getBbsGdcc() )
                .bbsDc( bbs.getBbsDc() )
                .build();
    }
    

}
