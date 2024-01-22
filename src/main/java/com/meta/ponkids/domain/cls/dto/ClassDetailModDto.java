package com.meta.ponkids.domain.cls.dto;

import com.meta.ponkids.domain.cls.entity.ClassDetail;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassDetailModDto {
	
	private Long 	classDetailSn;					// 클래스 상세 일련번호
	
	private Long 	classSn;						// 클래스 일련번호
	
	private Long 	classDetailSeq;					// 클래스 상세 순번
	
	private String 	classDetailItemTyCd;			// 클래스 상세 항목 유형 코드
	
	private String 	classDetailItemCn;				// 클래스 상세 항목
	
	private String 	classDetailEssntlYn;			// 클래스 상세 필수 여부
	
    private String 	updusrId;      					// 수정자 ID
    
    private String 	updusrIp;      					// 수정자 IP
	
	@Builder
	public ClassDetailModDto(Long classDetailSn, Long classSn, Long classDetailSeq, String classDetailItemTyCd,
			String classDetailItemCn, String classDetailEssntlYn, String updusrId, String updusrIp) {
		super();
		this.classDetailSn = classDetailSn;
		this.classSn = classSn;
		this.classDetailSeq = classDetailSeq;
		this.classDetailItemTyCd = classDetailItemTyCd;
		this.classDetailItemCn = classDetailItemCn;
		this.classDetailEssntlYn = classDetailEssntlYn;
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
				.updusrId(updusrId)
				.updusrIp(updusrIp)
				.build();
	}
	
	
	// Entity to Dto 메소드는 DTO 내부에서 생성.
	public ClassDetailModDto toDto(ClassDetail classDetail) {
		return ClassDetailModDto.builder()
				.classDetailSn(classDetail.getClassDetailSn())
				.classSn(classDetail.getClassSn())
				.classDetailSeq(classDetail.getClassDetailSeq())
				.classDetailItemTyCd(classDetail.getClassDetailItemTyCd())
				.classDetailItemCn(classDetail.getClassDetailItemCn())
				.classDetailEssntlYn(classDetail.getClassDetailEssntlYn())
				.updusrId(classDetail.getUpdusrId())
				.updusrIp(classDetail.getUpdusrIp())
				.build();
	}


}
