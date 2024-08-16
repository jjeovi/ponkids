package com.meta.ponkids.domain.user.service;

import com.meta.ponkids.domain.system.banner.dto.BannerSaveDto;
import com.meta.ponkids.domain.system.banner.entity.Banner;
import com.meta.ponkids.domain.user.dto.MultiUserChldrnSaveDto;
import com.meta.ponkids.domain.user.dto.UserChldrnModDto;
import com.meta.ponkids.domain.user.dto.UserChldrnSaveDto;
import com.meta.ponkids.domain.user.dto.UserModDto;
import com.meta.ponkids.domain.user.dto.UserRoleModDto;
import com.meta.ponkids.domain.user.entity.User;
import com.meta.ponkids.domain.user.entity.UserChldrn;
import com.meta.ponkids.domain.user.entity.UserRole;
import com.meta.ponkids.domain.user.repository.UserChldrnRepository;
import com.meta.ponkids.global.util.ip.IpUtils;
import com.meta.ponkids.global.util.session.SessionUtils;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * className	  : UserChldrnService
 * author		 : jjeoV
 * date		   : 2023-11-19
 * description	: class of 자녀 Service
 * ===========================================================
 * DATE			  AUTHOR			   NOTE
 * -----------------------------------------------------------
 * 2023-11-19		jjeoV			 최초 생성
 */
@Service
@RequiredArgsConstructor
public class UserChldrnService {
	private final UserChldrnRepository userChldrnRepository;
	
	@Transactional
	public UserChldrnSaveDto save( UserChldrnSaveDto saveDto, HttpServletRequest request ) throws IOException {
		
		saveDto.setRegisterId( SessionUtils.getUserId() );                // Id set : regist
		saveDto.setRegisterIp( IpUtils.getClientIP( request ) );            // Ip set : regist
		saveDto.setUpdusrId( SessionUtils.getUserId() );                    // Id set : update
		saveDto.setUpdusrIp( IpUtils.getClientIP( request ) );                // Ip set : update
		
		UserChldrn newUserChldrn = userChldrnRepository.save( saveDto.toEntity() );            // ** save -> save된 정보 newXxx 로 저장
		
		return saveDto;
	}

	@Transactional
	public void update( UserChldrnModDto modDto, HttpServletRequest request ) throws IOException {
		

		// userChldrn 수정			: 자녀수정
		// ================================================================================
		// target 조회
		UserChldrn userChldrn = userChldrnRepository.findByChldrnSnAndUserSn( modDto.getChldrnSn(), modDto.getUserSn() ).orElse( null );
		
		// target object 전환 ( entity to dto )
		UserChldrnModDto targetDto = new UserChldrnModDto();
		targetDto = targetDto.toDto( userChldrn );
		
		// target object 에 수정사항 set
		// entity 에서 반영하지 않을 컬럼은 updatable = false 옵션 추가
		if ( StringUtils.hasText( modDto.getChldrnNm() ) 		) targetDto.setChldrnNm( modDto.getChldrnNm() );					// 이름
		if ( StringUtils.hasText( modDto.getChldrnBrdtDate() )	) targetDto.setChldrnBrdtDate( modDto.getChldrnBrdtDate() );		// 생년월일
		if ( StringUtils.hasText( modDto.getChldrnGender() )	) targetDto.setChldrnGender( modDto.getChldrnGender() );			// 성별
		if ( StringUtils.hasText( modDto.getChldrnTelNo() )		) targetDto.setChldrnTelNo( modDto.getChldrnTelNo() );				// 연락처
		if ( StringUtils.hasText( modDto.getChldrnEmail() )		) targetDto.setChldrnEmail( modDto.getChldrnEmail() );				// 연락처
		
		targetDto.setAtchFileSn( modDto.getAtchFileSn() );													// 첨부파일 (첨부파일은 Null이어도 변경)
		
		// id,ip setting
		targetDto.setUpdusrIp( IpUtils.getClientIP( request ) );
		targetDto.setUpdusrId( SessionUtils.getUserId() );
		
		// target object 전환 ( dto to entity )
		userChldrn = targetDto.toEntity();
		
		// 수정사항 적용
		userChldrnRepository.save( userChldrn );
		
	}
	
	

}
