package com.meta.ponkids.domain.system.menu.service;

import com.meta.ponkids.domain.system.menu.dto.MenuListDto;
import com.meta.ponkids.domain.system.menu.dto.MenuModDto;
import com.meta.ponkids.domain.system.menu.dto.MenuSaveDto;
import com.meta.ponkids.domain.system.menu.entity.Menu;
import com.meta.ponkids.domain.system.menu.repository.MenuRepository;
import com.meta.ponkids.domain.system.role.dto.RoleListDto;
import com.meta.ponkids.global.util.ip.IpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {
	private final MenuRepository menuRepository;	// repository setting
	
	@Transactional
	public MenuSaveDto save( MenuSaveDto saveDto, HttpServletRequest request ) throws IOException {
//    public MenuSaveDto save( MenuSaveDto saveDto, MenuRoleSaveDto menuRoleSaveDto, HttpServletRequest request ) throws IOException {
		
		saveDto.setRegisterId( "admin@test.com" );							// Id set
		saveDto.setRegisterIp( IpUtils.getClientIP( request ) );			// Ip set
		
		Menu newMenu = menuRepository.save( saveDto.toEntity() );			// ** save -> save된 정보 newXxx 로 저장
		
		return saveDto;
		
	}

    public List<MenuListDto> getList( MenuListDto listDto ) {
        // 메뉴 목록은 category lv1Sn 값이 설정되어있어야 조회 가능. 그렇지 않으면 null return
        if (listDto.getCategory() != null && listDto.getCategory().getLv1Sn() != null ) {
            return menuRepository.getList( listDto );
        } else {
            return Collections.emptyList(); // 빈 List<> 생성
        }
    }
    
    public MenuModDto findById( Long pk ) {	// TODO 타입 체크 필요
        
        Menu menu = menuRepository.findById( pk ).orElse(null);
        
        MenuModDto modDto = new MenuModDto();
        modDto = modDto.toDto( menu );
        
        return modDto;
    }
    
    @Transactional
    public void update ( MenuModDto modDto, HttpServletRequest request ) throws IOException {
//    public void update ( MenuModDto modDto, MenuRoleModDto menuRoleModDto, HttpServletRequest request ) throws IOException {
    	
    	// target 조회
        Menu menu = menuRepository.findById( modDto.getMenuSn() ).orElse(null);	// TODO PK 체크
        
        // target object 전환 ( entity to dto )
        MenuModDto targetDto = new MenuModDto();
        targetDto = targetDto.toDto( menu );
        
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
        targetDto.setUpdusrIp(IpUtils.getClientIP( request ));
        targetDto.setUpdusrId("admin@test.com");
        
        // target object 전환 ( dto to entity )
        menu = targetDto.toEntity();
        
        // 수정사항 적용
        menuRepository.save( menu );
    	
    }

    @Transactional
    public void deleteAllById( Long pk ) {
        
        // delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
        menuRepository.deleteById( pk );    // Entity 의 @SQLDelete 를 수행
        
    }
    
    public List<RoleListDto> getPossibleAuthListAjax( MenuListDto listDto ) {
    	return menuRepository.getPossibleAuthListAjax( listDto );
    }
	

}
