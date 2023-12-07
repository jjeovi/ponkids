package com.meta.ponkids.domain.system.menu.service;

import org.springframework.stereotype.Service;

import com.meta.ponkids.domain.system.menu.dto.AdminMenuHierarchyDto;
import com.meta.ponkids.domain.system.menu.entity.AdminMenuHierarchy;
import com.meta.ponkids.domain.system.menu.repository.AdminMenuHierarchyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminMenuHierarchyService {
	
	private final AdminMenuHierarchyRepository adminMenuHierarchyRepository;
	
	public AdminMenuHierarchyDto findTop1ByMenuUrlOrderByMenuSn(String menuUrl) {
		
		AdminMenuHierarchy adminMenuHierarchy = adminMenuHierarchyRepository.findTop1ByMenuUrlOrderByMenuSn(menuUrl);
		
		AdminMenuHierarchyDto adminMenuHierarchyDto = new AdminMenuHierarchyDto();
		adminMenuHierarchyDto = adminMenuHierarchyDto.toDto(adminMenuHierarchy);
		
		return adminMenuHierarchyDto;
		
	}
}
