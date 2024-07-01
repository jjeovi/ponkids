package com.meta.ponkids.domain.cls.repository.custom;

import com.meta.ponkids.domain.cls.dto.ClassReqstListDto;
import com.meta.ponkids.domain.cls.dto.ClassReviewListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClassLikeRepositoryCustom {
    
    int countByUserSn( Long userSn );
}
