package com.meta.ponkids.domain.cls.dto;

import com.meta.ponkids.domain.cls.entity.ClassDetail;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO Dto 항목 setting
@Data
@NoArgsConstructor
public class ClassDetailModDto {
	
	
	private Long classDetailSn;
	
    private String updusrId;      	// 수정자 ID
    
    private String updusrIp;      	// 수정자 IP
	
	// TODO 생성자();
	//builder 생성
	@Builder
	public ClassDetailModDto (Long classDetailSn, String updusrId, String updusrIp) {
		this.classDetailSn = classDetailSn;
		this.updusrId = updusrId;
		this.updusrIp = updusrIp;
	}
	
	
	// TODO toEntity();
	// Dto to Entity 메소드 생성
	public ClassDetail toEntity() {
		return ClassDetail.builder()
				.classDetailSn(classDetailSn)
				.updusrId(updusrId)
				.updusrIp(updusrIp)
				.build();
	}
	
	
	//TODO toDto();
	// Entity to Dto 메소드는 DTO 내부에서 생성.
	public ClassDetailModDto toDto(ClassDetail classDetail) {
		return ClassDetailModDto.builder()
				.classDetailSn(classDetail.getClassDetailSn())
				.updusrId(classDetail.getUpdusrId())
				.updusrIp(classDetail.getUpdusrIp())
				.build();
	}

}
