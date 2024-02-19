package com.meta.ponkids.domain.cls.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassInqryListDto {
	
	private Long	classInqrySn;		// 클래스 문의 일련번호
	
	private Long	classSn;			// 클래스 일련번호
	
	private String	step;				// 계층
	
	private Long	parntsInqrySn;		// 부모 문의 일련번호
	
	private Long	userSn;				// 사용자 일련번호
	
	private String	inqrySj;			// 문의 제목
	
	private String	inqryCn;			// 문의 내용
	
	private String	openYn;				// 공개 여부 ( Y / N ) 
	
    private String	registerId;			// 등록자 ID
    
    private String	regDt;	// 등록일자
	
	private String	schOption;			// 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!

	private String	schCntn;			// 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
	
	@QueryProjection
	public ClassInqryListDto(Long classInqrySn, Long classSn, String step, Long parntsInqrySn, Long userSn,
			String inqrySj, String inqryCn, String openYn, String registerId, String regDt) {
		this.classInqrySn = classInqrySn;
		this.classSn = classSn;
		this.step = step;
		this.parntsInqrySn = parntsInqrySn;
		this.userSn = userSn;
		this.inqrySj = inqrySj;
		this.inqryCn = inqryCn;
		this.openYn = openYn;
		this.registerId = registerId;
		this.regDt = regDt;
	}
	

}