package com.meta.ponkids.domain.system.file.service;

import com.meta.ponkids.domain.system.file.entity.AtchFileDetail;
import com.meta.ponkids.domain.system.file.repository.AtchFileDetailRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtchFileDetailService {
    
    private final AtchFileDetailRepository atchFileDetailRepository;
    
    // 파일 다건 목록 조회
	public List<AtchFileDetail> getList(Long atchFileSn) {

		return atchFileDetailRepository.getList(atchFileSn);
	}
	
}
