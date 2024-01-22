package com.meta.ponkids.domain.system.popup.repository.custom;

import com.meta.ponkids.domain.system.popup.dto.PopupListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PopupRepositoryCustom {
    
    Page<PopupListDto> getList( PopupListDto listDto, Pageable pageable );
    
}
