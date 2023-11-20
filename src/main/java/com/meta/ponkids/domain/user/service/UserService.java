package com.meta.ponkids.domain.user.service;

import com.meta.ponkids.domain.user.dto.*;
import com.meta.ponkids.domain.user.entity.User;
import com.meta.ponkids.domain.user.entity.UserChldrn;
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
public class UserService {
    
    private final UserRepository userRepository;
    private final UserChldrnRepository userChldrnRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;  // 패스워드 인코딩
    
    @Transactional
    public UserSaveDto save( UserSaveDto userSaveDto, UserRoleSaveDto userRoleSaveDto, MultiUserChldrnSaveDto userChldrns, HttpServletRequest request ) {
        
        userSaveDto.setRegisterIp( IpUtils.getClientIP( request ) );                        // 회원 IP 저장
        userSaveDto.setPassword( passwordEncoder.encode( userSaveDto.getPassword() ) );   	// 비밀번호 암호화
        
        User newUser = userRepository.save( userSaveDto.toEntity() );        				// ** 회원 save -> save된 정보 newUser 로 저장 
        
        // 관리자 여부 Y 일 때 권한 등록
        if ( userSaveDto.getMngrYn().equals( "Y" ) ) {
        	userRoleSaveDto.setUserSn(newUser.getUserSn());						 			// 등록한 ID의 sn값 바로 호출 (newUser에서 값 호출)
            userRoleSaveDto.setRegisterIp( IpUtils.getClientIP( request ) );     			// 관리자 IP 저장
            userRoleSaveDto.setRegisterId( "admin@test.com" );                   			// TODO : 현재 세션의 userId값으로 수정
            
            userRoleRepository.save( userRoleSaveDto.toEntity() );							// * 권한 save
        }
        
        // 자녀 존재하면 자녀 등록
        // 사용자 일 경우에만 자녀 추가
        if ( userChldrns != null && userChldrns.getUserChldrns() != null && userChldrns.getUserChldrns().size() > 0 && userSaveDto.getMngrYn().equals( "N" ) ) {
            List<UserChldrn> userChldrnList = new ArrayList<>();
            for ( UserChldrnSaveDto userChldrn : userChldrns.getUserChldrns() ) {
                
                userChldrn.setUserSn( newUser.getUserSn() );													// 등록한 ID의 sn값 setting (newUser에서 값 호출)
                userChldrn.setUserChldrnSeq( (long)userChldrns.getUserChldrns().indexOf( userChldrn ) + 1 );	// userChldrnSeq Setting
                
                userChldrn.setRegisterIp( IpUtils.getClientIP( request ) );    									// 관리자 IP 저장
                userChldrn.setRegisterId( "admin@test.com" );                   								// TODO : 현재 세션의 userId값으로 수정
                
                userChldrnList.add( userChldrn.toEntity() );      												// userlist add
            }
            
            userChldrnRepository.saveAll( userChldrnList );     												// * userChldrn save. 한꺼번에 save. 각각 save보다 빠르다.
        }
        
        return userSaveDto;
    }
    
    
    public Page<UserListDto> getList( UserListDto userListDto, Pageable pageable ) {
        return userRepository.getList( userListDto, pageable );
    }
    
    
    public UserModDto findByUserSn( Long userSn ) {
        
        User user = userRepository.findByUserSn( userSn );
        
        UserModDto userModDto = new UserModDto();
        userModDto = userModDto.toDto( user );
        
        return userModDto;
        
    }
    
    public void update( UserModDto modDto ) {
        // target 조회
        User user = userRepository.findByUserSn( modDto.getUserSn() );
        
        // target object 전환 ( entity to dto )
        UserModDto targetDto = new UserModDto();
        targetDto = targetDto.toDto( user );
        
        // target object 에 수정사항 set
        // entity 에서 반영하지 않을 컬럼은 updatable = false 옵션 추가
        if ( StringUtils.hasText( modDto.getUserNm() ) ) targetDto.setUserNm( modDto.getUserNm() );          // 이름
        if ( StringUtils.hasText( modDto.getGender() ) ) targetDto.setGender( modDto.getGender() );          // 성별
        if ( StringUtils.hasText( modDto.getBrdtDate() ) ) targetDto.setBrdtDate( modDto.getBrdtDate() );      // 생년월일
        if ( StringUtils.hasText( modDto.getTelNo() ) ) targetDto.setTelNo( modDto.getTelNo() );            // 연락처
        if ( StringUtils.hasText( modDto.getResideArea() ) ) targetDto.setResideArea( modDto.getResideArea() );  // 거주지역
        if ( StringUtils.hasText( modDto.getRdnmAdr() ) ) targetDto.setRdnmAdr( modDto.getRdnmAdr() );        // 주소
        if ( StringUtils.hasText( modDto.getZip() ) ) targetDto.setZip( modDto.getZip() );                // 우편번호
        if ( StringUtils.hasText( modDto.getMngrYn() ) ) targetDto.setMngrYn( modDto.getMngrYn() );          // 관리자여부
        
        // target object 전환 ( dto to entity )
        user = targetDto.toEntity();
        
        // 수정사항 적용
        userRepository.save( user );
    }
    
    
    @Transactional
    public void deleteAllByUserSn( Long userSn ) {
        
        // delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
        userRepository.deleteById( userSn );    // User.java 의 @SQLDelete(sql = "UPDATE tb_user SET del_yn ='Y' WHERE user_id = ?") 를 수행
        
        // 권한 삭제 : userRole delete 처리
        userRoleRepository.deleteByUserSn( userSn );
        
        // 자녀 삭제 : userchldrn delete 처리
        userChldrnRepository.deleteAllByUserSn( userSn );
        
    }
    
}