package com.meta.ponkids.domain.user.login.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserChldrnSaveReqDto {
	
	private int userChldrnSeq;
	
	private String chldrnNm;
	
	private String chldrnGenderr;
	
	private String chldrnBrdtDate;
	
	private String chldrnEmail;
	
	private String chldrnTelNo;
	
	private String atchFileSn;

}
