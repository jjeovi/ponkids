package com.meta.ponkids.domain.user.repository.custom;

import com.meta.ponkids.domain.user.dto.UserChldrnListDto;
import com.meta.ponkids.domain.user.dto.UserChldrnModDto;

import java.util.List;

/**
 * InterfaceName  : UserChldrnRepositoryCustom
 * author         : jjeoV
 * date           : 2023-11-20
 * description    : interface of 자녀 RepositoryCustom
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-11-19        jjeoV             최초 생성
 */
public interface UserChldrnRepositoryCustom {
    
    List<UserChldrnListDto> getListByUserSn( Long userSn );
    
    UserChldrnListDto getByUserSnAndUserChldrnSeq( Long userSn, Long userChldrnSeq );
    
}
