package com.meta.ponkids.domain.system.file.service;

import com.meta.ponkids.domain.system.file.repository.AtchFileDetailRepository;
import com.meta.ponkids.domain.system.file.entity.AtchFileDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AtchFileDetailService {
    
    private final AtchFileDetailRepository atchFileDetailRepository;
    
    // 파일 다건 목록 조회
    public List<AtchFileDetail> getList( Long atchFileSn ) {
        
        return atchFileDetailRepository.getList( atchFileSn );
    }
    
}
