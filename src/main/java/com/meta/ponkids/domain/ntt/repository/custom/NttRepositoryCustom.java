package com.meta.ponkids.domain.ntt.repository.custom;

import com.meta.ponkids.domain.ntt.dto.NttListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


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
    
    List<NttListDto> getList( NttListDto nttListDto );
    
    List<NttListDto> getNoticeList( Long bbsSn );
    
    NttListDto detailByNttSn( Long nttSn );
    
    int getExistsNtt( Long bbsSn );
    
    
}
