package com.meta.ponkids.domain.system.menu.service;

import com.meta.ponkids.domain.system.menu.dto.AdminMenuHierarchyDto;
import com.meta.ponkids.domain.system.menu.entity.AdminMenuHierarchy;
import com.meta.ponkids.domain.system.menu.repository.AdminMenuHierarchyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminMenuHierarchyService {
    
    private final AdminMenuHierarchyRepository adminMenuHierarchyRepository;
    
    public AdminMenuHierarchyDto findTop1ByMenuUrlAndDelYnOrderByMenuSn( String menuUrl , String delYn) {
        
        AdminMenuHierarchy adminMenuHierarchy = adminMenuHierarchyRepository.findTop1ByMenuUrlAndDelYnOrderByMenuSn( menuUrl , delYn);
        
        if ( adminMenuHierarchy == null ) {
            return null;
        } else {
            AdminMenuHierarchyDto adminMenuHierarchyDto = new AdminMenuHierarchyDto();
            adminMenuHierarchyDto = adminMenuHierarchyDto.toDto( adminMenuHierarchy );
            
            return adminMenuHierarchyDto;
        }
        
    }
    
    public AdminMenuHierarchyDto findTop1ByMenuCdAndDelYnOrderByMenuSn( String menuCd, String delYn ) {
        
        AdminMenuHierarchy adminMenuHierarchy = adminMenuHierarchyRepository.findTop1ByMenuCdAndDelYnOrderByMenuSn( menuCd , delYn );
        
        if ( adminMenuHierarchy == null ) {
            return null;
        } else {
            AdminMenuHierarchyDto adminMenuHierarchyDto = new AdminMenuHierarchyDto();
            adminMenuHierarchyDto = adminMenuHierarchyDto.toDto( adminMenuHierarchy );
            
            return adminMenuHierarchyDto;
        }
        
    }
}
