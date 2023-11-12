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
        
        model.addAttribute( new UserSaveReqDto() );
        
        
        model.addAttribute( "authList", roleRepository.findAll() );
        
        
        return "admin/user/insert";
    }
    
    @PostMapping( "/admin/user/save" )
    public String userSave(
                            @ModelAttribute UserSaveReqDto userSaveReqDto,
//                            @ModelAttribute UserChldrnSaveReqDto userChldrnSaveReqDto,      // required false
                            @RequestParam(required = false) RoleSaveReqDto roleSaveReqDto,  // required false
                            Model model,
                            MultiUserChldrnSaveReqDto userChldrns,
                            HttpServletRequest request
    ) {
        // getClientIp setting
        userSaveReqDto.setRegisterIp( IpUtils.getClientIP( request ) );
        // save
        userService.save( userSaveReqDto, roleSaveReqDto, userChldrns );
        
        // logic :
        // userChldrnSaveReqDto 를 배열 처리 userChldrnSaveReqDto -> userChldrnList
        // userChldrnList 가 0 이상이면 크기만큼 userChldrn save
        
        //if( userChldrnSaveReqDto.get)
        
        
        // TODO message 생성하여 modal 에 저장 후 return
        return "admin/user/list";
    }
    
    @ResponseBody
    @RequestMapping( value = "/live/idDupCheck", method = { RequestMethod.GET } )
    public boolean ipDupCheck( @RequestParam( "userId" ) String userId ) {
        return userRepository.existsByUserId( userId );
    }
    
}
