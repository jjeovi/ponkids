package com.meta.ponkids.domain.user.login.controller;

import com.meta.ponkids.domain.system.role.entity.Role;
import com.meta.ponkids.domain.system.role.repository.RoleRepository;
import com.meta.ponkids.domain.user.login.dto.UserSaveReqDto;
import com.meta.ponkids.domain.user.login.repository.UserRepository;
import com.meta.ponkids.domain.user.login.service.UserService;
import com.meta.ponkids.global.util.ip.IpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public String userList() {
        
        return "admin/user/list";
    }
    
    @GetMapping( "/admin/user/insert" )
    public String userInsert( Model model ) {
        
        model.addAttribute( new UserSaveReqDto() );
        
        
        model.addAttribute( "authList", roleRepository.findAll() );
        
        
        return "admin/user/insert";
    }
    
    @PostMapping( "/admin/user/save" )
    public String userSave( @RequestParam( "userNm" ) String userNm,
                            @ModelAttribute UserSaveReqDto userSaveReqDto, HttpServletRequest request ) {
        // getClientIp setting
        userSaveReqDto.setRegisterIp( IpUtils.getClientIP( request ) );
        
        // TODO 프로필 있는지 확인하여 프로필 이미지 있으면 프로필 저장 후, atchFileSn 값 을 저장
        // TODO 자녀가 있으면 회원 등록 이후 자녀 정보의 등록도 필요
        
        // save
        userService.save( userSaveReqDto );
        
        // TODO message 생성하여 modal 에 저장 후 return
        return "admin/user/list";
    }
    
    
    @ResponseBody
    @RequestMapping( value = "/live/idDupCheck" ,method = {RequestMethod.GET})
    public boolean ipDupCheck( @RequestParam( "userId" ) String userId ) {
        return userRepository.existsByUserId( userId );
    }
    
}
