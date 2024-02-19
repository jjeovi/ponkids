package com.meta.ponkids.domain.cls.dto;

import com.meta.ponkids.domain.cls.entity.ClassInqry;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassInqryModDto {
	
	private Long	classInqrySn;		// 클래스 문의 일련번호
	
	private Long	classSn;			// 클래스 일련번호
	
	private String	step;				// 계층
	
	private Long	parntsInqrySn;		// 부모 문의 일련번호
	
	private Long	userSn;				// 사용자 일련번호
	
	private String	inqrySj;			// 문의 제목
	
	private String	inqryCn;			// 문의 내용
	
	private String	openYn;				// 공개 여부 ( Y / N ) 
	
    private String updusrId;      	// 수정자 ID
    
    private String updusrIp;      	// 수정자 IP
	
	//builder 생성
	@Builder
	public ClassInqryModDto(Long classInqrySn, Long classSn, String step, Long parntsInqrySn, Long userSn,
			String inqrySj, String inqryCn, String openYn, String updusrId, String updusrIp) {
		this.classInqrySn = classInqrySn;
		this.classSn = classSn;
		this.step = step;
		this.parntsInqrySn = parntsInqrySn;
		this.userSn = userSn;
		this.inqrySj = inqrySj;
		this.inqryCn = inqryCn;
		this.openYn = openYn;
		this.updusrId = updusrId;
		this.updusrIp = updusrIp;
	}
	
	
	
	// Dto to Entity 메소드 생성
	public ClassInqry toEntity() {
		return ClassInqry.builder()
				.classInqrySn(classInqrySn)
				.classSn(classSn)
				.step(step)
				.parntsInqrySn(parntsInqrySn)
				.userSn(userSn)
				.inqrySj(inqrySj)
				.inqryCn(inqryCn)
				.openYn(openYn)
				.updusrId(updusrId)
				.updusrIp(updusrIp)
				.build();
	}
	
	
	// Entity to Dto 메소드는 DTO 내부에서 생성.
	public ClassInqryModDto toDto(ClassInqry classInqry) {
		return ClassInqryModDto.builder()
				.classInqrySn(classInqry.getClassInqrySn())
				.classSn(classInqry.getClassSn())
				.step(classInqry.getStep())
				.parntsInqrySn(classInqry.getParntsInqrySn())
				.userSn(classInqry.getUserSn())
				.inqrySj(classInqry.getInqrySj())
				.inqryCn(classInqry.getInqryCn())
				.openYn(classInqry.getOpenYn())
				.updusrId(classInqry.getUpdusrId())
				.updusrIp(classInqry.getUpdusrIp())
				.build();
	}



}
