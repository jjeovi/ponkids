package com.meta.ponkids.domain.system.role.controller;

import com.meta.ponkids.domain.system.role.dto.RoleSaveRequest;
import com.meta.ponkids.domain.system.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class RoleController {

//    private final RoleService roleService;
//
//    /**
//     * methodName    : userList
//     * date           : 10/28/23
//     * description    :
//     */
//    @GetMapping( "/admin/user/list" )
//    public String userList(){
//
//        return "admin/user/list";
//    }
//
//    @GetMapping("/admin/user/insert" )
//    public String userInsert(Model model ) {
//
////        model.addAttribute(new userSaveReqDto());
//        return "admin/user/insert";
//    }
//
//    @PostMapping("/admin/user/save")
//    public String userSave( @RequestParam("userNm") String userNm,
//                            @ModelAttribute RoleSaveRequest roleSaveRequest, HttpServletRequest request){
//        // getClientIp setting
//
//        // TODO 프로필 있는지 확인하여 프로필 이미지 있으면 프로필 저장 후, atchFileSn 값 을 저장
//        // TODO 자녀가 있으면 회원 등록 이후 자녀 정보의 등록도 필요
//
//        // save
////        roleService.save(roleSaveRequest);
//
//        // TODO message 생성하여 modal 에 저장 후 return
//        return "admin/user/list";
//    }

}
