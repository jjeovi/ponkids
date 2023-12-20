package com.meta.ponkids.domain.bbs.service;

import com.meta.ponkids.domain.bbs.dto.BbsListDto;
import com.meta.ponkids.domain.bbs.dto.BbsModDto;
import com.meta.ponkids.domain.bbs.dto.BbsSaveReqDto;
import com.meta.ponkids.domain.bbs.entity.Bbs;
import com.meta.ponkids.domain.bbs.repository.BbsRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.meta.ponkids.global.util.ip.IpUtils;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;


/**
 * className      : BbsService
 * author         : ehlee
 * date           : 2023-12-02
 * description    : class of 게시판 Service
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-12-02        ehlee             최초 생성
 */
@Service
@RequiredArgsConstructor
public class BbsService {

    private final BbsRepository bbsRepository;

    @Transactional
    public BbsSaveReqDto save( BbsSaveReqDto bbsSaveReqDto , HttpServletRequest request ) {
    	
      // 임시로 로그인 아이디 셋팅 > 추후 변경 필요 
      bbsSaveReqDto.setRegisterId("ehlee");
    	
      // dto to entity 작업 (필수)
         Bbs bbs = Bbs.builder()
                      .bbsSn(bbsSaveReqDto.getBbsSn())
                      .bbsSeCd(bbsSaveReqDto.getBbsSeCd())
                      .bbsNm(bbsSaveReqDto.getBbsNm())
                      .bbsGdcc(bbsSaveReqDto.getBbsGdcc())
                      .bbsDc(bbsSaveReqDto.getBbsDc())
                      .replySetYn(bbsSaveReqDto.getReplySetYn())
                      .useYn(bbsSaveReqDto.getUseYn())
                      .openYn(bbsSaveReqDto.getOpenYn())
                      .registerId(bbsSaveReqDto.getRegisterId())
                      .registerIp( IpUtils.getClientIP( request ))
                      .regDt(LocalDateTime.now())
                      .updusrId(bbsSaveReqDto.getRegisterId())
                      .updusrIp( IpUtils.getClientIP( request ))
                      .updtDt(LocalDateTime.now())
                      .build();

         // 게시판 저장
         bbsRepository.save(bbs);

         return bbsSaveReqDto;
    }
    
    
	// 게시판 목록 조회
    public Page<BbsListDto> getList( BbsListDto bbsListDto, Pageable pageable ) {
	  
    	return bbsRepository.getList( bbsListDto, pageable ); 
	
	 }
    
    // 게시판 수정시
    public BbsModDto findByBbsSn( Long bbsSn ) {
    	
    	Bbs bbs = bbsRepository.findByBbsSn( bbsSn );
    	
    	
    	BbsModDto bbsModDto = new BbsModDto();
    	bbsModDto = bbsModDto.toDto( bbs );
    	
    	return bbsModDto;
    	
    }
    
    // 게시판 댓글 설정여부 조회
    public String getSetReplySetYn(Long bbsSn) {
    	
    	String replySetYn = bbsRepository.getSetReplySetYn(bbsSn);
    	
    	return replySetYn;
    	
    }
    
    // 게시판 구분 코드 조회
    public String getBbsSeCd(Long bbsSn) {
    	
    	String bbsSeCd = bbsRepository.getBbsSeCd(bbsSn);
    	
    	return bbsSeCd;
    	
    }
    
    // 게시판 수정
    @Transactional
    public void update( BbsModDto modDto  ,HttpServletRequest request  ) {
        // target 조회
        Bbs bbs = bbsRepository.findByBbsSn( modDto.getBbsSn() );
        
        // target object 전환 ( entity to dto )
        BbsModDto targetDto = new BbsModDto();
        targetDto = targetDto.toDto( bbs );
        
        modDto.setUpdusrIp( IpUtils.getClientIP( request ));
        
        // target object 에 수정사항 set
        // entity 에서 반영하지 않을 컬럼은 updatable = false 옵션 추가
        if ( StringUtils.hasText( modDto.getBbsNm() ) ) targetDto.setBbsNm( modDto.getBbsNm() );                    // 게시판 이름 
        if ( StringUtils.hasText( modDto.getBbsGdcc() ) ) targetDto.setBbsGdcc( modDto.getBbsGdcc() );              // 게시판 안내문구   
        if ( StringUtils.hasText( modDto.getBbsDc() ) ) targetDto.setBbsDc( modDto.getBbsDc() );                    // 게시판 설명
        if ( StringUtils.hasText( modDto.getReplySetYn() ) ) targetDto.setReplySetYn( modDto.getReplySetYn() );     // 댓글설정여부
        if ( StringUtils.hasText( modDto.getUseYn() ) ) targetDto.setUseYn( modDto.getUseYn() );                    // 사용여부
        if ( StringUtils.hasText( modDto.getOpenYn() ) ) targetDto.setOpenYn( modDto.getOpenYn() );                 // 공개여부여부

        
        // target object 전환 ( dto to entity )
        bbs = targetDto.toEntity();
        
        // 수정사항 적용
        bbsRepository.save( bbs );
    }
    
    // 게시판 삭제
    @Transactional
    public void deleteAllByBbsSn( Long bbsSn ) {
        
        // delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
    	bbsRepository.deleteAllByBbsSn( bbsSn );    
        
    
    }
    
    
}

