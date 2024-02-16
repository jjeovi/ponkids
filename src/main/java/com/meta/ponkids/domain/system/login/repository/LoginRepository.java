package com.meta.ponkids.domain.system.login.repository;

import com.meta.ponkids.domain.system.login.repository.custom.LoginRepositoryCustom;
import com.meta.ponkids.domain.user.entity.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<User, Long>, LoginRepositoryCustom {
	
	Optional<User> findByUserIdAndPassword( String userId, String password ) ;
	
	
	Optional<User> findByUserId( String userId ) ;

	Optional<User> findTop1ByUserNmAndTelNoAndMngrYn( String userNm, String telNo, String mngrYn ) ;




}
