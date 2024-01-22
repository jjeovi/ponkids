package com.meta.ponkids.domain.adm.system.login.repository.custom;

import com.meta.ponkids.domain.adm.system.login.dto.LoginDto;

public interface LoginRepositoryCustom {
    
    LoginDto getLogin( String userId );
}
