package com.meta.ponkids.domain.system.bbs.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.meta.ponkids.domain.system.bbs.dto.BbsListDto;
import com.meta.ponkids.domain.system.bbs.dto.BbsModDto;
import com.meta.ponkids.domain.system.bbs.dto.BbsSaveReqDto;
import com.meta.ponkids.domain.system.bbs.entity.Bbs;
import com.meta.ponkids.domain.system.bbs.repository.BbsRepository;
import com.meta.ponkids.domain.user.dto.UserListDto;
import com.meta.ponkids.domain.user.dto.UserModDto;
import com.meta.ponkids.domain.user.entity.User;
import com.meta.ponkids.domain.user.entity.UserChldrn;

import antlr.collections.List;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BbsService {

    private final BbsRepository bbsRepository;


    public BbsSaveReqDto save(BbsSaveReqDto bbsSaveReqDto) {

    	// dto to entity 작업 (필수)
        Bbs bbs = Bbs.builder()
                .bbsSn(bbsSaveReqDto.getBbsSn())
                .bbsSeCd(bbsSaveReqDto.getBbsSeCd())
                .bbsNm(bbsSaveReqDto.getBbsNm())
                .bbsGdcc(bbsSaveReqDto.getBbsGdcc())
                .bbsDc(bbsSaveReqDto.getBbsDc())
                .answerSetYn(bbsSaveReqDto.getAnswerSetYn())
                .useYn(bbsSaveReqDto.getUseYn())
                .openYn(bbsSaveReqDto.getOpenYn())
               // .registerId(bbsSaveReqDto.getRegisterId())
                .registerId("ehlee")
                //.registerIp("0.0.0.0")
               // .registerIp(bbsSaveReqDto.getRegisterIp())
               // .regDt(LocalDateTime.now())
              //  .upduserId(bbsSaveReqDto.getUpduserId())
                //.upduserIp(bbsSaveReqDto.getUpduserIp())
                //.updtDt(LocalDateTime.now())
                .delYn( "N" )
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
    	//bbsModDto = bbsModDto.toDto(bbsSn);
    	
    	return bbsModDto;
    	
    }
    
    
}

