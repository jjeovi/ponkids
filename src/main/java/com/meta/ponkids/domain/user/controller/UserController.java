package com.meta.ponkids.domain.user.controller;

import com.meta.ponkids.domain.system.cmmnCd.service.CmmnCdDetailService;
import com.meta.ponkids.domain.system.file.service.AtchFileService;
import com.meta.ponkids.domain.system.role.repository.RoleRepository;
import com.meta.ponkids.domain.user.repository.UserChldrnRepository;
import com.meta.ponkids.domain.user.repository.UserRepository;
import com.meta.ponkids.domain.user.service.UserRoleService;
import com.meta.ponkids.domain.user.service.UserService;
import com.meta.ponkids.domain.user.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;

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
    
    private final static String BASIC_PATH = "/user";
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
     */
    @Transactional
    @PostMapping( BASIC_PATH + "/live/{mcd}/insertAjax" )
    public String insert(
            @RequestParam( "file" ) MultipartFile files,
            @ModelAttribute UserSaveDto saveDto,
            @PathVariable String mcd,
            MultiUserChldrnSaveDto userChldrns,
            HttpServletRequest request,
            Model model ) throws IOException {
        
        if ( userRepository.existsByUserId( saveDto.getUserId() ) ) {
            // 중복 ID 존재시 가입 불가
            
            // 메시지 출력 및 url 이동 처리
            model.addAttribute( "resultMsg", "해당ID로 가입된 ID가 있습니다. 다시 시도해주세요." );
            model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
            
            return "common/alert";
            
        } else {
            // 회원가입 처리
        	
        	// 사용자 처리
        	saveDto.setMngrYn("N");	// 관리자 여부 N 으로 setting
            
            // 첨부파일 존재시 파일 저장
            if ( !files.isEmpty() ) {
                saveDto.setAtchFileSn( atchFileService.save( files ) );    // 파일 save (파일 개수 1개일 때 )
            }
            
            // 관리자 승인여부 Y 이면 승인일시 now로 setting
            if ( saveDto.getMngrConfmYn().equals( "Y" ) ) {
                saveDto.setConfmDt( LocalDateTime.now() );
            }
            
            // save
            userService.save( saveDto, new UserRoleSaveDto(), userChldrns, request );
        }
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 등록되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
        
        return "common/alert";
    }
    
    
}