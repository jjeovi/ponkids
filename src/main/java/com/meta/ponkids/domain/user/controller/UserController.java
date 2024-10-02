package com.meta.ponkids.domain.user.controller;

import com.meta.ponkids.domain.system.cmmnCd.service.CmmnCdDetailService;
import com.meta.ponkids.domain.system.file.service.AtchFileService;
import com.meta.ponkids.domain.system.login.dto.LoginDto;
import com.meta.ponkids.domain.system.role.repository.RoleRepository;
import com.meta.ponkids.domain.user.dto.MultiUserChldrnSaveDto;
import com.meta.ponkids.domain.user.dto.UserRoleSaveDto;
import com.meta.ponkids.domain.user.dto.UserSaveDto;
import com.meta.ponkids.domain.user.repository.UserChldrnRepository;
import com.meta.ponkids.domain.user.repository.UserRepository;
import com.meta.ponkids.domain.user.service.UserRoleService;
import com.meta.ponkids.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * className      : UserController
 * author         : jjeoV
 * date           : 2023-11-19
 * description    : class of 회원관리 Controller
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-11-19        jjeoV             최초 생성
 */
@Controller
@RequiredArgsConstructor
public class UserController {
    
	private final static String BASIC_VIEW_PATH = "user";
	private final static String BASIC_PATH = "/" + BASIC_VIEW_PATH;	// BASIC_VIEW_PATH 는  앞의 "/" 를 제거해야 함.
    
    
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final UserChldrnRepository userChldrnRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AtchFileService atchFileService;
    private final CmmnCdDetailService cmmnCdDetailService;
    
    /**
     * methodName    : insert
     * date           : 11/17/23
     * description    : user insert method
     * @throws ParseException 
     */
    @ResponseBody
    @Transactional
    @PostMapping( BASIC_PATH + "/live/{mcd}/insertAjax" )
    public Map<String, Object> insert(
            @RequestParam( "file" ) MultipartFile files,
            @ModelAttribute UserSaveDto saveDto,
            @PathVariable String mcd,
            MultiUserChldrnSaveDto userChldrns,
            HttpServletRequest request,
            Model model ) throws IOException {
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	
        
        if ( userRepository.existsByUserId( saveDto.getUserId() ) ) {
            // 중복 ID 존재시 가입 불가
            
        	// 결과코드 및 결과메시지 추가
            result.put("flag", "E");
            result.put("msg", "해당ID로 가입된 ID가 있습니다. 다시 시도해 주세요.");
            
            return result;
            
        } else {
            // 회원가입 처리
        	
        	// 사용자 처리
        	saveDto.setMngrYn("N");	// 관리자 여부 N 으로 setting
        	saveDto.setMngrConfmYn("Y");	// 관리자 여부 N 으로 setting
            
            // 첨부파일 존재시 파일 저장
            if ( !files.isEmpty() ) {
                saveDto.setAtchFileSn( atchFileService.save( files ) );    // 파일 save (파일 개수 1개일 때 )
            }
            
            // 관리자 승인여부 Y 이면 승인일시 now로 setting
            if ( saveDto.getMngrConfmYn() != null && saveDto.getMngrConfmYn().equals( "Y" ) ) {
                saveDto.setConfmDt( LocalDateTime.now() );
            }
            
            // save
            try {
				userService.save( saveDto, new UserRoleSaveDto(), userChldrns, request );
			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        // 결과코드 및 결과메시지 추가
        result.put("flag", "S");
        result.put("msg", "회원가입이 완료되었습니다.");
        
        return result;
    }
    
    
    
	/**
	 * methodName	 : authenticationCheckAjax
	 * date		   : 11/17/23
	 * description	: ajax로 현재 로그인 세션 있는지 여부 체크
	 */
	@ResponseBody
	@RequestMapping( value = "/live/authenticationCheckAjax", method = { RequestMethod.GET } )
	public boolean authenticationCheckAjax( ) {
		
		// 로그인 여부 확인 하여 
		// request 에 loginYn 추가
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if ( auth == null ) {
			return false ;
		} 
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		 // 세션 체크
		if ( principal == null || principal.getClass() != LoginDto.class ) {
			// loginDto 가 없을 시
			
			// 로그인 페이지로 이동

			return false;
			
		} else {
			// loginDto 있을 때 ( 권한 문제 or 승인 문제 ... ) 
			
			// loginDto로 변경
			LoginDto loginDto = ( LoginDto ) principal;

			return true;
			
		}
	}
    
    
}