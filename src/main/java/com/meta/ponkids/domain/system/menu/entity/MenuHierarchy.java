package com.meta.ponkids.domain.system.menu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Immutable
//@Subselect("with recursive menu_hierarchy(menu_sn, upper_menu_sn, menu_nm, hierarchy, menu_cd, menu_url, parnts_menu_yn, menu_seq, menu_dc_set_yn, menu_dc, menu_detail_dc, atch_file_sn, use_yn, new_window_yn, level) as (\n" +
//        "select menu_sn, upper_menu_sn, menu_nm,CONCAT('\\',menu_seq) AS hierarchy, menu_cd, menu_url, parnts_menu_yn, menu_seq, menu_dc_set_yn, menu_dc, menu_detail_dc, atch_file_sn, use_yn, new_window_yn, 0\n" +
//        "from tb_menu \n" +
//        "where upper_menu_sn is null\n" +
//        "union all \n" +
//        "select tm.menu_sn, tm.upper_menu_sn, tm.menu_nm, CONCAT_WS('\\', mh.hierarchy, tm.menu_seq)  As text, tm.menu_cd, tm.menu_url, tm.parnts_menu_yn, tm.menu_seq, tm.menu_dc_set_yn, tm.menu_dc, tm.menu_detail_dc, tm.atch_file_sn, tm.use_yn, tm.new_window_yn, mh.level+1\n" +
//        "from tb_menu tm, menu_hierarchy mh\n" +
//        "where tm.upper_menu_sn = mh.menu_sn\n" +
//        ") select * from menu_hierarchy\n" +
//        "order by hierarchy")
@Table(name = "vw_menu_hierarchy")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuHierarchy {
    @Id
    private Long menuSn ;
    
    private Long upperMenuSn ;
    
    private String menuNm ;
    
    private String hierarchy ;
    
    private String menuCd ;
    
    private String menuUrl ;
    
    private String parntsMenuYn ;
    
    private Long menuSeq ;
    
    private String menuDcSetYn ;
    
    private String menuDc ;
    
    private String menuDetailDc ;
    
    private Long atchFileSn ;
    
    private String useYn ;
    
    private String newWindowYn ;
    
    private String level ;
}