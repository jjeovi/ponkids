package com.meta.ponkids.domain.user.dto;

import com.meta.ponkids.domain.user.entity.UserChldrn;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserChldrnSaveDto {
	
	private String userId;
	
	private int userChldrnSeq;
	
	private String chldrnNm;
	
	private String chldrnGender;
	
	private String chldrnBrdtDate;
	
	private String chldrnEmail;
	
	private String chldrnTelNo;
	
	private String atchFileSn;
	
	private String registerId;
	
	private String registerIp;
	
	
	
	@Builder
	public UserChldrnSaveDto( String userId, int userChldrnSeq, String chldrnNm, String chldrnGender, String chldrnBrdtDate, String chldrnEmail, String chldrnTelNo, String atchFileSn, String registerId, String registerIp ) {
		this.userId = userId;
		this.userChldrnSeq = userChldrnSeq;
		this.chldrnNm = chldrnNm;
		this.chldrnGender = chldrnGender;
		this.chldrnBrdtDate = chldrnBrdtDate;
		this.chldrnEmail = chldrnEmail;
		this.chldrnTelNo = chldrnTelNo;
		this.atchFileSn = atchFileSn;
		this.registerId = registerId;
		this.registerIp = registerIp;
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
				.registerId( registerId )
				.registerIp( registerIp )
				.build();
	}
}
