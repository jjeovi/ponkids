package com.meta.ponkids.domain.system.ntt.dto;


import com.meta.ponkids.domain.system.bbs.dto.BbsModDto;
import com.meta.ponkids.domain.system.bbs.entity.Bbs;
import com.meta.ponkids.domain.system.ntt.entity.Ntt;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;


@NoArgsConstructor
@Data
public class NttModDto {

	@NotNull
    private int nttSn;
    
    private String nttNm;
    
    private String nttCn;
    

	

    // builder 생성
    @Builder
    public NttModDto( int nttSn ,String nttNm, String nttCn) {

        this.nttSn = nttSn;
        this.nttNm = nttNm;
        this.nttCn = nttCn;
   
   }
    
    
    // DTO to Entity 메소드는 DTO 내부에서 생성.
    public Ntt toEntity() {
        return Ntt.builder()
                .nttSn( nttSn )
                .nttNm( nttNm )
                .nttCn( nttCn )
                .build();
    }
    
    
    
    public NttModDto toDto(Ntt ntt) {
        return NttModDto.builder()
        		.nttSn( ntt.getNttSn() )
                .nttNm( ntt.getNttNm() )
                .nttCn( ntt.getNttCn() )
                .build();
    }
    

}
