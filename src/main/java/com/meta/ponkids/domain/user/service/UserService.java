package com.meta.ponkids.domain.user.service;


import com.meta.ponkids.domain.user.dto.MultiUserChldrnSaveReqDto;
import com.meta.ponkids.domain.user.dto.UserChldrnSaveReqDto;
import com.meta.ponkids.domain.user.dto.UserListResDto;
import com.meta.ponkids.domain.user.dto.UserSaveReqDto;
import com.meta.ponkids.domain.user.entity.User;
import com.meta.ponkids.domain.system.role.dto.RoleSaveReqDto;
import com.meta.ponkids.domain.user.entity.UserChldrn;
import com.meta.ponkids.domain.user.repository.UserChldrnRepository;
import com.meta.ponkids.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final UserChldrnRepository userChldrnRepository;
    private final PasswordEncoder passwordEncoder;  // 패스워드 인코딩
    
    
//    public Page<UserListResDto> paging
    
    public UserSaveReqDto save( UserSaveReqDto userSaveReqDto, RoleSaveReqDto roleSaveReqDto , MultiUserChldrnSaveReqDto userChldrns ) {

        userSaveReqDto.setPassword( passwordEncoder.encode( userSaveReqDto.getPassword() ) );   // 비밀번호 암호화
        
        // 관리자 승인여부 Y 이면 승인일시 now로 setting
        if (userSaveReqDto.getMngrConfmYn().equals( "Y" ) ) {
            userSaveReqDto.setConfmDt( LocalDateTime.now() );
        }
        
        // dto to entity 작업 (필수)
        User user = userSaveReqDto.toEntity();
        
        // 관리자 여부 Y 일 때 권한 등록
        if ( userSaveReqDto.getMngrYn().equals("Y") ) {
            user.getRoles().add(roleSaveReqDto.toEntity());
        }
        
        // save
        userRepository.save( user );
        
        // S : 자녀 존재하면 자녀 등록
        if ( userChldrns != null && userChldrns.getUserChldrns().size() > 0 ) {
            List<UserChldrn> userChldrnList = new ArrayList<>();
            for ( UserChldrnSaveReqDto userChldrn : userChldrns.getUserChldrns() ) {
                
                userChldrn.setUserId(user.getUserId());
                userChldrn.setUserChldrnSeq( userChldrns.getUserChldrns().indexOf( userChldrn ) + 1 );
                
                userChldrnList.add(userChldrn.toEntity());
            }
            
            userChldrnRepository.saveAll( userChldrnList );
        }
        // E : 자녀 존재하면 자녀 등록
        
        return userSaveReqDto;
    }
}