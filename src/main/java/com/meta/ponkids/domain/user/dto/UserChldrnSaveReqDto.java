package com.meta.ponkids.domain.user.dto;

import com.meta.ponkids.domain.user.entity.UserChldrn;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@Data
public class UserChldrnSaveReqDto {
	
	private String userId;
	
	private int userChldrnSeq;
	
	private String chldrnNm;
	
	private String chldrnGender;
	
	private String chldrnBrdtDate;
	
	private String chldrnEmail;
	
	private String chldrnTelNo;
	
	private String atchFileSn;
	
	@Builder
	public UserChldrnSaveReqDto( String userId, int userChldrnSeq, String chldrnNm, String chldrnGender, String chldrnBrdtDate, String chldrnEmail, String chldrnTelNo, String atchFileSn ) {
		this.userId = userId;
		this.userChldrnSeq = userChldrnSeq;
		this.chldrnNm = chldrnNm;
		this.chldrnGender = chldrnGender;
		this.chldrnBrdtDate = chldrnBrdtDate;
		this.chldrnEmail = chldrnEmail;
		this.chldrnTelNo = chldrnTelNo;
		this.atchFileSn = atchFileSn;
	}
	
	
	// DTO to Entity 메소드는 DTO 내부에서 생성.
	public UserChldrn toEntity() {
		return UserChldrn.builder()
				.userChldrnSeq( userChldrnSeq )
				.userId( userId )
				.chldrnNm( chldrnNm )
				.chldrnGender( chldrnGender )
				.chldrnBrdtDate( getChldrnBrdtDate() )
				.chldrnEmail( chldrnEmail )
				.chldrnTelNo( chldrnTelNo )
				.atchFileSn( atchFileSn )
				.build();
	}
}
