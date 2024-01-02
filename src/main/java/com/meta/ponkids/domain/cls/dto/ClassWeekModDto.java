package com.meta.ponkids.domain.cls.dto;

import com.meta.ponkids.domain.cls.entity.ClassWeek;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO Dto 항목 setting
@Data
@NoArgsConstructor
public class ClassWeekModDto {
	
	
	private Long classWeekSn;		// 클래스 요일 일련번호
	
	private Long classSn;           // 클래스 일련번호
	
	private String classDayCd;      // 클래스 요일 코드
	
    private String updusrId;      	// 수정자 ID
    
    private String updusrIp;      	// 수정자 IP
	
	// TODO 생성자();
	//builder 생성
	@Builder
	public ClassWeekModDto (Long classWeekSn,Long classSn, String classDayCd, String updusrId, String updusrIp) {
		this.classWeekSn 	= classWeekSn;
		this.classSn 		= classSn;
		this.classDayCd 	= classDayCd;
		this.updusrId 		= updusrId;
		this.updusrIp 		= updusrIp;
	}
	
	// TODO toEntity();
	// Dto to Entity 메소드 생성
	public ClassWeek toEntity() {
		return ClassWeek.builder()
				.classWeekSn(classWeekSn)
				.updusrId(updusrId)
				.updusrIp(updusrIp)
				.build();
	}
	
	//TODO toDto();
	// Entity to Dto 메소드는 DTO 내부에서 생성.
	public ClassWeekModDto toDto(ClassWeek classWeek) {
		return ClassWeekModDto.builder()
				.classWeekSn(classWeek.getClassWeekSn())
				.updusrId(classWeek.getUpdusrId())
				.updusrIp(classWeek.getUpdusrIp())
				.build();
	}

}
