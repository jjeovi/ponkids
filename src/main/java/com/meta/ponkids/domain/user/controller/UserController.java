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
    public String list( Model model,
                            @ModelAttribute UserListDto userListDto,
                            @PageableDefault( size = 10 ) Pageable pageable ) {
        
        
        Page<UserListDto> resultList = userService.getList( userListDto, pageable );
        
        
        model.addAttribute( "resultList", resultList );
        model.addAttribute( "searchDTO", userListDto );
        
        // 기본 경로 setting
        model.addAttribute("basicPath", BASIC_PATH);

        return BASIC_PATH + "/list";
    }
    
    @GetMapping( BASIC_PATH + "/insert" )
    public String insert( Model model ) {
        
        // 권한 리스트
        model.addAttribute( "authList", roleRepository.findAll() );
        
        // 가입 object 생성
        model.addAttribute( new UserSaveReqDto() );
        
        // 기본 경로 setting
        model.addAttribute("basicPath", BASIC_PATH);
        
        
        return BASIC_PATH + "/insert";
    }
    
    @PostMapping( BASIC_PATH + "/save" )
    public String save(
            @ModelAttribute UserSaveReqDto userSaveReqDto,
            @ModelAttribute UserRoleSaveReqDto userRoleSaveReqDto,  // required false
            MultiUserChldrnSaveReqDto userChldrns,
            Model model,
            HttpServletRequest request ) {
        
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
    
    
    @Transactional
    @GetMapping(BASIC_PATH + "/modify")
    public String modify(
            @RequestParam(required = true) String userId,
            Model model
    ) {
    	
    	// 권한 리스트
        model.addAttribute( "authList", roleRepository.findAll() );
        model.addAttribute("modDto", userService.findByUserId(userId));
        
        // 기본 경로 setting
        model.addAttribute("basicPath", BASIC_PATH);
        
        return BASIC_PATH + "/modify";
    }
    
    
    @Transactional
    @GetMapping( BASIC_PATH + "/delete")
    public String delete(
            @RequestParam(required = true) String userId,
            Model model
    ) {
        
        userService.deleteAllByUserId( userId );	// User.java 의 @SQLDelete(sql = "UPDATE tb_user SET del_yn ='Y' WHERE user_id = ?") 를 수행
        
//        userChldrnService.deleteAllByUserId(userId);
       
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