package com.meta.ponkids.domain.cls.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassDetailListDto {
	
	private Long 	classDetailSn;					// 클래스 상세 일련번호
	
	private Long 	classSn;						// 클래스 일련번호
	
	private Long 	classDetailSeq;					// 클래스 상세 순번
	
	private String 	classDetailItemTyCd;			// 클래스 상세 항목 유형 코드
	
	private String 	classDetailItemCn;				// 클래스 상세 항목
	
	private String 	classDetailEssntlYn;			// 클래스 상세 필수 여부
	
    private String 	registerId;      				// 등록자 ID
    
    private String 	regDt;							// 등록일자
	
	private String 	schOption;   					// 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String 	schCntn;     					// 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
	
	@QueryProjection
	public ClassDetailListDto(Long classDetailSn, Long classSn, Long classDetailSeq, String classDetailItemTyCd,
			String classDetailItemCn, String classDetailEssntlYn, String registerId, String regDt) {
		
		this.classDetailSn = classDetailSn;
		this.classSn = classSn;
		this.classDetailSeq = classDetailSeq;
		this.classDetailItemTyCd = classDetailItemTyCd;
		this.classDetailItemCn = classDetailItemCn;
		this.classDetailEssntlYn = classDetailEssntlYn;
		this.registerId = registerId;
		this.regDt = regDt;
	}
	

}