package com.meta.ponkids.domain.system.bbs.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.meta.ponkids.domain.system.bbs.dto.BbsListDto;
import com.meta.ponkids.domain.system.bbs.dto.BbsModDto;
import com.meta.ponkids.domain.system.bbs.dto.BbsSaveReqDto;
import com.meta.ponkids.domain.system.bbs.entity.Bbs;
import com.meta.ponkids.domain.system.bbs.repository.BbsRepository;
import com.meta.ponkids.domain.user.dto.UserListDto;
import com.meta.ponkids.domain.user.dto.UserModDto;
import com.meta.ponkids.domain.user.entity.User;
import com.meta.ponkids.domain.user.entity.UserChldrn;
import com.meta.ponkids.global.util.ip.IpUtils;

import antlr.collections.List;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class BbsService {

    private final BbsRepository bbsRepository;


    public BbsSaveReqDto save(BbsSaveReqDto bbsSaveReqDto ,HttpServletRequest request ) {

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
                .registerId("ehlee")
                .registerIp( IpUtils.getClientIP( request ))
               // .registerIp(bbsSaveReqDto.getRegisterIp())
                 .regDt(LocalDateTime.now())
              //  .upduserId(bbsSaveReqDto.getUpduserId())
                //.upduserIp(bbsSaveReqDto.getUpduserIp())
                //.updtDt(LocalDateTime.now())
                .build();

        // save
        bbsRepository.save(bbs);

        return bbsSaveReqDto;
    }
    
    
	
    public Page<BbsListDto> getList( BbsListDto bbsListDto, Pageable pageable ) {
	  return bbsRepository.getList(bbsListDto, pageable); 
	
	 }
    
    public BbsModDto findByBbsSn(int bbsSn) {
    	
    	Bbs bbs = bbsRepository.findByBbsSn(bbsSn);
    	
    	BbsModDto bbsModDto = new BbsModDto();
    	bbsModDto = bbsModDto.toDto(bbs);
    	
    	return bbsModDto;
    	
    }
    
    
    public void update( BbsModDto modDto ) {
        // target 조회
        Bbs bbs = bbsRepository.findByBbsSn( modDto.getBbsSn() );
        
        // target object 전환 ( entity to dto )
        BbsModDto targetDto = new BbsModDto();
        targetDto = targetDto.toDto( bbs );
        
        // target object 에 수정사항 set
        // entity 에서 반영하지 않을 컬럼은 updatable = false 옵션 추가
        if ( StringUtils.hasText( modDto.getBbsNm() ) ) targetDto.setBbsNm( modDto.getBbsNm() );          // 게시판 이름 
        if ( StringUtils.hasText( modDto.getBbsGdcc() ) ) targetDto.setBbsGdcc( modDto.getBbsGdcc() );    // 게시판 안내문구   
        if ( StringUtils.hasText( modDto.getBbsDc() ) ) targetDto.setBbsDc( modDto.getBbsDc() );          // 게시판 설명
        if ( StringUtils.hasText( modDto.getReplySetYn() ) ) targetDto.setReplySetYn( modDto.getReplySetYn() );            // 댓글설정여부
        if ( StringUtils.hasText( modDto.getUseYn() ) ) targetDto.setUseYn( modDto.getUseYn() );          // 사용여부
        if ( StringUtils.hasText( modDto.getOpenYn() ) ) targetDto.setOpenYn( modDto.getOpenYn() );        // 공개여부여부

        
        // target object 전환 ( dto to entity )
        bbs = targetDto.toEntity();
        
        // 수정사항 적용
        bbsRepository.save( bbs );
    }
    
    
}

