package com.meta.ponkids.domain.adm.system.popup.repository.custom;

import com.meta.ponkids.domain.adm.system.popup.dto.PopupListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PopupRepositoryCustom {
    
    Page<PopupListDto> getList( PopupListDto listDto, Pageable pageable );
    
}
