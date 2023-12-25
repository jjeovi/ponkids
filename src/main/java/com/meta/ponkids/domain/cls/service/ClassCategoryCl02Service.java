package com.meta.ponkids.domain.cls.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meta.ponkids.domain.cls.dto.ClassCategoryCl02ListDto;
import com.meta.ponkids.domain.cls.dto.ClassCategoryCl02ModDto;
import com.meta.ponkids.domain.cls.dto.ClassCategoryCl02SaveDto;
import com.meta.ponkids.domain.cls.entity.ClassCategoryCl02;
import com.meta.ponkids.domain.cls.repository.ClassCategoryCl02Repository;
import com.meta.ponkids.global.util.ip.IpUtils;
import com.meta.ponkids.global.util.session.SessionUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassCategoryCl02Service {
	private final ClassCategoryCl02Repository classCategoryCl02Repository;	// repository setting
	
	@Transactional
	public ClassCategoryCl02SaveDto save( ClassCategoryCl02SaveDto saveDto, HttpServletRequest request ) throws IOException {
//    public ClassCategoryCl02SaveDto save( ClassCategoryCl02SaveDto saveDto, ClassCategoryCl02RoleSaveDto classCategoryCl02RoleSaveDto, HttpServletRequest request ) throws IOException {
		
		saveDto.setRegisterId( SessionUtils.getClientId() );				// Id set
		saveDto.setRegisterIp( IpUtils.getClientIP( request ) );			// Ip set
		
		ClassCategoryCl02 newClassCategoryCl02 = classCategoryCl02Repository.save( saveDto.toEntity() );			// ** save -> save된 정보 newXxx 로 저장
		
		return saveDto;
		
	}
	
    public Page<ClassCategoryCl02ListDto> getList( ClassCategoryCl02ListDto listDto, Pageable pageable ) {
        return classCategoryCl02Repository.getList( listDto, pageable );
    }
    
    public ClassCategoryCl02ModDto findById( Long pk ) {	// TODO 타입 체크 필요
        
        ClassCategoryCl02 classCategoryCl02 = classCategoryCl02Repository.findById( pk ).orElse(null);
        
        if ( classCategoryCl02 == null ) {
        	
        	return null;
        	
        } else {
        	ClassCategoryCl02ModDto modDto = new ClassCategoryCl02ModDto();
        	modDto = modDto.toDto( classCategoryCl02 );
        	
        	return modDto;
        }
    }
    
    
    public List<ClassCategoryCl02ListDto> findAll() {	// TODO 타입 체크 필요
        
    	List<ClassCategoryCl02> classCategoryCl02List = classCategoryCl02Repository.findAll();
        
    	
    	// entity to dto (List) 전환
    	ClassCategoryCl02ListDto classCategoryCl02ListDto = new ClassCategoryCl02ListDto();	// new로 listDto 생성
    	List<ClassCategoryCl02ListDto> listDtoList = classCategoryCl02List.stream().map(m -> classCategoryCl02ListDto.toDto(m)).collect(Collectors.toList());
        
        return listDtoList;
    }
    
    @Transactional
    public void update ( ClassCategoryCl02ModDto modDto, HttpServletRequest request ) throws IOException {
//    public void update ( ClassCategoryCl02ModDto modDto, ClassCategoryCl02RoleModDto classCategoryCl02RoleModDto, HttpServletRequest request ) throws IOException {
    	
    	// target 조회
        ClassCategoryCl02 classCategoryCl02 = classCategoryCl02Repository.findById( modDto.getClSn() ).orElse(null);	// TODO PK 체크
        
        // target object 전환 ( entity to dto )
        ClassCategoryCl02ModDto targetDto = new ClassCategoryCl02ModDto();
        targetDto = targetDto.toDto( classCategoryCl02 );
        
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
        classCategoryCl02 = targetDto.toEntity();
        
        // 수정사항 적용
        classCategoryCl02Repository.save( classCategoryCl02 );
    	
    }

    @Transactional
    public void deleteAllById( Long pk ) {
        
        // delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
        classCategoryCl02Repository.deleteById( pk );    // Entity 의 @SQLDelete 를 수행
    }
	

}
