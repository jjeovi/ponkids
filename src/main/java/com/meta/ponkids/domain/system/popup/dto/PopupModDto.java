package com.meta.ponkids.domain.system.popup.dto;

import com.meta.ponkids.domain.system.popup.entity.Popup;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO Dto 항목 setting
@Data
@NoArgsConstructor
public class PopupModDto {
    
    
    private Long popupSn;
    
    private String updusrId;        // 수정자 ID
    
    private String updusrIp;        // 수정자 IP
    
    // TODO 생성자();
    //builder 생성
    @Builder
    public PopupModDto( Long popupSn ) {
        this.popupSn = popupSn;
    }
    
    
    // TODO toEntity();
    // Dto to Entity 메소드 생성
    public Popup toEntity() {
        return Popup.builder()
                .popupSn( popupSn )
                .build();
    }
    
    
    //TODO toDto();
    // Entity to Dto 메소드는 DTO 내부에서 생성.
    public PopupModDto toDto( Popup popup ) {
        return PopupModDto.builder()
                .popupSn( popup.getPopupSn() )
                .build();
    }
    
}
