package com.meta.ponkids.domain.cls.dto;

import com.meta.ponkids.domain.cls.entity.ClassDetailOptn;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO Dto 항목 setting
@Data
@NoArgsConstructor
public class ClassDetailOptnModDto {
	
	
	private Long classDetailOptnSn;
	
    private String updusrId;      	// 수정자 ID
    
    private String updusrIp;      	// 수정자 IP
	
	// TODO 생성자();
	//builder 생성
	@Builder
	public ClassDetailOptnModDto (Long classDetailOptnSn, String updusrId, String updusrIp) {
		this.classDetailOptnSn = classDetailOptnSn;
		this.updusrId = updusrId;
		this.updusrIp = updusrIp;
	}
	
	
	// TODO toEntity();
	// Dto to Entity 메소드 생성
	public ClassDetailOptn toEntity() {
		return ClassDetailOptn.builder()
				.classDetailOptnSn(classDetailOptnSn)
				.updusrId(updusrId)
				.updusrIp(updusrIp)
				.build();
	}
	
	
	//TODO toDto();
	// Entity to Dto 메소드는 DTO 내부에서 생성.
	public ClassDetailOptnModDto toDto(ClassDetailOptn classDetailOptn) {
		return ClassDetailOptnModDto.builder()
				.classDetailOptnSn(classDetailOptn.getClassDetailOptnSn())
				.updusrId(classDetailOptn.getUpdusrId())
				.updusrIp(classDetailOptn.getUpdusrIp())
				.build();
	}

}
