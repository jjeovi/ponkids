package com.meta.ponkids.domain.system.ntt.dto;

import com.meta.ponkids.domain.system.ntt.entity.Ntt;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;


@NoArgsConstructor
@Data
public class NttModDto {

	@NotNull
    private Long nttSn;
	private Long bbsSn;
    private int nttRdcnt;
    private String nttNm;
    private String nttCn;
    private String noticeSetYn;
    private String updusrId;      
    private String updusrIp; 
    private LocalDateTime updtDt; 
    private Long atchFileSn;      	// 첨부파일 일련번호
    private Long atchFileSnOri;     // 첨부파일 일련번호
	
    

	

    // builder 생성
    @Builder
    public NttModDto( Long nttSn ,Long bbsSn,String nttNm, String nttCn, String noticeSetYn, int nttRdcnt
    		          ,String updusrId,String updusrIp, LocalDateTime updtDt , Long atchFileSn) {

        this.nttSn = nttSn;
        this.bbsSn = bbsSn;
        this.nttNm = nttNm;
        this.nttCn = nttCn;
        this.noticeSetYn = noticeSetYn;
        this.nttRdcnt = nttRdcnt;
        this.updusrId = updusrId;
        this.updusrIp = updusrIp;
        this.updtDt = updtDt;
        this.atchFileSn = atchFileSn;
   }
    
    
    // DTO to Entity 메소드는 DTO 내부에서 생성.
    public Ntt toEntity() {
        return Ntt.builder()
                .nttSn(nttSn)
                .bbsSn(bbsSn)
                .nttNm(nttNm)
                .nttCn(nttCn)
                .noticeSetYn(noticeSetYn)
                .nttRdcnt(nttRdcnt)
                .updusrId(updusrId)
                .updusrIp(updusrIp)
                .updtDt(updtDt)
                .atchFileSn( atchFileSn )
                .build();
    }
    
    
    
    public NttModDto toDto(Ntt ntt) {
        return NttModDto.builder()
        		.nttSn(ntt.getNttSn())
        		.bbsSn(ntt.getBbsSn())
                .nttNm(ntt.getNttNm())
                .nttCn(ntt.getNttCn())
                .noticeSetYn(ntt.getNoticeSetYn())
                .nttRdcnt( ntt.getNttRdcnt())
                .updusrId(ntt.getUpdusrId())
                .updusrIp(ntt.getUpdusrIp())
                .updtDt(ntt.getUpdtDt())
                .atchFileSn( ntt.getAtchFileSn() )
                .build();
    }
    

}
