package com.meta.ponkids.domain.system.role.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleListDto {
	
	private Long roleSn;			// 권한 일련번호
	
	private String roleNm;			// 권한 이름
	
	private String roleDc;			// 권한 설명

	@QueryProjection
	public RoleListDto(Long roleSn, String roleNm, String roleDc) {
		this.roleSn = roleSn;
		this.roleNm = roleNm;
		this.roleDc = roleDc;
	}

}