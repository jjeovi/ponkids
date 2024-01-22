package com.meta.ponkids.domain.adm.system.login.repository;

import com.meta.ponkids.domain.adm.system.login.repository.custom.LoginRepositoryCustom;
import com.meta.ponkids.domain.adm.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<User, Long>, LoginRepositoryCustom {

}
