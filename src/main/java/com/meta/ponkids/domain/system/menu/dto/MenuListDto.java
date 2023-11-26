package com.meta.ponkids.domain.system.menu.dto;

import com.meta.ponkids.global.common.dto.CategoryDto;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import lombok.NoArgsConstructor;

// TODO Dto 항목 setting
@Data
@NoArgsConstructor
public class MenuListDto {
	
	private Long menuSn;
	
	private Long roleSn;
	
	private Long upperMenuSn;
	
	private String menuNm;
	
	private String menuCd;
	
	private String menuUrl;
	
	private String parntsMenuYn;
	
	private Long menuSeq;
	
	private String menuDcSetYn;
	
	private String menuDc;
	
	private String menuDetailDc;
	
	private Long atchFileSn;
	
	private String useYn;
	
	private String newWindowYn;
	
	private String schOption;   // 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
	
	private String schCntn;     // 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
	
	private CategoryDto category;    // 카테고리 검색 : 생성자에는 추가하지 않음!
	
	@QueryProjection
	public MenuListDto( Long menuSn, Long roleSn, Long upperMenuSn, String menuNm, String menuCd, String menuUrl, String parntsMenuYn, Long menuSeq, String menuDcSetYn, String menuDc, String menuDetailDc, Long atchFileSn, String useYn, String newWindowYn ) {
		this.menuSn = menuSn;
		this.roleSn = roleSn;
		this.upperMenuSn = upperMenuSn;
		this.menuNm = menuNm;
		this.menuCd = menuCd;
		this.menuUrl = menuUrl;
		this.parntsMenuYn = parntsMenuYn;
		this.menuSeq = menuSeq;
		this.menuDcSetYn = menuDcSetYn;
		this.menuDc = menuDc;
		this.menuDetailDc = menuDetailDc;
		this.atchFileSn = atchFileSn;
		this.useYn = useYn;
		this.newWindowYn = newWindowYn;
	}
	
	// TODO 생성자();
	public MenuListDto( Long menuSn, Long roleSn, Long upperMenuSn, String menuNm, String menuCd, String menuUrl, String parntsMenuYn, Long menuSeq, String menuDcSetYn, String menuDc, String menuDetailDc, Long atchFileSn, String useYn, String newWindowYn, String schOption, String schCntn, CategoryDto category ) {
		this.menuSn = menuSn;
		this.roleSn = roleSn;
		this.upperMenuSn = upperMenuSn;
		this.menuNm = menuNm;
		this.menuCd = menuCd;
		this.menuUrl = menuUrl;
		this.parntsMenuYn = parntsMenuYn;
		this.menuSeq = menuSeq;
		this.menuDcSetYn = menuDcSetYn;
		this.menuDc = menuDc;
		this.menuDetailDc = menuDetailDc;
		this.atchFileSn = atchFileSn;
		this.useYn = useYn;
		this.newWindowYn = newWindowYn;
		this.schOption = schOption;
		this.schCntn = schCntn;
		this.category = category;
	}
}