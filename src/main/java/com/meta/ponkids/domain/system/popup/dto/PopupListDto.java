package com.meta.ponkids.domain.system.popup.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO Dto 항목 setting
@Data
@NoArgsConstructor
public class PopupListDto {
    
    private Long popupSn;
    
    private String schOption;   // 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String schCntn;     // 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
    
    // TODO 생성자();
    @QueryProjection
    public PopupListDto( Long popupSn ) {
        this.popupSn = popupSn;
    }
    
}