package com.meta.ponkids.domain.user.controller;

import com.meta.ponkids.domain.system.cmmnCd.service.CmmnCdDetailService;
import com.meta.ponkids.domain.system.file.service.AtchFileService;
import com.meta.ponkids.domain.system.role.repository.RoleRepository;
import com.meta.ponkids.domain.user.repository.UserChldrnRepository;
import com.meta.ponkids.domain.user.repository.UserRepository;
import com.meta.ponkids.domain.user.service.UserRoleService;
import com.meta.ponkids.domain.user.service.UserService;
import com.meta.ponkids.global.util.ip.IpUtils;
import com.meta.ponkids.global.util.session.SessionUtils;
import com.meta.ponkids.domain.user.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.text.ParseException;
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
public class UserAdmController {
    
    private final static String BASIC_VIEW_PATH = "admin/user";
    private final static String BASIC_PATH = "/" + BASIC_VIEW_PATH;	// BASIC_VIEW_PATH 는  앞의 "/" 를 제거해야 함.
    
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final UserChldrnRepository userChldrnRepository;
    private final UserRepository userRepository;
    
    private final RoleRepository roleRepository;
    
    private final AtchFileService atchFileService;
    
    private final CmmnCdDetailService cmmnCdDetailService;
    
    /**
     * methodName    : list
     * date           : 11/17/23
     * description    : user list method
     */
    @GetMapping( BASIC_PATH + "/{mcd}/list" )
    public String list( @ModelAttribute UserListDto userListDto,
                        @PageableDefault( size = 10 ) Pageable pageable,
                        @PathVariable String mcd,
                        Model model ) {
        
        // 목록 조회
        Page<UserListDto> resultList = userService.getList( userListDto, pageable );
        model.addAttribute( "resultList", resultList );
        
        // 검색 dto setting
        model.addAttribute( "searchDTO", userListDto );
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return BASIC_VIEW_PATH + "/list";
    }
    
    /**
     * methodName    : regist
     * date           : 11/17/23
     * description    : user regist method
     */
    @GetMapping( BASIC_PATH + "/{mcd}/regist" )
    public String regist( @PathVariable String mcd, Model model ) {
        
        // 권한 리스트
        model.addAttribute( "roleList", roleRepository.findAllByOrderByRoleSn() );
        
        // 거주지역 리스트
        model.addAttribute( "resideAreaList", cmmnCdDetailService.getList( "RESIDE_AREA_CD" ) );
        
        // 가입 object 생성
        model.addAttribute( new UserSaveDto() );
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return BASIC_VIEW_PATH + "/regist";
    }
    
    /**
     * methodName    : insert
     * date           : 11/17/23
     * description    : user insert method
     * @throws ParseException 
     */
    @Transactional
    @PostMapping( BASIC_PATH + "/{mcd}/insert" )
    public String insert(
            @RequestParam( "file" ) MultipartFile files,
            @ModelAttribute UserSaveDto saveDto,
            @ModelAttribute UserRoleSaveDto userRoleSaveDto,  // required false
            @PathVariable String mcd,
            MultiUserChldrnSaveDto userChldrns,
            HttpServletRequest request,
            Model model ) throws IOException, ParseException {
        
        if ( userRepository.existsByUserId( saveDto.getUserId() ) ) {
            // 중복 ID 존재시 가입 불가
            
            // 메시지 출력 및 url 이동 처리
            model.addAttribute( "resultMsg", "해당ID로 가입된 ID가 있습니다. 다시 시도해주세요." );
            model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
            
            return "common/alert";
            
        } else {
            // 회원가입 처리
            
            // 첨부파일 존재시 파일 저장
            if ( !files.isEmpty() ) {
                saveDto.setAtchFileSn( atchFileService.save( files ) );    // 파일 save (파일 개수 1개일 때 )
            }
            
            // 관리자 승인여부 Y 이면 승인일시 now로 setting
            if ( saveDto.getMngrConfmYn() != null && saveDto.getMngrConfmYn().equals( "Y" ) ) {
                saveDto.setConfmDt( LocalDateTime.now() );
                saveDto.setConfmerIp( IpUtils.getClientIP( request ) );
                saveDto.setConfmerId( SessionUtils.getUserId() );
            }
            
            // save
            userService.save( saveDto, userRoleSaveDto, userChldrns, request );
        }
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 등록되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
        
        return "common/alert";
    }
    
