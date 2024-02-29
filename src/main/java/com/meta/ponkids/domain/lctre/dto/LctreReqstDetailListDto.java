package com.meta.ponkids.domain.lctre.dto;

import java.util.List;

import com.meta.ponkids.domain.lctre.entity.LctreReqstDetail;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode( callSuper = false )
public class LctreReqstDetailListDto {
    
    private Long    lctreReqstDetailSn;     	// 수업 신청 상세 일련번호
    
    private Long    lctreReqstSn;				// 수업 신청 일련번호
    
    private Long    classDetailSn;				// 클래스 상세 일련번호
    
    private Long 		classDetailSeq;			// 클래스 상세 순번
	
	private String 		classDetailItemTyCd;	// 클래스 상세 항목 유형 일련번호
	
	private String 		classDetailItemTyNm;	// 클래스 상세 항목 유형 명
	
	private String 		classDetailItemCn;		// 클래스 상세 항목
	
	private String 		classDetailEssntlYn;	// 클래스 상세 필수 여부
	
	private String 		classDetailEssntlYnNm;	// 클래스 상세 필수 여부 명 (필수, 선택 )
    
    private String  classDetailAnswer;		// 클래스 상세 답변
    
    private String  registerId;             	// 등록자 id
    
    private String  registerIp;             	// 등록자 ip
    
    private String  updusrId;               	// 수정자 id
    
    private String  updusrIp;               	// 수정자 ip

    private List<LctreReqstDetailListDto> lctreReqstDetailListDtos;	// list
    
    @Builder
    @QueryProjection
    public LctreReqstDetailListDto( Long	lctreReqstDetailSn, Long 	lctreReqstSn, 			Long	classDetailSn,
    								Long	classDetailSeq, 	String	classDetailItemTyCd,	String	classDetailItemTyNm,
    								String	classDetailItemCn,	String	classDetailEssntlYn,	String	classDetailEssntlYnNm,
    								String	classDetailAnswer ) {
        this.lctreReqstDetailSn     = lctreReqstDetailSn;
        this.lctreReqstSn           = lctreReqstSn;
        this.classDetailSn          = classDetailSn;
        this.classDetailSeq         = classDetailSeq;
        this.classDetailItemTyCd    = classDetailItemTyCd;
        this.classDetailItemTyNm    = classDetailItemTyNm;
        this.classDetailItemCn      = classDetailItemCn;
        this.classDetailEssntlYn    = classDetailEssntlYn;
        this.classDetailEssntlYnNm  = classDetailEssntlYnNm;
        this.classDetailAnswer      = classDetailAnswer;
    }
    
    // Dto to Entity 메소드 생성
    public LctreReqstDetail toEntity() {
        return LctreReqstDetail.builder()
                .lctreReqstDetailSn( lctreReqstDetailSn )
                .lctreReqstSn( lctreReqstSn )
                .classDetailSn( classDetailSn )
                .classDetailAnswer( classDetailAnswer )
                .registerId( registerId )
                .registerIp( registerIp )
                .updusrId( updusrId )
                .updusrIp( updusrIp )
                .build();
    }
    
}
