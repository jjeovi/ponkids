package com.meta.ponkids.domain.lctre.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meta.ponkids.domain.lctre.dto.LctreReqstDetailSaveDto;
import com.meta.ponkids.domain.lctre.dto.LctreReqstSaveDto;
import com.meta.ponkids.domain.lctre.entity.LctreReqst;
import com.meta.ponkids.domain.lctre.entity.LctreReqstDetail;
import com.meta.ponkids.domain.lctre.repository.LctreReqstDetailRepository;
import com.meta.ponkids.domain.lctre.repository.LctreReqstRepository;
import com.meta.ponkids.global.util.ip.IpUtils;
import com.meta.ponkids.global.util.session.SessionUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LctreReqstService {
	
	private final LctreReqstRepository 			lctreReqstRepository;		// 수업 신청 repository setting
	private final LctreReqstDetailRepository 	lctreReqstDetailRepository;	// 수업 신청 상세 repository setting
	
	@Transactional
	public LctreReqstSaveDto save( LctreReqstSaveDto saveDto, HttpServletRequest request ) throws IOException {
		
		if ( saveDto != null ) {
			
			// list initial
			List<LctreReqstSaveDto> lctreReqsts = saveDto.getLctreReqsts();
			
			List<LctreReqst> lctreReqstList = new ArrayList<>();
			List<LctreReqstDetail> lctreReqstDetailList = new ArrayList<>();
			
			
			if ( lctreReqsts != null && lctreReqsts.size() > 0 ) {
				
				// 2. TB_LCTRE_REQST insert
				// ===========================================
				
				for (LctreReqstSaveDto lctreReqst : lctreReqsts) {
					lctreReqst.setRegisterId( SessionUtils.getClientId() );				// Id set : regist
					lctreReqst.setRegisterIp( IpUtils.getClientIP( request ) );			// Ip set : regist
					lctreReqst.setUpdusrId( SessionUtils.getClientId() );				// Id set : update
					lctreReqst.setUpdusrIp( IpUtils.getClientIP( request ) );			// Ip set : update
					
					// 2-1. 클래스 신청 일련번호 (classReqstSn 값 set) set
					lctreReqst.setClassReqstSn( saveDto.getClassReqstSn() );
					
					lctreReqstList.add( lctreReqst.toEntity() );         
				}
				
				lctreReqstList = lctreReqstRepository.saveAll( lctreReqstList );
				
				
				
				// 3. TB_LCTRE_REQST_DETAIL insert
				// ===========================================
				int i = 0 ;		// i =  lctreReqstList 의 i 번째 값을 얻기 위해 사용하는 변수 
				for (LctreReqstSaveDto lctreReqst : lctreReqsts) {
					// list initial
					List<LctreReqstDetailSaveDto> lctreReqstDetails = lctreReqst.getLctreReqstDetails();
					
					if ( lctreReqstDetails != null && lctreReqstDetails.size() > 0 ) {
						
						for (LctreReqstDetailSaveDto lctreReqstDetail : lctreReqstDetails) {
							lctreReqstDetail.setRegisterId( SessionUtils.getClientId() );				// Id set : regist
							lctreReqstDetail.setRegisterIp( IpUtils.getClientIP( request ) );			// Ip set : regist
							lctreReqstDetail.setUpdusrId( SessionUtils.getClientId() );				// Id set : update
							lctreReqstDetail.setUpdusrIp( IpUtils.getClientIP( request ) );			// Ip set : update
							
							// 2-1. 수업 신청 일련번호 (lctreReqstSn 값 set) set
							lctreReqstDetail.setLctreReqstSn( lctreReqstList.get(i).getLctreReqstSn() );
							
							lctreReqstDetailList.add( lctreReqstDetail.toEntity() );
						}
						
					}
					i++;
				}
				
				lctreReqstDetailList = lctreReqstDetailRepository.saveAll( lctreReqstDetailList );
				
			}
			
		}
		
		return saveDto;
	}
	

//    public Page<LctreListDto> getList( LctreListDto listDto, Pageable pageable ) {
//        return lctreRepository.getList( listDto, pageable );
//    }
//    
//    
//    public List<LctreAjaxDto> findByClassSnAndClassDayCdAjax( LctreListDto listDto ) {
//    	List<Lctre> lctreList = lctreRepository.findByClassSnAndClassDayCdOrderByLctreSeqAsc(listDto.getClassSn(), listDto.getClassDayCd());
//    	
//    	LctreAjaxDto lctreListDto = new LctreAjaxDto();
//    	List<LctreAjaxDto> listDtos = lctreList
//    			.stream()
//    			.map( m -> lctreListDto.toDto( m ) )
//    			.collect( Collectors.toList() );
//    	
//    	return listDtos;
//    }
//    
//    
//    public LctreListDto getListByClassSn( Long classSn ) {
//    	return lctreRepository.getListByClassSn( classSn );
//    }
//    
//    
//    public LctreModDto findTop1ByClassSnOrderByLctreSeqDesc ( Long pk ) {
//        // target 조회
//        Lctre lctre = lctreRepository.findTop1ByClassSnOrderByLctreSeqDesc( pk ).orElse(null);
//        
//        if (lctre == null ) {
//            return null;
//        } else {
//            
//            LctreModDto targetDto  = new LctreModDto();
//            targetDto = targetDto.toDto( lctre );
//            
//            return targetDto;
//        }
//        
//    }
//    
//    
//    public LctreModDto findById( Long pk ) {	// TODO 타입 체크 필요
//        
//        Lctre lctre = lctreRepository.findById( pk ).orElse(null);
//        
//        if (lctre == null ) { 
//        	
//        	return null;
//        } else {
//        
//	        LctreModDto modDto = new LctreModDto();
//	        modDto = modDto.toDto( lctre );
//	        
//	        return modDto;
//        }
//    }
//    
//    @Transactional
//    public void update ( LctreModDto modDto, HttpServletRequest request ) throws IOException {
////    public void update ( LctreModDto modDto, LctreRoleModDto lctreRoleModDto, HttpServletRequest request ) throws IOException {
//    	
//    	// target 조회
//        Lctre lctre = lctreRepository.findById( modDto.getLctreSn() ).orElse(null);	// TODO PK 체크
//        
//        // target object 전환 ( entity to dto )
//        LctreModDto targetDto = new LctreModDto();
//        targetDto = targetDto.toDto( lctre );
//        
//        // TODO target object 에 수정사항 set	
//        // entity 에서 반영하지 않을 컬럼은 updatable = false 옵션 추가
//        if ( StringUtils.hasText(modDto.getClassDayCd())) targetDto.setClassDayCd( modDto.getClassDayCd() );    // 수업 요일
//        targetDto.setLctreSeq( modDto.getLctreSeq() );                                                          // 수업 순번
//        if(StringUtils.hasText( modDto.getLctreSj() ))  targetDto.setLctreSj( modDto.getLctreSj() );            // 수업 제목
//        targetDto.setLctreAmt( modDto.getLctreAmt() );            												// 수업 금액
//        targetDto.setLctreDc( modDto.getLctreDc() );                                                            // 수업 설명
//        targetDto.setLctreApplcntGuidance( modDto.getLctreApplcntGuidance() );                                  // 신청자 안내
//        if(StringUtils.hasText( modDto.getRcritNmprSetYn() ))  targetDto.setRcritNmprSetYn( modDto.getRcritNmprSetYn() );            // 모집인원 설정여부
//        targetDto.setRcritNmprCo( modDto.getRcritNmprCo() );                                                    // 모집인원 수
//        if(StringUtils.hasText( modDto.getPreparRcritNmprSetYn() ))  targetDto.setPreparRcritNmprSetYn( modDto.getPreparRcritNmprSetYn() );            // 모집인원 설정여부
//        targetDto.setPreparRcritNmprCo( modDto.getPreparRcritNmprCo() );                                        // 모집인원 수
//        
//        // id,ip setting
//        targetDto.setUpdusrIp( IpUtils.getClientIP( request ) );
//        targetDto.setUpdusrId( SessionUtils.getClientId() );
//        
//        // target object 전환 ( dto to entity )
//        lctre = targetDto.toEntity();
//        
//        // 수정사항 적용
//        lctreRepository.save( lctre );
//    	
//    }
//
//    @Transactional
//    public void deleteAllById( Long pk ) {
//        
//        // delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
//        lctreRepository.deleteById( pk );    // Entity 의 @SQLDelete 를 수행
//        
//    }
	

}
