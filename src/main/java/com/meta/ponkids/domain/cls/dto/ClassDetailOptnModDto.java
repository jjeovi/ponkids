package com.meta.ponkids.domain.cls.dto;

import com.meta.ponkids.domain.cls.entity.ClassDetailOptn;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassDetailOptnModDto {
	
	private Long 	classDetailOptnSn;				// 클래스 상세 옵션 일련번호
	
	private Long 	classDetailSn;					// 클래스 상세 일련번호
	
	private Long 	classDetailOptnSeq;				// 클래스 상세 옵션 순번
	
	private String 	classsDetailOptnCn;				// 클래스 상세 옵션 내용
	
    private String 	updusrId;      					// 수정자 ID
    
    private String 	updusrIp;      					// 수정자 IP
	
	//builder 생성
	@Builder
	public ClassDetailOptnModDto(Long classDetailOptnSn, Long classDetailSn, Long classDetailOptnSeq,
			String classsDetailOptnCn, String updusrId, String updusrIp) {

		this.classDetailOptnSn = classDetailOptnSn;
		this.classDetailSn = classDetailSn;
		this.classDetailOptnSeq = classDetailOptnSeq;
		this.classsDetailOptnCn = classsDetailOptnCn;
		this.updusrId = updusrId;
		this.updusrIp = updusrIp;
	}
	
	
	// Dto to Entity 메소드 생성
	public ClassDetailOptn toEntity() {
		return ClassDetailOptn.builder()
				.classDetailOptnSn(classDetailOptnSn)
				.classDetailSn(classDetailSn)
				.classDetailOptnSeq(classDetailOptnSeq)
				.classsDetailOptnCn(classsDetailOptnCn)
				.updusrId(updusrId)
				.updusrIp(updusrIp)
				.build();
	}
	
	
	// Entity to Dto 메소드는 DTO 내부에서 생성.
	public ClassDetailOptnModDto toDto(ClassDetailOptn classDetailOptn) {
		return ClassDetailOptnModDto.builder()
				.classDetailOptnSn(classDetailOptn.getClassDetailOptnSn())
				.classDetailSn(classDetailOptn.getClassDetailSn())
				.classDetailOptnSeq(classDetailOptn.getClassDetailOptnSeq())
				.classsDetailOptnCn(classDetailOptn.getClasssDetailOptnCn())
				.updusrId(classDetailOptn.getUpdusrId())
				.updusrIp(classDetailOptn.getUpdusrIp())
				.build();
	}

}
