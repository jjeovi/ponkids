package com.meta.ponkids.domain.cls.repository.custom;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.ponkids.domain.cls.dto.ClassReqstListDto;

public interface ClassReqstRepositoryCustom {
    
    Page<ClassReqstListDto> getList( ClassReqstListDto listDto, Pageable pageable );
    
    ClassReqstListDto getByClassReqstSn( Long classReqstSn );
    
    // 이력 존재 확인 : 이력은 classPayment 의 paymentStatus 가 DONE 인 것만 가져옴.
    Long getByClassReqstHistoryCnt( Long classSn, Long userSn );
    
}
