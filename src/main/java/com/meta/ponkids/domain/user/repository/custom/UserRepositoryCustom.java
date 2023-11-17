package com.meta.ponkids.domain.user.repository.custom;

import java.util.List;

import com.meta.ponkids.domain.user.dto.UserListDto;
import com.meta.ponkids.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {
	
	Page<UserListDto> getList( UserListDto userListDto, Pageable pageable );

}
