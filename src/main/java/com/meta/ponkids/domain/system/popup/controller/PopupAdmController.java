package com.meta.ponkids.domain.system.popup.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.meta.ponkids.domain.system.file.service.AtchFileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.meta.ponkids.domain.system.popup.dto.PopupListDto;
import com.meta.ponkids.domain.system.popup.dto.PopupModDto;
import com.meta.ponkids.domain.system.popup.dto.PopupSaveDto;
import com.meta.ponkids.domain.system.popup.service.PopupService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PopupAdmController {
	
	private final PopupService popupService;
    
    private final AtchFileService atchFileService;
	
    private final static String BASIC_VIEW_PATH = "admin/popup";
    private final static String BASIC_PATH = "/" + BASIC_VIEW_PATH;	// BASIC_VIEW_PATH 는  앞의 "/" 를 제거해야 함.
	
    @GetMapping( BASIC_PATH + "/{mcd}/list" )
    public String list( @ModelAttribute PopupListDto listDto,
    					@PathVariable String mcd,
                        @PageableDefault( size = 10 ) Pageable pageable,
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
        
        return BASIC_VIEW_PATH + "/list";
    }
    
    @GetMapping( BASIC_PATH + "/{mcd}/regist" )
    public String regist( @PathVariable String mcd, Model model ) {
        
    	// S : 필요한 객체 setting
    	
        // 가입 object 생성
        model.addAttribute( new PopupSaveDto() );
    	
    	// E : 필요한 객체 setting
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return BASIC_VIEW_PATH + "/regist";
    }
    
    @Transactional
    @PostMapping( BASIC_PATH + "/{mcd}/insert" )
    public String insert (
            @ModelAttribute PopupSaveDto saveDto,
//            @ModelAttribute PopupRoleSaveDto popupRoleSaveDto,  // required false
            @PathVariable String mcd,
            @RequestParam( "atchFile" ) MultipartFile atchFile,
            HttpServletRequest request,
            Model model ) throws IOException {
    	
    	// S : 필요한 객체 setting
        
        // 썸네일 이미지 존재시 파일 저장
        if ( !atchFile.isEmpty() ) {
            saveDto.setAtchFileSn( atchFileService.save( atchFile ) );
        }
    	// E : 필요한 객체 setting
        
        // 등록 처리
        
        // save
        try {
            popupService.save( saveDto, request );
        }catch ( Exception e ) {
            
            // 메시지 출력 및 url 이동 처리
            model.addAttribute( "resultMsg", "등록 중 오류가 발생했습니다. " + e.getMessage() + "\n다시 시도해주세요." );
            model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
            
            return "common/alert";
        }
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 등록되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
        
        return "common/alert";
    }
    
    @GetMapping( value = { 
    		BASIC_PATH + "/{mcd}/detail",
            BASIC_PATH + "/{mcd}/modify" } )
    public String detailOrModify (
            @RequestParam( required = true ) Long pk,	// 타입 체크
            @PathVariable String mcd,
            HttpServletRequest request,
            Model model ) {
    	
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
        
        return BASIC_VIEW_PATH + "/" + remainPath;
    }
    
    @Transactional
    @PostMapping( BASIC_PATH + "/{mcd}/update" )
    public String update(
            @RequestParam( "atchFile" ) MultipartFile atchFile,
            @ModelAttribute PopupModDto modDto,
            @PathVariable String mcd,
            HttpServletRequest request,
            Model model ) throws IOException {
    	
    	// S : 필요한 객체 setting
        
        // 첨부파일 존재시 파일 저장
        if ( !atchFile.isEmpty() ) {
            // 기존에 첨부파일 있을시 삭제
            if ( modDto.getAtchFileSn() != null ) {
                atchFileService.delete( modDto.getAtchFileSnOri() );
            }
            
            // 첨부파일 저장
            modDto.setAtchFileSn( atchFileService.save( atchFile ) );    // 파일 save (파일 개수 1개일 때 )
        } else {
            // 첨부파일 존재하지않을 때
            // 기존 첨부파일이 있었는데 삭제됬다면 삭제처리
            if ( modDto.getAtchFileSnOri() != null && modDto.getAtchFileSn() == null ) {
                atchFileService.delete( modDto.getAtchFileSnOri() );
                modDto.setAtchFileSn( null );
            }
        }
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
        popupService.deleteAllById( pk );		// By 뒤에는 custom
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 삭제되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
        
        return "common/alert";
    }
	

}
