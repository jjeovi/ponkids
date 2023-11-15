package com.meta.ponkids.domain.user.service;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meta.ponkids.domain.user.dto.MultiUserChldrnSaveReqDto;
import com.meta.ponkids.domain.user.dto.UserChldrnSaveReqDto;
import com.meta.ponkids.domain.user.dto.UserListDto;
import com.meta.ponkids.domain.user.dto.UserRoleSaveReqDto;
import com.meta.ponkids.domain.user.dto.UserSaveReqDto;
import com.meta.ponkids.domain.user.entity.UserChldrn;
import com.meta.ponkids.domain.user.repository.UserChldrnRepository;
import com.meta.ponkids.domain.user.repository.UserRepository;
import com.meta.ponkids.domain.user.repository.UserRoleRepository;
import com.meta.ponkids.global.util.ip.IpUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final UserChldrnRepository userChldrnRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;  // 패스워드 인코딩
    
    @Transactional
    public UserSaveReqDto save( UserSaveReqDto userSaveReqDto, UserRoleSaveReqDto userRoleSaveReqDto, MultiUserChldrnSaveReqDto userChldrns, HttpServletRequest request ) {
        
        userSaveReqDto.setRegisterIp( IpUtils.getClientIP( request ) );                         // 회원 IP 저장
        userSaveReqDto.setPassword( passwordEncoder.encode( userSaveReqDto.getPassword() ) );   // 비밀번호 암호화
        
        userRepository.save( userSaveReqDto.toEntity() );        // ** 회원 save
        
        // 관리자 여부 Y 일 때 권한 등록
        if ( userSaveReqDto.getMngrYn().equals( "Y" ) ) {
            userRoleSaveReqDto.setRegisterIp( IpUtils.getClientIP( request ) );     // 관리자 IP 저장
            userRoleSaveReqDto.setRegisterId( "admin@test.com" );                   // TODO : 현재 세션의 userId값으로 수정
            
            userRoleRepository.save( userRoleSaveReqDto.toEntity() );
        }
        
        // 자녀 존재하면 자녀 등록
        // 사용자 일 경우에만 자녀 추가
        if ( userChldrns != null && userChldrns.getUserChldrns() != null && userChldrns.getUserChldrns().size() > 0 && userSaveReqDto.getMngrYn().equals( "N" ) ) {
            List<UserChldrn> userChldrnList = new ArrayList<>();
            for ( UserChldrnSaveReqDto userChldrn : userChldrns.getUserChldrns() ) {
                
                userChldrn.setUserId( userSaveReqDto.getUserId() );             // userId Setting
                userChldrn.setUserChldrnSeq( userChldrns.getUserChldrns().indexOf( userChldrn ) + 1 );  // userChldrnSeq Setting
                
                userChldrn.setRegisterIp( IpUtils.getClientIP( request ) );     // 관리자 IP 저장
                userChldrn.setRegisterId( "admin@test.com" );                   // TODO : 현재 세션의 userId값으로 수정
                
                userChldrnList.add( userChldrn.toEntity() );      // userlist add
            }
            
            userChldrnRepository.saveAll( userChldrnList );     // 한꺼번에 save. 각각 save보다 빠르다.
        }
        
        return userSaveReqDto;
    }
    
    public Page<UserListDto> getList( UserListDto userListDto, Pageable pageable){
    	return userRepository.getList(userListDto, pageable);
    }
}