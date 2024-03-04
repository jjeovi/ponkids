package com.meta.ponkids.domain.qestnar.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QestnarQestnListDto {
	
	private Long qestnarQestnSn;
	
	private Long qestnarGroupSn;
	
	private Long qestnarQestnSeq;
	
	private String qestnarQestnItemTyCd;
	
	private String qestnarQestnItemCn;
	
	private String qestnarQestnEssntlYn;
	
	private Long atchFileSn;
	
    private String registerId;      // 등록자 ID
    
    private String regDt;	// 등록일자
	
	private String schOption;   	// 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String schCntn;     	// 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!

    
	@QueryProjection
	public QestnarQestnListDto(Long qestnarQestnSn, Long qestnarGroupSn, Long qestnarQestnSeq,
			String qestnarQestnItemTyCd, String qestnarQestnItemCn, String qestnarQestnEssntlYn, Long atchFileSn, String registerId,
			String regDt) {
		this.qestnarQestnSn = qestnarQestnSn;
		this.qestnarGroupSn = qestnarGroupSn;
		this.qestnarQestnSeq = qestnarQestnSeq;
		this.qestnarQestnItemTyCd = qestnarQestnItemTyCd;
		this.qestnarQestnItemCn = qestnarQestnItemCn;
		this.qestnarQestnEssntlYn = qestnarQestnEssntlYn;
		this.atchFileSn = atchFileSn;
		this.registerId = registerId;
		this.regDt = regDt;
	}
	
	

}