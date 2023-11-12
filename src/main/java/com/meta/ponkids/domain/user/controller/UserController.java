package com.meta.ponkids.domain.user.controller;

import com.meta.ponkids.domain.system.role.dto.RoleSaveReqDto;
import com.meta.ponkids.domain.system.role.repository.RoleRepository;
import com.meta.ponkids.domain.user.dto.*;
import com.meta.ponkids.domain.user.repository.UserRepository;
import com.meta.ponkids.domain.user.service.UserService;
import com.meta.ponkids.global.util.ip.IpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    
    /**
     * methodName    : userList
     * date           : 10/28/23
     * description    :
     */
    @GetMapping( "/admin/user/list" )
    public String userList(Model model,@ModelAttribute UserListResDto userListResDto) {
    
//        userService.findAll(userListResDto);
        
//        model.addAttribute( "authList", userService.findAll() );
        
        return "admin/user/list";
    }
    
    @GetMapping( "/admin/user/insert" )
    public String userInsert( Model model ) {
        
        // 권한 리스트
        model.addAttribute( "authList", roleRepository.findAll() );
        
        // 가입 object 생성
        model.addAttribute( new UserSaveReqDto() );
        
        return "admin/user/insert";
    }
    
    @PostMapping( "/admin/user/save" )
    public String userSave(
                            @ModelAttribute UserSaveReqDto userSaveReqDto,
                            @ModelAttribute UserRoleSaveReqDto userRoleSaveReqDto,  // required false
                            MultiUserChldrnSaveReqDto userChldrns,
                            Model model,
                            HttpServletRequest request ) {
        
        if ( userRepository.existsByUserId( userSaveReqDto.getUserId() ) ) {
            // 중복 ID 존재시 가입 불가
            
        } else {
            // 회원가입 처리
            
            // 관리자 승인여부 Y 이면 승인일시 now로 setting
            if (userSaveReqDto.getMngrConfmYn().equals( "Y" ) ) {
                userSaveReqDto.setConfmDt( LocalDateTime.now() );
            }
            
            // save
            userService.save( userSaveReqDto, userRoleSaveReqDto, userChldrns, request );
            
        }
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute("resultMsg", "정상적으로 등록되었습니다.");
        model.addAttribute("moveUrl", "/admin/user/list");
        
        return "common/alert";
    }
    
    @ResponseBody
    @RequestMapping( value = "/live/idDupCheck", method = { RequestMethod.GET } )
    public boolean ipDupCheck( @RequestParam( "userId" ) String userId ) {
        return userRepository.existsByUserId( userId );
    }
    
}