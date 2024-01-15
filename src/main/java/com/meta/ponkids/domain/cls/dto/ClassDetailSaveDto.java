package com.meta.ponkids.domain.cls.dto;

import com.meta.ponkids.domain.cls.entity.ClassDetail;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODO Dto 항목 setting
@Data
@NoArgsConstructor
public class ClassDetailSaveDto {
	
	private Long classDetailSn;
	
	private String registerId;      // 등록자 id
	
	private String registerIp;      // 등록자 ip
	
	private String updusrId;      	// 수정자 id
	
	private String updusrIp;      	// 수정자 ip
	
	// TODO 생성자();
	@Builder
	public ClassDetailSaveDto(Long classDetailSn, String registerId, String registerIp, String updusrId, String updusrIp ) {
		this.classDetailSn = classDetailSn;
		this.registerId = registerId;
		this.registerIp = registerIp;
		this.updusrId = updusrId;
		this.updusrIp = updusrIp;
	}
	
	// TODO toEntity();
	// Dto to Entity 메소드 생성
	public ClassDetail toEntity() {
		return ClassDetail.builder()
				.classDetailSn(classDetailSn)
				.registerId(registerId)
				.registerIp(registerIp)
				.updusrId(updusrId)
				.updusrIp(updusrIp)
				.build();
	}

}
