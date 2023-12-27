package com.meta.ponkids.domain.cls.service;

import com.meta.ponkids.domain.cls.dto.ClassListDto;
import com.meta.ponkids.domain.cls.dto.ClassModDto;
import com.meta.ponkids.domain.cls.dto.ClassSaveDto;
import com.meta.ponkids.domain.cls.entity.Class;
import com.meta.ponkids.domain.cls.repository.ClassRepository;
import com.meta.ponkids.global.util.ip.IpUtils;
import com.meta.ponkids.global.util.session.SessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ClassService {
    private final ClassRepository classRepository;    // repository setting
    
    @Transactional
    public ClassSaveDto save( ClassSaveDto saveDto, HttpServletRequest request ) throws IOException {
        
        saveDto.setRegisterId( SessionUtils.getClientId() );                // Id set : regist
        saveDto.setRegisterIp( IpUtils.getClientIP( request ) );            // Ip set : regist
        saveDto.setUpdusrId( SessionUtils.getClientId() );                    // Id set : update
        saveDto.setUpdusrIp( IpUtils.getClientIP( request ) );                // Ip set : update
        
        Class newClass = classRepository.save( saveDto.toEntity() );            // ** save -> save된 정보 newXxx 로 저장
        
        return saveDto;
        
    }
    
    public Page<ClassListDto> getList( ClassListDto listDto, Pageable pageable ) {
        return classRepository.getList( listDto, pageable );
    }
    
    
    public ClassModDto findById( Long pk ) {
        
        Class clas = classRepository.findById( pk ).orElse( null );
        
        if ( clas == null ) {
            
            return null;
            
        } else {
            ClassModDto modDto = new ClassModDto();
            modDto = modDto.toDto( clas );
            
            return modDto;
        }
        
    }
    
    @Transactional
    public void update( ClassModDto modDto, HttpServletRequest request ) throws IOException {
        
        // target 조회
        Class clas = classRepository.findById( modDto.getClassSn() ).orElse( null );
        
        // target object 전환 ( entity to dto )
        ClassModDto targetDto = new ClassModDto();
        targetDto = targetDto.toDto( clas );
        
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
        clas = targetDto.toEntity();
        
        // 수정사항 적용
        classRepository.save( clas );
        
    }
    
    @Transactional
    public void deleteAllById( Long pk ) {
        
        // delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
        classRepository.deleteById( pk );    // Entity 의 @SQLDelete 를 수행
        
    }
    
}
