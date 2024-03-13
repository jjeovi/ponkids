package com.meta.ponkids.domain.system.popup.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PopupListDto {
	
	private Long 	popupSn;
	
	private String 	popupNm;
	
	private String 	popupCn;
	
	private String	useYn;
	
	private String	popupBeginDt;
	
	private String	popupEndDt;
	
	private String 		popupPeriod;		// 팝업기간
	
	private Long	atchFileSn;
	
	private String	url;
	
    private String	registerId;      // 등록자 ID
    
    private String	regDt;	// 등록일자
	
	private String	schOption;   	// 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String	schCntn;     	// 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
	
	
	@QueryProjection
	public PopupListDto( Long popupSn, String popupNm, String popupCn, String useYn, String popupBeginDt, String popupEndDt, String popupPeriod, Long atchFileSn, String url, String registerId, String regDt ) {
		this.popupSn = popupSn;
		this.popupNm = popupNm;
		this.popupCn = popupCn;
		this.useYn = useYn;
		this.popupBeginDt = popupBeginDt;
		this.popupEndDt = popupEndDt;
		this.popupPeriod = popupPeriod;
		this.atchFileSn = atchFileSn;
		this.url = url;
		this.registerId = registerId;
		this.regDt = regDt;
	}


}