package com.meta.ponkids.domain.system.bbs.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    	
    	//임시로 로그인 아이디 셋팅
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

          // save
           bbsRepository.save(bbs);

           return bbsSaveReqDto;
    }
    
    
	
    public Page<BbsListDto> getList( BbsListDto bbsListDto, Pageable pageable ) {
	  return bbsRepository.getList(bbsListDto, pageable); 
	
	 }
    
    public BbsModDto findByBbsSn(Long bbsSn) {
    	
    	Bbs bbs = bbsRepository.findByBbsSn(bbsSn);
    	
    	
    	BbsModDto bbsModDto = new BbsModDto();
    	bbsModDto = bbsModDto.toDto(bbs);
    	
    	return bbsModDto;
    	
    }
    
    public String getSetReplySetYn(Long bbsSn) {
    	
    	String replySetYn = bbsRepository.getSetReplySetYn(bbsSn);
    	
    	return replySetYn;
    	
    }
    
    public String getBbsSeCd(Long bbsSn) {
    	
    	String bbsSeCd = bbsRepository.getBbsSeCd(bbsSn);
    	
    	return bbsSeCd;
    	
    }
    
    
    
    public void update( BbsModDto modDto  ,HttpServletRequest request  ) {
        // target 조회
        Bbs bbs = bbsRepository.findByBbsSn( modDto.getBbsSn() );
        
        // target object 전환 ( entity to dto )
        BbsModDto targetDto = new BbsModDto();
        targetDto = targetDto.toDto( bbs );
        
        modDto.setUpdusrIp( IpUtils.getClientIP( request ));
        
        // target object 에 수정사항 set
        // entity 에서 반영하지 않을 컬럼은 updatable = false 옵션 추가
        if ( StringUtils.hasText( modDto.getBbsNm() ) ) targetDto.setBbsNm( modDto.getBbsNm() );          // 게시판 이름 
        if ( StringUtils.hasText( modDto.getBbsGdcc() ) ) targetDto.setBbsGdcc( modDto.getBbsGdcc() );    // 게시판 안내문구   
        if ( StringUtils.hasText( modDto.getBbsDc() ) ) targetDto.setBbsDc( modDto.getBbsDc() );          // 게시판 설명
        if ( StringUtils.hasText( modDto.getReplySetYn() ) ) targetDto.setReplySetYn( modDto.getReplySetYn() );     // 댓글설정여부
        if ( StringUtils.hasText( modDto.getUseYn() ) ) targetDto.setUseYn( modDto.getUseYn() );          // 사용여부
        if ( StringUtils.hasText( modDto.getOpenYn() ) ) targetDto.setOpenYn( modDto.getOpenYn() );        // 공개여부여부

        
        // target object 전환 ( dto to entity )
        bbs = targetDto.toEntity();
        
        // 수정사항 적용
        bbsRepository.save( bbs );
    }
    
    
    @Transactional
    public void deleteAllByBbsSn( Long bbsSn ) {
        
        // delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
    	bbsRepository.deleteAllByBbsSn( bbsSn );    // User.java 의 @SQLDelete(sql = "UPDATE tb_user SET del_yn ='Y' WHERE user_sn = ?") 를 수행
        
    
    }
    
    
}

