package com.meta.ponkids.domain.system.banner.repository;

import com.meta.ponkids.domain.system.banner.entity.Banner;
import com.meta.ponkids.domain.system.banner.repository.custom.BannerRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// TODO PK(*ID) 체크
public interface BannerRepository extends JpaRepository<Banner, Long>, BannerRepositoryCustom {
    
    Optional<Banner> findById( Long pk );    // TODO PK(*ID) 체크
}
