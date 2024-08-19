package com.meta.ponkids.domain.lctre.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.meta.ponkids.domain.lctre.dto.LctreListDto;
import com.meta.ponkids.domain.lctre.repository.LctreRepository;
import com.meta.ponkids.global.exception.CustomException;
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
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class LctreReqstService {


	private final LctreRepository 				lctreRepository;			// 수업 repository setting
	private final LctreReqstRepository 			lctreReqstRepository;		// 수업 신청 repository setting

	private final LctreReqstDetailRepository 	lctreReqstDetailRepository;	// 수업 신청 상세 repository setting
	
	@Transactional
	public LctreReqstSaveDto save( LctreReqstSaveDto saveDto, HttpServletRequest request, String moveUrl ) throws IOException {
		
		if ( saveDto != null ) {


			// list initial
			List<LctreReqstSaveDto> lctreReqsts = saveDto.getLctreReqsts();
			
			List<LctreReqst> lctreReqstList = new ArrayList<>();
			List<LctreReqstDetail> lctreReqstDetailList = new ArrayList<>();
			
			
			if ( lctreReqsts != null && lctreReqsts.size() > 0 ) {
				
				// 2. TB_LCTRE_REQST insert
				// ===========================================

				// 새로운 list 선언 ( long 변수)
				List<Long> rltmReqstList = new ArrayList<>();		// 현재신청중같은수업의신청 수를 체크하기위한 list

				for (LctreReqstSaveDto lctreReqst : lctreReqsts) {

					// [START] 수업별로 유효성 체크 진행
					LctreListDto targetDto = lctreRepository.getByLctreSn( lctreReqst.getLctreSn() );

					//      - 모집인원설정여부, 예비모집인원 설졍여부 확인
					String rcritNmprSetYn 		= targetDto.getRcritNmprSetYn();		// 모집 인원 설정 여부
					String preparRcritNmprSetYn = targetDto.getPreparRcritNmprSetYn();	// 예비 모집 인원 설정 여부
					Long rltmReqstNmprCo		= targetDto.getRltmReqstNmprCo();		// 실시간 신청 인원 수
					Long rcritNmprCo			= targetDto.getRcritNmprCo();			// 모집 인원 수
					Long rltmPreparReqstNmprCo	= targetDto.getRltmPreparReqstNmprCo();	// 실시간 예비 신청 인원 수
					Long preparRcritNmprCo		= targetDto.getPreparRcritNmprCo();		// 예비 모집 인원 수
					long nowReqstSameLctreCo = rltmReqstList.stream()
							.filter( value -> value.equals( targetDto.getLctreSn() ) )
							.count();													// 현재신청중같은수업의신청 수 : 현재 신청건 중 같은 수업으로 신청한 수업의 수


					//      1. 모집인원설정여부 설정시 : 모집인원수, 실시간신청인원수 확인
					//         (1) 모집인원수 >  ( 실시간신청인원수 +  현재신청중같은수업의신청 수 ) : 그대로 insert
					//         (2) 모집인원수 <= ( 실시간신청인원수 +  현재신청중같은수업의신청 수 ) :
					//             (2-1) [예비모집인원설정 Y 인 경우] : 모집인원수 + 예비모집인원수 > ( 실시간신청인원수 +  현재신청중같은수업의신청 수 ) : 예비인원설정 후 insert
					//                                              모집인원수 + 예비모집인원수 = ( 실시간신청인원수 +  현재신청중같은수업의신청 수 ) : 수강신청 실패 로직 (인원수초과 알림)
					//             (2-2) [예비모집인원설정 N 인 경우] : 수강신청 실패 로직 (인원수초과 알림)

					if ( StringUtils.hasText( rcritNmprSetYn ) && "Y".equals( rcritNmprSetYn ) ) {						//      1. 모집인원설정여부 설정시 : 모집인원수, 실시간신청인원수 확인
						if ( rcritNmprCo <= ( rltmReqstNmprCo + nowReqstSameLctreCo ) ) {															//         (2) 모집인원수 <= 실시간신청인원수 :
							if ( StringUtils.hasText( preparRcritNmprSetYn ) && "Y".equals( preparRcritNmprSetYn ) ) {	//             (2-1) [예비모집인원설정 Y 인 경우] : 모집인원수 + 예비모집인원수 > 실시간신청인원수 : 예비인원설정 후 insert
								if ( rcritNmprCo + preparRcritNmprCo > ( rltmReqstNmprCo + + nowReqstSameLctreCo + rltmPreparReqstNmprCo ) ) {
									// 예비인원 설정
									lctreReqst.setPreparNmprYn( "Y" );
									saveDto.setPreparNmprYn( "Y" );

									// rltmReqstList 리스트에 해당 lctreSn 값 추가
									rltmReqstList.add( lctreReqst.getLctreSn() );
								} else {
									// 수강신청 실패 로직 (인원수초과 알림)
									throw new CustomException( "[" +  targetDto.getLctreSj() +"] 수업의 모집인원 수를 초과하였습니다.", moveUrl );
								}
							} else {
								// 수강신청 실패 로직 (인원수초과 알림)
								throw new CustomException( "[" +  targetDto.getLctreSj() +"] 수업의 모집인원 수를 초과하였습니다.", moveUrl );
							}
						} else {
							// rltmReqstList 리스트에 해당 lctreSn 값 추가
							rltmReqstList.add( lctreReqst.getLctreSn() );
						}
					} else {
						// rltmReqstList 리스트에 해당 lctreSn 값 추가
						rltmReqstList.add( lctreReqst.getLctreSn() );
					}
					// [END] 수업별로 유효성 체크 진행


					lctreReqst.setRegisterId( SessionUtils.getUserId() );				// Id set : regist
					lctreReqst.setRegisterIp( IpUtils.getClientIP( request ) );			// Ip set : regist
					lctreReqst.setUpdusrId( SessionUtils.getUserId() );				// Id set : update
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
							lctreReqstDetail.setRegisterId( SessionUtils.getUserId() );				// Id set : regist
							lctreReqstDetail.setRegisterIp( IpUtils.getClientIP( request ) );			// Ip set : regist
							lctreReqstDetail.setUpdusrId( SessionUtils.getUserId() );				// Id set : update
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


	public List<LctreReqstListDto> getListByLctreSn( Long lctreSn ) {

		List<LctreReqstListDto> lctreReqsts = lctreReqstRepository.getListByLctreSn( lctreSn );


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
