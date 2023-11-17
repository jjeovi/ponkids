package com.meta.ponkids.domain.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.meta.ponkids.domain.user.entity.UserChldrn;
import com.meta.ponkids.domain.user.repository.UserChldrnRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserChldrnService {
	private final UserChldrnRepository userChldrnRepository;
	
//	
//	public void deleteAllByUserId(String userId) {
//		
//		userChldrnRepository.deleteAllByUserId(userId);
//		
//		List<UserChldrn> userChldrnList = userChldrnRepository.findByUserId(userId); 
//		
//		System.out.println(userChldrnList);
//		
//	}

}
