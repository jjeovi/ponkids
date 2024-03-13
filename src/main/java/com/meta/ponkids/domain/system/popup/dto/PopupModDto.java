package com.meta.ponkids.domain.system.popup.dto;

import com.meta.ponkids.domain.system.popup.entity.Popup;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PopupModDto {
    
    
    private Long popupSn;
    
    private String popupNm;
    
    private String popupCn;
    
    private String useYn;
    
    private String popupBeginDt;
    
    private String popupEndDt;
    
    private Long atchFileSn;
    
    private Long    atchFileSnOri;          // 첨부 파일 일련번호(비교용)
    
    private String url;
    
    private String updusrId;        // 수정자 ID
    
    private String updusrIp;        // 수정자 IP
    
    //builder 생성
    @Builder
    public PopupModDto( Long popupSn, String popupNm, String popupCn, String useYn, String popupBeginDt,
                        String popupEndDt, Long atchFileSn, String url, String updusrId, String updusrIp ) {
        this.popupSn = popupSn;
        this.popupNm = popupNm;
        this.popupCn = popupCn;
        this.useYn = useYn;
        this.popupBeginDt = popupBeginDt;
        this.popupEndDt = popupEndDt;
        this.atchFileSn = atchFileSn;
        this.url = url;
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
                .updusrId( updusrId )
                .updusrIp( updusrIp )
                .build();
    }
    
    // Entity to Dto 메소드는 DTO 내부에서 생성.
    public PopupModDto toDto( Popup popup ) {
        return PopupModDto.builder()
				.popupSn( popup.getPopupSn() )
				.popupNm( popup.getPopupNm() )
				.popupCn( popup.getPopupCn() )
				.useYn( popup.getUseYn() )
				.popupBeginDt( popup.getPopupBeginDt() )
				.popupEndDt( popup.getPopupEndDt() )
				.atchFileSn( popup.getAtchFileSn() )
				.url( popup.getUrl() )
                .updusrId( popup.getUpdusrId() )
                .updusrIp( popup.getUpdusrIp() )
                .build();
    }
    
}
