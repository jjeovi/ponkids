package com.meta.ponkids.domain.user.repository;

import com.meta.ponkids.domain.user.entity.UserChldrn;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserChldrnRepository extends JpaRepository<UserChldrn, Integer> {
    
    List<UserChldrn> findByUserId(String userId);
    
    @Modifying(clearAutomatically = true)
    @Query(value="UPDATE tb_user_chldrn "
    		+ "      SET del_yn = 'Y'"
    		+ "        , updt_dt = now() "
    		+ "    WHERE user_id = :userId", nativeQuery = true)	// nativeQuery true 없으면 error
    int deleteAllByUserId(@Param("userId") String userId);

}
