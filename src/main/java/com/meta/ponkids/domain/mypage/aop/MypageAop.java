package com.meta.ponkids.domain.mypage.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.meta.ponkids.domain.system.menu.repository.MenuRepository;
import com.meta.ponkids.domain.system.menu.service.AdminMenuHierarchyService;
import com.meta.ponkids.domain.system.menu.service.UserMenuHierarchyService;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class MypageAop {
	
	@Value( "${key.menuCd.auth}" )
	private String MCD;
	
	// 모든 controller mapping 조건이 기준 -> '/admin/' 으로 시작하는 url만 필터 (url필터는 소스로처리)
	@Pointcut( "execution(* *..*MypageController..*(..))" )
	public void paramSet() {
	}
	
	// 메서드가 실행 되기 전에 실행이 됨.
	@Before( "paramSet()" )
	public void paramSetAop( JoinPoint joinPoint ) throws Exception {
		
		// 마이페이지 공통으로 메서드 실행 되기 전 
		// 필수 요소 파라미터를 model 에 추가하여 보낸다.
		// - 1.관심 개수 (좋아요 한 개수 )
		
		
		// S : model declare
		Model model = null;
		
		Object[] args = joinPoint.getArgs(); // 메서드의 파라미터의 값 배열을 꺼내옵니다.
		
		MethodSignature signature = ( MethodSignature ) joinPoint.getSignature();
		Method method = signature.getMethod();
		
		// mcd 가 파라미터로 존재하는지 확인
		for ( int i = 0; i < method.getParameters().length; i++ ) {
			if ( args[ i ] instanceof Model ) model = ( Model ) args[ i ];
		}
		// E : model declare
		
		// - 1.관심 개수 (좋아요 한 개수 )
		model.addAttribute( "testParam", "play" );

		
	}
	
}