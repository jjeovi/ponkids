package com.meta.ponkids.domain.system.ntt.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meta.ponkids.domain.system.bbs.dto.BbsModDto;
import com.meta.ponkids.domain.system.bbs.entity.Bbs;
import com.meta.ponkids.domain.system.ntt.dto.NttListDto;
import com.meta.ponkids.domain.system.ntt.dto.NttModDto;
import com.meta.ponkids.domain.system.ntt.dto.NttSaveReqDto;
import com.meta.ponkids.domain.system.ntt.entity.Ntt;
import com.meta.ponkids.domain.system.ntt.repository.NttRepository;
import com.meta.ponkids.domain.user.dto.MultiUserChldrnSaveDto;
import com.meta.ponkids.domain.user.dto.UserModDto;
import com.meta.ponkids.domain.user.dto.UserRoleModDto;
import com.meta.ponkids.domain.user.entity.User;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

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
    
    
    @Transactional
	public void update(int nttSn, HttpServletRequest request) {
	
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

    
}

