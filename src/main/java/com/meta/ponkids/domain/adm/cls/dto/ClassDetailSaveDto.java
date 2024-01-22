package com.meta.ponkids.domain.adm.cls.dto;

import com.meta.ponkids.domain.adm.cls.entity.ClassDetail;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassDetailSaveDto {
	
	private Long 	classDetailSn;					// 클래스 상세 일련번호
	
	private Long 	classSn;						// 클래스 일련번호
	
	private Long 	classDetailSeq;					// 클래스 상세 순번
	
	private String 	classDetailItemTyCd;			// 클래스 상세 항목 유형 코드
	
	private String 	classDetailItemCn;				// 클래스 상세 항목
	
	private String 	classDetailEssntlYn;			// 클래스 상세 필수 여부
	
	private String 	registerId;      				// 등록자 id
	
	private String 	registerIp;      				// 등록자 ip
	
	private String 	updusrId;      					// 수정자 id
	
	private String 	updusrIp;      					// 수정자 ip
	
	@Builder
	public ClassDetailSaveDto(Long classDetailSn, Long classSn, Long classDetailSeq, String classDetailItemTyCd,
			String classDetailItemCn, String classDetailEssntlYn, String registerId, String registerIp, String updusrId,
			String updusrIp) {

		this.classDetailSn = classDetailSn;
		this.classSn = classSn;
		this.classDetailSeq = classDetailSeq;
		this.classDetailItemTyCd = classDetailItemTyCd;
		this.classDetailItemCn = classDetailItemCn;
		this.classDetailEssntlYn = classDetailEssntlYn;
		this.registerId = registerId;
		this.registerIp = registerIp;
		this.updusrId = updusrId;
		this.updusrIp = updusrIp;
	}

	// Dto to Entity 메소드 생성
	public ClassDetail toEntity() {
		return ClassDetail.builder()
				.classDetailSn(classDetailSn)
				.classSn(classSn)
				.classDetailSeq(classDetailSeq)
				.classDetailItemTyCd(classDetailItemTyCd)
				.classDetailItemCn(classDetailItemCn)
				.classDetailEssntlYn(classDetailEssntlYn)
				.registerId(registerId)
				.registerIp(registerIp)
				.updusrId(updusrId)
				.updusrIp(updusrIp)
				.build();
	}
}
