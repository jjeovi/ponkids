package com.meta.ponkids.domain.cls.repository.custom;

import com.meta.ponkids.domain.cls.dto.ClassListDto;
import com.meta.ponkids.domain.cls.dto.ClassModDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClassRepositoryCustom {
    
    Page<ClassListDto> getList( ClassListDto listDto, Pageable pageable );
    
    List<ClassListDto> getList( ClassListDto listDto);
    
    ClassListDto getByClassSn( Long classSn, Long userSn ) ;
    
    
    ClassListDto getByClassSn( Long classSn ) ;
    
    // 나의 클래스와 같은 카테고리의 다른 클래스 ( 내 클래스는 제외하고 검색 ) : 10건만 조회
    List<ClassListDto> getListTop10OtherClassExceptMeByCtgrySn( ClassListDto listDto );
        
}
