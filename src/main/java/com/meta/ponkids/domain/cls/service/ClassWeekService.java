package com.meta.ponkids.domain.cls.service;

import com.meta.ponkids.domain.cls.dto.*;
import com.meta.ponkids.domain.cls.entity.ClassWeek;
import com.meta.ponkids.domain.cls.repository.ClassWeekRepository;
import com.meta.ponkids.global.util.ip.IpUtils;
import com.meta.ponkids.global.util.session.SessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassWeekService {
    private final ClassWeekRepository classWeekRepository;    // repository setting
    
    @Transactional
    public void save( ClassDto saveDto, HttpServletRequest request ) throws IOException {
        
        if ( saveDto.getClassWeek().size() != 0 ) {
            List<ClassWeek> classWeekList = new ArrayList<>();
            for ( String yoil : saveDto.getClassWeek() ) {
                ClassWeekSaveDto classWeekSaveDto = new ClassWeekSaveDto();
                classWeekSaveDto.setClassSn( saveDto.getClassSn() );
                classWeekSaveDto.setClassDayCd( yoil );
                
                classWeekSaveDto.setRegisterIp( IpUtils.getClientIP( request ) );                                       // 등록자 ip
                classWeekSaveDto.setRegisterId( SessionUtils.getClientId() );                                           // 등록자 id
                classWeekSaveDto.setUpdusrIp( IpUtils.getClientIP( request ) );                                         // 수정자 ip
                classWeekSaveDto.setUpdusrId( SessionUtils.getClientId() );                                             // 수정자 id
                
                classWeekList.add( classWeekSaveDto.toEntity() );
                
            }
            classWeekRepository.saveAll( classWeekList );
        }
        
    }
    
    public Page<ClassWeekListDto> getList( ClassWeekListDto listDto, Pageable pageable ) {
        return classWeekRepository.getList( listDto, pageable );
    }
    
    public List<ClassWeekListDto> findByClassSnOrderByClassWeekSn( Long pk) {
        List<ClassWeek> classWeekList =  classWeekRepository.findByClassSnOrderByClassWeekSn( pk );
        
        ClassWeekListDto classWeekListDto = new ClassWeekListDto();
        List<ClassWeekListDto> listDtos = classWeekList.stream().map( m -> classWeekListDto.toDto( m ) ).collect( Collectors.toList());
        
        return listDtos;
    }
    
    
    public List<ClassWeekListDto> getListByClassSn( Long pk) {
    	return classWeekRepository.getListByClassSn( pk );
    }
    
    public ClassWeekModDto findById( Long pk ) {    // 타입 체크 필요
        
        ClassWeek classWeek = classWeekRepository.findById( pk ).orElse( null );
        
        if ( classWeek == null ) {
            
            return null;
        } else {
            
            ClassWeekModDto modDto = new ClassWeekModDto();
            modDto = modDto.toDto( classWeek );
            
            return modDto;
        }
    }
    
    @Transactional
    public void update( ClassWeekModDto modDto, HttpServletRequest request ) throws IOException {
//    public void update ( ClassWeekModDto modDto, ClassWeekRoleModDto classWeekRoleModDto, HttpServletRequest request ) throws IOException {
        
        // target 조회
        ClassWeek classWeek = classWeekRepository.findById( modDto.getClassWeekSn() ).orElse( null );    // PK 체크
        
        // target object 전환 ( entity to dto )
        ClassWeekModDto targetDto = new ClassWeekModDto();
        targetDto = targetDto.toDto( classWeek );
        
        // target object 에 수정사항 set	
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
        classWeek = targetDto.toEntity();
        
        // 수정사항 적용
        classWeekRepository.save( classWeek );
        
    }
    
    @Transactional
    public void deleteAllByClassSn(Long pk ) {
        
        classWeekRepository.deleteAllByClassSn(pk);
        
    }
    
}
