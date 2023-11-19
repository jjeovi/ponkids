package com.meta.ponkids.domain.system.ntt.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.meta.ponkids.domain.system.ntt.dto.NttSaveReqDto;
import com.meta.ponkids.domain.system.ntt.entity.Ntt;
import com.meta.ponkids.domain.system.ntt.repository.NttRepository;

import antlr.collections.List;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NttService {

    private final NttRepository nttRepository;


    public NttSaveReqDto save(NttSaveReqDto BbsNttSaveReqDto) {

    	// dto to entity 작업 (필수)
        Ntt bbsNtt = Ntt.builder()
        		.nttSn(BbsNttSaveReqDto.getNttSn())
        		.bbsSn(BbsNttSaveReqDto.getBbsSn())
        		.nttSeq("1")
        		.nttNm(BbsNttSaveReqDto.getNttNm())
        		.nttCn(BbsNttSaveReqDto.getNttCn())
        		.nttRdCnt(1)
                .registerId("ehlee")
                .delYn( "N" )
                .build();

        // save
        nttRepository.save(bbsNtt);

        return BbsNttSaveReqDto;
    }
    
    
	

    
}

