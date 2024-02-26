package com.meta.ponkids.domain.cls.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meta.ponkids.domain.cls.dto.ClassLikeSaveDto;
import com.meta.ponkids.domain.cls.entity.ClassLike;
import com.meta.ponkids.domain.cls.repository.ClassLikeRepository;
import com.meta.ponkids.domain.cls.service.ClassLikeService;
import com.meta.ponkids.domain.system.login.dto.LoginDto;
import com.meta.ponkids.global.util.session.SessionUtils;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class ClassLikeController {
	
	private final static String BASIC_PATH = "/classLike";
	private final static String BASIC_VIEW_PATH = "pon/classLike";
	
	private final ClassLikeService classLikeService;
	
	private final ClassLikeRepository classLikeRepository;
	

	// toggle 방식으로 
	// 클릭 할 때마다 insert <-> delete 를 번갈아가며 실행
	@Transactional
	@ResponseBody
	@GetMapping( BASIC_PATH + "/live/toggleLikeAjax" )
	public Map<String, Object> toggleLikeAjax (
			@ModelAttribute ClassLikeSaveDto saveDto,
			HttpServletRequest request,
			Model model ) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		// result
		// userSn setting
		LoginDto loginDto = SessionUtils.getAuthentication();
		

		// 로그인 안되어 있으면 return 
		if ( loginDto == null || loginDto.getUserSn() == null ) {
			// 메시지 출력 및 url 이동 처리
			result.put("flag", "E");
			result.put("msg", "로그인 후 이용해주세요.");
			
			return result;
		}
		
		
		saveDto.setUserSn(loginDto.getUserSn());
		
		// 1. usersn 과 classSn 으로 조회하여 
		ClassLike classLike  = classLikeRepository.findByClassSnAndUserSn( saveDto.getClassSn(), saveDto.getUserSn() ).orElse(null);
		
		
		if ( classLike == null ) {
			// 1-1. 값이 없으면 insert
			
			try {
				classLikeService.save( saveDto, request );
				result.put("likeStatus", "insert");
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				result.put("flag", "E");
				result.put("msg", "관심을 등록 하는 중 오류가 발생했습니다.");
				
				return result;
			}
			
		} else {
			// 1-2. 값이 있으면 delete
			classLikeService.deleteByClassSnAndUserSn( saveDto );
			result.put("likeStatus", "delete");
			
		}
		
		result.put( "flag" , "S" );	// 결과 성공
		
		return result;
	}
	
	

}
