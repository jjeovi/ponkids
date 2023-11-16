package com.meta.ponkids.domain.user.repository;

import com.meta.ponkids.domain.user.entity.UserChldrn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserChldrnRepository extends JpaRepository<UserChldrn, Integer> {
    

    UserChldrn findFirstByUserId(String userId);

}
