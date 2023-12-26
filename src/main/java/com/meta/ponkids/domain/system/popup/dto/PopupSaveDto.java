package com.meta.ponkids.domain.system.popup.dto;

import com.meta.ponkids.domain.system.popup.entity.Popup;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODO Dto 항목 setting
@Data
@NoArgsConstructor
public class PopupSaveDto {
	
	private Long popupSn;
	
	private String registerId;      // 등록자 id
	
	private String registerIp;      // 등록자 ip
	
    private String updusrId;      			// 수정자 ID
    
    private String updusrIp;      			// 수정자 IP
	
	// TODO 생성자();
	@Builder
	public PopupSaveDto(Long popupSn) {
		this.popupSn = popupSn;
	}
	
	// TODO toEntity();
	// Dto to Entity 메소드 생성
	public Popup toEntity() {
		return Popup.builder()
				.popupSn(popupSn)
				.build();
	}

}
