package com.meta.ponkids.domain.qestnar.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.meta.ponkids.domain.qestnar.dto.QestnarAnswerDetailSaveDto;
import com.meta.ponkids.domain.qestnar.dto.QestnarAnswerListDto;
import com.meta.ponkids.domain.qestnar.dto.QestnarAnswerModDto;
import com.meta.ponkids.domain.qestnar.dto.QestnarAnswerSaveDto;
import com.meta.ponkids.domain.qestnar.entity.QestnarAnswer;
import com.meta.ponkids.domain.qestnar.entity.QestnarAnswerDetail;
import com.meta.ponkids.domain.qestnar.repository.QestnarAnswerDetailRepository;
import com.meta.ponkids.domain.qestnar.repository.QestnarAnswerRepository;
import com.meta.ponkids.global.util.ip.IpUtils;
import com.meta.ponkids.global.util.session.SessionUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QestnarAnswerService {
	private final QestnarAnswerRepository qestnarAnswerRepository;	// repository setting
	private final QestnarAnswerDetailRepository qestnarAnswerDetailRepository;	// repository setting
	
	@Transactional
	public QestnarAnswerSaveDto save( QestnarAnswerSaveDto saveDto, HttpServletRequest request ) throws IOException {
		
		// 1. 설문조사 답변 등록
		// 2. 설문조사 답변 상세 (실제답변 :답안지라고생각하면 됨 ) 등록 
		// ==============================================================================
		
		
		// S : 1. 설문조사 답변 등록
		
		saveDto.setRegisterId( SessionUtils.getClientId() );				// Id set : regist
		saveDto.setRegisterIp( IpUtils.getClientIP( request ) );			// Ip set : regist
		saveDto.setUpdusrId( SessionUtils.getClientId() );					// Id set : update
		saveDto.setUpdusrIp( IpUtils.getClientIP( request ) );				// Ip set : update
		
		QestnarAnswer newQestnarAnswer = qestnarAnswerRepository.save( saveDto.toEntity() );			// ** save -> save된 정보 newXxx 로 저장
		// E : 1. 설문조사 답변 등록
		
		// S : 2. 설문조사 답변 상세 (실제답변 :답안지라고생각하면 됨 ) 등록 
		List<QestnarAnswerDetail> qestnarAnswerDetailList = new ArrayList<>();
		
		if ( saveDto.getQestnarAnswerDetails() != null && saveDto.getQestnarAnswerDetails().size() > 0 ) {

			for(QestnarAnswerDetailSaveDto qestnarAnswerDetail : saveDto.getQestnarAnswerDetails() ) {
				
				qestnarAnswerDetail.setQestnarAnswerSn( newQestnarAnswer.getQestnarAnswerSn() );
				
				qestnarAnswerDetail.setRegisterId( SessionUtils.getClientId() );				// Id set : regist
				qestnarAnswerDetail.setRegisterIp( IpUtils.getClientIP( request ) );			// Ip set : regist
				qestnarAnswerDetail.setUpdusrId( SessionUtils.getClientId() );					// Id set : update
				qestnarAnswerDetail.setUpdusrIp( IpUtils.getClientIP( request ) );				// Ip set : update
				
				
				switch( qestnarAnswerDetail.getQestnarQestnItemTyCd() ) {
				
					case "ANSWER" :
					case "ANSWER_LONG" :
						if ( StringUtils.hasText( qestnarAnswerDetail.getQestnarAnswer() ) ) {
							
							if ( qestnarAnswerDetail.getQestnarQestnDetailSn() != null )			qestnarAnswerDetail.setQestnarQestnDetailSn( null );
							if ( qestnarAnswerDetail.getQestnarQestnDetailSnList() != null )		qestnarAnswerDetail.setQestnarQestnDetailSnList( null );
							
						}
						
						qestnarAnswerDetailList.add( qestnarAnswerDetail.toEntity() );
						break;
						
					case "SELECTIVE_ONE" :
						
						if (qestnarAnswerDetail.getQestnarQestnDetailSn() != null ) {
							if ( StringUtils.hasText( qestnarAnswerDetail.getQestnarAnswer() ) )	qestnarAnswerDetail.setQestnarAnswer( null );
							if ( qestnarAnswerDetail.getQestnarQestnDetailSnList() != null )		qestnarAnswerDetail.setQestnarQestnDetailSnList( null );
						}
						
						qestnarAnswerDetailList.add( qestnarAnswerDetail.toEntity() );
						break;
						
					case "SELECTIVE_MULTI" :
						
						if ( qestnarAnswerDetail.getQestnarQestnDetailSnList() != null ) {
							if ( StringUtils.hasText( qestnarAnswerDetail.getQestnarAnswer() ) )	qestnarAnswerDetail.setQestnarAnswer( null );
							if ( qestnarAnswerDetail.getQestnarQestnDetailSn() != null )			qestnarAnswerDetail.setQestnarQestnDetailSn( null );
						}
						
						for ( Long qestnarQestnDetailSn : qestnarAnswerDetail.getQestnarQestnDetailSnList()) {
							QestnarAnswerDetailSaveDto qestnarAnswerDetailSaveDto = new QestnarAnswerDetailSaveDto();
							
							qestnarAnswerDetailSaveDto.setQestnarAnswerSn( newQestnarAnswer.getQestnarAnswerSn() );
							qestnarAnswerDetailSaveDto.setQestnarQestnSn( qestnarAnswerDetail.getQestnarQestnSn() );
							qestnarAnswerDetailSaveDto.setQestnarQestnDetailSn(qestnarQestnDetailSn);
							qestnarAnswerDetailSaveDto.setRegisterId( SessionUtils.getClientId() );				// Id set : regist
							qestnarAnswerDetailSaveDto.setRegisterIp( IpUtils.getClientIP( request ) );			// Ip set : regist
							qestnarAnswerDetailSaveDto.setUpdusrId( SessionUtils.getClientId() );					// Id set : update
							qestnarAnswerDetailSaveDto.setUpdusrIp( IpUtils.getClientIP( request ) );				// Ip set : update
							
							qestnarAnswerDetailList.add( qestnarAnswerDetailSaveDto.toEntity() );
						}
						
						break;
				}
				
			}
			
			qestnarAnswerDetailRepository.saveAll( qestnarAnswerDetailList );
		}
		// E : 2. 설문조사 답변 상세 (실제답변 :답안지라고생각하면 됨 ) 등록
		
		
		return saveDto;
		
	}
	

    public Page<QestnarAnswerListDto> getList( QestnarAnswerListDto listDto, Pageable pageable ) {
        return qestnarAnswerRepository.getList( listDto, pageable );
    }
    
    
    public QestnarAnswerModDto findById( Long pk ) {	// TODO 타입 체크 필요
        
        QestnarAnswer qestnarAnswer = qestnarAnswerRepository.findById( pk ).orElse(null);
        
        if (qestnarAnswer == null ) { 
        	
        	return null;
        } else {
        
	        QestnarAnswerModDto modDto = new QestnarAnswerModDto();
	        modDto = modDto.toDto( qestnarAnswer );
	        
	        return modDto;
        }
    }
    
    @Transactional
    public void update ( QestnarAnswerModDto modDto, HttpServletRequest request ) throws IOException {
//    public void update ( QestnarAnswerModDto modDto, QestnarAnswerRoleModDto qestnarAnswerRoleModDto, HttpServletRequest request ) throws IOException {
    	
    	// target 조회
        QestnarAnswer qestnarAnswer = qestnarAnswerRepository.findById( modDto.getQestnarAnswerSn() ).orElse(null);	// TODO PK 체크
        
        // target object 전환 ( entity to dto )
        QestnarAnswerModDto targetDto = new QestnarAnswerModDto();
        targetDto = targetDto.toDto( qestnarAnswer );
        
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
        qestnarAnswer = targetDto.toEntity();
        
        // 수정사항 적용
        qestnarAnswerRepository.save( qestnarAnswer );
    	
    }

    @Transactional
    public void deleteAllById( Long pk ) {
        
        // delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
        qestnarAnswerRepository.deleteById( pk );    // Entity 의 @SQLDelete 를 수행
        
    }
	

}
