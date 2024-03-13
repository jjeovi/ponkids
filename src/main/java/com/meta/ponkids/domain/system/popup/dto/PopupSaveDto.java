package com.meta.ponkids.domain.system.popup.dto;

import com.meta.ponkids.domain.system.popup.entity.Popup;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PopupSaveDto {
    
    private Long popupSn;
    
    private String popupNm;
    
    private String popupCn;
    
    private String useYn;
    
    private String popupBeginDt;
    
    private String popupEndDt;
    
    private Long atchFileSn;
    
    private String url;
    
    private String registerId;      // 등록자 id
    
    private String registerIp;      // 등록자 ip
    
    private String updusrId;        // 수정자 id
    
    private String updusrIp;        // 수정자 ip
    
    
    @Builder
    public PopupSaveDto( Long popupSn, String popupNm, String popupCn, String useYn, String popupBeginDt, String popupEndDt, Long atchFileSn, String url, String registerId, String registerIp, String updusrId, String updusrIp ) {
        this.popupSn = popupSn;
        this.popupNm = popupNm;
        this.popupCn = popupCn;
        this.useYn = useYn;
        this.popupBeginDt = popupBeginDt;
        this.popupEndDt = popupEndDt;
        this.atchFileSn = atchFileSn;
        this.url = url;
        this.registerId = registerId;
        this.registerIp = registerIp;
        this.updusrId = updusrId;
        this.updusrIp = updusrIp;
    }
    
    // Dto to Entity 메소드 생성
    public Popup toEntity() {
        return Popup.builder()
				.popupSn( popupSn )
				.popupNm( popupNm )
				.popupCn( popupCn )
				.useYn( useYn )
				.popupBeginDt( popupBeginDt )
				.popupEndDt( popupEndDt )
				.atchFileSn( atchFileSn )
				.url( url )
                .registerId( registerId )
                .registerIp( registerIp )
                .updusrId( updusrId )
                .updusrIp( updusrIp )
                .build();
    }
    
}
