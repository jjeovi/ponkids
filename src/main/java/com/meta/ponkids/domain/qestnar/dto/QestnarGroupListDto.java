package com.meta.ponkids.domain.qestnar.dto;

import java.util.List;

import com.meta.ponkids.global.common.dto.CategoryDto;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QestnarGroupListDto {
	
	private Long qestnarGroupSn;
	
	private String qestnarGroupCd;
	
	private String qestnarGroupNm;
	
	private String qestnarGroupDc;
	
	private String upendGdccSetYn;	// 상단 안내문 설정 여부
	
	private String upendGdcc;		// 상단 안내문
	
	private String lptGdccSetYn;	// 상단 안내문 설정 여부
	
	private String lptGdcc;			// 하단 안내문
	
	private String privcyYn;
	
	private String privcyYnNm;
	
	private String useYn;
	
	private String useYnNm;
	
	private String registerId;      // 등록자 ID
	
	private String regDt;	// 등록일자
	
	private List<QestnarQestnSaveDto>		qestnarQestns;		// 입력 항목
    
	private String schOption;   	// 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!

	private String schCntn;     	// 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
	
	private CategoryDto category;    // 카테고리 검색 : 생성자에는 추가하지 않음!
	
	@QueryProjection
	public QestnarGroupListDto(Long qestnarGroupSn, String qestnarGroupCd, String qestnarGroupNm, String qestnarGroupDc,
			String upendGdccSetYn, String upendGdcc, String lptGdccSetYn, String lptGdcc,
			String privcyYn, String privcyYnNm, String useYn, String useYnNm, String registerId, String regDt) {
		this.qestnarGroupSn = qestnarGroupSn;
		this.qestnarGroupCd = qestnarGroupCd;
		this.qestnarGroupNm = qestnarGroupNm;
		this.qestnarGroupDc = qestnarGroupDc;
		this.upendGdccSetYn = upendGdccSetYn;
		this.upendGdcc = upendGdcc;
		this.lptGdccSetYn = lptGdccSetYn;
		this.lptGdcc = lptGdcc;
		this.privcyYn = privcyYn;
		this.privcyYnNm = privcyYnNm;
		this.useYn = useYn;
		this.useYnNm = useYnNm;
		this.registerId = registerId;
		this.regDt = regDt;
	}
	


}