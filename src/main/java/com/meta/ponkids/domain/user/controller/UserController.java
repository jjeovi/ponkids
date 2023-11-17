
package com.meta.ponkids.domain.user.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meta.ponkids.domain.system.role.repository.RoleRepository;
import com.meta.ponkids.domain.user.dto.MultiUserChldrnSaveReqDto;
import com.meta.ponkids.domain.user.dto.UserListDto;
import com.meta.ponkids.domain.user.dto.UserRoleSaveReqDto;
import com.meta.ponkids.domain.user.dto.UserSaveReqDto;
import com.meta.ponkids.domain.user.repository.UserRepository;
import com.meta.ponkids.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    
    private final static String BASIC_PATH = "/admin/user";
    
    /**
     * methodName    : userList
     * date           : 10/28/23
     * description    :
     */
    @GetMapping( BASIC_PATH + "/list" )
    public String list( @ModelAttribute UserListDto userListDto, 
    					@PageableDefault( size = 10 ) Pageable pageable,
    					Model model ) {
        
        // 목록 조회
        Page<UserListDto> resultList = userService.getList( userListDto, pageable );
        model.addAttribute( "resultList", resultList );
        
        // 검색 dto setting
        model.addAttribute( "searchDTO", userListDto );
        
        // 기본 경로 setting
        model.addAttribute("basicPath", BASIC_PATH);

        return BASIC_PATH + "/list";
    }
    
    
    @GetMapping( BASIC_PATH + "/regist" )
    public String regist( Model model ) {
        
        // 권한 리스트
        model.addAttribute( "authList", roleRepository.findAll() );
        
        // 가입 object 생성
        model.addAttribute( new UserSaveReqDto() );
        
        // 기본 경로 setting
        model.addAttribute("basicPath", BASIC_PATH);
        
        return BASIC_PATH + "/regist";
    }
    
    
    @PostMapping( BASIC_PATH + "/insert" )
    public String insert(
            @ModelAttribute UserSaveReqDto userSaveReqDto,
            @ModelAttribute UserRoleSaveReqDto userRoleSaveReqDto,  // required false
            MultiUserChldrnSaveReqDto userChldrns,
            HttpServletRequest request,
            Model model ) {
        
        if ( userRepository.existsByUserId( userSaveReqDto.getUserId() ) ) {
            // 중복 ID 존재시 가입 불가
            
            // 메시지 출력 및 url 이동 처리
            model.addAttribute( "resultMsg", "해당ID로 가입된 ID가 있습니다. 다시 시도해주세요." );
            model.addAttribute( "moveUrl", BASIC_PATH +"/list" );
            
            return "common/alert";
            
        } else {
            // 회원가입 처리
            
            // 관리자 승인여부 Y 이면 승인일시 now로 setting
            if ( userSaveReqDto.getMngrConfmYn().equals( "Y" ) ) {
                userSaveReqDto.setConfmDt( LocalDateTime.now() );
            }
            
            // save
            userService.save( userSaveReqDto, userRoleSaveReqDto, userChldrns, request );
            
        }
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 등록되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH +"/list" );
        
        return "common/alert";
    }
    
    
    @GetMapping(value= { BASIC_PATH + "/detail", 
    		             BASIC_PATH + "/modify" } )	 
    public String detailOrModify(
            @RequestParam(required = true) String userId,
            Model model,
            HttpServletRequest request ) {
    	
    	// 권한 리스트
        model.addAttribute( "authList", roleRepository.findAll() );
        
        // target object 조회
        model.addAttribute("targetDto", userService.findByUserId(userId));
        
        // 기본 경로 setting
        model.addAttribute("basicPath", BASIC_PATH);
        
        
        String urlPath = request.getServletPath();
        String remainPath = ""; 
        if ( urlPath.split(BASIC_PATH)[1].startsWith("/detail") ) remainPath = "detail";
        if ( urlPath.split(BASIC_PATH)[1].startsWith("/modify") ) remainPath = "modify";
        
        
        return BASIC_PATH + "/" + remainPath;
    }
    
    
    @PostMapping(BASIC_PATH + "/update")
    public String update(
    		@RequestParam(required = true) String userId,
    		Model model ) {
    	
    	// 권한 리스트
    	model.addAttribute( "authList", roleRepository.findAll() );
    	model.addAttribute("modDto", userService.findByUserId(userId));
    	
    	// 기본 경로 setting
    	model.addAttribute("basicPath", BASIC_PATH);
    	
    	return BASIC_PATH + "/update";
    }
    
    
    @Transactional
    @PostMapping( BASIC_PATH + "/delete")
    public String delete(
            @RequestParam(required = true) String userId,
            Model model ) {
        
    	// 삭제 처리 
        userService.deleteAllByUserId( userId );	
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 삭제되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH +"/list" );
        
        return "common/alert";
    }
    
    @ResponseBody
    @RequestMapping( value = "/live/idDupCheck", method = { RequestMethod.GET } )
    public boolean ipDupCheck( @RequestParam( "userId" ) String userId ) {
    	
        return userRepository.existsByUserId( userId );
    }
    
    
    
}
