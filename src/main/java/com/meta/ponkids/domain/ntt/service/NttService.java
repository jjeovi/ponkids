package com.meta.ponkids.domain.ntt.service;

import com.meta.ponkids.domain.ntt.dto.NttSaveReqDto;
import com.meta.ponkids.domain.ntt.entity.Ntt;
import com.meta.ponkids.domain.ntt.repository.NttRepository;
import com.meta.ponkids.domain.ntt.dto.NttListDto;
import com.meta.ponkids.domain.ntt.dto.NttModDto;
import com.meta.ponkids.global.util.ip.IpUtils;
import com.meta.ponkids.global.util.session.SessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;


/**
 * className      : NttService
 * author         : ehlee
 * date           : 2023-12-02
 * description    : class of 게시물 Service
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-12-02        ehlee             최초 생성
 */
@Service
@RequiredArgsConstructor
public class NttService {
    
    private final NttRepository nttRepository;
    
    @Transactional
    public NttSaveReqDto save( NttSaveReqDto nttSaveReqDto, HttpServletRequest request ) {
        
        int nttSeq = nttRepository.MaxNttSeq( nttSaveReqDto.getBbsSn() );
        
        // dto to entity 작업 (필수)
        Ntt ntt = Ntt.builder()
                .nttSn( nttSaveReqDto.getNttSn() )
                .bbsSn( nttSaveReqDto.getBbsSn() )
                .nttSeq( nttSeq )
                .nttNm( nttSaveReqDto.getNttNm() )
                .nttCn( nttSaveReqDto.getNttCn() )
                .noticeSetYn( nttSaveReqDto.getNoticeSetYn() )
                .nttRdcnt( 1 )
                .openYn( "Y" )
                .atchFileSn( nttSaveReqDto.getAtchFileSn() )
                .cnAtchFileSn( nttSaveReqDto.getCnAtchFileSn() )
                .registerId( SessionUtils.getUserId() )
                .registerIp( IpUtils.getClientIP( request ) )
                .regDt( LocalDateTime.now() )
                .updusrId( SessionUtils.getUserId() )
                .updusrIp( IpUtils.getClientIP( request ) )
                .updtDt( LocalDateTime.now() )
                .build();
        
        // 게시물 저장
        nttRepository.save( ntt );
        
        return nttSaveReqDto;
    }
    
    
    // 게시물 목록 조회
    public Page<NttListDto> getList( NttListDto nttListDto, Pageable pageable ) {
        return nttRepository.getList( nttListDto, pageable );
        
    }
    
    // 게시물 목록 조회 (페이징 상관없이 전체리스트 조회 )
    public List<NttListDto> getList( NttListDto nttListDto ) {
    	return nttRepository.getList( nttListDto );
    	
    }
    
    
    // 게시물 수정시
    public NttListDto detailByNttSn( Long nttSn ) {
    	
    	return  nttRepository.detailByNttSn( nttSn );
    	
    }
    
    
    // 게시물 수정시
    public NttModDto findByNttSn( Long nttSn ) {
        
        Ntt ntt = nttRepository.findByNttSn( nttSn );
        
        NttModDto nttModDto = new NttModDto();
        nttModDto = nttModDto.toDto( ntt );
        
        return nttModDto;
        
    }
    
    //조회수 업데이트
    @Transactional
    public void update( Long nttSn, HttpServletRequest request ) {
        
        Ntt ntt = nttRepository.findByNttSn( nttSn );
        
        int nttRdcnt = nttRepository.getMaxNttRdcnt( nttSn );
        
        // target object 전환 ( entity to dto )
        NttModDto targetDto = new NttModDto();
        targetDto = targetDto.toDto( ntt );
        
        targetDto.setNttRdcnt( nttRdcnt );
        
        // target object 전환 ( dto to entity )
        ntt = targetDto.toEntity();
        
        //조회수 저장 
        nttRepository.save( ntt );
        
    }
    
    // 게시물 수정시
    @Transactional
    public void nttUpdate( NttModDto modDto, HttpServletRequest request ) {
        // target 조회
        Ntt ntt = nttRepository.findByNttSn( modDto.getNttSn() );
        
        // target object 전환 ( entity to dto )
        NttModDto targetDto = new NttModDto();
        targetDto = targetDto.toDto( ntt );
        
        
        targetDto.setUpdusrIp( IpUtils.getClientIP( request ) );
        targetDto.setUpdusrId( "ehlee" ); // 임시 셋팅
//        targetDto.setUpdtDt( LocalDateTime.now() );	// -> Ntt 의 toEntity 에 선언
        
        // target object 에 수정사항 set
        // entity 에서 반영하지 않을 컬럼은 updatable = false 옵션 추가
        if ( StringUtils.hasText( modDto.getNttNm() ) ) targetDto.setNttNm( modDto.getNttNm() );
        if ( StringUtils.hasText( modDto.getNttCn() ) ) targetDto.setNttCn( modDto.getNttCn() );
        if ( StringUtils.hasText( modDto.getNoticeSetYn() ) ) targetDto.setNoticeSetYn( modDto.getNoticeSetYn() );
        
        targetDto.setAtchFileSn( modDto.getAtchFileSn() );
        targetDto.setCnAtchFileSn( modDto.getCnAtchFileSn() );
        
        // target object 전환 ( dto to entity )
        ntt = targetDto.toEntity();
        
        // 수정사항 적용
        nttRepository.save( ntt );
    }
    
    // 게시물 삭제
    @Transactional
    public void deleteAllByNttSn( Long nttSn ) {
        
        // delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
        nttRepository.deleteAllByNttSn( nttSn );    // User.java 의 @SQLDelete(sql = "UPDATE tb_user SET del_yn ='Y' WHERE user_sn = ?") 를 수행
        
        
    }
    
    // 공지 목록 조회
    public List<NttListDto> getNoticeList( Long bbsSn ) {
        
        return nttRepository.getNoticeList( bbsSn );
    }
    
    
}

