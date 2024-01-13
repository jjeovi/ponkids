package com.meta.ponkids.domain.lctre.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meta.ponkids.domain.lctre.dto.LctreModDto;
import com.meta.ponkids.domain.lctre.dto.LctreListDto;
import com.meta.ponkids.domain.lctre.dto.LctreSaveDto;
import com.meta.ponkids.domain.lctre.entity.Lctre;
import com.meta.ponkids.domain.lctre.repository.LctreRepository;
import com.meta.ponkids.global.util.ip.IpUtils;
import com.meta.ponkids.global.util.session.SessionUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LctreService {
	private final LctreRepository lctreRepository;	// repository setting
	
	@Transactional
	public LctreSaveDto save( LctreSaveDto saveDto, HttpServletRequest request ) throws IOException {
//    public LctreSaveDto save( LctreSaveDto saveDto, LctreRoleSaveDto lctreRoleSaveDto, HttpServletRequest request ) throws IOException {
		
		saveDto.setRegisterId( SessionUtils.getClientId() );				// Id set : regist
		saveDto.setRegisterIp( IpUtils.getClientIP( request ) );			// Ip set : regist
		saveDto.setUpdusrId( SessionUtils.getClientId() );					// Id set : update
		saveDto.setUpdusrIp( IpUtils.getClientIP( request ) );				// Ip set : update
		
		Lctre newLctre = lctreRepository.save( saveDto.toEntity() );			// ** save -> save된 정보 newXxx 로 저장
		
		return saveDto;
		
	}
	

    public Page<LctreListDto> getList( LctreListDto listDto, Pageable pageable ) {
        return lctreRepository.getList( listDto, pageable );
    }
    
    
    public LctreModDto findById( Long pk ) {	// TODO 타입 체크 필요
        
        Lctre lctre = lctreRepository.findById( pk ).orElse(null);
        
        if (lctre == null ) { 
        	
        	return null;
        } else {
        
	        LctreModDto modDto = new LctreModDto();
	        modDto = modDto.toDto( lctre );
	        
	        return modDto;
        }
    }
    
    @Transactional
    public void update ( LctreModDto modDto, HttpServletRequest request ) throws IOException {
//    public void update ( LctreModDto modDto, LctreRoleModDto lctreRoleModDto, HttpServletRequest request ) throws IOException {
    	
    	// target 조회
        Lctre lctre = lctreRepository.findById( modDto.getLctreSn() ).orElse(null);	// TODO PK 체크
        
        // target object 전환 ( entity to dto )
        LctreModDto targetDto = new LctreModDto();
        targetDto = targetDto.toDto( lctre );
        
        // TODO target object 에 수정사항 set	
        // entity 에서 반영하지 않을 컬럼은 updatable = false 옵션 추가
//        if ( StringUtils.hasText( modDto.getUserNm() ) ) targetDto.setUserNm( modDto.getUserNm() );          	// 이름
//        if ( StringUtils.hasText( modDto.getGender() ) ) targetDto.setGender( modDto.getGender() );          	// 성별
//        if ( StringUtils.hasText( modDto.getBrdtDate() ) ) targetDto.setBrdtDate( modDto.getBrdtDate() );		// 생년월일
//        if ( StringUtils.hasText( modDto.getTelNo() ) ) targetDto.setTelNo( modDto.getTelNo() );            	// 연락처
//        if ( StringUtils.hasText( modDto.getResideArea() ) ) targetDto.setResideArea( modDto.getResideArea() );	// 거주지역
//        if ( StringUtils.hasText( modDto.getRdnmAdr() ) ) targetDto.setRdnmAdr( modDto.getRdnmAdr() );        	// 주소
//        if ( StringUtils.hasText( modDto.getZip() ) ) targetDto.setZip( modDto.getZip() );                		// 우편번호
//        if ( StringUtils.hasText( modDto.getMngrYn() ) ) targetDto.setMngrYn( modDto.getMngrYn() );          	// 관리자여부
        
//        targetDto.setAtchFileSn( modDto.getAtchFileSn() );          											// 첨부파일 (첨부파일은 Null이어도 변경)
        
        // id,ip setting
        targetDto.setUpdusrIp( IpUtils.getClientIP( request ) );
        targetDto.setUpdusrId( SessionUtils.getClientId() );
        
        // target object 전환 ( dto to entity )
        lctre = targetDto.toEntity();
        
        // 수정사항 적용
        lctreRepository.save( lctre );
    	
    }

    @Transactional
    public void deleteAllById( Long pk ) {
        
        // delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
        lctreRepository.deleteById( pk );    // Entity 의 @SQLDelete 를 수행
        
    }
	

}
