package com.meta.ponkids.domain.cls.service;

import com.meta.ponkids.domain.cls.dto.ClassDetailOptnListDto;
import com.meta.ponkids.domain.cls.dto.ClassDetailOptnModDto;
import com.meta.ponkids.domain.cls.dto.ClassDetailOptnSaveDto;
import com.meta.ponkids.domain.cls.entity.ClassDetailOptn;
import com.meta.ponkids.domain.cls.repository.ClassDetailOptnRepository;
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
public class ClassDetailOptnService {
	private final ClassDetailOptnRepository classDetailOptnRepository;	// repository setting
	
	@Transactional
	public ClassDetailOptnSaveDto save( ClassDetailOptnSaveDto saveDto, HttpServletRequest request ) throws IOException {
//    public ClassDetailOptnSaveDto save( ClassDetailOptnSaveDto saveDto, ClassDetailOptnRoleSaveDto classDetailOptnRoleSaveDto, HttpServletRequest request ) throws IOException {
		
		saveDto.setRegisterId( SessionUtils.getUserId() );				// Id set : regist
		saveDto.setRegisterIp( IpUtils.getClientIP( request ) );			// Ip set : regist
		saveDto.setUpdusrId( SessionUtils.getUserId() );					// Id set : update
		saveDto.setUpdusrIp( IpUtils.getClientIP( request ) );				// Ip set : update
		
		ClassDetailOptn newClassDetailOptn = classDetailOptnRepository.save( saveDto.toEntity() );			// ** save -> save된 정보 newXxx 로 저장
		
		return saveDto;
		
	}
	

    public Page<ClassDetailOptnListDto> getList( ClassDetailOptnListDto listDto, Pageable pageable ) {
        return classDetailOptnRepository.getList( listDto, pageable );
    }
    
    
    public ClassDetailOptnModDto findById( Long pk ) {	// TODO 타입 체크 필요
        
        ClassDetailOptn classDetailOptn = classDetailOptnRepository.findById( pk ).orElse(null);
        
        if (classDetailOptn == null ) { 
        	
        	return null;
        } else {
        
	        ClassDetailOptnModDto modDto = new ClassDetailOptnModDto();
	        modDto = modDto.toDto( classDetailOptn );
	        
	        return modDto;
        }
    }
    
    @Transactional
    public void update ( ClassDetailOptnModDto modDto, HttpServletRequest request ) throws IOException {
//    public void update ( ClassDetailOptnModDto modDto, ClassDetailOptnRoleModDto classDetailOptnRoleModDto, HttpServletRequest request ) throws IOException {
    	
    	// target 조회
        ClassDetailOptn classDetailOptn = classDetailOptnRepository.findById( modDto.getClassDetailOptnSn() ).orElse(null);	// TODO PK 체크
        
        // target object 전환 ( entity to dto )
        ClassDetailOptnModDto targetDto = new ClassDetailOptnModDto();
        targetDto = targetDto.toDto( classDetailOptn );
        
        
        // id,ip setting
        targetDto.setUpdusrIp( IpUtils.getClientIP( request ) );
        targetDto.setUpdusrId( SessionUtils.getUserId() );
        
        // target object 전환 ( dto to entity )
        classDetailOptn = targetDto.toEntity();
        
        // 수정사항 적용
        classDetailOptnRepository.save( classDetailOptn );
    	
    }

    @Transactional
    public void deleteAllById( Long pk ) {
        
        // delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
        classDetailOptnRepository.deleteById( pk );    // Entity 의 @SQLDelete 를 수행
        
    }
	

}
