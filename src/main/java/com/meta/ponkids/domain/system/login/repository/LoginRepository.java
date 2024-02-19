package com.meta.ponkids.domain.system.login.repository;

import com.meta.ponkids.domain.system.login.repository.custom.LoginRepositoryCustom;
import com.meta.ponkids.domain.user.entity.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<User, Long>, LoginRepositoryCustom {
	
	Optional<User> findByUserIdAndPassword( String userId, String password ) ;
	
	
	Optional<User> findByUserId( String userId ) ;

	// 아이디 찾기 - 회원이름, 휴대폰번호 조회
	Optional<User> findTop1ByUserNmAndTelNoAndMngrYn( String userNm, String telNo, String mngrYn ) ;

	//	비밀번호 찾기 - 회원이름, 이메일 조회
	Optional<User> findByUserNmAndUserIdAndMngrYn( String userNm, String userId, String mngrYn ) ;






}
