package com.meta.ponkids.domain.lctre.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meta.ponkids.domain.lctre.dto.LctreReqstDetailSaveDto;
import com.meta.ponkids.domain.lctre.dto.LctreReqstListDto;
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
	
	public List<LctreReqstListDto> getListByClassReqstSn( Long classReqstSn ) {
		
		List<LctreReqstListDto> lctreReqsts = lctreReqstRepository.getListByClassReqstSn( classReqstSn );
		
		
		if ( lctreReqsts != null && lctreReqsts.size() > 0 ) {
			
			lctreReqsts = lctreReqsts
						.stream()
						.map( m -> {
							m.setLctreReqstDetails( lctreReqstDetailRepository.getListByLctreReqstSn( m.getLctreReqstSn() ) );
							return m;
						} ).collect( Collectors.toList() );
			
		}
		
		
		return lctreReqsts;
	}
	
	
	// 각 수업신청건에 대한 수업 신청 상세 init 작업 
	private LctreReqstListDto initLctreReqstDetails( LctreReqstListDto m ) {
		
		m.setLctreReqstDetails( lctreReqstDetailRepository.getListByLctreReqstSn( m.getLctreReqstSn() ) );
		return m;
	}

	@Transactional
	public void deleteByClassReqstSn( Long classReqstSn ) {

		// 1. 수업 신청 delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
		lctreReqstRepository.deleteByClassReqstSn( classReqstSn );
		// 2. 수업 상세 신청 delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
		lctreReqstDetailRepository.deleteByClassReqstSn( classReqstSn );
	}



}
