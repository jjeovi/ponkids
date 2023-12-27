package com.meta.ponkids.domain.cls.service;

import com.meta.ponkids.domain.cls.dto.ClassCategoryCl01ListDto;
import com.meta.ponkids.domain.cls.dto.ClassCategoryCl01ModDto;
import com.meta.ponkids.domain.cls.dto.ClassCategoryCl01SaveDto;
import com.meta.ponkids.domain.cls.entity.ClassCategoryCl01;
import com.meta.ponkids.domain.cls.repository.ClassCategoryCl01Repository;
import com.meta.ponkids.global.util.ip.IpUtils;
import com.meta.ponkids.global.util.session.SessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassCategoryCl01Service {
    private final ClassCategoryCl01Repository classCategoryCl01Repository;    // repository setting
    
    @Transactional
    public ClassCategoryCl01SaveDto save( ClassCategoryCl01SaveDto saveDto, HttpServletRequest request ) throws IOException {
//    public ClassCategoryCl01SaveDto save( ClassCategoryCl01SaveDto saveDto, ClassCategoryCl01RoleSaveDto classCategoryCl01RoleSaveDto, HttpServletRequest request ) throws IOException {
        
        saveDto.setRegisterId( SessionUtils.getClientId() );                // Id set : regist
        saveDto.setRegisterIp( IpUtils.getClientIP( request ) );            // Ip set : regist
        saveDto.setUpdusrId( SessionUtils.getClientId() );                    // Id set : update
        saveDto.setUpdusrIp( IpUtils.getClientIP( request ) );                // Ip set : update
        
        ClassCategoryCl01 newClassCategoryCl01 = classCategoryCl01Repository.save( saveDto.toEntity() );            // ** save -> save된 정보 newXxx 로 저장
        
        return saveDto;
    }
    
    public Page<ClassCategoryCl01ListDto> getList( ClassCategoryCl01ListDto listDto, Pageable pageable ) {
        
        return classCategoryCl01Repository.getList( listDto, pageable );
        
    }
    
    public ClassCategoryCl01ModDto findById( Long pk ) {    // TODO 타입 체크 필요
        
        ClassCategoryCl01 classCategoryCl01 = classCategoryCl01Repository.findById( pk ).orElse( null );
        
        if ( classCategoryCl01 == null ) {
            
            return null;
            
        } else {
            
            ClassCategoryCl01ModDto modDto = new ClassCategoryCl01ModDto();
            modDto = modDto.toDto( classCategoryCl01 );
            
            return modDto;
        }
        
    }
    
    public List<ClassCategoryCl01ListDto> findAll() {    // TODO 타입 체크 필요
        
        List<ClassCategoryCl01> classCategoryCl01List = classCategoryCl01Repository.findAllByOrderByClSeq();
        
        
        // entity to dto (List) 전환
        ClassCategoryCl01ListDto classCategoryCl01ListDto = new ClassCategoryCl01ListDto();    // new로 listDto 생성
        List<ClassCategoryCl01ListDto> listDtoList = classCategoryCl01List.stream().map( m -> classCategoryCl01ListDto.toDto( m ) ).collect( Collectors.toList() );
        
        return listDtoList;
    }
    
    @Transactional
    public void update( ClassCategoryCl01ModDto modDto, HttpServletRequest request ) throws IOException {
//    public void update ( ClassCategoryCl01ModDto modDto, ClassCategoryCl01RoleModDto classCategoryCl01RoleModDto, HttpServletRequest request ) throws IOException {
        
        // target 조회
        ClassCategoryCl01 classCategoryCl01 = classCategoryCl01Repository.findById( modDto.getClSn() ).orElse( null );    // TODO PK 체크
        
        // target object 전환 ( entity to dto )
        ClassCategoryCl01ModDto targetDto = new ClassCategoryCl01ModDto();
        targetDto = targetDto.toDto( classCategoryCl01 );
        
        // TODO target object 에 수정사항 set	
        // entity 에서 반영하지 않을 컬럼은 updatable = false 옵션 추가
        if ( StringUtils.hasText( modDto.getClNm() ) ) targetDto.setClNm( modDto.getClNm() );            // 이름
        if ( modDto.getClSeq() != null ) targetDto.setClSeq( modDto.getClSeq() );                        // 성별
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
        classCategoryCl01 = targetDto.toEntity();
        
        // 수정사항 적용
        classCategoryCl01Repository.save( classCategoryCl01 );
    }
    
    @Transactional
    public void deleteAllById( Long pk ) {
        
        // delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
        classCategoryCl01Repository.deleteById( pk );    // Entity 의 @SQLDelete 를 수행
        
    }
    
}
