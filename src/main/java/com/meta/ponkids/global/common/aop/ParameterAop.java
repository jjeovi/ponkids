package com.meta.ponkids.global.common.aop;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.meta.ponkids.domain.system.login.dto.LoginDto;
import com.meta.ponkids.domain.system.menu.dto.AdminMenuHierarchyDto;
import com.meta.ponkids.domain.system.menu.repository.MenuRepository;
import com.meta.ponkids.domain.system.menu.service.AdminMenuHierarchyService;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class ParameterAop {
    
    private final MenuRepository menuRepository;
    private final AdminMenuHierarchyService adminMenuHierarchyService;
    
    
    @Value( "${key.menuCd.auth}" )
    private String MCD;
    
    // 모든 controller mapping 조건이 기준 -> '/admin/' 으로 시작하는 url만 필터 (url필터는 소스로처리)
    @Pointcut( "execution(* *..*Controller..*(..))" )
    public void menuCdCheck() {
    }
    
    // 메서드가 실행 되기 전에 실행이 됨.
    @Before( "menuCdCheck()" )
    public void mcdCheckAop( JoinPoint joinPoint ) throws Exception {
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
        
        // request 선언
        HttpServletRequest request = ( ( ServletRequestAttributes ) RequestContextHolder.currentRequestAttributes() ).getRequest();
        String requestUri = request.getRequestURI();
        
        
        if ( requestUri.startsWith( "/admin/" ) ) {
            
            // 들어온 mcd값이 db에 젖아되어 있는 mcd 값과 다를시에는 mcd값을 맞춰서 redirect 시킵니다.
            
            // mcd : mcdxxx (xxx 는 숫자.. 자리수는 고정아님)
            String mcd = "";
            
            // model setting
            Model model = null;
            
            Object[] args = joinPoint.getArgs(); // 메서드의 파라미터의 값 배열을 꺼내옵니다.
            
            MethodSignature signature = ( MethodSignature ) joinPoint.getSignature();
            Method method = signature.getMethod();
            
            // mcd 가 파라미터로 존재하는지 확인
            for ( int i = 0; i < method.getParameters().length; i++ ) {
                
                if ( args[ i ] instanceof String &&
                        args[ i ].toString().startsWith( MCD ) ) mcd = args[ i ].toString();
                
                if ( args[ i ] instanceof Model ) model = ( Model ) args[ i ];
            }
            
            // mcd가 파라미터로 없으면 return
            if ( !StringUtils.hasText( mcd ) ) return;
            
            // model 이 null 이라면 return !
            if ( model == null ) return;
            
            
            // mcd 값이 url에 존재하지 않으면 return
            if ( !requestUri.contains( mcd ) ) return;
            
            String srchUrlReg = makeRegExp( requestUri, mcd );
            
            // 정규식 검색으로 조횧한 url (같은 url 여러개 있을 시, 최상위 1개만 조회)
            String url = menuRepository.findBymenuUrlRegExp( srchUrlReg );
            
            if ( !StringUtils.hasText( url ) ) {
                // 정규식 조회로 해당하는 url 이 없을 경우
                // mcd 값만 model 에 추가한 후 그냥 return 함 ( mcd 값을 모델에 추가하는 이유는 html 에서 메뉴에 없는 url이라도 mcd를 참조하여 left menu에 연동 할 수 있기 때문에 )
                
                model.addAttribute( MCD, mcd );
                return;
                
            } else {
                // 정규식 조회로 해당하는 url 이 있을 경우
                
                // response 선언
                HttpServletResponse response = ( ( ServletRequestAttributes ) RequestContextHolder.currentRequestAttributes() ).getResponse();
                
                if ( equalCheck( requestUri, url, mcd ) ) {
                    // 내가 접속한 requestUri 와 db에서 조회한 url 이 정규식 뿐만 아니라 mcd까지 전체url이 같을 경우 (이 부분이 최종으로 return 되야 정상적으로 관리자URL에 접속 했다고 판단)
                    
                    
                    // 권한 체크 (내 계정의 권한에 없는 url 일 경우 401 return)
                    if ( !authCheckAop( requestUri ) ) {
                        // 권한 없음 페이지 이동
                        response.sendRedirect( "/error/admin/401" ); // 권한없음 ( 401 )
                    } else {
                        
                        // mcd 값 model 에 추가
                        model.addAttribute( MCD, mcd );
                        
                        AdminMenuHierarchyDto presentMenuDto = adminMenuHierarchyService.findTop1ByMenuUrlOrderByMenuSn( requestUri );
                        // 현재 메뉴 정보 (currentMenu) model 에 추가
                        model.addAttribute( "currentMenu", presentMenuDto );
                        
                    }
                    
                    return;
                    
                } else {
                    // 내가 접속한 requestUri 와 db에서 조회한 url 이 정규화 되어있는 부분만 같고, mcd 부분이 다를 경우에는 , url에 존재하는mcd로 맞춰서 redirect 시킴
                    // ex ) : 내가 접속한 url   : contextPath + "/admin/menu/mcd123/list"  (mcd123은 db에 저장되어있는 mcd값과 다름!)
                    //        db에서 조회한 url : contextPath + "/admin/menu/mcd712/list"
                    //       일 경우, "/admin/menu/mcd712/list" 로 redirect 시켜 mcd값을 맞춘다.
                    
                    // -> mcd값을 잘 몰르땐 /admin/menu/mcd/list와 같이 mcd만 입력해도 알아서 db에 있는 mcd값으로 redirect 시킨다.
                    
                    
                    // mcd 값 db에 저장되어있는 url로 매핑시켜 redirect
                    response.sendRedirect( makeRedirect( requestUri, url, mcd ) ); // mcd를 맞춰서 redirect
                }
            }
        }
    }
    
    public boolean authCheckAop( String requestUri ) throws Exception {
        
        if ( requestUri.startsWith( "/admin/" ) ) {
            
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            
            LoginDto loginDto = ( LoginDto ) principal;
            
            // 메뉴 url 로 권한 리스트 조회
            List<String> roleSnList = menuRepository.findRoleSnByMenuUrl( requestUri );
            
            if ( roleSnList.contains( loginDto.getRoleSn().toString() ) ) {
                return true;
            } else {
                return false;
            }
            
        } else {
            return true;
        }
        
    }
    
    
    private String makeRegExp( String requestUri, String mcd ) {
        
        String[] separateUrl = requestUri.split( mcd );
        return separateUrl[ 0 ] + MCD + ".*" + separateUrl[ 1 ];    // 정규식 표현 생성
    }
    
    private boolean equalCheck( String requestUri, String url, String mcd ) {
        
        String[] separateUrl = requestUri.split( mcd );
        String dbMcd = url.replace( separateUrl[ 0 ], "" );
        dbMcd = dbMcd.replace( separateUrl[ 1 ], "" );
        
        return dbMcd.equals( mcd );
    }
    
    private String makeRedirect( String requestUri, String url, String mcd ) {
        
        String[] separateUrl = requestUri.split( mcd );
        String dbMcd = url.replace( separateUrl[ 0 ], "" );
        dbMcd = dbMcd.replace( separateUrl[ 1 ], "" );
        
        return separateUrl[ 0 ] + dbMcd + separateUrl[ 1 ];
    }
    
    
}