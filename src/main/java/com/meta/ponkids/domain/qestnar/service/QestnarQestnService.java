package com.meta.ponkids.domain.qestnar.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meta.ponkids.domain.qestnar.dto.QestnarGroupDto;
import com.meta.ponkids.domain.qestnar.dto.QestnarGroupSaveDto;
import com.meta.ponkids.domain.qestnar.dto.QestnarQestnDetailSaveDto;
import com.meta.ponkids.domain.qestnar.dto.QestnarQestnListDto;
import com.meta.ponkids.domain.qestnar.dto.QestnarQestnModDto;
import com.meta.ponkids.domain.qestnar.dto.QestnarQestnSaveDto;
import com.meta.ponkids.domain.qestnar.entity.QestnarQestn;
import com.meta.ponkids.domain.qestnar.entity.QestnarQestnDetail;
import com.meta.ponkids.domain.qestnar.repository.QestnarQestnDetailRepository;
import com.meta.ponkids.domain.qestnar.repository.QestnarQestnRepository;
import com.meta.ponkids.global.util.ip.IpUtils;
import com.meta.ponkids.global.util.session.SessionUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QestnarQestnService {
	
	private final QestnarQestnRepository qestnarQestnRepository;	// repository setting
	private final QestnarQestnDetailRepository qestnarQestnDetailRepository;	// repository setting
	
	@Transactional
		public void save( QestnarGroupDto saveDto, HttpServletRequest request ) throws IOException {
		
		
		List<QestnarQestn> qestnarQestnList = new ArrayList<>();
		List<QestnarQestnDetail> qestnarQestnDetailList = new ArrayList<>();
		
		int i = 1;
		for ( QestnarQestnSaveDto qestnarQestn : saveDto.getQestnarQestns() ) {
			
			qestnarQestn.setQestnarGroupSn( saveDto.getQestnarGroupSn() );
			
			qestnarQestn.setQestnarQestnSeq((long)i++);
			
			qestnarQestn.setRegisterId(SessionUtils.getUserId());
			qestnarQestn.setRegisterIp( IpUtils.getClientIP(request));
			qestnarQestn.setUpdusrId(SessionUtils.getUserId());
			qestnarQestn.setUpdusrIp( IpUtils.getClientIP(request));
			
			qestnarQestnList.add( qestnarQestn.toEntity() );
		}
		
		qestnarQestnList = qestnarQestnRepository.saveAll( qestnarQestnList );
		
		
		// tb_qestnar_qestn_detail ( 설문조사 질문 상세 insert )
		// ===========================================
		i = 0;
		for ( QestnarQestnSaveDto qestnarQestn : saveDto.getQestnarQestns() ) {
			
			List<QestnarQestnDetailSaveDto> qestnarQestnDetails = qestnarQestn.getQestnarQestnDetails();
			
			if ( qestnarQestnDetails != null && qestnarQestnDetails.size() > 0 ) {
				
				int j=1;
				
				for (QestnarQestnDetailSaveDto qestnarQestnDetail : qestnarQestnDetails) {
					
					qestnarQestnDetail.setRegisterId( SessionUtils.getUserId() );				// Id set : regist
					qestnarQestnDetail.setRegisterIp( IpUtils.getClientIP( request ) );			// Ip set : regist
					qestnarQestnDetail.setUpdusrId( SessionUtils.getUserId() );				// Id set : update
					qestnarQestnDetail.setUpdusrIp( IpUtils.getClientIP( request ) );			// Ip set : update
					
					qestnarQestnDetail.setQestnarQestnSn( qestnarQestnList.get(i).getQestnarQestnSn() );
					qestnarQestnDetail.setQestnarQestnDetailSeq( (long)j++ );	// seq : 1부터 시작
					
					qestnarQestnDetailList.add( qestnarQestnDetail.toEntity() );
					
				}
				
				qestnarQestnDetailList = qestnarQestnDetailRepository.saveAll( qestnarQestnDetailList );
				
			}
			
			i++;
			
			
		}
		
		
	}
	

    public Page<QestnarQestnListDto> getList( QestnarQestnListDto listDto, Pageable pageable ) {
        return qestnarQestnRepository.getList( listDto, pageable );
    }
    
    
    public List<QestnarQestnListDto> findByQestnarGroupSn( Long qestnarGroupSn ) {
    	return qestnarQestnRepository.findByQestnarGroupSn( qestnarGroupSn );
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
        
        
        // id,ip setting
        targetDto.setUpdusrIp( IpUtils.getClientIP( request ) );
        targetDto.setUpdusrId( SessionUtils.getUserId() );
        
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
    
    @Transactional
    public void deleteAllByQestnarGroupSn( Long pk ) {
    	
    	// delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
    	qestnarQestnRepository.deleteAllByQestnarGroupSn( pk );    // Entity 의 @SQLDelete 를 수행
    	
    }
	

}
