package com.meta.ponkids.domain.cls.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.meta.ponkids.domain.cls.dto.ClassListDto;
import com.meta.ponkids.domain.cls.dto.ClassModDto;
import com.meta.ponkids.domain.cls.dto.ClassSaveDto;
import com.meta.ponkids.domain.cls.entity.Class;
import com.meta.ponkids.domain.cls.repository.ClassRepository;
import com.meta.ponkids.global.common.dto.CategoryDto;
import com.meta.ponkids.global.util.ip.IpUtils;
import com.meta.ponkids.global.util.session.SessionUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassService {
    private final ClassRepository classRepository;    // repository setting
    
    @Transactional
    public ClassSaveDto save( ClassSaveDto saveDto, HttpServletRequest request ) throws IOException {
        
        saveDto.setRegisterId( SessionUtils.getClientId() );                // Id set : regist
        saveDto.setRegisterIp( IpUtils.getClientIP( request ) );            // Ip set : regist
        saveDto.setUpdusrId( SessionUtils.getClientId() );                  // Id set : update
        saveDto.setUpdusrIp( IpUtils.getClientIP( request ) );              // Ip set : update
        
        if ( StringUtils.hasText( saveDto.getClassPdSetYn() ) && saveDto.getClassPdSetYn().equals( "Y" ) ) {
            // 표시기간설정여부 체크하여 표시기간 있을 시 숫자제외 다른 문자들 제거 작업 필요
            
            if ( !StringUtils.hasText( saveDto.getClassBeginDt() ) || !StringUtils.hasText( saveDto.getClassEndDt() ) ) {
                throw new IOException( "[표시기간 설정 필요]" );
            }
            
        } else if ( StringUtils.hasText( saveDto.getClassPdSetYn() ) && saveDto.getClassPdSetYn().equals( "N" ) ) {
            // 표시기간설정이 N 인 경우 (상시) , 시작일시, 종료일시 값을 null 로 setting
            saveDto.setClassBeginDt( null );
            saveDto.setClassEndDt( null );
            
        }
        
        Class newClass = classRepository.save( saveDto.toEntity() );            // ** save -> save된 정보 newXxx 로 저장
        saveDto.setClassSn( newClass.getClassSn() );
        
        return saveDto;
        
    }
    
    public Page<ClassListDto> getList( ClassListDto listDto, Pageable pageable ) {
        return classRepository.getList( listDto, pageable );
    }
    
    
    public List<ClassListDto> getList( ClassListDto listDto ) {
        
        List<ClassListDto> listDtos = classRepository.getList( listDto );
        
        // 카테고리 값 뿌리기 위한  setting
        for( ClassListDto dto : listDtos ) dto = createCategory( dto );
        
        return listDtos;
    }
    
    // 나의 클래스와 같은 카테고리의 다른 클래스 ( 내 클래스는 제외하고 검색 ) : 10건만 조회
    public List<ClassListDto> getListTop10OtherClassExceptMeByCtgrySn( ClassListDto targetDto ) {
    	
    	return classRepository.getListTop10OtherClassExceptMeByCtgrySn( targetDto );
    }
    
    
    // 커리큘럼으로 classList 검색
    public List<ClassListDto> getListByCrseSn( ClassListDto listDto ) {
    	
    	Long ctgrySn 	= listDto.getCtgrySn();			// 카테고리 일련번호
    	Long crseSn 	= listDto.getCrseSn(); 			// 커리큘럼 일련번호 
    	
    	List<Class> classList = null ;
    	
    	if ( crseSn == 0 ) {	// 커리큘럼 일련번호가 전체일 경우 : 카테고리 일련번호로 클래스 검색 
    		classList = classRepository.findByCtgrySnOrderByClassSnDesc( ctgrySn );
    	} else {				// 그 외 일경우 : 커리큘럼 일련번호로 검색
    		classList = classRepository.findByCrseSnOrderByClassSnDesc( crseSn );
    	}
    	
    	ClassListDto classListDto = new ClassListDto();
    	List<ClassListDto> listDtos = classList
    			.stream()
    			.map( m -> classListDto.toDto( m ) )							// 1. entity to dto 작업
    			.map( m -> createCategory( m ) )                                   // 2. 카테고리 값 뿌리기 위한  setting
                .collect( Collectors.toList() );								// 3. 1,2 과정을 거친 후 toList로 전환
    	
    	return listDtos;
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
    
    
    // pk 로 조회 ( 고유 1건 조회 ) 
    public ClassListDto getByClassSn( Long pk, Long userSn ) {
    	return classRepository.getByClassSn( pk, userSn );
    }
    
    
    // pk 로 조회 ( 고유 1건 조회 ) 
    public ClassListDto getByClassSn( Long pk ) {
    	return classRepository.getByClassSn( pk );
    }
    
    @Transactional
    public void update( ClassModDto modDto, HttpServletRequest request ) throws IOException {
        
        // target 조회
        Class clas = classRepository.findById( modDto.getClassSn() ).orElse( null );
        
        // target object 전환 ( entity to dto )
        ClassModDto targetDto = new ClassModDto();
        targetDto = targetDto.toDto( clas );
        
        // target object 에 수정사항 set	
        // entity 에서 반영하지 않을 컬럼은 updatable = false 옵션 추가
        if ( modDto.getCtgrySn() != null )                      targetDto.setCtgrySn( modDto.getCtgrySn() ); 	                // 카테고리
        if ( modDto.getCrseSn() != null )                       targetDto.setCrseSn( modDto.getCrseSn() );                      // 커리큘럼
        if ( StringUtils.hasText( modDto.getClassSj() ) )       targetDto.setClassSj( modDto.getClassSj() );                    // 클래스 제목
        if ( StringUtils.hasText( modDto.getClassSumry() ) )    targetDto.setClassSumry( modDto.getClassSumry() );              // 클래스 요약
        if ( StringUtils.hasText( modDto.getClassDc() ) )       targetDto.setClassDc( modDto.getClassDc() );                    // 클래스 설명
        if ( modDto.getClassAmt() != null )                     targetDto.setClassAmt( modDto.getClassAmt() );                  // 금액
        if ( modDto.getClassDscntBfeAmt() != null )             targetDto.setClassDscntBfeAmt( modDto.getClassDscntBfeAmt() );  // 할인된 금액
        targetDto.setClassTrgtCd(modDto.getClassTrgtCd());																		// 클래스 대상 코드
        if ( StringUtils.hasText( modDto.getClassExpsrYn() ) )  targetDto.setClassExpsrYn( modDto.getClassExpsrYn() );          // 표시여부
        if ( StringUtils.hasText( modDto.getClassExpsrYn() ) )  targetDto.setClassExpsrYn( modDto.getClassExpsrYn() );          // 표시여부
        if ( StringUtils.hasText( modDto.getClassPdSetYn() ) )  targetDto.setClassPdSetYn( modDto.getClassPdSetYn() );          // 표시기간설정여부
        
        targetDto.setThumbAtchFileSn( modDto.getThumbAtchFileSn() );    // 첨부파일 ( 썸네일 )
        targetDto.setAtchFileSn( modDto.getAtchFileSn() );              // 첨부파일 (본문)
        targetDto.setClassBeginDt( modDto.getClassBeginDt() );          // 표시시작일시
        targetDto.setClassEndDt( modDto.getClassEndDt() );              // 표시종료일시
        
        // id,ip setting
        targetDto.setUpdusrIp( IpUtils.getClientIP( request ) );
        targetDto.setUpdusrId( SessionUtils.getClientId() );
        
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
    
    
    
    
    // ================================== UTIL ========================================
    // ================================== UTIL ========================================
    // ================================== UTIL ========================================
    
    
    private ClassListDto createCategory(ClassListDto listDto ) {
        
        CategoryDto categoryDto = new CategoryDto();
        
        categoryDto.setCategorySn(listDto.getClassSn());    // 이 부분이 결국은 html 에서 카테고리검색의 li value 값이 됨
        categoryDto.setCategoryNm(listDto.getClassSj());    // 이 부분이 결국은 html 에서 카테고리검색의 li 명칭이 됨
        
        listDto.setCategory(categoryDto);
        
        return listDto;
        
    }
    
}
