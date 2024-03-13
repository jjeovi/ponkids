package com.meta.ponkids.domain.system.popup.repository.custom;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.meta.ponkids.domain.system.popup.dto.PopupListDto;

public interface PopupRepositoryCustom {
	
	Page<PopupListDto> getList( PopupListDto listDto, Pageable pageable );
	
	List<PopupListDto> getPonList( );

}
