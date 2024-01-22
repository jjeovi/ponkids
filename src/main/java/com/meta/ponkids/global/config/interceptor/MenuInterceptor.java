package com.meta.ponkids.global.config.interceptor;

import com.meta.ponkids.domain.adm.system.login.dto.LoginDto;
import com.meta.ponkids.domain.adm.system.menu.dto.MenuListDto;
import com.meta.ponkids.domain.adm.system.menu.service.MenuService;
import com.meta.ponkids.global.common.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class MenuInterceptor implements HandlerInterceptor {
    
    
    @Autowired
    private final MenuService menuService;
    @Value( "${key.menuCd.auth}" )
    private String MCD;
    
    public MenuInterceptor( MenuService menuService ) {
        this.menuService = menuService;
    }
    
    @Override
    public void postHandle( HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView ) throws Exception {
        
        // requestUri Setting
        String requestUri = request.getRequestURI();
        
        // loginDto Setting
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LoginDto loginDto = ( LoginDto ) principal;
        
        // listDto Setting
        MenuListDto listDto = new MenuListDto();
        listDto.setCategory( new CategoryDto() );
        listDto.getCategory().setLv1Sn( loginDto.getRoleSn() );
        listDto.setUseYn( "Y" );
        // listDto Setting
        
        // menuLit Setting
        List<MenuListDto> menuList = menuService.getList( listDto );
        
        // mcd setting
        if ( modelAndView != null ) {
            String mcd = ( String ) modelAndView.getModel().get( MCD );
            request.setAttribute( MCD, mcd );
        }
        
        
        request.setAttribute( "menuList", menuList );
        request.setAttribute( "currentPageUrl", requestUri );
        
    }
    
}
