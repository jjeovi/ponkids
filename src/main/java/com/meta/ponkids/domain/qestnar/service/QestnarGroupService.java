package com.meta.ponkids.domain.qestnar.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.meta.ponkids.domain.qestnar.dto.QestnarGroupModDto;
import com.meta.ponkids.domain.qestnar.dto.QestnarGroupListDto;
import com.meta.ponkids.domain.qestnar.dto.QestnarGroupSaveDto;
import com.meta.ponkids.domain.qestnar.entity.QestnarGroup;
import com.meta.ponkids.domain.qestnar.repository.QestnarGroupRepository;
import com.meta.ponkids.global.util.ip.IpUtils;
import com.meta.ponkids.global.util.session.SessionUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QestnarGroupService {
	private final QestnarGroupRepository qestnarGroupRepository;	// repository setting
	
	@Transactional
	public QestnarGroupSaveDto save( QestnarGroupSaveDto saveDto, HttpServletRequest request ) throws IOException {
//    public QestnarGroupSaveDto save( QestnarGroupSaveDto saveDto, QestnarGroupRoleSaveDto qestnarGroupRoleSaveDto, HttpServletRequest request ) throws IOException {
		
		saveDto.setRegisterId( SessionUtils.getClientId() );				// Id set : regist
		saveDto.setRegisterIp( IpUtils.getClientIP( request ) );			// Ip set : regist
		saveDto.setUpdusrId( SessionUtils.getClientId() );					// Id set : update
		saveDto.setUpdusrIp( IpUtils.getClientIP( request ) );				// Ip set : update
		
		QestnarGroup newQestnarGroup = qestnarGroupRepository.save( saveDto.toEntity() );			// ** save -> save된 정보 newXxx 로 저장
		saveDto.setQestnarGroupSn( newQestnarGroup.getQestnarGroupSn() );
		
		return saveDto;
		
	}
	

    public Page<QestnarGroupListDto> getList( QestnarGroupListDto listDto, Pageable pageable ) {
        return qestnarGroupRepository.getList( listDto, pageable );
    }
    
    
    public QestnarGroupModDto findById( Long pk ) {	// TODO 타입 체크 필요
        
        QestnarGroup qestnarGroup = qestnarGroupRepository.findById( pk ).orElse(null);
        
        if (qestnarGroup == null ) { 
        	
        	return null;
        } else {
        
	        QestnarGroupModDto modDto = new QestnarGroupModDto();
	        modDto = modDto.toDto( qestnarGroup );
	        
	        return modDto;
        }
    }
    
    @Transactional
    public void update ( QestnarGroupModDto modDto, HttpServletRequest request ) throws IOException {
//    public void update ( QestnarGroupModDto modDto, QestnarGroupRoleModDto qestnarGroupRoleModDto, HttpServletRequest request ) throws IOException {
    	
    	// target 조회
        QestnarGroup qestnarGroup = qestnarGroupRepository.findById( modDto.getQestnarGroupSn() ).orElse(null);	// TODO PK 체크
        
        // target object 전환 ( entity to dto )
        QestnarGroupModDto targetDto = new QestnarGroupModDto();
        targetDto = targetDto.toDto( qestnarGroup );
        
        // TODO target object 에 수정사항 set	
        // entity 에서 반영하지 않을 컬럼은 updatable = false 옵션 추가
        if ( StringUtils.hasText( modDto.getQestnarGroupNm() ) ) targetDto.setQestnarGroupNm( modDto.getQestnarGroupNm() );          	// 이름
        if ( StringUtils.hasText( modDto.getQestnarGroupDc() ) ) targetDto.setQestnarGroupDc( modDto.getQestnarGroupDc() );          	// 이름
        if ( StringUtils.hasText( modDto.getPrivcyYn() ) ) targetDto.setPrivcyYn( modDto.getPrivcyYn() );          	// 이름
        if ( StringUtils.hasText( modDto.getUseYn() ) ) targetDto.setUseYn( modDto.getUseYn() );          	// 이름
        
        // id,ip setting
        targetDto.setUpdusrIp( IpUtils.getClientIP( request ) );
        targetDto.setUpdusrId( SessionUtils.getClientId() );
        
        // target object 전환 ( dto to entity )
        qestnarGroup = targetDto.toEntity();
        
        // 수정사항 적용
        qestnarGroupRepository.save( qestnarGroup );
    	
    }

    @Transactional
    public void deleteAllById( Long pk ) {
        
        // delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
        qestnarGroupRepository.deleteById( pk );    // Entity 의 @SQLDelete 를 수행
        
    }
	

}
