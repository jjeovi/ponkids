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
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

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
    
    
    // 사용자 main 에 표출할 list
    public List<BannerListDto> getMainList( String bannerClCd , Long userSn ) {
        return bannerRepository.getMainList( bannerClCd , userSn );
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
        
        // entity 에서 반영하지 않을 컬럼은 updatable = false 옵션 추가
        if ( StringUtils.hasText( modDto.getBannerClCd() ) ) targetDto.setBannerClCd( modDto.getBannerClCd() );				// 배너 분류
        if ( StringUtils.hasText( modDto.getBannerClDetailCd() ) ) targetDto.setBannerClDetailCd( modDto.getBannerClDetailCd() );	// 배너 상세분류
        targetDto.setAtchFileSn( modDto.getAtchFileSn() );																	// 배너 이미지
        if ( StringUtils.hasText( modDto.getBannerNm() ) ) targetDto.setBannerNm( modDto.getBannerNm() );					// 배너 이름
        if ( modDto.getBannerExpsrSeq() != null ) targetDto.setBannerExpsrSeq( modDto.getBannerExpsrSeq() );				// 배너 순번
        targetDto.setBannerDc( modDto.getBannerDc() );																		// 배너 설명
        if ( StringUtils.hasText( modDto.getClassMapngYn() ) ) targetDto.setClassMapngYn( modDto.getClassMapngYn() );		// 클래스 매핑 여부
        if ( modDto.getClassSn() != null ) targetDto.setClassSn( modDto.getClassSn() );										// 클래스 sn
        if ( StringUtils.hasText( modDto.getUrl() ) ) targetDto.setUrl( modDto.getUrl() );									// url
        if ( StringUtils.hasText( modDto.getBannerPdSetYn() ) ) targetDto.setBannerPdSetYn( modDto.getBannerPdSetYn() );	// 배너 표시 기간 여부
        if ( StringUtils.hasText( modDto.getBannerBeginDt() ) ) targetDto.setBannerBeginDt( modDto.getBannerBeginDt() );	// 배너 표시 시작 일시
        if ( StringUtils.hasText( modDto.getBannerEndDt() ) ) targetDto.setBannerEndDt( modDto.getBannerEndDt() );			// 배너 표시 시작 일시
        if ( StringUtils.hasText( modDto.getUseYn() ) ) targetDto.setUseYn( modDto.getUseYn() );							// 사용여부
        
        
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
