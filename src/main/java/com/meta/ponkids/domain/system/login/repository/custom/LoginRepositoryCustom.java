package com.meta.ponkids.domain.system.login.repository.custom;

import com.meta.ponkids.domain.system.login.dto.LoginDto;

public interface LoginRepositoryCustom {
    
    LoginDto getLogin( String userId );
}
