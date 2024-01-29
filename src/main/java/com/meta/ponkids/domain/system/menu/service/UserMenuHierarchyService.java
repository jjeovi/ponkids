package com.meta.ponkids.domain.system.menu.service;

import org.springframework.stereotype.Service;

import com.meta.ponkids.domain.system.menu.dto.UserMenuHierarchyDto;
import com.meta.ponkids.domain.system.menu.entity.UserMenuHierarchy;
import com.meta.ponkids.domain.system.menu.repository.UserMenuHierarchyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserMenuHierarchyService {
    
    private final UserMenuHierarchyRepository userMenuHierarchyRepository;
    
    public UserMenuHierarchyDto findTop1ByMenuUrlAndDelYnOrderByMenuSn( String menuUrl , String delYn) {
        
        UserMenuHierarchy userMenuHierarchy = userMenuHierarchyRepository.findTop1ByMenuUrlAndDelYnOrderByMenuSn( menuUrl , delYn );
        
        if( userMenuHierarchy == null ) { 
        	return null;
        } else {

            UserMenuHierarchyDto userMenuHierarchyDto = new UserMenuHierarchyDto();
            userMenuHierarchyDto = userMenuHierarchyDto.toDto( userMenuHierarchy );
            
            return userMenuHierarchyDto;
        }
        
    }
    public UserMenuHierarchyDto findTop1ByMenuCdAndDelYnOrderByMenuSn( String menuCd, String delYn ) {
        
    	UserMenuHierarchy userMenuHierarchy = userMenuHierarchyRepository.findTop1ByMenuCdAndDelYnOrderByMenuSn( menuCd , delYn );
        
        if ( userMenuHierarchy == null ) {
            return null;
        } else {
        	UserMenuHierarchyDto userMenuHierarchyDto = new UserMenuHierarchyDto();
        	userMenuHierarchyDto = userMenuHierarchyDto.toDto( userMenuHierarchy );
            
            return userMenuHierarchyDto;
        }
        
    }
}
