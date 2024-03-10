package com.meta.ponkids.domain.qestnar.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meta.ponkids.domain.qestnar.dto.QestnarAnswerReplyListDto;
import com.meta.ponkids.domain.qestnar.dto.QestnarAnswerReplyModDto;
import com.meta.ponkids.domain.qestnar.dto.QestnarAnswerReplySaveDto;
import com.meta.ponkids.domain.qestnar.entity.QestnarAnswerReply;
import com.meta.ponkids.domain.qestnar.repository.QestnarAnswerReplyRepository;
import com.meta.ponkids.global.util.ip.IpUtils;
import com.meta.ponkids.global.util.session.SessionUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QestnarAnswerReplyService {
	private final QestnarAnswerReplyRepository qestnarAnswerReplyRepository;	// repository setting
	
	@Transactional
	public QestnarAnswerReplySaveDto save( QestnarAnswerReplySaveDto saveDto, HttpServletRequest request ) throws IOException {
//    public QestnarAnswerReplySaveDto save( QestnarAnswerReplySaveDto saveDto, QestnarAnswerReplyRoleSaveDto qestnarAnswerReplyRoleSaveDto, HttpServletRequest request ) throws IOException {
		
		saveDto.setRegisterId( SessionUtils.getClientId() );				// Id set : regist
		saveDto.setRegisterIp( IpUtils.getClientIP( request ) );			// Ip set : regist
		saveDto.setUpdusrId( SessionUtils.getClientId() );					// Id set : update
		saveDto.setUpdusrIp( IpUtils.getClientIP( request ) );				// Ip set : update
		
		QestnarAnswerReply newQestnarAnswerReply = qestnarAnswerReplyRepository.save( saveDto.toEntity() );			// ** save -> save된 정보 newXxx 로 저장
		
		return saveDto;
		
	}
	
//
//    public Page<QestnarAnswerReplyListDto> getList( QestnarAnswerReplyListDto listDto, Pageable pageable ) {
//        return qestnarAnswerReplyRepository.getList( listDto, pageable );
//    }
//    
    
    public List<QestnarAnswerReplyListDto> getListByQestnarAnswerSn( Long qestnarAnswerSn ) {
    	return qestnarAnswerReplyRepository.getListByQestnarAnswerSn( qestnarAnswerSn );
    }
    
    
    public QestnarAnswerReplyModDto findById( Long pk ) {	// TODO 타입 체크 필요
        
        QestnarAnswerReply qestnarAnswerReply = qestnarAnswerReplyRepository.findById( pk ).orElse(null);
        
        if (qestnarAnswerReply == null ) { 
        	
        	return null;
        } else {
        
	        QestnarAnswerReplyModDto modDto = new QestnarAnswerReplyModDto();
	        modDto = modDto.toDto( qestnarAnswerReply );
	        
	        return modDto;
        }
    }
    
    @Transactional
    public void update ( QestnarAnswerReplyModDto modDto, HttpServletRequest request ) throws IOException {
//    public void update ( QestnarAnswerReplyModDto modDto, QestnarAnswerReplyRoleModDto qestnarAnswerReplyRoleModDto, HttpServletRequest request ) throws IOException {
    	
    	// target 조회
        QestnarAnswerReply qestnarAnswerReply = qestnarAnswerReplyRepository.findById( modDto.getQestnarAnswerReplySn() ).orElse(null);	// TODO PK 체크
        
        // target object 전환 ( entity to dto )
        QestnarAnswerReplyModDto targetDto = new QestnarAnswerReplyModDto();
        targetDto = targetDto.toDto( qestnarAnswerReply );
        
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
        qestnarAnswerReply = targetDto.toEntity();
        
        // 수정사항 적용
        qestnarAnswerReplyRepository.save( qestnarAnswerReply );
    	
    }

    @Transactional
    public void deleteAllById( Long pk ) {
        
        // delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
        qestnarAnswerReplyRepository.deleteById( pk );    // Entity 의 @SQLDelete 를 수행
        
    }
	

}
