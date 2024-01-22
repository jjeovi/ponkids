package com.meta.ponkids.domain.adm.lctre.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.meta.ponkids.domain.adm.lctre.dto.LctreListDto;
import com.meta.ponkids.domain.adm.lctre.dto.LctreModDto;
import com.meta.ponkids.domain.adm.lctre.dto.LctreSaveDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meta.ponkids.domain.adm.lctre.entity.Lctre;
import com.meta.ponkids.domain.adm.lctre.repository.LctreRepository;
import com.meta.ponkids.global.util.ip.IpUtils;
import com.meta.ponkids.global.util.session.SessionUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

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
    
    public LctreModDto findTop1ByClassSnOrderByLctreSeqDesc ( Long pk ) {
        // target 조회
        Lctre lctre = lctreRepository.findTop1ByClassSnOrderByLctreSeqDesc( pk ).orElse(null);
        
        if (lctre == null ) {
            return null;
        } else {
            
            LctreModDto targetDto  = new LctreModDto();
            targetDto = targetDto.toDto( lctre );
            
            return targetDto;
        }
        
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
        if ( StringUtils.hasText(modDto.getClassDayCd())) targetDto.setClassDayCd( modDto.getClassDayCd() );    // 수업 요일
        targetDto.setLctreSeq( modDto.getLctreSeq() );                                                          // 수업 순번
        if(StringUtils.hasText( modDto.getLctreSj() ))  targetDto.setLctreSj( modDto.getLctreSj() );            // 수업 제목
        targetDto.setLctreDc( modDto.getLctreDc() );                                                            // 수업 설명
        targetDto.setLctreApplcntGuidance( modDto.getLctreApplcntGuidance() );                                  // 신청자 안내
        if(StringUtils.hasText( modDto.getRcritNmprSetYn() ))  targetDto.setRcritNmprSetYn( modDto.getRcritNmprSetYn() );            // 모집인원 설정여부
        targetDto.setRcritNmprCo( modDto.getRcritNmprCo() );                                                    // 모집인원 수
        if(StringUtils.hasText( modDto.getPreparRcritNmprSetYn() ))  targetDto.setPreparRcritNmprSetYn( modDto.getPreparRcritNmprSetYn() );            // 모집인원 설정여부
        targetDto.setPreparRcritNmprCo( modDto.getPreparRcritNmprCo() );                                        // 모집인원 수
        
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
