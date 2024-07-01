package com.meta.ponkids.domain.cls.repository;

import java.util.Optional;

import com.meta.ponkids.domain.cls.repository.custom.ClassLikeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import com.meta.ponkids.domain.cls.entity.ClassLike;

public interface ClassLikeRepository extends JpaRepository<ClassLike, Long>, ClassLikeRepositoryCustom {
    
    void deleteByClassSnAndUserSn( Long classSn, Long userSn );
    
    Optional<ClassLike> findByClassSnAndUserSn( Long classSn, Long userSn );
    
    // 반환형은 숫자이고 파라미터는 userSn을 받는 데, userSn으로 개수를 체크하는 거 추가해줘야 함
    int countByUserSn( Long userSn );
}
