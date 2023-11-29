package com.meta.ponkids.domain.system.menu.dto;

import java.util.List;

import com.meta.ponkids.domain.system.menu.entity.Menu;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO Dto 항목 setting
@Data
@NoArgsConstructor
public class MenuModDto {
	
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
	
	private List<Long> roleSnList;
	
	private String updusrId;      	// 수정자 ID
	   
	private String updusrIp;      	// 수정자 IP
	    
	private String delYn;           // 삭제여부
	
	//builder 생성
	@Builder
	public MenuModDto( Long menuSn, Long upperMenuSn, String menuNm, String menuCd, String menuUrl, String parntsMenuYn, Long menuSeq,
			String menuDcSetYn, String menuDc, String menuDetailDc, Long atchFileSn, String useYn, String newWindowYn, String updusrId, String updusrIp, String delYn ) {
		super();
		this.menuSn = menuSn;
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
        this.updusrId = updusrId;
        this.updusrIp = updusrIp;
        this.delYn = delYn;
	}
	
	// Dto to Entity 메소드 생성
	public Menu toEntity() {
		return Menu.builder()
				.menuSn(menuSn)
				.upperMenuSn(upperMenuSn)
				.menuNm(menuNm)
				.menuCd(menuCd)
				.menuUrl(menuUrl)
				.parntsMenuYn(parntsMenuYn)
				.menuSeq(menuSeq)
				.menuDcSetYn(menuDcSetYn)
				.menuDc(menuDc)
				.menuDetailDc(menuDetailDc)
				.atchFileSn(atchFileSn)
				.useYn(useYn)
				.newWindowYn(newWindowYn)
				.updusrId( updusrId)
                .updusrIp( updusrIp)
				.build();
	}
	
	// Entity to Dto 메소드는 DTO 내부에서 생성.
	public MenuModDto toDto(Menu menu) {
		return MenuModDto.builder()
				.menuSn(menu.getMenuSn())
				.upperMenuSn(menu.getUpperMenuSn())
				.menuNm(menu.getMenuNm())
				.menuCd(menu.getMenuCd())
				.menuUrl(menu.getMenuUrl())
				.parntsMenuYn(menu.getParntsMenuYn())
				.menuSeq(menu.getMenuSeq())
				.menuDcSetYn(menu.getMenuDcSetYn())
				.menuDc(menu.getMenuDc())
				.menuDetailDc(menu.getMenuDetailDc())
				.atchFileSn(menu.getAtchFileSn())
				.useYn(menu.getUseYn())
				.newWindowYn(menu.getNewWindowYn())
				.updusrId(menu.getUpdusrId())
                .updusrIp(menu.getUpdusrIp())
				.build();
	}


}
