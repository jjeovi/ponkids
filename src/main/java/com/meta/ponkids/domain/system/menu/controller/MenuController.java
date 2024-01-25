package com.meta.ponkids.domain.system.menu.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meta.ponkids.domain.system.menu.dto.MenuListDto;
import com.meta.ponkids.domain.system.menu.service.MenuService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MenuController {
    
    private final static String BASIC_PATH = "/menu";
    private final MenuService menuService;
    @Value( "${key.default.admin}" )
    private String TYPE_ADMIN;
    
    @Value( "${key.default.user}" )
    private String TYPE_USER;
    
    @Value( "${key.default.adminRootMenuSn}" )
    private Long ADMIN_ROOT_MENU_SN;
    
    @Value( "${key.default.userRootMenuSn}" )
    private Long USER_ROOT_MENU_SN;
    
    @ResponseBody
    @GetMapping( BASIC_PATH + "/live/{type}/getMenuListAjax" )
    public Map<String, Object> getMenuListAjax( @PathVariable String type ) {
        // 해당 권한에 맞는 menuList 가져온 뒤 drawMenuTree 로 메뉴를 그린다.
        Map<String, Object> result = new HashMap<String, Object>();
        
        // MenuListDto 생성
        MenuListDto listDto = new MenuListDto();
        
        // 메뉴 list 출력
        if ( type.equals( TYPE_USER ) ) {
            result.put( "resultList", menuService.getUserMenuList( listDto ) );
        }
        return result;
    }
    
}
