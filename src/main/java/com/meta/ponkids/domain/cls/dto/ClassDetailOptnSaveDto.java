package com.meta.ponkids.domain.cls.dto;

import com.meta.ponkids.domain.cls.entity.ClassDetailOptn;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassDetailOptnSaveDto {
	
	private Long 	classDetailOptnSn;				// 클래스 상세 옵션 일련번호
	
	private Long 	classDetailSn;					// 클래스 상세 일련번호
	
	private Long 	classDetailOptnSeq;				// 클래스 상세 옵션 순번
	
	private String 	classsDetailOptnCn;				// 클래스 상세 옵션 내용
	
	private String registerId;      				// 등록자 id
	
	private String registerIp;      				// 등록자 ip
	
	private String updusrId;      					// 수정자 id
	
	private String updusrIp;      					// 수정자 ip
	
	@Builder
	public ClassDetailOptnSaveDto(Long classDetailOptnSn, Long classDetailSn, Long classDetailOptnSeq,
			String classsDetailOptnCn, String registerId, String registerIp, String updusrId, String updusrIp) {
		
		this.classDetailOptnSn = classDetailOptnSn;
		this.classDetailSn = classDetailSn;
		this.classDetailOptnSeq = classDetailOptnSeq;
		this.classsDetailOptnCn = classsDetailOptnCn;
		this.registerId = registerId;
		this.registerIp = registerIp;
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
				.registerId(registerId)
				.registerIp(registerIp)
				.updusrId(updusrId)
				.updusrIp(updusrIp)
				.build();
	}

}
