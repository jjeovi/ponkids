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
		
		saveDto.setRegisterId( SessionUtils.getUserId() );				// Id set : regist
		saveDto.setRegisterIp( IpUtils.getClientIP( request ) );			// Ip set : regist
		saveDto.setUpdusrId( SessionUtils.getUserId() );					// Id set : update
		saveDto.setUpdusrIp( IpUtils.getClientIP( request ) );				// Ip set : update
		
		QestnarAnswer newQestnarAnswer = qestnarAnswerRepository.save( saveDto.toEntity() );			// ** save -> save된 정보 newXxx 로 저장
		// E : 1. 설문조사 답변 등록
		
		// S : 2. 설문조사 답변 상세 (실제답변 :답안지라고생각하면 됨 ) 등록 
		List<QestnarAnswerDetail> qestnarAnswerDetailList = new ArrayList<>();
		
		if ( saveDto.getQestnarAnswerDetails() != null && saveDto.getQestnarAnswerDetails().size() > 0 ) {

			for(QestnarAnswerDetailSaveDto qestnarAnswerDetail : saveDto.getQestnarAnswerDetails() ) {
				
				qestnarAnswerDetail.setQestnarAnswerSn( newQestnarAnswer.getQestnarAnswerSn() );
				
				qestnarAnswerDetail.setRegisterId( SessionUtils.getUserId() );				// Id set : regist
				qestnarAnswerDetail.setRegisterIp( IpUtils.getClientIP( request ) );			// Ip set : regist
				qestnarAnswerDetail.setUpdusrId( SessionUtils.getUserId() );					// Id set : update
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
							
							for ( Long qestnarQestnDetailSn : qestnarAnswerDetail.getQestnarQestnDetailSnList() ) {
								QestnarAnswerDetailSaveDto qestnarAnswerDetailSaveDto = new QestnarAnswerDetailSaveDto();
								
								qestnarAnswerDetailSaveDto.setQestnarAnswerSn( newQestnarAnswer.getQestnarAnswerSn() );
								qestnarAnswerDetailSaveDto.setQestnarQestnSn( qestnarAnswerDetail.getQestnarQestnSn() );
								qestnarAnswerDetailSaveDto.setQestnarQestnDetailSn(qestnarQestnDetailSn);
								qestnarAnswerDetailSaveDto.setRegisterId( SessionUtils.getUserId() );				// Id set : regist
								qestnarAnswerDetailSaveDto.setRegisterIp( IpUtils.getClientIP( request ) );			// Ip set : regist
								qestnarAnswerDetailSaveDto.setUpdusrId( SessionUtils.getUserId() );					// Id set : update
								qestnarAnswerDetailSaveDto.setUpdusrIp( IpUtils.getClientIP( request ) );				// Ip set : update
								
								qestnarAnswerDetailList.add( qestnarAnswerDetailSaveDto.toEntity() );
							}
							
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
    
    
    public List<QestnarAnswerListDto> getListByUserSn( QestnarAnswerListDto listDto ) {
    	return qestnarAnswerRepository.getListByUserSn( listDto );
    }
    
    
    public QestnarAnswerListDto findById( Long pk ) {	// TODO 타입 체크 필요
        
        return qestnarAnswerRepository.getByClassInqrySn( pk );
    }
    
    
    public List<QestnarAnswerListDto> getList( QestnarAnswerListDto listDto ) {	// TODO 타입 체크 필요
    	
    	return qestnarAnswerRepository.getList( listDto );
    }
    
    
    
    @Transactional
    public void update ( QestnarAnswerModDto modDto, HttpServletRequest request ) throws IOException {
//    public void update ( QestnarAnswerModDto modDto, QestnarAnswerRoleModDto qestnarAnswerRoleModDto, HttpServletRequest request ) throws IOException {
    	
    	// target 조회
        QestnarAnswer qestnarAnswer = qestnarAnswerRepository.findById( modDto.getQestnarAnswerSn() ).orElse(null);	// TODO PK 체크
        
        // target object 전환 ( entity to dto )
        QestnarAnswerModDto targetDto = new QestnarAnswerModDto();
        targetDto = targetDto.toDto( qestnarAnswer );
        
        
        // id,ip setting
        targetDto.setUpdusrIp( IpUtils.getClientIP( request ) );
        targetDto.setUpdusrId( SessionUtils.getUserId() );
        
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
