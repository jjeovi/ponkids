package com.meta.ponkids.domain.user.repository;

import com.meta.ponkids.domain.user.entity.User;
import com.meta.ponkids.domain.user.repository.custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, String>, UserRepositoryCustom {
    
    boolean existsByUserId( String userId );
    
    public User findByUserId( String userId);
    
}
