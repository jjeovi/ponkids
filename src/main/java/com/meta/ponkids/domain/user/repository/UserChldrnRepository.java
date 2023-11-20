package com.meta.ponkids.domain.user.repository;

import com.meta.ponkids.domain.user.entity.UserChldrn;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * InterfaceName  : UserChldrnRepository
 * author         : jjeoV
 * date           : 2023-11-19
 * description    : interface of 자녀 Repository
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-11-19        jjeoV             최초 생성
 */
public interface UserChldrnRepository extends JpaRepository<UserChldrn, Integer> {
    
    List<UserChldrn> findByUserSn( Long userSn );
    
    @Modifying( clearAutomatically = true )
    @Query( value = "UPDATE tb_user_chldrn "
            + "      SET del_yn = 'Y'"
            + "        , updt_dt = now() "
            + "    WHERE user_sn = :userSn", nativeQuery = true )
        // nativeQuery true 없으면 error
    int deleteAllByUserSn( @Param( "userSn" ) Long userSn );
    
}
