package com.meta.ponkids.domain.adm.system.popup.controller;

import com.meta.ponkids.domain.adm.system.popup.dto.PopupListDto;
import com.meta.ponkids.domain.adm.system.popup.dto.PopupModDto;
import com.meta.ponkids.domain.adm.system.popup.dto.PopupSaveDto;
import com.meta.ponkids.domain.adm.system.popup.service.PopupService;
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

@Controller
@RequiredArgsConstructor
public class PopupController {
    
    private final static String BASIC_PATH = "/admin/popup";
    private final PopupService popupService;
    
    @GetMapping( BASIC_PATH + "/{mcd}/list" )
    public String list( @ModelAttribute PopupListDto listDto,
                        @PageableDefault( size = 10 ) Pageable pageable,
                        @PathVariable String mcd,
                        Model model ) {
        
        // S : 필요한 객체 setting
        
        // 목록 조회
        Page<PopupListDto> resultList = popupService.getList( listDto, pageable );
        model.addAttribute( "resultList", resultList );
        
        // 검색 dto setting
        model.addAttribute( "searchDTO", listDto );
        
        // E : 필요한 객체 setting
        
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return BASIC_PATH + "/list";
    }
    
    @GetMapping( BASIC_PATH + "/{mcd}/regist" )
    public String regist( @PathVariable String mcd, Model model ) {
        
        // S : 필요한 객체 setting
        
        // 가입 object 생성
        model.addAttribute( new PopupSaveDto() );
        
        // E : 필요한 객체 setting
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return BASIC_PATH + "/regist";
    }
    
    @Transactional
    @PostMapping( BASIC_PATH + "/{mcd}/insert" )
    public String insert(
            @ModelAttribute PopupSaveDto saveDto,
//            @ModelAttribute PopupRoleSaveDto popupRoleSaveDto,  // required false
            @PathVariable String mcd,
            HttpServletRequest request,
            Model model ) throws IOException {
        
        // S : 필요한 객체 setting
        
        // E : 필요한 객체 setting
        
        // 등록 처리
        
        // save
        popupService.save( saveDto, request );
//        popupService.save( saveDto, popupRoleSaveDto, request );
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 등록되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
        
        return "common/alert";
    }
    
    @GetMapping( value = {
            BASIC_PATH + "/{mcd}/detail",
            BASIC_PATH + "/{mcd}/modify" } )
    public String detailOrModify(
            @RequestParam( required = true ) Long pk,    // 타입 체크
            @PathVariable String mcd,
            Model model,
            HttpServletRequest request ) {
        
        // S : 필요한 객체 setting
        
        // target object 조회
        model.addAttribute( "targetDto", popupService.findById( pk ) );
        
        // E : 필요한 객체 setting
        
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        String urlPath = request.getServletPath();
        String remainPath = "";
        if ( urlPath.split( BASIC_PATH )[ 1 ].endsWith( "/detail" ) ) remainPath = "detail";
        if ( urlPath.split( BASIC_PATH )[ 1 ].endsWith( "/modify" ) ) remainPath = "modify";
        
        return BASIC_PATH + "/" + remainPath;
    }
    
    @Transactional
    @PostMapping( BASIC_PATH + "/{mcd}/update" )
    public String update(
            @RequestParam( "file" ) MultipartFile files,        // 첨부파일 필요시
            @ModelAttribute PopupModDto modDto,
//            @ModelAttribute PopupRoleModDto popupRoleModDto,  // required false
            @PathVariable String mcd,
            HttpServletRequest request,
            Model model ) throws IOException {
        
        // S : 필요한 객체 setting
        
        // E : 필요한 객체 setting
        
        
        // update 구현
        popupService.update( modDto, request );
//        popupService.update( modDto, popupRoleModDto, request );
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 수정되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
        
        return "common/alert";
    }
    
    
    @Transactional
    @PostMapping( BASIC_PATH + "/{mcd}/delete" )
    public String delete(
            @RequestParam( required = true ) Long pk,
            @PathVariable String mcd,
            Model model ) {
        
        // 삭제 처리
        popupService.deleteAllById( pk );        // By 뒤에는 custom
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 삭제되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
        
        return "common/alert";
    }
    
    
}