    /**
     * methodName    : detailOrModify
     * date           : 11/17/23
     * description    : user detail or user modify method
     */
    @GetMapping( value = {
            BASIC_PATH + "/{mcd}/detail",
            BASIC_PATH + "/{mcd}/modify" } )
    public String detailOrModify(
            @RequestParam( required = true ) Long userSn,
            @PathVariable String mcd,
            Model model,
            HttpServletRequest request ) {
        
        // 권한 리스트
        model.addAttribute( "roleList", roleRepository.findAllByOrderByRoleSn() );
        
        // target object 조회
        UserModDto targetDto = userService.findByUserSn( userSn );
        model.addAttribute( "targetDto", targetDto );
        
        // 거주지역 리스트
        model.addAttribute( "resideAreaList", cmmnCdDetailService.getList( "RESIDE_AREA_CD" ) );
        
        // roleDto 조회 
        if ( targetDto.getMngrYn().equals( "Y" ) ) {
            // 관리자일 경우에만 조회
            model.addAttribute( "userRoleModDto", userRoleService.findByUserSn( userSn ) );
        }
        
        // chldrn target object 조회
        model.addAttribute( "targetChldrnDtoList", userChldrnRepository.getListByUserSn( userSn ) );
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        String urlPath = request.getServletPath();
        String remainPath = "";
        if ( urlPath.split( BASIC_PATH )[ 1 ].endsWith( "/detail" ) ) remainPath = "detail";
        if ( urlPath.split( BASIC_PATH )[ 1 ].endsWith( "/modify" ) ) remainPath = "modify";
        
        return BASIC_VIEW_PATH + "/" + remainPath;
    }
    
    /**
     * methodName    : update
     * date
     * : 11/17/23
     * description    : user update method
     */
    @Transactional
    @PostMapping( BASIC_PATH + "/{mcd}/update" )
    public String update(
            @RequestParam( "file" ) MultipartFile files,
            @ModelAttribute UserModDto modDto,
            @ModelAttribute UserRoleModDto userRoleModDto,  // required false
            @PathVariable String mcd,
            MultiUserChldrnSaveDto userChldrns,
            HttpServletRequest request,
            Model model ) throws IOException {
        
        // 첨부파일 존재시 파일 저장
        if ( !files.isEmpty() ) {
            // 기존에 첨부파일 있을시 삭제
            if ( modDto.getAtchFileSnOri() != null ) {
                atchFileService.delete( modDto.getAtchFileSnOri() );
            }
            
            // 첨부파일 저장
            modDto.setAtchFileSn( atchFileService.save( files ) );    // 파일 save (파일 개수 1개일 때 )
        } else {
            // 첨부파일 존재하지않을 때
            // 기존 첨부파일이 있었는데 삭제됬다면 삭제처리
            if ( modDto.getAtchFileSnOri() != null && modDto.getAtchFileSn() == null ) {
                atchFileService.delete( modDto.getAtchFileSnOri() );
                modDto.setAtchFileSn( null );
            }
        }
        
        // update 구현
        userService.update( modDto, userRoleModDto, userChldrns, request );
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 수정되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
        
        return "common/alert";
    }
    
    /**
     * methodName    : delete
     * date           : 11/17/23
     * description    : user delete method
     */
    @Transactional
    @PostMapping( BASIC_PATH + "/{mcd}/delete" )
    public String delete(
            @RequestParam( required = true ) Long userSn,
            @PathVariable String mcd,
            Model model ) {
        
        // 첨부파일 삭제
        Long atchFileSn = userService.findByUserSn( userSn ).getAtchFileSn();
        if ( atchFileSn != null ) {
            atchFileService.delete( atchFileSn );
        }
        
        // 삭제 처리
        userService.deleteAllByUserSn( userSn );
       
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 삭제되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
        
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