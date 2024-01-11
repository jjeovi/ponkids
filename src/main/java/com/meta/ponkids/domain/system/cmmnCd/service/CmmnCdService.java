package com.meta.ponkids.domain.system.cmmnCd.service;

import com.meta.ponkids.domain.system.cmmnCd.dto.CmmnCdListDto;
import com.meta.ponkids.domain.system.cmmnCd.dto.CmmnCdModDto;
import com.meta.ponkids.domain.system.cmmnCd.dto.CmmnCdSaveDto;
import com.meta.ponkids.domain.system.cmmnCd.entity.CmmnCd;
import com.meta.ponkids.domain.system.cmmnCd.repository.CmmnCdRepository;
import com.meta.ponkids.global.util.ip.IpUtils;
import com.meta.ponkids.global.util.session.SessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CmmnCdService {
    private final CmmnCdRepository cmmnCdRepository;    // repository setting
    
    @Transactional
    public CmmnCdSaveDto save( CmmnCdSaveDto saveDto, HttpServletRequest request ) throws IOException {
//    public CmmnCdSaveDto save( CmmnCdSaveDto saveDto, CmmnCdRoleSaveDto cmmnCdRoleSaveDto, HttpServletRequest request ) throws IOException {
        
        saveDto.setRegisterId( SessionUtils.getClientId() );                // Id set : regist
        saveDto.setRegisterIp( IpUtils.getClientIP( request ) );            // Ip set : regist
        saveDto.setUpdusrId( SessionUtils.getClientId() );                    // Id set : update
        saveDto.setUpdusrIp( IpUtils.getClientIP( request ) );                // Ip set : update
        
        CmmnCd newCmmnCd = cmmnCdRepository.save( saveDto.toEntity() );            // ** save -> save된 정보 newXxx 로 저장
        
        return saveDto;
        
    }
    
    
    public Page<CmmnCdListDto> getList( CmmnCdListDto listDto, Pageable pageable ) {
        return cmmnCdRepository.getList( listDto, pageable );
    }
    
    
    public CmmnCdModDto findById( Long pk ) {    // TODO 타입 체크 필요
        
        CmmnCd cmmnCd = cmmnCdRepository.findById( pk ).orElse( null );
        
        CmmnCdModDto modDto = new CmmnCdModDto();
        modDto = modDto.toDto( cmmnCd );
        
        return modDto;
    }
    
    @Transactional
    public void update( CmmnCdModDto modDto, HttpServletRequest request ) throws IOException {
//    public void update ( CmmnCdModDto modDto, CmmnCdRoleModDto cmmnCdRoleModDto, HttpServletRequest request ) throws IOException {
        
        // target 조회
        CmmnCd cmmnCd = cmmnCdRepository.findById( modDto.getCdSn() ).orElse( null );    // TODO PK 체크
        
        // target object 전환 ( entity to dto )
        CmmnCdModDto targetDto = new CmmnCdModDto();
        targetDto = targetDto.toDto( cmmnCd );
        
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
        cmmnCd = targetDto.toEntity();
        
        // 수정사항 적용
        cmmnCdRepository.save( cmmnCd );
        
    }
    
    @Transactional
    public void deleteAllById( Long pk ) {
        
        // delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
        cmmnCdRepository.deleteById( pk );    // Entity 의 @SQLDelete 를 수행
        
    }
    
    
}
