package com.meta.ponkids.domain.cls.dto;

import com.meta.ponkids.domain.cls.entity.ClassLike;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassLikeSaveDto {
	
	private Long	classLikeSn;		// 클래스 관심 일련번호
	
	private Long	classSn;			// 클래스 일련번호
	
	private Long	userSn;				// 사용자 일련번호
	
	private String	registerId;      // 등록자 id
	
	private String	registerIp;      // 등록자 ip
	
	private String	updusrId;      	// 수정자 id
	
	private String	updusrIp;      	// 수정자 ip
	
	
	@Builder
	@QueryProjection
	public ClassLikeSaveDto(Long classLikeSn, Long classSn, Long userSn, 
			String registerId, String registerIp, String updusrId, String updusrIp) {
		this.classLikeSn = classLikeSn;
		this.classSn = classSn;
		this.userSn = userSn;
		this.registerId = registerId;
		this.registerIp = registerIp;
		this.updusrId = updusrId;
		this.updusrIp = updusrIp;
	}
	
	
	// Entity to Dto 메소드는 DTO 내부에서 생성
	public ClassLikeSaveDto toDto( ClassLike classLike ) {
		
		return ClassLikeSaveDto.builder()
				.classLikeSn( classLike.getClassLikeSn() )
				.classSn( classLike.getClassSn() )
				.userSn( classLike.getUserSn() )
				.build();
	}
	

	// Dto to Entity 메소드 생성
	public ClassLike toEntity() {
		return ClassLike.builder()
				.classLikeSn(classLikeSn)
				.classSn(classSn)
				.userSn(userSn)
				.registerId(registerId)
				.registerIp(registerIp)
				.updusrId(updusrId)
				.updusrIp(updusrIp)
				.build();
	}
	

}