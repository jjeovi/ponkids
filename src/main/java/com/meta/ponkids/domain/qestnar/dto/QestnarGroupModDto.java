package com.meta.ponkids.domain.qestnar.dto;

import java.util.List;

import com.meta.ponkids.domain.qestnar.entity.QestnarGroup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = false )
public class QestnarGroupModDto extends QestnarGroupDto {

	private Long qestnarGroupSn;

	private String qestnarGroupCd;
	
	private String qestnarGroupNm;

	private String qestnarGroupDc;
	
	private String upendGdccSetYn;	// 상단 안내문 설정 여부
	
	private String upendGdcc;		// 상단 안내문
	
	private String lptGdccSetYn;	// 상단 안내문 설정 여부
	
	private String lptGdcc;			// 하단 안내문

	private String privcyYn;
	
	private String loginEssntlYn;	// 로그인 필수 여부
	
	private String replySetYn;		// 댓글 설정 여부

	private String useYn;

	private String updusrId;      	// 수정자 ID

	private String updusrIp;      	// 수정자 IP
	
	private List<QestnarQestnSaveDto>		qestnarQestns;		// 입력 항목
    
	//builder 생성
	@Builder
	public QestnarGroupModDto(Long qestnarGroupSn, String qestnarGroupCd,  String qestnarGroupNm, String qestnarGroupDc,
			String upendGdccSetYn, String upendGdcc, String lptGdccSetYn, String lptGdcc,
			String privcyYn, String loginEssntlYn, String replySetYn, String useYn, String updusrId, String updusrIp) {
		this.qestnarGroupSn = qestnarGroupSn;
		this.qestnarGroupCd = qestnarGroupCd;
		this.qestnarGroupNm = qestnarGroupNm;
		this.qestnarGroupDc = qestnarGroupDc;
		this.upendGdccSetYn = upendGdccSetYn;
		this.upendGdcc 		= upendGdcc;
		this.lptGdccSetYn 	= lptGdccSetYn;
		this.lptGdcc 		= lptGdcc;
		this.privcyYn 		= privcyYn;
		this.loginEssntlYn 	= loginEssntlYn;
		this.replySetYn 	= replySetYn;
		this.useYn 			= useYn;
		this.updusrId		= updusrId;
		this.updusrIp 		= updusrIp;
	}
	
	// Dto to Entity 메소드 생성
	public QestnarGroup toEntity() {
		return QestnarGroup.builder()
				.qestnarGroupSn(qestnarGroupSn)
				.qestnarGroupCd(qestnarGroupCd)
				.qestnarGroupNm(qestnarGroupNm)
				.qestnarGroupDc(qestnarGroupDc)
				.upendGdccSetYn(upendGdccSetYn)
				.upendGdcc(upendGdcc)
				.lptGdccSetYn(lptGdccSetYn)
				.lptGdcc(lptGdcc)
				.privcyYn(privcyYn)
				.loginEssntlYn(loginEssntlYn)
				.replySetYn(replySetYn)
				.useYn(useYn)
				.updusrId(updusrId)
				.updusrIp(updusrIp)
				.build();
	}
	
	// Entity to Dto 메소드는 DTO 내부에서 생성.
	public QestnarGroupModDto toDto(QestnarGroup qestnarGroup) {
		return QestnarGroupModDto.builder()
				.qestnarGroupSn(qestnarGroup.getQestnarGroupSn())
				.qestnarGroupCd(qestnarGroup.getQestnarGroupCd())
				.qestnarGroupNm(qestnarGroup.getQestnarGroupNm())
				.qestnarGroupDc(qestnarGroup.getQestnarGroupDc())
				.upendGdccSetYn(qestnarGroup.getUpendGdccSetYn())
				.upendGdcc(qestnarGroup.getUpendGdcc())
				.lptGdccSetYn(qestnarGroup.getLptGdccSetYn())
				.lptGdcc(qestnarGroup.getLptGdcc())
				.privcyYn(qestnarGroup.getPrivcyYn())
				.loginEssntlYn(qestnarGroup.getLoginEssntlYn())
				.replySetYn(qestnarGroup.getReplySetYn())
				.useYn(qestnarGroup.getUseYn())
				.updusrId(qestnarGroup.getUpdusrId())
				.updusrIp(qestnarGroup.getUpdusrIp())
				.build();
	}

}
