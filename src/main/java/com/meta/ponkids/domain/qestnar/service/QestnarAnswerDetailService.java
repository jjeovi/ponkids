package com.meta.ponkids.domain.qestnar.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meta.ponkids.domain.qestnar.dto.QestnarAnswerDetailModDto;
import com.meta.ponkids.domain.qestnar.dto.QestnarAnswerDetailListDto;
import com.meta.ponkids.domain.qestnar.dto.QestnarAnswerDetailSaveDto;
import com.meta.ponkids.domain.qestnar.entity.QestnarAnswerDetail;
import com.meta.ponkids.domain.qestnar.repository.QestnarAnswerDetailRepository;
import com.meta.ponkids.global.util.ip.IpUtils;
import com.meta.ponkids.global.util.session.SessionUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QestnarAnswerDetailService {
	private final QestnarAnswerDetailRepository qestnarAnswerDetailRepository;	// repository setting
	
	@Transactional
	public QestnarAnswerDetailSaveDto save( QestnarAnswerDetailSaveDto saveDto, HttpServletRequest request ) throws IOException {
//    public QestnarAnswerDetailSaveDto save( QestnarAnswerDetailSaveDto saveDto, QestnarAnswerDetailRoleSaveDto qestnarAnswerDetailRoleSaveDto, HttpServletRequest request ) throws IOException {
		
		saveDto.setRegisterId( SessionUtils.getClientId() );				// Id set : regist
		saveDto.setRegisterIp( IpUtils.getClientIP( request ) );			// Ip set : regist
		saveDto.setUpdusrId( SessionUtils.getClientId() );					// Id set : update
		saveDto.setUpdusrIp( IpUtils.getClientIP( request ) );				// Ip set : update
		
		QestnarAnswerDetail newQestnarAnswerDetail = qestnarAnswerDetailRepository.save( saveDto.toEntity() );			// ** save -> save된 정보 newXxx 로 저장
		
		return saveDto;
		
	}
	

    public Page<QestnarAnswerDetailListDto> getList( QestnarAnswerDetailListDto listDto, Pageable pageable ) {
        return qestnarAnswerDetailRepository.getList( listDto, pageable );
    }
    
    
    public QestnarAnswerDetailModDto findById( Long pk ) {	// TODO 타입 체크 필요
        
        QestnarAnswerDetail qestnarAnswerDetail = qestnarAnswerDetailRepository.findById( pk ).orElse(null);
        
        if (qestnarAnswerDetail == null ) { 
        	
        	return null;
        } else {
        
	        QestnarAnswerDetailModDto modDto = new QestnarAnswerDetailModDto();
	        modDto = modDto.toDto( qestnarAnswerDetail );
	        
	        return modDto;
        }
    }
    
    @Transactional
    public void update ( QestnarAnswerDetailModDto modDto, HttpServletRequest request ) throws IOException {
//    public void update ( QestnarAnswerDetailModDto modDto, QestnarAnswerDetailRoleModDto qestnarAnswerDetailRoleModDto, HttpServletRequest request ) throws IOException {
    	
    	// target 조회
        QestnarAnswerDetail qestnarAnswerDetail = qestnarAnswerDetailRepository.findById( modDto.getQestnarAnswerDetailSn() ).orElse(null);	// TODO PK 체크
        
        // target object 전환 ( entity to dto )
        QestnarAnswerDetailModDto targetDto = new QestnarAnswerDetailModDto();
        targetDto = targetDto.toDto( qestnarAnswerDetail );
        
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
        qestnarAnswerDetail = targetDto.toEntity();
        
        // 수정사항 적용
        qestnarAnswerDetailRepository.save( qestnarAnswerDetail );
    	
    }

    @Transactional
    public void deleteAllById( Long pk ) {
        
        // delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
        qestnarAnswerDetailRepository.deleteById( pk );    // Entity 의 @SQLDelete 를 수행
        
    }
	

}
