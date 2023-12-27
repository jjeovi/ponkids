package com.meta.ponkids.domain.system.menu.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuHierarchy {
    
    @Id
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
    
    private Long level;
    
    private LocalDateTime updtDt;
}