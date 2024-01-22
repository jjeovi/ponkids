package com.meta.ponkids.domain.adm.system.role.service;


import com.meta.ponkids.domain.adm.system.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    
    private final RoleRepository roleRepository;
    
    
}