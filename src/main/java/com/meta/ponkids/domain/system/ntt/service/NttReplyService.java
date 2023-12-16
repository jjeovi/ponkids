package com.meta.ponkids.domain.system.ntt.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.meta.ponkids.domain.system.ntt.dto.NttReplyListDto;
import com.meta.ponkids.domain.system.ntt.dto.NttReplyModDto;
import com.meta.ponkids.domain.system.ntt.dto.NttReplySaveReqDto;
import com.meta.ponkids.domain.system.ntt.entity.NttReply;
import com.meta.ponkids.domain.system.ntt.repository.NttReplyRepository;
import com.meta.ponkids.domain.system.ntt.repository.NttRepository;
import com.meta.ponkids.global.util.ip.IpUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;






/**
 * className      : NttReplyService
 * author         : ehlee
 * date           : 2023-12-02
 * description    : class of 게시물 댓글 Service
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-12-02        ehlee             최초 생성
 */
@Service
@RequiredArgsConstructor
public class NttReplyService {

    private final NttReplyRepository nttReplyRepository;
   
    @Transactional
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
                                    .writerDt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                                    .build();

        // save
        nttReplyRepository.save(nttReply);

        return nttReplySaveReqDto;
    }
   
    // 댓글 목록 조회
	public List<NttReplyListDto> getList(Long nttSn) {

		return nttReplyRepository.getList(nttSn);
	}
	
	// 답글 목록조회
	public List<NttReplyListDto> getAnswerReplyList(Long nttReplySn) {

		return nttReplyRepository.getAnswerReplyList(nttReplySn);
	}
	
    
	// 댓글, 답글 수정
	@Transactional
	public void update(NttReplyModDto modDto, HttpServletRequest request) {
		
	    	NttReply nttReplySn  = nttReplyRepository.findByNttReplySn(modDto.getNttReplySn() );
	    	
	        // target object 전환 ( entity to dto )
	        NttReplyModDto targetDto = new NttReplyModDto();
	        targetDto = targetDto.toDto( nttReplySn );
	        
	        targetDto.setUpdusrIp( IpUtils.getClientIP( request ));
	        targetDto.setUpdusrId("ehlee"); // 임시 셋팅
	        targetDto.setUpdtDt( LocalDateTime.now());
	        targetDto.setWriterDt( LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

	        // target object 에 수정사항 set
	        // entity 에서 반영하지 않을 컬럼은 updatable = false 옵션 추가
	        if ( StringUtils.hasText( modDto.getNttReplyCn() ) ) targetDto.setNttReplyCn( modDto.getNttReplyCn() );   
	        
	        // target object 전환 ( dto to entity )
	        nttReplySn = targetDto.toEntity();
	        
	        // 수정사항 적용
	        nttReplyRepository.save( nttReplySn );
	    	
	 }
	
	// 댓글, 답글 삭제
    @Transactional
    public void deleteAllByNttReplySn( Long nttReplySn ) {
        
        // delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
    	nttReplyRepository.deleteAllByNttReplySn( nttReplySn );    // User.java 의 @SQLDelete(sql = "UPDATE tb_user SET del_yn ='Y' WHERE user_sn = ?") 를 수행
    
    }

}

