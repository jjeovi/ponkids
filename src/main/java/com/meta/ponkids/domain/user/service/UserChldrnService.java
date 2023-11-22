package com.meta.ponkids.domain.user.service;

import com.meta.ponkids.domain.user.dto.UserChldrnListDto;
import com.meta.ponkids.domain.user.dto.UserModDto;
import com.meta.ponkids.domain.user.entity.User;
import com.meta.ponkids.domain.user.entity.UserChldrn;
import com.meta.ponkids.domain.user.repository.UserChldrnRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

/**
 * className      : UserChldrnService
 * author         : jjeoV
 * date           : 2023-11-19
 * description    : class of 자녀 Service
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-11-19        jjeoV             최초 생성
 */
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
