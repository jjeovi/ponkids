package com.meta.ponkids.domain.adm.system.menu.dto;

import com.meta.ponkids.domain.adm.system.menu.entity.AdminMenuHierarchy;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminMenuHierarchyDto {
    
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
    public AdminMenuHierarchyDto( Long menuSn, Long upperMenuSn, String menuNm, String menuPath, String hierarchy,
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
    
    public AdminMenuHierarchyDto toDto( AdminMenuHierarchy adminMenuHierarchy ) {
        
        return AdminMenuHierarchyDto.builder()
                .menuSn( adminMenuHierarchy.getMenuSn() )
                .upperMenuSn( adminMenuHierarchy.getUpperMenuSn() )
                .menuNm( adminMenuHierarchy.getMenuNm() )
                .menuPath( adminMenuHierarchy.getMenuPath() )
                .hierarchy( adminMenuHierarchy.getHierarchy() )
                .requiredMenu( adminMenuHierarchy.getRequiredMenu() )
                .childMenuCnt( adminMenuHierarchy.getChildMenuCnt() )
                .menuCd( adminMenuHierarchy.getMenuCd() )
                .menuUrl( adminMenuHierarchy.getMenuUrl() )
                .parntsMenuYn( adminMenuHierarchy.getParntsMenuYn() )
                .menuSeq( adminMenuHierarchy.getMenuSeq() )
                .menuDcSetYn( adminMenuHierarchy.getMenuDcSetYn() )
                .menuDc( adminMenuHierarchy.getMenuDc() )
                .menuDetailDc( adminMenuHierarchy.getMenuDetailDc() )
                .atchFileSn( adminMenuHierarchy.getAtchFileSn() )
                .useYn( adminMenuHierarchy.getUseYn() )
                .newWindowYn( adminMenuHierarchy.getNewWindowYn() )
                .delYn( adminMenuHierarchy.getDelYn() )
                .level( adminMenuHierarchy.getLevel() )
                .build();
    }
    
}
