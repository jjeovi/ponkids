package com.meta.ponkids.domain.system.popup.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meta.ponkids.domain.system.popup.dto.PopupModDto;
import com.meta.ponkids.domain.system.popup.dto.PopupListDto;
import com.meta.ponkids.domain.system.popup.dto.PopupSaveDto;
import com.meta.ponkids.domain.system.popup.entity.Popup;
import com.meta.ponkids.domain.system.popup.repository.PopupRepository;
import com.meta.ponkids.global.util.ip.IpUtils;
import com.meta.ponkids.global.util.session.SessionUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class PopupService {
	private final PopupRepository popupRepository;	// repository setting
	
	@Transactional
	public PopupSaveDto save( PopupSaveDto saveDto, HttpServletRequest request ) throws IOException {
//    public PopupSaveDto save( PopupSaveDto saveDto, PopupRoleSaveDto popupRoleSaveDto, HttpServletRequest request ) throws IOException {
		
		saveDto.setRegisterId( SessionUtils.getClientId() );				// Id set : regist
		saveDto.setRegisterIp( IpUtils.getClientIP( request ) );			// Ip set : regist
		saveDto.setUpdusrId( SessionUtils.getClientId() );					// Id set : update
		saveDto.setUpdusrIp( IpUtils.getClientIP( request ) );				// Ip set : update
		
		Popup newPopup = popupRepository.save( saveDto.toEntity() );			// ** save -> save된 정보 newXxx 로 저장
		
		return saveDto;
		
	}
	

    public Page<PopupListDto> getList( PopupListDto listDto, Pageable pageable ) {
        return popupRepository.getList( listDto, pageable );
    }
    
    
    public List<PopupListDto> getPonList() {
    	return popupRepository.getPonList();
    }
    
    
    public PopupModDto findById( Long pk ) {	// TODO 타입 체크 필요
        
        Popup popup = popupRepository.findById( pk ).orElse(null);
        
        if (popup == null ) { 
        	
        	return null;
        } else {
        
	        PopupModDto modDto = new PopupModDto();
	        modDto = modDto.toDto( popup );
	        
	        return modDto;
        }
    }
    
    @Transactional
    public void update ( PopupModDto modDto, HttpServletRequest request ) throws IOException {
//    public void update ( PopupModDto modDto, PopupRoleModDto popupRoleModDto, HttpServletRequest request ) throws IOException {
    	
    	// target 조회
        Popup popup = popupRepository.findById( modDto.getPopupSn() ).orElse(null);	// TODO PK 체크
        
        // target object 전환 ( entity to dto )
        PopupModDto targetDto = new PopupModDto();
        targetDto = targetDto.toDto( popup );
        
        // TODO target object 에 수정사항 set	
        // entity 에서 반영하지 않을 컬럼은 updatable = false 옵션 추가
        if ( StringUtils.hasText( modDto.getPopupNm() ) ) targetDto.setPopupNm( modDto.getPopupNm() );          	// 팝업 이름
        if ( StringUtils.hasText( modDto.getPopupCn() ) ) targetDto.setPopupCn( modDto.getPopupCn() );          	// 팝업 설명
        if ( StringUtils.hasText( modDto.getUseYn() ) ) targetDto.setUseYn( modDto.getUseYn() );          	        // 사용여부
        if ( StringUtils.hasText( modDto.getUrl() ) ) targetDto.setUrl( modDto.getUrl() );          	            // url
        if ( StringUtils.hasText( modDto.getPopupBeginDt() ) ) targetDto.setPopupBeginDt( modDto.getPopupBeginDt() );   // 팝업 시작일시
        if ( StringUtils.hasText( modDto.getPopupEndDt() ) ) targetDto.setPopupEndDt( modDto.getPopupEndDt() );         // 팝업 종료일시
        
        targetDto.setAtchFileSn( modDto.getAtchFileSn() );          											// 첨부파일 (첨부파일은 Null이어도 변경)
        
        // id,ip setting
        targetDto.setUpdusrIp( IpUtils.getClientIP( request ) );
        targetDto.setUpdusrId( SessionUtils.getClientId() );
        
        // target object 전환 ( dto to entity )
        popup = targetDto.toEntity();
        
        // 수정사항 적용
        popupRepository.save( popup );
    }

    @Transactional
    public void deleteAllById( Long pk ) {
        
        // delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
        popupRepository.deleteById( pk );    // Entity 의 @SQLDelete 를 수행
        
    }
	

}
