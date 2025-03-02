package com.meta.ponkids.domain.cls.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meta.ponkids.domain.cls.dto.ClassInqryModDto;
import com.meta.ponkids.domain.cls.dto.ClassInqryListDto;
import com.meta.ponkids.domain.cls.dto.ClassInqrySaveDto;
import com.meta.ponkids.domain.cls.entity.ClassInqry;
import com.meta.ponkids.domain.cls.repository.ClassInqryRepository;
import com.meta.ponkids.global.util.ip.IpUtils;
import com.meta.ponkids.global.util.session.SessionUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassInqryService {
	private final ClassInqryRepository classInqryRepository;	// repository setting
	
	@Transactional
	public ClassInqrySaveDto save( ClassInqrySaveDto saveDto, HttpServletRequest request ) throws IOException {
//    public ClassInqrySaveDto save( ClassInqrySaveDto saveDto, ClassInqryRoleSaveDto classInqryRoleSaveDto, HttpServletRequest request ) throws IOException {
		
		saveDto.setRegisterId( SessionUtils.getUserId() );				// Id set : regist
		saveDto.setRegisterIp( IpUtils.getClientIP( request ) );		// Ip set : regist
		saveDto.setUpdusrId( SessionUtils.getUserId() );				// Id set : update
		saveDto.setUpdusrIp( IpUtils.getClientIP( request ) );			// Ip set : update
		
		ClassInqry newClassInqry = classInqryRepository.save( saveDto.toEntity() );			// ** save -> save된 정보 newXxx 로 저장
		
		return saveDto;
	}
	

    public Page<ClassInqryListDto> getList( ClassInqryListDto listDto, Pageable pageable ) {
        return classInqryRepository.getList( listDto, pageable );
    }
    
    
    public ClassInqryModDto findById( Long pk ) {	// TODO 타입 체크 필요
        
        ClassInqry classInqry = classInqryRepository.findById( pk ).orElse(null);
        
        if (classInqry == null ) { 
        	
        	return null;
        } else {
        
	        ClassInqryModDto modDto = new ClassInqryModDto();
	        modDto = modDto.toDto( classInqry );
	        
	        return modDto;
        }
    }
    
    @Transactional
    public void update ( ClassInqryModDto modDto, HttpServletRequest request ) throws IOException {
//    public void update ( ClassInqryModDto modDto, ClassInqryRoleModDto classInqryRoleModDto, HttpServletRequest request ) throws IOException {
    	
    	// target 조회
        ClassInqry classInqry = classInqryRepository.findById( modDto.getClassInqrySn() ).orElse(null);	// TODO PK 체크
        
        // target object 전환 ( entity to dto )
        ClassInqryModDto targetDto = new ClassInqryModDto();
        targetDto = targetDto.toDto( classInqry );
        
        // id,ip setting
        targetDto.setUpdusrIp( IpUtils.getClientIP( request ) );
        targetDto.setUpdusrId( SessionUtils.getUserId() );
        
        // target object 전환 ( dto to entity )
        classInqry = targetDto.toEntity();
        
        // 수정사항 적용
        classInqryRepository.save( classInqry );
    }
    
    public ClassInqryListDto findById( ClassInqryListDto listDto ) {
    	
    	return classInqryRepository.getByClassInqrySn(listDto);
    }
    
    public List<ClassInqryListDto> findReplyByStepAndParntsInqrySn( ClassInqryListDto listDto ) {
    	
    	return classInqryRepository.findByStepAndParntsInqrySn(listDto.getStep(), listDto.getClassInqrySn());
    }

    @Transactional
    public void deleteAllById( Long pk ) {
        
        // delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
        classInqryRepository.deleteById( pk );    // Entity 의 @SQLDelete 를 수행
    }
	
}
