package com.meta.ponkids.domain.system.ntt.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.meta.ponkids.domain.system.bbs.dto.BbsModDto;
import com.meta.ponkids.domain.system.bbs.entity.Bbs;
import com.meta.ponkids.domain.system.ntt.dto.NttListDto;
import com.meta.ponkids.domain.system.ntt.dto.NttModDto;
import com.meta.ponkids.domain.system.ntt.dto.NttReplyListDto;
import com.meta.ponkids.domain.system.ntt.dto.NttSaveReqDto;
import com.meta.ponkids.domain.system.ntt.entity.Ntt;
import com.meta.ponkids.domain.system.ntt.repository.NttRepository;
import com.meta.ponkids.global.util.ip.IpUtils;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class NttService {

    private final NttRepository nttRepository;


    public NttSaveReqDto save(NttSaveReqDto nttSaveReqDto,HttpServletRequest request ) {
    	
    	//임시로 로그인 아이디 셋팅
    	nttSaveReqDto.setRegisterId("ehlee");
    	
    	int nttSeq = nttRepository.MaxNttSeq(nttSaveReqDto.getBbsSn());

    	// dto to entity 작업 (필수)
        Ntt ntt = Ntt.builder()
        		.nttSn(nttSaveReqDto.getNttSn())
        		.bbsSn(nttSaveReqDto.getBbsSn())
        		.nttSeq(nttSeq)
        		.nttNm(nttSaveReqDto.getNttNm())
        		.nttCn(nttSaveReqDto.getNttCn())
        		.noticeSetYn(nttSaveReqDto.getNoticeSetYn())
        		.nttRdcnt(1)
                .openYn( "Y" )
                .registerId(nttSaveReqDto.getRegisterId())
                .registerIp( IpUtils.getClientIP( request ))
                .regDt(LocalDateTime.now())
                .updusrId(nttSaveReqDto.getRegisterId())
                .updusrIp( IpUtils.getClientIP( request ))
                .updtDt(LocalDateTime.now())
                .build();

        // save
        nttRepository.save(ntt);

        return nttSaveReqDto;
    }
    
    public Page<NttListDto> getList( NttListDto nttListDto, Pageable pageable ) {
	  return nttRepository.getList(nttListDto, pageable); 
	
	 }
    
    
    
    public NttModDto findByNttSn(Long nttSn) {
    	
    	Ntt ntt = nttRepository.findByNttSn(nttSn);
    	
    	NttModDto nttModDto = new NttModDto();
    	nttModDto = nttModDto.toDto(ntt);

    	return nttModDto;
    	
    }
    
    
    @Transactional
	public void update(Long nttSn, HttpServletRequest request) {
	
    	Ntt ntt  = nttRepository.findByNttSn(nttSn);
    	
      	int nttRdcnt = nttRepository.getMaxNttRdcnt(nttSn);
    	
        // target object 전환 ( entity to dto )
        NttModDto targetDto = new NttModDto();
        targetDto = targetDto.toDto( ntt );
    	
        targetDto.setNttRdcnt( nttRdcnt);
        
        // target object 전환 ( dto to entity )
        ntt = targetDto.toEntity();
        
        // 수정사항 적용
        
        //조회수 저장 
        nttRepository.save( ntt );
    	
	}
    
    
    public void nttUpdate( NttModDto modDto  ,HttpServletRequest request  ) {
        // target 조회
    	Ntt ntt  = nttRepository.findByNttSn(modDto.getNttSn()  );
        
        // target object 전환 ( entity to dto )
        NttModDto targetDto = new NttModDto();
        targetDto = targetDto.toDto( ntt );
    	
        
        targetDto.setUpdusrIp( IpUtils.getClientIP( request ));
        targetDto.setUpdusrId("ehlee"); // 임시 셋팅
        targetDto.setUpdtDt( LocalDateTime.now());
        
        // target object 에 수정사항 set
        // entity 에서 반영하지 않을 컬럼은 updatable = false 옵션 추가
        if ( StringUtils.hasText( modDto.getNttNm() ) ) targetDto.setNttNm( modDto.getNttNm() );          
        if ( StringUtils.hasText( modDto.getNttCn() ) ) targetDto.setNttCn( modDto.getNttCn() );   
        if ( StringUtils.hasText( modDto.getNoticeSetYn() ) ) targetDto.setNoticeSetYn( modDto.getNoticeSetYn() );   

        
        // target object 전환 ( dto to entity )
        ntt = targetDto.toEntity();
        
        // 수정사항 적용
        nttRepository.save( ntt );
    }
    
    
    @Transactional
    public void deleteAllByNttSn( Long nttSn ) {
        
        // delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
    	nttRepository.deleteAllByNttSn( nttSn );    // User.java 의 @SQLDelete(sql = "UPDATE tb_user SET del_yn ='Y' WHERE user_sn = ?") 를 수행
        
    
    }
    
    
    public int getExistsNtt(Long  bbsSn) {
	  return nttRepository.getExistsNtt(bbsSn); 
	
	 }
    
    
	public List<NttListDto> getNoticeList(Long bbsSn) {

		return nttRepository.getNoticeList(bbsSn);
	}
	

    

    
}

