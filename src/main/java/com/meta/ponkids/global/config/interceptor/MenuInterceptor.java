package com.meta.ponkids.global.config.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.meta.ponkids.domain.system.menu.dto.MenuListDto;
import com.meta.ponkids.domain.system.menu.service.MenuService;

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
        
        // menuLit Setting
        List<MenuListDto> menuList = menuService.getUserMenuList( new MenuListDto() );
        
        // mcd setting
        if ( modelAndView != null ) {
            String mcd = ( String ) modelAndView.getModel().get( MCD );
            request.setAttribute( MCD, mcd );
        }
        
     // requestUri Setting
        String requestUri = request.getRequestURI();
        System.out.println("requestUri ========= " + requestUri);
        
        request.setAttribute( "menuList", menuList );
        request.setAttribute( "currentPageUrl", requestUri );
        
    }
    
}
