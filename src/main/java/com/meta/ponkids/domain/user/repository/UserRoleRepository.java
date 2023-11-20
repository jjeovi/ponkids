package com.meta.ponkids.domain.user.repository;

import com.meta.ponkids.domain.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * InterfaceName  : UserRoleRepository
 * author         : jjeoV
 * date           : 2023-11-19
 * description    : interface of 회원 권한 Repository
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-11-19        jjeoV             최초 생성
 */
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    
    @Modifying( clearAutomatically = true )
    @Query( value = "UPDATE tb_user_role "
            + "      SET del_yn = 'Y'"
            + "        , updt_dt = now() "
            + "    WHERE user_sn = :userSn", nativeQuery = true )
        // nativeQuery true 없으면 error
    int deleteByUserSn( @Param( "userSn" ) Long userSn );
    
}
