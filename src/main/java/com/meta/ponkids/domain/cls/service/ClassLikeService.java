package com.meta.ponkids.domain.cls.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meta.ponkids.domain.cls.dto.ClassInqryModDto;
import com.meta.ponkids.domain.cls.dto.ClassInqryListDto;
import com.meta.ponkids.domain.cls.dto.ClassInqrySaveDto;
import com.meta.ponkids.domain.cls.dto.ClassLikeSaveDto;
import com.meta.ponkids.domain.cls.entity.ClassInqry;
import com.meta.ponkids.domain.cls.entity.ClassLike;
import com.meta.ponkids.domain.cls.repository.ClassInqryRepository;
import com.meta.ponkids.domain.cls.repository.ClassLikeRepository;
import com.meta.ponkids.global.util.ip.IpUtils;
import com.meta.ponkids.global.util.session.SessionUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassLikeService {
	private final ClassLikeRepository classLikeRepository;	// repository setting
	
	@Transactional
	public ClassLikeSaveDto save( ClassLikeSaveDto saveDto, HttpServletRequest request ) throws IOException {
		
		saveDto.setRegisterId( SessionUtils.getClientId() );				// Id set : regist
		saveDto.setRegisterIp( IpUtils.getClientIP( request ) );			// Ip set : regist
		saveDto.setUpdusrId( SessionUtils.getClientId() );					// Id set : update
		saveDto.setUpdusrIp( IpUtils.getClientIP( request ) );				// Ip set : update
		
		ClassLike newClassLike = classLikeRepository.save( saveDto.toEntity() );			// ** save -> save된 정보 newXxx 로 저장
		
		return saveDto;
		
	}

    @Transactional
    public void deleteByClassSnAndUserSn( ClassLikeSaveDto saveDto ) {
        
        // delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
        classLikeRepository.deleteByClassSnAndUserSn( saveDto.getClassSn(), saveDto.getUserSn() );    // Entity 의 @SQLDelete 를 수행
        
    }
	

}
