package com.meta.ponkids.domain.system.cmmnCd.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.meta.ponkids.domain.system.cmmnCd.dto.CmmnCdListDto;
import com.meta.ponkids.domain.system.cmmnCd.dto.CmmnCdModDto;
import com.meta.ponkids.domain.system.cmmnCd.dto.CmmnCdSaveDto;
import com.meta.ponkids.domain.system.cmmnCd.entity.CmmnCd;
import com.meta.ponkids.domain.system.cmmnCd.repository.CmmnCdRepository;
import com.meta.ponkids.global.util.ip.IpUtils;
import com.meta.ponkids.global.util.session.SessionUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CmmnCdService {
    private final CmmnCdRepository cmmnCdRepository;    // repository setting
    
    @Transactional
    public CmmnCdSaveDto save( CmmnCdSaveDto saveDto, HttpServletRequest request ) throws IOException {
//    public CmmnCdSaveDto save( CmmnCdSaveDto saveDto, CmmnCdRoleSaveDto cmmnCdRoleSaveDto, HttpServletRequest request ) throws IOException {
        
        saveDto.setRegisterId( SessionUtils.getUserId() );                // Id set : regist
        saveDto.setRegisterIp( IpUtils.getClientIP( request ) );            // Ip set : regist
        saveDto.setUpdusrId( SessionUtils.getUserId() );                    // Id set : update
        saveDto.setUpdusrIp( IpUtils.getClientIP( request ) );                // Ip set : update
        
        cmmnCdRepository.save( saveDto.toEntity() );            // ** save -> save된 정보 newXxx 로 저장
        
        return saveDto;
        
    }
    
    
    public Page<CmmnCdListDto> getList( CmmnCdListDto listDto, Pageable pageable ) {
        return cmmnCdRepository.getList( listDto, pageable );
    }
    
    
    public CmmnCdModDto findById( Long pk ) { 
        
        CmmnCd cmmnCd = cmmnCdRepository.findById( pk ).orElse( null );
        
        if( cmmnCd == null ) {
        	return null;
        }
        
        CmmnCdModDto modDto = new CmmnCdModDto();
        modDto = modDto.toDto( cmmnCd );
        
        return modDto;
    }
    
  
    
    public CmmnCdModDto findByCdNm( String cdNm ) {    
        
        CmmnCd cmmnCd = cmmnCdRepository.findByCdNm( cdNm ).orElse( null );
        
        CmmnCdModDto modDto = new CmmnCdModDto();
        modDto = modDto.toDto( cmmnCd );
        
        return modDto;
    }
    
    
    public List<CmmnCdListDto> findAll() {
    	
    	List<CmmnCd> cmmnCdList = cmmnCdRepository.findAllByOrderByCdSnDesc();
    	
    	// entity to dto (List) 전환
    	CmmnCdListDto cmmnCdListDto = new CmmnCdListDto();			// new 로 listDto 생성
    	List<CmmnCdListDto> listDtos = cmmnCdList.stream().map( m -> cmmnCdListDto.toDto( m ) ).collect( Collectors.toList() );
    	
    	return listDtos;
    	
    }
    
    @Transactional
    public void update( CmmnCdModDto modDto, HttpServletRequest request ) throws IOException {
//    public void update ( CmmnCdModDto modDto, CmmnCdRoleModDto cmmnCdRoleModDto, HttpServletRequest request ) throws IOException {
        
        // target 조회
        CmmnCd cmmnCd = cmmnCdRepository.findById( modDto.getCdSn() ).orElse( null );    // TODO PK 체크
        
        // target object 전환 ( entity to dto )
        CmmnCdModDto targetDto = new CmmnCdModDto();
        targetDto = targetDto.toDto( cmmnCd );
        
        // entity 에서 반영하지 않을 컬럼은 updatable = false 옵션 추가
        if ( StringUtils.hasText( modDto.getCdNm() )) targetDto.setCdNm(modDto.getCdNm());
        if ( StringUtils.hasText( modDto.getCdDc() )) targetDto.setCdDc(modDto.getCdDc());
        targetDto.setCdVal1( modDto.getCdVal1());
        targetDto.setCdVal2( modDto.getCdVal2());
        targetDto.setCdVal3( modDto.getCdVal3());
        targetDto.setCdVal4( modDto.getCdVal4());
        targetDto.setCdVal5( modDto.getCdVal5());
        targetDto.setClCd( modDto.getClCd());
        targetDto.setRemark( modDto.getRemark());
        if ( StringUtils.hasText( modDto.getUseYn() )) targetDto.setUseYn(modDto.getUseYn());
        if ( StringUtils.hasText( modDto.getSysEssntlCmmnYn() )) targetDto.setSysEssntlCmmnYn(modDto.getSysEssntlCmmnYn());
        
        
        // id,ip setting
        targetDto.setUpdusrIp( IpUtils.getClientIP( request ) );
        targetDto.setUpdusrId( SessionUtils.getUserId() );
        
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
