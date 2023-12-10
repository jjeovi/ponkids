package com.meta.ponkids.domain.system.menu.dto;

import java.util.List;

import com.meta.ponkids.domain.system.menu.entity.Menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {
	
	private Long menuSn;
	
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
	
	private List<Long> roleSnList;		// 메뉴권한 (리스트)
	
	private String updusrId;      	// 수정자 ID
	
	private String updusrIp;      	// 수정자 IP
	
	private String delYn;           // 삭제여부
	
}
