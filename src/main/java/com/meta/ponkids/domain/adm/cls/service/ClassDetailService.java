package com.meta.ponkids.domain.adm.cls.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.meta.ponkids.domain.adm.cls.dto.ClassDetailListDto;
import com.meta.ponkids.domain.adm.cls.dto.ClassDetailModDto;
import com.meta.ponkids.domain.adm.cls.dto.ClassDetailSaveDto;
import com.meta.ponkids.domain.adm.cls.entity.ClassDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meta.ponkids.domain.adm.cls.dto.ClassDto;
import com.meta.ponkids.domain.adm.cls.repository.ClassDetailRepository;
import com.meta.ponkids.global.util.ip.IpUtils;
import com.meta.ponkids.global.util.session.SessionUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassDetailService {
	private final ClassDetailRepository classDetailRepository;	// repository setting
	
	@Transactional
	public void save( ClassDto saveDto, HttpServletRequest request ) throws IOException {
		
		
		List<ClassDetail> classDetailList = new ArrayList<>();
		
		int i = 1;
		for ( ClassDetailSaveDto classDetail : saveDto.getClassDetails() ) {
			
			classDetail.setClassSn( saveDto.getClassSn() );
			classDetail.setClassDetailSeq((long)i++);
			
			classDetail.setRegisterId(SessionUtils.getClientId());
			classDetail.setRegisterIp( IpUtils.getClientIP(request));
			classDetail.setUpdusrId(SessionUtils.getClientId());
			classDetail.setUpdusrIp( IpUtils.getClientIP(request));
			
			classDetailList.add(classDetail.toEntity());
		}
		
		classDetailRepository.saveAll(classDetailList);
		
	}
	

    public Page<ClassDetailListDto> getList( ClassDetailListDto listDto, Pageable pageable ) {
        return classDetailRepository.getList( listDto, pageable );
    }
    
    public List<ClassDetailListDto> findByClassSnOrderByClassDetailSeq(Long pk) {
    	
    	return classDetailRepository.findByClassSnOrderByClassDetailSeq( pk );
    }
    
    
    public ClassDetailModDto findById( Long pk ) {	// TODO 타입 체크 필요
        
        ClassDetail classDetail = classDetailRepository.findById( pk ).orElse(null);
        
        if (classDetail == null ) { 
        	
        	return null;
        } else {
        
	        ClassDetailModDto modDto = new ClassDetailModDto();
	        modDto = modDto.toDto( classDetail );
	        
	        return modDto;
        }
    }
    
    @Transactional
    public void update ( ClassDetailModDto modDto, HttpServletRequest request ) throws IOException {
//    public void update ( ClassDetailModDto modDto, ClassDetailRoleModDto classDetailRoleModDto, HttpServletRequest request ) throws IOException {
    	
    	// target 조회
        ClassDetail classDetail = classDetailRepository.findById( modDto.getClassDetailSn() ).orElse(null);	// TODO PK 체크
        
        // target object 전환 ( entity to dto )
        ClassDetailModDto targetDto = new ClassDetailModDto();
        targetDto = targetDto.toDto( classDetail );
        
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
        classDetail = targetDto.toEntity();
        
        // 수정사항 적용
        classDetailRepository.save( classDetail );
    	
    }

    @Transactional
    public void deleteAllById( Long pk ) {
        
        // delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
        classDetailRepository.deleteById( pk );    // Entity 의 @SQLDelete 를 수행
        
    }
    
    
    @Transactional
    public void deleteAllByClassSn(Long pk ) {
        
    	classDetailRepository.deleteAllByClassSn(pk);
        
    }
	

}
