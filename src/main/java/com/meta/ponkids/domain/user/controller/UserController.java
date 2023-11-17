package com.meta.ponkids.domain.user.controller;

import com.meta.ponkids.domain.system.role.repository.RoleRepository;
import com.meta.ponkids.domain.user.dto.*;
import com.meta.ponkids.domain.user.repository.UserRepository;
import com.meta.ponkids.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    
    private final static String BASIC_PATH = "/admin/user";
    
    /**
     * methodName    : list
     * date           : 11/17/23
     * description    : user list method
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
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return BASIC_PATH + "/list";
    }
    
    /**
     * methodName    : regist
     * date           : 11/17/23
     * description    : user regist method
     */
    @GetMapping( BASIC_PATH + "/regist" )
    public String regist( Model model ) {
        
        // 권한 리스트
        model.addAttribute( "authList", roleRepository.findAll() );
        
        // 가입 object 생성
        model.addAttribute( new UserSaveReqDto() );
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return BASIC_PATH + "/regist";
    }
    
    
    /**
     * methodName    : insert
     * date           : 11/17/23
     * description    : user insert method
     */
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
            model.addAttribute( "moveUrl", BASIC_PATH + "/list" );
            
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
        model.addAttribute( "moveUrl", BASIC_PATH + "/list" );
        
        return "common/alert";
    }
    
    /**
     * methodName    : detailOrModify
     * date           : 11/17/23
     * description    : user detail or user modify method
     */
    @GetMapping( value = { BASIC_PATH + "/detail",
            BASIC_PATH + "/modify" } )
    public String detailOrModify(
            @RequestParam( required = true ) String userId,
            Model model,
            HttpServletRequest request ) {
        
        // 권한 리스트
        model.addAttribute( "authList", roleRepository.findAll() );
        
        // target object 조회
        model.addAttribute( "targetDto", userService.findByUserId( userId ) );
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        
        String urlPath = request.getServletPath();
        String remainPath = "";
        if ( urlPath.split( BASIC_PATH )[ 1 ].startsWith( "/detail" ) ) remainPath = "detail";
        if ( urlPath.split( BASIC_PATH )[ 1 ].startsWith( "/modify" ) ) remainPath = "modify";
        
        
        return BASIC_PATH + "/" + remainPath;
    }
    
    /**
     * methodName    : update
     * date           : 11/17/23
     * description    : user update method
     */
    @PostMapping( BASIC_PATH + "/update" )
    public String update(
            @RequestParam( required = true ) String userId,
            @ModelAttribute UserModDto modDto,
            Model model ) {
        
        // TODO update 구현
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 등록되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/list" );
        
        return "common/alert";
    }
    
    
    /**
     * methodName    : delete
     * date           : 11/17/23
     * description    : user delete method
     */
    @Transactional
    @PostMapping( BASIC_PATH + "/delete" )
    public String delete(
            @RequestParam( required = true ) String userId,
            Model model ) {
        
        // 삭제 처리
        userService.deleteAllByUserId( userId );
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 삭제되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/list" );
        
        return "common/alert";
    }
    
    /**
     * methodName    : idDupCheck
     * date           : 11/17/23
     * description    : id 중복체크 ajax
     */
    @ResponseBody
    @RequestMapping( value = "/live/idDupCheck", method = { RequestMethod.GET } )
    public boolean idDupCheck( @RequestParam( "userId" ) String userId ) {
        
        return userRepository.existsByUserId( userId );
    }
    
    
}