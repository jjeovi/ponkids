package com.meta.ponkids.domain.user.service;

import com.meta.ponkids.domain.system.file.service.AtchFileService;
import com.meta.ponkids.domain.user.dto.*;
import com.meta.ponkids.domain.user.entity.User;
import com.meta.ponkids.domain.user.entity.UserChldrn;
import com.meta.ponkids.domain.user.entity.UserRole;
import com.meta.ponkids.domain.user.repository.UserChldrnRepository;
import com.meta.ponkids.domain.user.repository.UserRepository;
import com.meta.ponkids.domain.user.repository.UserRoleRepository;
import com.meta.ponkids.global.util.ip.IpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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