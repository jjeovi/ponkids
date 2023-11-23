package com.meta.ponkids.domain.system.ntt.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.meta.ponkids.domain.system.bbs.dto.BbsModDto;
import com.meta.ponkids.domain.system.bbs.entity.Bbs;
import com.meta.ponkids.domain.system.ntt.dto.NttListDto;
import com.meta.ponkids.domain.system.ntt.dto.NttModDto;
import com.meta.ponkids.domain.system.ntt.dto.NttReplyListDto;
import com.meta.ponkids.domain.system.ntt.dto.NttReplySaveReqDto;
import com.meta.ponkids.domain.system.ntt.dto.NttSaveReqDto;
import com.meta.ponkids.domain.system.ntt.entity.Ntt;
import com.meta.ponkids.domain.system.ntt.entity.NttReply;
import com.meta.ponkids.domain.system.ntt.repository.NttReplyRepository;
import com.meta.ponkids.domain.system.ntt.repository.NttRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NttReplyService {

    private final NttReplyRepository nttReplyRepository;

    public NttReplySaveReqDto save(NttReplySaveReqDto nttReplySaveReqDto) {

    
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
                .registerId("ehlee")
                .registerIp("0.0.0.0")
                .regDt(LocalDateTime.now())
                .build();

        // save
        nttReplyRepository.save(nttReply);

        return nttReplySaveReqDto;
    }

	public List<NttReplyListDto> getList(int nttSn) {

		return nttReplyRepository.getList(nttSn);
	}

    
}

