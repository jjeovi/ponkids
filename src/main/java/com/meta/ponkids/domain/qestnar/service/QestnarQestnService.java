package com.meta.ponkids.domain.qestnar.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meta.ponkids.domain.qestnar.dto.QestnarQestnModDto;
import com.meta.ponkids.domain.cls.dto.ClassDetailSaveDto;
import com.meta.ponkids.domain.cls.entity.ClassDetail;
import com.meta.ponkids.domain.qestnar.dto.QestnarGroupSaveDto;
import com.meta.ponkids.domain.qestnar.dto.QestnarQestnListDto;
import com.meta.ponkids.domain.qestnar.dto.QestnarQestnSaveDto;
import com.meta.ponkids.domain.qestnar.entity.QestnarQestn;
import com.meta.ponkids.domain.qestnar.repository.QestnarQestnRepository;
import com.meta.ponkids.global.util.ip.IpUtils;
import com.meta.ponkids.global.util.session.SessionUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QestnarQestnService {
	private final QestnarQestnRepository qestnarQestnRepository;	// repository setting
	
	@Transactional
	public void save( QestnarGroupSaveDto saveDto, HttpServletRequest request ) throws IOException {
//    public QestnarQestnSaveDto save( QestnarQestnSaveDto saveDto, QestnarQestnRoleSaveDto qestnarQestnRoleSaveDto, HttpServletRequest request ) throws IOException {
		
		
		List<QestnarQestn> qestnarQestnList = new ArrayList<>();
		
		int i = 1;
		for ( QestnarQestnSaveDto qestnarQestn : saveDto.getQestnarQestns() ) {
			
			qestnarQestn.setQestnarGroupSn( saveDto.getQestnarGroupSn() );
			
			qestnarQestn.setQestnarQestnSeq((long)i++);
			
			qestnarQestn.setRegisterId(SessionUtils.getClientId());
			qestnarQestn.setRegisterIp( IpUtils.getClientIP(request));
			qestnarQestn.setUpdusrId(SessionUtils.getClientId());
			qestnarQestn.setUpdusrIp( IpUtils.getClientIP(request));
			
			qestnarQestnList.add( qestnarQestn.toEntity() );
		}
		
		qestnarQestnRepository.saveAll( qestnarQestnList );
		
		
	}
	

    public Page<QestnarQestnListDto> getList( QestnarQestnListDto listDto, Pageable pageable ) {
        return qestnarQestnRepository.getList( listDto, pageable );
    }
    
    
    public QestnarQestnModDto findById( Long pk ) {	// TODO 타입 체크 필요
        
        QestnarQestn qestnarQestn = qestnarQestnRepository.findById( pk ).orElse(null);
        
        if (qestnarQestn == null ) { 
        	
        	return null;
        } else {
        
	        QestnarQestnModDto modDto = new QestnarQestnModDto();
	        modDto = modDto.toDto( qestnarQestn );
	        
	        return modDto;
        }
    }
    
    @Transactional
    public void update ( QestnarQestnModDto modDto, HttpServletRequest request ) throws IOException {
//    public void update ( QestnarQestnModDto modDto, QestnarQestnRoleModDto qestnarQestnRoleModDto, HttpServletRequest request ) throws IOException {
    	
    	// target 조회
        QestnarQestn qestnarQestn = qestnarQestnRepository.findById( modDto.getQestnarQestnSn() ).orElse(null);	// TODO PK 체크
        
        // target object 전환 ( entity to dto )
        QestnarQestnModDto targetDto = new QestnarQestnModDto();
        targetDto = targetDto.toDto( qestnarQestn );
        
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
        qestnarQestn = targetDto.toEntity();
        
        // 수정사항 적용
        qestnarQestnRepository.save( qestnarQestn );
    	
    }

    @Transactional
    public void deleteAllById( Long pk ) {
        
        // delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
        qestnarQestnRepository.deleteById( pk );    // Entity 의 @SQLDelete 를 수행
        
    }
	

}
