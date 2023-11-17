package com.meta.ponkids.domain.system.bbs.dto;

import com.meta.ponkids.domain.system.bbs.entity.Bbs;

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
    
    private String answerSetYn;
    
    private String useYn;
    
    private String openYn;

	private String registerId;
	
	
	

    // builder 생성
    @Builder
    public BbsModDto( int bbsSn ,String bbsSeCd, String bbsNm,  String answerSetYn, String useYn,String openYn	 ,String registerId ) {

        this.bbsSn = bbsSn;
        this.bbsSeCd = bbsSeCd;
        this.bbsNm = bbsNm;
        this.answerSetYn = answerSetYn;
        this.useYn = useYn;
        this.openYn = openYn;
        this.registerId = registerId;
   }
    
    
    // DTO to Entity 메소드는 DTO 내부에서 생성.
    public Bbs toEntity() {
        return Bbs.builder()
                .bbsSn( bbsSn )
                .bbsSeCd( bbsSeCd )
                .bbsNm( bbsNm )
                .answerSetYn( answerSetYn )
                .useYn( useYn )
                .openYn( openYn )
                .registerId( registerId )
                .build();
    }
    

}
