package com.meta.ponkids.domain.adm.system.menu.service;

import com.meta.ponkids.domain.adm.system.menu.entity.UserMenuHierarchy;
import com.meta.ponkids.domain.adm.system.menu.dto.UserMenuHierarchyDto;
import com.meta.ponkids.domain.adm.system.menu.repository.UserMenuHierarchyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMenuHierarchyService {
    
    private final UserMenuHierarchyRepository userMenuHierarchyRepository;
    
    public UserMenuHierarchyDto findTop1ByMenuUrlAndDelYnOrderByMenuSn( String menuUrl , String delYn) {
        
        UserMenuHierarchy userMenuHierarchy = userMenuHierarchyRepository.findTop1ByMenuUrlAndDelYnOrderByMenuSn( menuUrl , delYn );
        
        UserMenuHierarchyDto userMenuHierarchyDto = new UserMenuHierarchyDto();
        userMenuHierarchyDto = userMenuHierarchyDto.toDto( userMenuHierarchy );
        
        return userMenuHierarchyDto;
        
    }
}
