package com.meta.ponkids.domain.system.menu.dto;

import com.meta.ponkids.global.common.dto.CategoryDto;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuListDto {
	
	private Long menuSn;
	private Long id;		// menuSn 과 id 는 같은 값으로 mapping (jstree 의 변수 id를 매핑하기 위한 임시 변수)
	
	private Long roleSn;
	
	private Long upperMenuSn;
	private String parent;	// upperMenuSn 과 parent 는 같은 값으로 mapping (jstree 의 변수 parent를 매핑하기 위한 임시 변수)
	
	private String menuNm;
	private String text;	// menuNm 과 text 는 같은 값으로 mapping ( jstree 의 변수 text를 매핑하기 위한 임시 변수 )
	
	private String menuPath;
	
	private String hierarchy;
	
	private String requiredMenu;
	
	private Long childMenuCnt;	// 자녀 메뉴 개수
	
	private String menuCd;
	
	private String menuUrl;
	
	private String parntsMenuYn;
	private String types;	// parntsMenuYn 과 types 는 같은 값으로 mapping ( jstree 의 변수 types를 매핑하기 위한 임시 변수 )
	
	private Long menuSeq;
	
	private String menuDcSetYn;
	
	private String menuDc;
	
	private String menuDetailDc;
	
	private Long atchFileSn;
	
	private String useYn;
	
	private String newWindowYn;
	
	private Long level;
	
	private String schOption;   // 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
	
	private String schCntn;     // 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
	
	private CategoryDto category;    // 카테고리 검색 : 생성자에는 추가하지 않음!
	
	@QueryProjection
	public MenuListDto( Long menuSn, Long id,  Long roleSn, Long upperMenuSn, String parent, String menuNm, String text, String menuPath, String hierarchy, String requiredMenu, Long childMenuCnt, String menuCd, String menuUrl, String parntsMenuYn, String types, Long menuSeq, String menuDcSetYn, String menuDc, String menuDetailDc, Long atchFileSn, String useYn, String newWindowYn, Long level) {
		this.menuSn = menuSn;
		this.id = id;
		this.roleSn = roleSn;
		this.upperMenuSn = upperMenuSn;
		this.parent = parent;
		this.menuNm = menuNm;
		this.text = text;
		this.menuPath = menuPath;
		this.hierarchy = hierarchy;
		this.requiredMenu = requiredMenu;
		this.childMenuCnt = childMenuCnt;
		this.menuCd = menuCd;
		this.menuUrl = menuUrl;
		this.parntsMenuYn = parntsMenuYn;
		this.types = types;
		this.menuSeq = menuSeq;
		this.menuDcSetYn = menuDcSetYn;
		this.menuDc = menuDc;
		this.menuDetailDc = menuDetailDc;
		this.atchFileSn = atchFileSn;
		this.useYn = useYn;
		this.newWindowYn = newWindowYn;
		this.level = level;
	}
}

