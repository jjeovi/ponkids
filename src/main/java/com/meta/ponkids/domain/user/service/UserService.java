
package com.meta.ponkids.domain.user.service;

import com.meta.ponkids.domain.user.entity.User;
import com.meta.ponkids.domain.user.entity.UserChldrn;
import com.meta.ponkids.domain.user.entity.UserRole;
import com.meta.ponkids.domain.user.repository.UserChldrnRepository;
import com.meta.ponkids.domain.user.repository.UserRepository;
import com.meta.ponkids.domain.user.repository.UserRoleRepository;
import com.meta.ponkids.domain.system.file.service.AtchFileService;
import com.meta.ponkids.domain.user.dto.*;
import com.meta.ponkids.global.util.date.DateUtils;
import com.meta.ponkids.global.util.ip.IpUtils;
import com.meta.ponkids.global.util.session.SessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
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
    private final AtchFileService atchFileService;
    
    @Transactional
    public UserSaveDto save( UserSaveDto userSaveDto, UserRoleSaveDto userRoleSaveDto, MultiUserChldrnSaveDto userChldrns, HttpServletRequest request ) throws IOException, ParseException {
        
        userSaveDto.setRegisterIp( IpUtils.getClientIP( request ) );                        // 회원 IP 저장
        userSaveDto.setUpdusrIp( IpUtils.getClientIP( request ) );                            // Ip set : update
        
        userSaveDto.setPassword( passwordEncoder.encode( userSaveDto.getPassword() ) );    // 비밀번호 암호화
        
        // 유저 날짜 형식 체크하여 yyyy-MM-dd 아닐 경우 체크 하여 포맷 변환
        if ( ! DateUtils.checkDateFormat(userSaveDto.getBrdtDate(), "yyyy-MM-dd") ) {
        	
        	if ( DateUtils.checkDateFormat(userSaveDto.getBrdtDate(), "yyyyMMdd") ) {
        		String changeFormatBrdtDate = DateUtils.setChangeDateFormat( userSaveDto.getBrdtDate(), "yyyy-MM-dd" );
        		userSaveDto.setBrdtDate(changeFormatBrdtDate);
        	}
        	
        }
        
        
        // joinForSns ( sns 회원가입) 시 snsType 으로 회원가입 여부 조회
        if ( StringUtils.hasText( userSaveDto.getSnsType() ) ) {
            switch ( userSaveDto.getSnsType() ) {
                case "kakao" :
                    userSaveDto.setSnsKakaoCntnYn( "Y" );
                    userSaveDto.setSnsKakaoCntnDt( LocalDateTime.now() );
                    break;
                case "google" :
                    userSaveDto.setSnsGoogleCntnYn( "Y" );
                    userSaveDto.setSnsGoogleCntnDt( LocalDateTime.now() );
                    break;
                case "naver" :
                    userSaveDto.setSnsNaverCntnYn( "Y" );
                    userSaveDto.setSnsNaverCntnDt( LocalDateTime.now() );
                    break;
                case "facebook" :
                    userSaveDto.setSnsFacebookCntnYn( "Y" );
                    userSaveDto.setSnsFacebookCntnDt( LocalDateTime.now() );
                    break;
                case "apple" :
                    userSaveDto.setSnsAppleCntnYn( "Y" );
                    userSaveDto.setSnsAppleCntnDt( LocalDateTime.now() );
                    break;
            }
        }
        
        User newUser = userRepository.save( userSaveDto.toEntity() );                        // ** 회원 save -> save된 정보 newUser 로 저장
        
        // 관리자 여부 Y 일 때 권한 등록
        if ( userSaveDto.getMngrYn().equals( "Y" ) ) {
            userRoleSaveDto.setUserSn( newUser.getUserSn() );                               // 등록한 ID의 sn값 바로 호출 (newUser에서 값 호출)
            userRoleSaveDto.setRegisterIp( IpUtils.getClientIP( request ) );                // 관리자 IP 저장
            userRoleSaveDto.setRegisterId( SessionUtils.getUserId() );                    // 등록자 ID setting
            
            userRoleRepository.save( userRoleSaveDto.toEntity() );                            // * 권한 save
        }
        
        // 자녀 존재하면 자녀 등록
        // 사용자 일 경우에만 자녀 추가
        if ( userChldrns != null && userChldrns.getUserChldrns() != null && userChldrns.getUserChldrns().size() > 0 && userSaveDto.getMngrYn().equals( "N" ) ) {
            List<UserChldrn> userChldrnList = new ArrayList<>();
            for ( UserChldrnSaveDto userChldrn : userChldrns.getUserChldrns() ) {
                
                userChldrn.setUserSn( newUser.getUserSn() );                                                    // 등록한 ID의 sn값 setting (newUser에서 값 호출)
                userChldrn.setUserChldrnSeq( ( long ) userChldrns.getUserChldrns().indexOf( userChldrn ) + 1 );    // userChldrnSeq Setting
                
                userChldrn.setRegisterIp( IpUtils.getClientIP( request ) );                                        // 관리자 IP 저장
                userChldrn.setRegisterId( SessionUtils.getUserId() );                                                // TODO : 현재 세션의 userId값으로 수정
                if ( userChldrn.getRegisterId() == null ) {
                	userChldrn.setRegisterId( userSaveDto.getUserId() );
                }
                
                // 자녀 프로필 존재시 추가
                if ( !userChldrn.getFile().isEmpty() ) {
                    userChldrn.setAtchFileSn( atchFileService.save( userChldrn.getFile() ) );
                }
                
                // 날짜 유효성 체크하여 포맷 변경
                if ( ! DateUtils.checkDateFormat(userChldrn.getChldrnBrdtDate(), "yyyy-MM-dd") ) {
                	
                	if ( DateUtils.checkDateFormat(userChldrn.getChldrnBrdtDate(), "yyyyMMdd") ) {
                		String changeFormatBrdtDate = DateUtils.setChangeDateFormat( userChldrn.getChldrnBrdtDate(), "yyyy-MM-dd" );
                		userChldrn.setChldrnBrdtDate(changeFormatBrdtDate);
                	}
                	
                }
                
                userChldrnList.add( userChldrn.toEntity() );                                                    // userlist add
            }
            
            userChldrnRepository.saveAll( userChldrnList );                                                    // * userChldrn save. 한꺼번에 save. 각각 save보다 빠르다.
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
    
    
    public UserModDto findByUserId( String userId ) {
    	
    	User user = userRepository.findByUserId( userId );
    	
    	if ( user == null ) {
    		return null;
    	}
    	UserModDto userModDto = new UserModDto();
    	userModDto = userModDto.toDto( user );
    	
    	return userModDto;
    }

    @Transactional
    public void update( UserModDto modDto, HttpServletRequest request ) throws IOException {


        // 1. user 수정			: 회원수정
        // ================================================================================
        // target 조회
        User user = userRepository.findByUserSn( modDto.getUserSn() );

        // target object 전환 ( entity to dto )
        UserModDto targetDto = new UserModDto();
        targetDto = targetDto.toDto( user );

        // target object 에 수정사항 set
        // entity 에서 반영하지 않을 컬럼은 updatable = false 옵션 추가
        if ( StringUtils.hasText( modDto.getUserNm() ) ) targetDto.setUserNm( modDto.getUserNm() );            // 이름
        if ( StringUtils.hasText( modDto.getGender() ) ) targetDto.setGender( modDto.getGender() );            // 성별
        if ( StringUtils.hasText( modDto.getBrdtDate() ) ) targetDto.setBrdtDate( modDto.getBrdtDate() );        // 생년월일
        if ( StringUtils.hasText( modDto.getTelNo() ) ) targetDto.setTelNo( modDto.getTelNo() );                // 연락처
        if ( StringUtils.hasText( modDto.getResideArea() ) )
            targetDto.setResideArea( modDto.getResideArea() );    // 거주지역
        if ( StringUtils.hasText( modDto.getRdnmAdr() ) ) targetDto.setRdnmAdr( modDto.getRdnmAdr() );            // 주소
        if ( StringUtils.hasText( modDto.getZip() ) )
            targetDto.setZip( modDto.getZip() );                        // 우편번호
        if ( StringUtils.hasText( modDto.getMngrYn() ) ) targetDto.setMngrYn( modDto.getMngrYn() );            // 관리자여부

        targetDto.setAtchFileSn( modDto.getAtchFileSn() );                                                    // 첨부파일 (첨부파일은 Null이어도 변경)


        // id,ip setting
        targetDto.setUpdusrIp( IpUtils.getClientIP( request ) );
        targetDto.setUpdusrId( SessionUtils.getUserId() );

        // target object 전환 ( dto to entity )
        user = targetDto.toEntity();

        // 수정사항 적용
        userRepository.save( user );



    }
    
    
    @Transactional
//    public UserSaveDto save( UserSaveDto userSaveDto, UserRoleSaveDto userRoleSaveDto, MultiUserChldrnSaveDto userChldrns, HttpServletRequest request ) throws IOException {
    public void update( UserModDto modDto, UserRoleModDto userRoleModDto, MultiUserChldrnSaveDto userChldrns, HttpServletRequest request ) throws IOException {
        
        
        // 1. user 수정			: 회원수정
        // 2. userRole 수정		: 권한수정
        // 3. userChldrns 수정	: 자녀수정
        
        
        // 1. user 수정			: 회원수정
        // ================================================================================
        // target 조회
        User user = userRepository.findByUserSn( modDto.getUserSn() );
        
        // target object 전환 ( entity to dto )
        UserModDto targetDto = new UserModDto();
        targetDto = targetDto.toDto( user );
        
        // target object 에 수정사항 set
        // entity 에서 반영하지 않을 컬럼은 updatable = false 옵션 추가
        if ( StringUtils.hasText( modDto.getUserNm() ) ) targetDto.setUserNm( modDto.getUserNm() );            // 이름
        if ( StringUtils.hasText( modDto.getGender() ) ) targetDto.setGender( modDto.getGender() );            // 성별
        if ( StringUtils.hasText( modDto.getBrdtDate() ) ) targetDto.setBrdtDate( modDto.getBrdtDate() );        // 생년월일
        if ( StringUtils.hasText( modDto.getTelNo() ) ) targetDto.setTelNo( modDto.getTelNo() );                // 연락처
        if ( StringUtils.hasText( modDto.getResideArea() ) )
            targetDto.setResideArea( modDto.getResideArea() );    // 거주지역
        if ( StringUtils.hasText( modDto.getRdnmAdr() ) ) targetDto.setRdnmAdr( modDto.getRdnmAdr() );            // 주소
        if ( StringUtils.hasText( modDto.getZip() ) )
            targetDto.setZip( modDto.getZip() );                        // 우편번호
        if ( StringUtils.hasText( modDto.getMngrYn() ) ) targetDto.setMngrYn( modDto.getMngrYn() );            // 관리자여부
        
        targetDto.setAtchFileSn( modDto.getAtchFileSn() );                                                    // 첨부파일 (첨부파일은 Null이어도 변경)
        
        
        // id,ip setting
        targetDto.setUpdusrIp( IpUtils.getClientIP( request ) );
        targetDto.setUpdusrId( SessionUtils.getUserId() );
        
        // target object 전환 ( dto to entity )
        user = targetDto.toEntity();
        
        // 수정사항 적용
        userRepository.save( user );
        
        
        // 2. userRole 수정		: 권한수정
        // ================================================================================
        
        // target 조회
        UserRole userRole = userRoleRepository.findByUserSn( modDto.getUserSn() );
        
        // 사용자일경우 userRole == null 
        if ( userRole != null ) {
            
            // target object 전환 ( entity to dto )
            UserRoleModDto userRoleTargetDto = new UserRoleModDto();
            userRoleTargetDto = userRoleTargetDto.toDto( userRole );
            
            // target object 에 수정사항 set
            // entity 에서 반영하지 않을 컬럼은 updatable = false 옵션 추가
            if ( userRoleModDto.getRoleSn() != null ) userRoleTargetDto.setRoleSn( userRoleModDto.getRoleSn() );   // 권한
            
            // userRoleSn setting
            userRoleTargetDto.setUserRoleSn( userRole.getUserRoleSn() );
            
            // id,ip setting
            userRoleTargetDto.setUpdusrIp( IpUtils.getClientIP( request ) );
            userRoleTargetDto.setUpdusrId( SessionUtils.getUserId() );
            
            // target object 전환 ( dto to entity )
            userRole = userRoleTargetDto.toEntity();
            
            // 수정사항 적용
            userRoleRepository.save( userRole );
            
        }
        
        
        // 3. userChldrns 수정	: 자녀수정
        // ================================================================================
        // 기존자녀 ( existChldrns ) , 신규자녀 ( newChldrns)
        // 기존 userChldrn 전부 삭제 후 
        // 새로 save
        
        // 기존 userChldrn 삭제
        userChldrnRepository.deleteAllByUserSn( modDto.getUserSn() );
        
        // 새로 save
        if ( userChldrns != null && userChldrns.getUserChldrns() != null && userChldrns.getUserChldrns().size() > 0 && modDto.getMngrYn().equals( "N" ) ) {
            List<UserChldrn> userChldrnList = new ArrayList<>();
            for ( UserChldrnSaveDto userChldrn : userChldrns.getUserChldrns() ) {
                
                userChldrn.setUserSn( modDto.getUserSn() );                                                    // 등록한 ID의 sn값 setting (newUser에서 값 호출)
                userChldrn.setUserChldrnSeq( ( long ) userChldrns.getUserChldrns().indexOf( userChldrn ) + 1 );    // userChldrnSeq Setting
                
                userChldrn.setRegisterIp( IpUtils.getClientIP( request ) );                                        // 관리자 IP 저장
                userChldrn.setRegisterId( SessionUtils.getUserId() );                                                // TODO : 현재 세션의 userId값으로 수정
                
                // 자녀 프로필 존재시 추가
                if ( !userChldrn.getFile().isEmpty() ) {
                    userChldrn.setAtchFileSn( atchFileService.save( userChldrn.getFile() ) );
                }
                userChldrnList.add( userChldrn.toEntity() );                                                    // userlist add
            }
            userChldrnRepository.saveAll( userChldrnList );                                                    // * userChldrn save. 한꺼번에 save. 각각 save보다 빠르다.
        }
    }
    
    // user sns 계정통합 업데이트
    @Transactional
    public void updateSnsCntn( Long userSn, String snsType ) throws IOException {
        HttpServletRequest request = ( ( ServletRequestAttributes ) RequestContextHolder.currentRequestAttributes() ).getRequest();
        
        //  user sns 계정통합 업데이트
        // ================================================================================
        
        // target 조회
        User user = userRepository.findByUserSn( userSn );
        
        // target object 전환 ( entity to dto )
        UserModDto targetDto = new UserModDto();
        targetDto = targetDto.toDto( user );
        
        // target object 에 수정사항 set
        // snsType에 따라 값을 수정
        
        // joinForSns ( sns 회원가입) 시 snsType 으로 회원가입 여부 조회
        if ( StringUtils.hasText( snsType ) ) {
            switch ( snsType ) {
                case "kakao" :
                    targetDto.setSnsKakaoCntnYn( "Y" );
                    targetDto.setSnsKakaoCntnDt( LocalDateTime.now() );
                    break;
                case "google" :
                    targetDto.setSnsGoogleCntnYn( "Y" );
                    targetDto.setSnsGoogleCntnDt( LocalDateTime.now() );
                    break;
                case "naver" :
                    targetDto.setSnsNaverCntnYn( "Y" );
                    targetDto.setSnsNaverCntnDt( LocalDateTime.now() );
                    break;
                case "facebook" :
                    targetDto.setSnsFacebookCntnYn( "Y" );
                    targetDto.setSnsFacebookCntnDt( LocalDateTime.now() );
                    break;
                case "apple" :
                    targetDto.setSnsAppleCntnYn( "Y" );
                    targetDto.setSnsAppleCntnDt( LocalDateTime.now() );
                    break;
            }
        }
        
        // id,ip setting
        targetDto.setUpdusrIp( IpUtils.getClientIP( request ) );
        targetDto.setUpdusrId( SessionUtils.getUserId() );
        
        // target object 전환 ( dto to entity )
        user = targetDto.toEntity();
        
        // 수정사항 적용
        userRepository.save( user );
    }
    
    
    @Transactional
    public void deleteAllByUserSn( Long userSn ) {
        
        // delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
        userRepository.deleteById( userSn );    // User.java 의 @SQLDelete(sql = "UPDATE tb_user SET del_yn ='Y' WHERE user_sn = ?") 를 수행
        
        // 권한 삭제 : userRole delete 처리
        userRoleRepository.deleteByUserSn( userSn );
        
        // 자녀 삭제 : userchldrn delete 처리
        userChldrnRepository.deleteAllByUserSn( userSn );
    }
    
}