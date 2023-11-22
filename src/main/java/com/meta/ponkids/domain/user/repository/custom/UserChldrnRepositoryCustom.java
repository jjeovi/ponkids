package com.meta.ponkids.domain.user.repository.custom;

import java.util.List;

import com.meta.ponkids.domain.user.dto.UserChldrnListDto;

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
    
}
