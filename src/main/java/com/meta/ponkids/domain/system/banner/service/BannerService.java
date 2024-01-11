package com.meta.ponkids.domain.system.banner.service;

import com.meta.ponkids.domain.system.banner.dto.BannerListDto;
import com.meta.ponkids.domain.system.banner.dto.BannerModDto;
import com.meta.ponkids.domain.system.banner.dto.BannerSaveDto;
import com.meta.ponkids.domain.system.banner.entity.Banner;
import com.meta.ponkids.domain.system.banner.repository.BannerRepository;
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
public class BannerService {
    private final BannerRepository bannerRepository;    // repository setting
    
    @Transactional
    public BannerSaveDto save( BannerSaveDto saveDto, HttpServletRequest request ) throws IOException {
//    public BannerSaveDto save( BannerSaveDto saveDto, BannerRoleSaveDto bannerRoleSaveDto, HttpServletRequest request ) throws IOException {
        
        saveDto.setRegisterId( SessionUtils.getClientId() );                // Id set : regist
        saveDto.setRegisterIp( IpUtils.getClientIP( request ) );            // Ip set : regist
        saveDto.setUpdusrId( SessionUtils.getClientId() );                    // Id set : update
        saveDto.setUpdusrIp( IpUtils.getClientIP( request ) );                // Ip set : update
        
        Banner newBanner = bannerRepository.save( saveDto.toEntity() );            // ** save -> save된 정보 newXxx 로 저장
        
        return saveDto;
        
    }
    
    
    public Page<BannerListDto> getList( BannerListDto listDto, Pageable pageable ) {
        return bannerRepository.getList( listDto, pageable );
    }
    
    
    public BannerModDto findById( Long pk ) {    // TODO 타입 체크 필요
        
        Banner banner = bannerRepository.findById( pk ).orElse( null );
        
        BannerModDto modDto = new BannerModDto();
        modDto = modDto.toDto( banner );
        
        return modDto;
    }
    
    @Transactional
    public void update( BannerModDto modDto, HttpServletRequest request ) throws IOException {
//    public void update ( BannerModDto modDto, BannerRoleModDto bannerRoleModDto, HttpServletRequest request ) throws IOException {
        
        // target 조회
        Banner banner = bannerRepository.findById( modDto.getBannerSn() ).orElse( null );    // TODO PK 체크
        
        // target object 전환 ( entity to dto )
        BannerModDto targetDto = new BannerModDto();
        targetDto = targetDto.toDto( banner );
        
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
        banner = targetDto.toEntity();
        
        // 수정사항 적용
        bannerRepository.save( banner );
        
    }
    
    @Transactional
    public void deleteAllById( Long pk ) {
        
        // delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
        bannerRepository.deleteById( pk );    // Entity 의 @SQLDelete 를 수행
        
    }
    
    
}
