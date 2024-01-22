package com.meta.ponkids.domain.system.popup.repository;

import com.meta.ponkids.domain.system.popup.entity.Popup;
import com.meta.ponkids.domain.system.popup.repository.custom.PopupRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// TODO PK(*ID) 체크
public interface PopupRepository extends JpaRepository<Popup, Long>, PopupRepositoryCustom {
    
    Optional<Popup> findById( Long pk );    // TODO PK(*ID) 체크
}
