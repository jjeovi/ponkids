package com.meta.ponkids.domain.ntt.repository.custom;

import com.meta.ponkids.domain.ntt.dto.NttListDto;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * InterfaceName  : NttRepositoryCustom
 * author         : ehlee
 * date           : 2023-12-02
 * description    : interface of 게시물 RepositoryCustom
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-12-02        ehlee             최초 생성
 */
public interface NttRepositoryCustom {
	
	Page<NttListDto> getList( NttListDto nttListDto, Pageable pageable );
	List<NttListDto> getNoticeList(Long bbsSn); 
	int getExistsNtt( Long bbsSn);
	

}
