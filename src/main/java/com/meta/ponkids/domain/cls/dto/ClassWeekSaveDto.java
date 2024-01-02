package com.meta.ponkids.domain.cls.dto;

import com.meta.ponkids.domain.cls.entity.ClassWeek;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODO Dto 항목 setting
@Data
@NoArgsConstructor
public class ClassWeekSaveDto {
	
	private Long classWeekSn;		// 클래스 요일 일련번호
	
	private Long classSn;           // 클래스 일련번호
	
	private String classDayCd;      // 클래스 요일 코드
	
	private String registerId;      // 등록자 id
	
	private String registerIp;      // 등록자 ip
	
	private String updusrId;      	// 수정자 id
	
	private String updusrIp;      	// 수정자 ip
	
	// TODO 생성자();
	@Builder
	public ClassWeekSaveDto(Long classWeekSn, String registerId, String registerIp, String updusrId, String updusrIp ) {
		this.classWeekSn = classWeekSn;
		this.registerId = registerId;
		this.registerIp = registerIp;
		this.updusrId = updusrId;
		this.updusrIp = updusrIp;
	}
	
	// TODO toEntity();
	// Dto to Entity 메소드 생성
	public ClassWeek toEntity() {
		return ClassWeek.builder()
				.classWeekSn(classWeekSn)
				.registerId(registerId)
				.registerIp(registerIp)
				.updusrId(updusrId)
				.updusrIp(updusrIp)
				.build();
	}

}
