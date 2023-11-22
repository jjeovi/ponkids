package com.meta.ponkids.domain.system.ntt.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.meta.ponkids.domain.system.bbs.dto.BbsModDto;
import com.meta.ponkids.domain.system.bbs.entity.Bbs;
import com.meta.ponkids.domain.system.ntt.dto.NttListDto;
import com.meta.ponkids.domain.system.ntt.dto.NttModDto;
import com.meta.ponkids.domain.system.ntt.dto.NttSaveReqDto;
import com.meta.ponkids.domain.system.ntt.entity.Ntt;
import com.meta.ponkids.domain.system.ntt.repository.NttRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NttService {

    private final NttRepository nttRepository;


    public NttSaveReqDto save(NttSaveReqDto nttSaveReqDto) {

    	// dto to entity 작업 (필수)
        Ntt bbsNtt = Ntt.builder()
        		.nttSn(nttSaveReqDto.getNttSn())
        		.bbsSn(nttSaveReqDto.getBbsSn())
        		.nttSeq(1)
        		.nttNm(nttSaveReqDto.getNttNm())
        		.nttCn(nttSaveReqDto.getNttCn())
        		.nttRdcnt(1)
                .registerId("ehlee")
                .openYn( "Y" )
                .registerIp("0.0.0.0")
                .regDt(LocalDateTime.now())
                .build();

        // save
        nttRepository.save(bbsNtt);

        return nttSaveReqDto;
    }
    
    public Page<NttListDto> getList( NttListDto nttListDto, Pageable pageable ) {
	  return nttRepository.getList(nttListDto, pageable); 
	
	 }
    
    
    
    public NttModDto findByNttSn(int nttSn) {
    	
    	Ntt ntt = nttRepository.findByNttSn(nttSn);
    	
    	NttModDto nttModDto = new NttModDto();
    	nttModDto = nttModDto.toDto(ntt);

    	return nttModDto;
    	
    }

    
    
	

    
}

