package com.meta.ponkids.domain.user.login.repository;

import com.meta.ponkids.domain.user.login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByUserId(String UserId);

}
