package com.meta.ponkids.domain.system.bbs.repository.custom;

import com.meta.ponkids.domain.system.bbs.dto.BbsListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * InterfaceName  : BbsRepositoryCustom
 * author         : jjeoV
 * date           : 2023-12-02
 * description    : interface of 게시판 RepositoryCustom
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-12-02        ehlee             최초 생성
 */
public interface BbsRepositoryCustom {
	
	Page<BbsListDto> getList( BbsListDto bbsListDto, Pageable pageable );
	String getSetReplySetYn(Long bbsSn);
	String getBbsSeCd(Long bbsSn);
}
