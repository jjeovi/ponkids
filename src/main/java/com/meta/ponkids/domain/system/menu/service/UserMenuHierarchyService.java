package com.meta.ponkids.domain.system.menu.service;

import com.meta.ponkids.domain.system.menu.dto.UserMenuHierarchyDto;
import com.meta.ponkids.domain.system.menu.entity.UserMenuHierarchy;
import com.meta.ponkids.domain.system.menu.repository.UserMenuHierarchyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMenuHierarchyService {
    
    private final UserMenuHierarchyRepository userMenuHierarchyRepository;
    
    public UserMenuHierarchyDto findTop1ByMenuUrlOrderByMenuSn( String menuUrl ) {
        
        UserMenuHierarchy userMenuHierarchy = userMenuHierarchyRepository.findTop1ByMenuUrlOrderByMenuSn( menuUrl );
        
        UserMenuHierarchyDto userMenuHierarchyDto = new UserMenuHierarchyDto();
        userMenuHierarchyDto = userMenuHierarchyDto.toDto( userMenuHierarchy );
        
        return userMenuHierarchyDto;
        
    }
}
