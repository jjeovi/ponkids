package com.meta.ponkids.global.common.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.meta.ponkids.domain.system.menu.repository.MenuRepository;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class ParameterAop {
	
	private final MenuRepository menuRepository;
	

	@Value("${speficic.menuCd}")
	private String MCD;

	
    @Pointcut("execution(* *..*Controller..*(..))")
    public void menuCdCheck() {}
    
    // 모든 controller mapping 조건이 기준 
    // 메서드가 실행 되기 전에 실행이 됨.
    @Before("menuCdCheck()")
    public void beforeAop(JoinPoint joinPoint) throws Exception {
//    	-> 1. menuCd 값이 있는지 확인. (mcd로 시작하는 String값의 parameter가 존재하면 Y )
//    	-> 1-1. menuCd 값이 없으면, end
//    	-> 2. (menuCd 값이 있으면) requestUri 를 조회 후, 해당 requestUri 에서 
//    	menuCd  값을 ' .* ' 로 치환, 후 requestUri 끝에 $ 를 붙여 (makeRegExp)  하나의 문자열을 만들고, 그 문자열로 tb_menu에 있는지 조회
//    	-> 2-1. 없으면 model.addAttribute 로 menuCd값 추가 후 end
//    	-> 3. 있으면 값에서 앞과 뒤를 제거 후, menuCd만 남겨, 들어온menuCd 와 같은 값인지 체크
//    	-> 3-1. 같은 값이면 model.addAttribute 로 menuCd값 추가 후 end
//    	-> 3-2. 같은 값이 아니면, 앞/menuCd/뒤 로 redirect     	
    	
    	
    	
    	// 여기서 mcd값을 얻기 위한 선행 조건 : 
    	// 1. mcd를 이름으로 하는 PathVariable 이 존재
    	// 2. mcd 문자열은 "mcd" 문자열로 시작해야 함
    	// 3. 메서드에 Model 객체가 있어야 함.
    	
    	// 들어온 mcd값이 db에 젖아되어 있는 mcd 값과 다를시에는 mcd값을 맞춰서 redirect 시킵니다.
    	
    	// mcd : mcdxxx (xxx 는 숫자.. 자리수는 고정아님)
    	String mcd = "";
    	
    	// model setting
    	Model model = null;

    	Object[] args = joinPoint.getArgs(); // 메서드의 파라미터의 값 배열을 꺼내옵니다.
        
    	MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    	Method method = signature.getMethod();

    	// mcd 가 파라미터로 존재하는지 확인
        for (int i = 0; i < method.getParameters().length; i++) {
        	
        	if (args[i] instanceof String && 
        		args[i].toString().startsWith("mcd")) 	mcd = args[i].toString();
        	
        	if (args[i] instanceof Model) 				model = (Model) args[i];
        }
         
        // mcd가 파라미터로 없으면 return 
        if(!StringUtils.hasText(mcd)) return ;
        
        // model 이 null 이라면 return !  
        if(model == null)	return ;
        	
        // request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String requestUri = request.getRequestURI();
        
        // mcd 값이 url에 존재하지 않으면 return
        if(!requestUri.contains(mcd))	return ; 
        	
        String srchUrlReg = makeRegExp(requestUri, mcd);
        
        String url = menuRepository.findBymenuUrlRegExp(srchUrlReg);
        
        if(!StringUtils.hasText(url)) {
        	 model.addAttribute("mcd", mcd.replace("mcd", ""));
        	 return ;
        } else {
        	if ( equalCheck(requestUri, url, mcd) ) {
        		model.addAttribute("mcd", mcd.replace("mcd", ""));
        		return ;
        	} else {
                HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
                
                response.sendRedirect( makeRedirect(requestUri, url, mcd) ); // 인증이 성공한 후에는 root로 이동
        	}
        	
        }

    }
    
    
    private String makeRegExp(String requestUri, String mcd) {
    	
    	String [] separateUrl = requestUri.split(mcd);
    	return separateUrl[0] + MCD + ".*" + separateUrl[1];	// 정규식 표현 생성 
    }
    
    private boolean equalCheck(String requestUri, String url, String mcd ) {
    	
    	String [] separateUrl = requestUri.split(mcd);
    	String dbMcd = url.replace(separateUrl[0], "");
    	dbMcd = dbMcd.replace(separateUrl[1], "");
    	
    	return dbMcd.equals(mcd);
    	
    }
    
    private String makeRedirect(String requestUri, String url, String mcd ) {
    	
    	String [] separateUrl = requestUri.split(mcd);
    	String dbMcd = url.replace(separateUrl[0], "");
    	dbMcd = dbMcd.replace(separateUrl[1], "");
    	
    	return separateUrl[0] + dbMcd + separateUrl[1];
    	
    }
    
    
}