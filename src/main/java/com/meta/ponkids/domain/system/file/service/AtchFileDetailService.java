package com.meta.ponkids.domain.system.file.service;

import com.meta.ponkids.domain.system.file.repository.AtchFileDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtchFileDetailService {
    
    private final AtchFileDetailRepository atchFileDetailRepository;
}
