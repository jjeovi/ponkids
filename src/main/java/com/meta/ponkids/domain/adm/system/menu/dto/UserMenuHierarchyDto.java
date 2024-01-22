package com.meta.ponkids.domain.adm.system.menu.dto;

import com.meta.ponkids.domain.adm.system.menu.entity.UserMenuHierarchy;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserMenuHierarchyDto {
    
    private Long menuSn;
    
    private Long upperMenuSn;
    
    private String menuNm;
    
    private String menuPath;
    
    private String hierarchy;
    
    private String requiredMenu;
    
    private Long childMenuCnt;
    
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
    
    private String delYn;
    
    private Long level;
    
    @Builder
    public UserMenuHierarchyDto( Long menuSn, Long upperMenuSn, String menuNm, String menuPath, String hierarchy,
                                 String requiredMenu, Long childMenuCnt, String menuCd, String menuUrl, String parntsMenuYn,
                                 Long menuSeq, String menuDcSetYn, String menuDc, String menuDetailDc, Long atchFileSn, String useYn,
                                 String newWindowYn, String delYn, Long level ) {
        this.menuSn = menuSn;
        this.upperMenuSn = upperMenuSn;
        this.menuNm = menuNm;
        this.menuPath = menuPath;
        this.hierarchy = hierarchy;
        this.requiredMenu = requiredMenu;
        this.childMenuCnt = childMenuCnt;
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
        this.delYn = delYn;
        this.level = level;
    }
    
    public UserMenuHierarchyDto toDto( UserMenuHierarchy userMenuHierarchy ) {
        
        return UserMenuHierarchyDto.builder()
                .menuSn( userMenuHierarchy.getMenuSn() )
                .upperMenuSn( userMenuHierarchy.getUpperMenuSn() )
                .menuNm( userMenuHierarchy.getMenuNm() )
                .menuPath( userMenuHierarchy.getMenuPath() )
                .hierarchy( userMenuHierarchy.getHierarchy() )
                .requiredMenu( userMenuHierarchy.getRequiredMenu() )
                .childMenuCnt( userMenuHierarchy.getChildMenuCnt() )
                .menuCd( userMenuHierarchy.getMenuCd() )
                .menuUrl( userMenuHierarchy.getMenuUrl() )
                .parntsMenuYn( userMenuHierarchy.getParntsMenuYn() )
                .menuSeq( userMenuHierarchy.getMenuSeq() )
                .menuDcSetYn( userMenuHierarchy.getMenuDcSetYn() )
                .menuDc( userMenuHierarchy.getMenuDc() )
                .menuDetailDc( userMenuHierarchy.getMenuDetailDc() )
                .atchFileSn( userMenuHierarchy.getAtchFileSn() )
                .useYn( userMenuHierarchy.getUseYn() )
                .newWindowYn( userMenuHierarchy.getNewWindowYn() )
                .delYn( userMenuHierarchy.getDelYn() )
                .level( userMenuHierarchy.getLevel() )
                .build();
    }
    
}
