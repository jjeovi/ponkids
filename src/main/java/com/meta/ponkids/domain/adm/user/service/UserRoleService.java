package com.meta.ponkids.domain.adm.user.service;

import com.meta.ponkids.domain.adm.user.entity.UserRole;
import com.meta.ponkids.domain.adm.user.repository.UserRoleRepository;
import com.meta.ponkids.domain.adm.user.dto.UserRoleModDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * className      : UserService
 * author         : jjeoV
 * date           : 2023-11-19
 * description    : class of 회원 Service
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-11-19        jjeoV             최초 생성
 */
@Service
@RequiredArgsConstructor
public class UserRoleService {
    
    private final UserRoleRepository userRoleRepository;
    
    public UserRoleModDto findByUserSn( Long userSn ) {
        
        UserRole userRole = userRoleRepository.findByUserSn( userSn );
        
        UserRoleModDto userRoleModDto = new UserRoleModDto();
        userRoleModDto = userRoleModDto.toDto( userRole );
        
        return userRoleModDto;
    }
    
    
}