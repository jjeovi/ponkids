package com.meta.ponkids.domain.lctre.dto;

import com.meta.ponkids.domain.lctre.entity.Lctre;

import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode( callSuper = false )
public class LctreSaveDto extends LctreDto {
    
    private Long    lctreSn;                // 수업 일련번호
    
    private Long    classSn;                // 클래스 일련번호
    
    private String  classDayCd;             // 클래스 요일 일련번호
    
    private Long    lctreSeq;               // 수업 순번
    
    private String  lctreSj;                // 수업 제목
    
    private String  lctreDc;                // 수업 설명
    
    private String  lctreApplcntGuidance;   // 수업 신청자 안내
    
    private String  rcritNmprSetYn;         // 모집 인원 설정 여부
    
    private Long    rcritNmprCo;            // 모집 인원 수
    
    private String  preparRcritNmprSetYn;   // 예비 모집 인원 설정 여부
    
    private Long    preparRcritNmprCo;      // 예비 모집 인원 수
    
    private String  registerId;             // 등록자 id
    
    private String  registerIp;             // 등록자 ip
    
    private String  updusrId;               // 수정자 id
    
    private String  updusrIp;               // 수정자 ip
    
    @Builder
    public LctreSaveDto( Long lctreSn, Long classSn, String classDayCd, Long lctreSeq, String lctreSj, String lctreDc, String lctreApplcntGuidance, String rcritNmprSetYn, Long rcritNmprCo, String preparRcritNmprSetYn, Long preparRcritNmprCo, String registerId, String registerIp, String updusrId, String updusrIp ) {
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
        this.registerId = registerId;
        this.registerIp = registerIp;
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
                .registerId( registerId )
                .registerIp( registerIp )
                .updusrId( updusrId )
                .updusrIp( updusrIp )
                .build();
    }
    
}
