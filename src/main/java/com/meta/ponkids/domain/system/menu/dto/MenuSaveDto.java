package com.meta.ponkids.domain.system.menu.dto;

import com.meta.ponkids.domain.system.menu.entity.Menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = false )
public class MenuSaveDto extends MenuDto {
    
    private Long menuSn;
    
    private Long upperMenuSn;
    
    private String menuNm;
    
    private String menuCd;
    
    private String menuUrl;
    
    private String parntsMenuYn;
    
    private Long menuSeq;
    
    private String menuDcSetYn;
    
    private String menuDc;
    
    private String menuDetailDc;
    
    private Long atchFileSn;
    
    private String useYn;
    
    private String newWindowYn;
    
    private String registerId;      // 등록자 ID
    
    private String registerIp;      // 등록자 IP
    
    private String delYn;           // 삭제여부
    
    private String updusrId;            // 수정자 ID
    
    private String updusrIp;            // 수정자 IP
    
    @Builder
    public MenuSaveDto( Long menuSn, Long upperMenuSn, String menuNm, String menuCd, String menuUrl, String parntsMenuYn, Long menuSeq,
                        String menuDcSetYn, String menuDc, String menuDetailDc, Long atchFileSn, String useYn, String newWindowYn, String registerId, String registerIp, String updusrId, String updusrIp ) {
        this.menuSn = menuSn;
        this.upperMenuSn = upperMenuSn;
        this.menuNm = menuNm;
        this.menuCd = menuCd;
        this.menuUrl = menuUrl;
        this.parntsMenuYn = parntsMenuYn;
        this.menuSeq = menuSeq;
        this.menuDcSetYn = menuDcSetYn;
        this.menuDc = menuDc;
        this.menuDetailDc = menuDetailDc;
        this.atchFileSn = atchFileSn;
        this.useYn = useYn;
        this.newWindowYn = newWindowYn;
        this.registerId = registerId;
        this.registerIp = registerIp;
        this.updusrId = updusrId;
        this.updusrIp = updusrIp;
    }
    
    // Dto to Entity 메소드 생성
    public Menu toEntity() {
        return Menu.builder()
                .menuSn( menuSn )
                .upperMenuSn( upperMenuSn )
                .menuNm( menuNm )
                .menuCd( menuCd )
                .menuUrl( menuUrl )
                .parntsMenuYn( parntsMenuYn )
                .menuSeq( menuSeq )
                .menuDcSetYn( menuDcSetYn )
                .menuDc( menuDc )
                .menuDetailDc( menuDetailDc )
                .atchFileSn( atchFileSn )
                .useYn( useYn )
                .newWindowYn( newWindowYn )
                .registerId( registerId )
                .registerIp( registerIp )
                .updusrId( updusrId )
                .updusrIp( updusrIp )
                .build();
    }
    
}
