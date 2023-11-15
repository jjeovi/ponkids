package com.meta.ponkids.domain.user.repository.custom;

import java.util.List;

import com.meta.ponkids.domain.user.dto.UserListDto;
import com.meta.ponkids.domain.user.entity.User;

public interface UserRepositoryCustom {
	
	List<UserListDto> findList(UserListDto userListDto);
	
//	List<Book> findAllBooks();

}
