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
import com.meta.ponkids.domain.system.ntt.dto.NttReplyModDto;
import com.meta.ponkids.domain.system.ntt.dto.NttReplySaveReqDto;
import com.meta.ponkids.domain.system.ntt.dto.NttSaveReqDto;
import com.meta.ponkids.domain.system.ntt.entity.Ntt;
import com.meta.ponkids.domain.system.ntt.entity.NttReply;
import com.meta.ponkids.domain.system.ntt.repository.NttReplyRepository;
import com.meta.ponkids.domain.system.ntt.repository.NttRepository;
import com.meta.ponkids.global.util.ip.IpUtils;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class NttReplyService {

    private final NttReplyRepository nttReplyRepository;

    public NttReplySaveReqDto save(NttReplySaveReqDto nttReplySaveReqDto ,HttpServletRequest request) {
        
      	//임시로 로그인 아이디 셋팅
    	nttReplySaveReqDto.setRegisterId("ehlee");
    
    	int nttReplySeq =nttReplyRepository.MaxNttReplySeq(nttReplySaveReqDto.getNttSn());
    	
    	// dto to entity 작업 (필수)
        NttReply nttReply = NttReply.builder()
        		.nttReplySn(nttReplySaveReqDto.getNttReplySn())
        		.nttSn(nttReplySaveReqDto.getNttSn())
        		.step(nttReplySaveReqDto.getStep())
        		.parntsReplySn(nttReplySaveReqDto.getParntsReplySn())
        		.nttReplyCn(nttReplySaveReqDto.getNttReplyCn())
        		.nttReplySeq(nttReplySeq)
        		.openYn( "Y" )
                .registerId(nttReplySaveReqDto.getRegisterId())
                .registerIp( IpUtils.getClientIP( request ))
                .regDt(LocalDateTime.now())
                .updusrId(nttReplySaveReqDto.getRegisterId())
                .updusrIp( IpUtils.getClientIP( request ))
                .updtDt(LocalDateTime.now())
                .build();

        // save
        nttReplyRepository.save(nttReply);

        return nttReplySaveReqDto;
    }

	public List<NttReplyListDto> getList(Long nttSn) {

		return nttReplyRepository.getList(nttSn);
	}
	
	
	public List<NttReplyListDto> getAnswerReplyList(Long nttReplySn) {

		return nttReplyRepository.getAnswerReplyList(nttReplySn);
	}
	

	
	
	public void update(NttReplyModDto modDto, HttpServletRequest request) {
		
	    	NttReply nttReplySn  = nttReplyRepository.findByNttReplySn(modDto.getNttReplySn() );
	    	
	        // target object 전환 ( entity to dto )
	        NttReplyModDto targetDto = new NttReplyModDto();
	        targetDto = targetDto.toDto( nttReplySn );
	        
	        targetDto.setUpdusrIp( IpUtils.getClientIP( request ));
	        targetDto.setUpdusrId("ehlee"); // 임시 셋팅
	        targetDto.setUpdtDt( LocalDateTime.now());
	        
	        // target object 에 수정사항 set
	        // entity 에서 반영하지 않을 컬럼은 updatable = false 옵션 추가
	        if ( StringUtils.hasText( modDto.getNttReplyCn() ) ) targetDto.setNttReplyCn( modDto.getNttReplyCn() );   
	        
	        // target object 전환 ( dto to entity )
	        nttReplySn = targetDto.toEntity();
	        
	        // 수정사항 적용
	        nttReplyRepository.save( nttReplySn );
	    	
	 }
	
	
    @Transactional
    public void deleteAllByNttReplySn( Long nttReplySn ) {
        
        // delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
    	nttReplyRepository.deleteAllByNttReplySn( nttReplySn );    // User.java 의 @SQLDelete(sql = "UPDATE tb_user SET del_yn ='Y' WHERE user_sn = ?") 를 수행
        
    
    }
    
	    	


}

