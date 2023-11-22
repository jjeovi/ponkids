package com.meta.ponkids.domain.user.repository;

import com.meta.ponkids.domain.user.entity.User;
import com.meta.ponkids.domain.user.repository.custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * InterfaceName  : UserRepository
 * author         : jjeoV
 * date           : 2023-11-19
 * description    : interface of 회원 Repository
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-11-19        jjeoV             최초 생성
 */
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    
    boolean existsByUserId( String userId );
    
    User findByUserSn( Long userSn );
    
}
