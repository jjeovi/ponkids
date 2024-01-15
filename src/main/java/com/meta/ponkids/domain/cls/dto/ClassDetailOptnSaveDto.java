package com.meta.ponkids.domain.cls.dto;

import com.meta.ponkids.domain.cls.entity.ClassDetailOptn;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODO Dto 항목 setting
@Data
@NoArgsConstructor
public class ClassDetailOptnSaveDto {
	
	private Long classDetailOptnSn;
	
	private String registerId;      // 등록자 id
	
	private String registerIp;      // 등록자 ip
	
	private String updusrId;      	// 수정자 id
	
	private String updusrIp;      	// 수정자 ip
	
	// TODO 생성자();
	@Builder
	public ClassDetailOptnSaveDto(Long classDetailOptnSn, String registerId, String registerIp, String updusrId, String updusrIp ) {
		this.classDetailOptnSn = classDetailOptnSn;
		this.registerId = registerId;
		this.registerIp = registerIp;
		this.updusrId = updusrId;
		this.updusrIp = updusrIp;
	}
	
	// TODO toEntity();
	// Dto to Entity 메소드 생성
	public ClassDetailOptn toEntity() {
		return ClassDetailOptn.builder()
				.classDetailOptnSn(classDetailOptnSn)
				.registerId(registerId)
				.registerIp(registerIp)
				.updusrId(updusrId)
				.updusrIp(updusrIp)
				.build();
	}

}
