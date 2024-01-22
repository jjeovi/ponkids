package com.meta.ponkids.domain.adm.lctre.dto;

import com.meta.ponkids.domain.adm.lctre.entity.Lctre;

import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode( callSuper = false )
public class LctreModDto extends LctreDto {
	
	
	private Long 	lctreSn;				// 수업 일련번호
	
	private Long 	classSn;				// 클래스 일련번호
    
    private String 	classDayCd;				// 클래스 요일 코드
	
	private Long 	lctreSeq;				// 수업 순번
	
	private String 	lctreSj;				// 수업 제목
	
	private String 	lctreDc;				// 수업 설명
	
	private String 	lctreApplcntGuidance;	// 수업 신청자 안내
	
	private String 	rcritNmprSetYn;			// 모집 인원 설정 여부
	
	private Long 	rcritNmprCo;			// 모집 인원 수
	
	private String 	preparRcritNmprSetYn;	// 예비 모집 인원 설정 여부
	
	private Long 	preparRcritNmprCo;		// 예비 모집 인원 수
    
    private String 	updusrId;                // 수정자 ID
    
    private String 	updusrIp;                // 수정자 IP
    
    @Builder
    public LctreModDto( Long lctreSn, Long classSn, String classDayCd, Long lctreSeq, String lctreSj, String lctreDc, String lctreApplcntGuidance, String rcritNmprSetYn, Long rcritNmprCo, String preparRcritNmprSetYn, Long preparRcritNmprCo, String updusrId, String updusrIp ) {
        this.lctreSn = lctreSn;
        this.classSn = classSn;
        this.classDayCd = classDayCd;
        this.lctreSeq = lctreSeq;
        this.lctreSj = lctreSj;
        this.lctreDc = lctreDc;
        this.lctreApplcntGuidance = lctreApplcntGuidance;
        this.rcritNmprSetYn = rcritNmprSetYn;
        this.rcritNmprCo = rcritNmprCo;
        this.preparRcritNmprSetYn = preparRcritNmprSetYn;
        this.preparRcritNmprCo = preparRcritNmprCo;
        this.updusrId = updusrId;
        this.updusrIp = updusrIp;
    }
    
    
    // Dto to Entity 메소드 생성
    public Lctre toEntity() {
        return Lctre.builder()
                .lctreSn( lctreSn )
                .classSn( classSn )
                .classDayCd( classDayCd )
                .lctreSeq( lctreSeq )
                .lctreSj( lctreSj )
                .lctreDc( lctreDc )
                .lctreApplcntGuidance( lctreApplcntGuidance )
                .rcritNmprSetYn( rcritNmprSetYn )
                .rcritNmprCo( rcritNmprCo )
                .preparRcritNmprSetYn( preparRcritNmprSetYn )
                .preparRcritNmprCo( preparRcritNmprCo )
                .updusrId( updusrId )
                .updusrIp( updusrIp )
                .build();
    }
    
    public LctreModDto toDto( Lctre lctre ) {
        return LctreModDto.builder()
                .lctreSn( lctre.getLctreSn() )
                .classSn( lctre.getClassSn() )
                .classDayCd( lctre.getClassDayCd() )
                .lctreSeq( lctre.getLctreSeq() )
                .lctreSj( lctre.getLctreSj() )
                .lctreDc( lctre.getLctreDc() )
                .lctreApplcntGuidance( lctre.getLctreApplcntGuidance() )
                .rcritNmprSetYn( lctre.getRcritNmprSetYn() )
                .rcritNmprCo( lctre.getRcritNmprCo() )
                .preparRcritNmprSetYn( lctre.getPreparRcritNmprSetYn() )
                .preparRcritNmprCo( lctre.getPreparRcritNmprCo() )
                .lctreSn( lctre.getLctreSn() )
                .updusrId( lctre.getUpdusrId() )
                .updusrIp( lctre.getUpdusrIp() )
                .build();
    }
	
}
