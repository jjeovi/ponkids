package com.meta.ponkids.domain.system.cmmnCd.service;

import com.meta.ponkids.domain.system.cmmnCd.dto.CmmnCdDetailListDto;
import com.meta.ponkids.domain.system.cmmnCd.dto.CmmnCdDetailModDto;
import com.meta.ponkids.domain.system.cmmnCd.dto.CmmnCdDetailSaveDto;
import com.meta.ponkids.domain.system.cmmnCd.entity.CmmnCd;
import com.meta.ponkids.domain.system.cmmnCd.entity.CmmnCdDetail;
import com.meta.ponkids.domain.system.cmmnCd.repository.CmmnCdDetailRepository;
import com.meta.ponkids.domain.system.cmmnCd.repository.CmmnCdRepository;
import com.meta.ponkids.global.util.ip.IpUtils;
import com.meta.ponkids.global.util.session.SessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CmmnCdDetailService {
    
    private final CmmnCdRepository cmmnCdRepository;
    private final CmmnCdDetailRepository cmmnCdDetailRepository;    // repository setting
    
    @Transactional
    public CmmnCdDetailSaveDto save( CmmnCdDetailSaveDto saveDto, HttpServletRequest request ) throws IOException {
//    public CmmnCdDetailSaveDto save( CmmnCdDetailSaveDto saveDto, CmmnCdDetailRoleSaveDto cmmnCdDetailRoleSaveDto, HttpServletRequest request ) throws IOException {
        
        saveDto.setRegisterId( SessionUtils.getClientId() );                // Id set : regist
        saveDto.setRegisterIp( IpUtils.getClientIP( request ) );            // Ip set : regist
        saveDto.setUpdusrId( SessionUtils.getClientId() );                    // Id set : update
        saveDto.setUpdusrIp( IpUtils.getClientIP( request ) );                // Ip set : update
        
        CmmnCdDetail newCmmnCdDetail = cmmnCdDetailRepository.save( saveDto.toEntity() );            // ** save -> save된 정보 newXxx 로 저장
        
        return saveDto;
        
    }
    
    
    public Page<CmmnCdDetailListDto> getList( CmmnCdDetailListDto listDto, Pageable pageable ) {
        return cmmnCdDetailRepository.getList( listDto, pageable );
    }
    
    
    public List<CmmnCdDetailListDto> getList( String cdNm ) {
        
        CmmnCd cmmnCd = cmmnCdRepository.findByCdNm( cdNm );
        
        if ( cmmnCd != null ) {
            
            List<CmmnCdDetail> cmmnCdDetailList = cmmnCdDetailRepository.findByCdSnAndUseYnOrderByCdDetailSeqAsc( cmmnCd.getCdSn(), "Y" );    // useYn 은 default로 Y
            
            // entity to dto (List) 전환
            CmmnCdDetailListDto cmmnCdDetailListDto = new CmmnCdDetailListDto();    // new로 listDto 생성
            List<CmmnCdDetailListDto> cmmnCdDetailListDtoList = cmmnCdDetailList.stream().map( m -> cmmnCdDetailListDto.toDto( m ) ).collect( Collectors.toList() );
            
            return cmmnCdDetailListDtoList;
        } else {
            
            return null;
            
        }
        
    }
    
    
    public CmmnCdDetailModDto findById( Long pk ) {    // TODO 타입 체크 필요
        
        CmmnCdDetail cmmnCdDetail = cmmnCdDetailRepository.findById( pk ).orElse( null );
        
        CmmnCdDetailModDto modDto = new CmmnCdDetailModDto();
        modDto = modDto.toDto( cmmnCdDetail );
        
        return modDto;
    }
    
    @Transactional
    public void update( CmmnCdDetailModDto modDto, HttpServletRequest request ) throws IOException {
//    public void update ( CmmnCdDetailModDto modDto, CmmnCdDetailRoleModDto cmmnCdDetailRoleModDto, HttpServletRequest request ) throws IOException {
        
        // target 조회
        CmmnCdDetail cmmnCdDetail = cmmnCdDetailRepository.findById( modDto.getCdDetailSn() ).orElse( null );    // TODO PK 체크
        
        // target object 전환 ( entity to dto )
        CmmnCdDetailModDto targetDto = new CmmnCdDetailModDto();
        targetDto = targetDto.toDto( cmmnCdDetail );
        
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
        targetDto.setUpdusrId( "admin@test.com" );
        
        // target object 전환 ( dto to entity )
        cmmnCdDetail = targetDto.toEntity();
        
        // 수정사항 적용
        cmmnCdDetailRepository.save( cmmnCdDetail );
        
    }
    
    @Transactional
    public void deleteAllById( Long pk ) {
        
        // delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
        cmmnCdDetailRepository.deleteById( pk );    // Entity 의 @SQLDelete 를 수행
        
    }
    
    
}
