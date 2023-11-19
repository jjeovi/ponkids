package com.meta.ponkids.domain.user.repository.custom;

import com.meta.ponkids.domain.user.dto.UserListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * InterfaceName  : UserRepositoryCustom
 * author         : jjeoV
 * date           : 2023-11-19
 * description    : interface of 회원 RepositoryCustom
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-11-19        jjeoV             최초 생성
 */
public interface UserRepositoryCustom {
    
    Page<UserListDto> getList( UserListDto userListDto, Pageable pageable );
    
}
