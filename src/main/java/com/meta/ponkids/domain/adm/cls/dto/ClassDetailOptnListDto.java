package com.meta.ponkids.domain.adm.cls.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassDetailOptnListDto {
	
	private Long 	classDetailOptnSn;				// 클래스 상세 옵션 일련번호
	
	private Long 	classDetailSn;					// 클래스 상세 일련번호
	
	private Long 	classDetailOptnSeq;				// 클래스 상세 옵션 순번
	
	private String 	classsDetailOptnCn;				// 클래스 상세 옵션 내용
	
    private String 	registerId;      				// 등록자 ID
    
    private String 	regDt;							// 등록일자
	
	private String 	schOption;   					// 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String 	schCntn;     					// 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
    
    
    @QueryProjection
	public ClassDetailOptnListDto(Long classDetailOptnSn, Long classDetailSn, Long classDetailOptnSeq,
			String classsDetailOptnCn, String registerId, String regDt) {
    	
		this.classDetailOptnSn = classDetailOptnSn;
		this.classDetailSn = classDetailSn;
		this.classDetailOptnSeq = classDetailOptnSeq;
		this.classsDetailOptnCn = classsDetailOptnCn;
		this.registerId = registerId;
		this.regDt = regDt;
	}
	

}