package com.meta.ponkids.domain.lctre.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

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

import com.meta.ponkids.domain.cls.dto.ClassModDto;
import com.meta.ponkids.domain.cls.service.ClassService;
import com.meta.ponkids.domain.cls.service.ClassWeekService;
import com.meta.ponkids.domain.lctre.dto.LctreListDto;
import com.meta.ponkids.domain.lctre.dto.LctreModDto;
import com.meta.ponkids.domain.lctre.dto.LctreSaveDto;
import com.meta.ponkids.domain.lctre.service.LctreService;
import com.meta.ponkids.domain.system.cmmnCd.service.CmmnCdDetailService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LctreController {
    
    private final LctreService lctreService;
    
    private final ClassService classService;
    private final ClassWeekService classWeekService;
    
    private final CmmnCdDetailService cmmnCdDetailService;
    
    private final static String BASIC_PATH = "/admin/lctre";
    
    
    @GetMapping( value = { BASIC_PATH + "/{mcd}/list",
                            BASIC_PATH + "/{classSn}/{mcd}/list" } )
    public String list( @ModelAttribute LctreListDto listDto,
                        @PathVariable String mcd,
                        @PathVariable( required = false ) Long classSn,
                        @PageableDefault( size = 10 ) Pageable pageable,
                        Model model ) {
        
        // S : 필요한 객체 setting
        
        // 목록 조회
        Page<LctreListDto> resultList = lctreService.getList( listDto, pageable );
        model.addAttribute( "resultList", resultList );
        
        // 검색 dto setting
        model.addAttribute( "searchDTO", listDto );
        
        // E : 필요한 객체 setting
        
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return BASIC_PATH + "/list";
    }
    
    @GetMapping( BASIC_PATH + "/{classSn}/{mcd}/regist" )
    public String regist( 	@PathVariable String mcd,
    						@PathVariable Long classSn,
    						Model model ) {
        
        // S : 필요한 객체 setting
        
        // 가입 object 생성
        model.addAttribute( new LctreSaveDto() );
        
        // 클래스 정보 add
        ClassModDto classDto  = classService.findById(classSn);
        
        // 클래스가 존재 하지 않을 시
        if(classDto == null ) {
        	// 메시지 출력 및 url 이동 처리
            model.addAttribute( "resultMsg", "클래스의 정보를 확인해주세요." );
            model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
            
            return "common/alert";
        }
        
        
        
        model.addAttribute("classDto", classService.findById(classSn));
        
        // 클래스 요일 List add
        model.addAttribute( "classWeekList", classWeekService.getListByClassSn( classSn ) );    // 요일리스트
        
        // E : 필요한 객체 setting
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return BASIC_PATH + "/regist";
    }
    
    @Transactional
    @PostMapping( BASIC_PATH + "/{mcd}/insert" )
    public String insert(
            @ModelAttribute LctreSaveDto saveDto,
//            @ModelAttribute LctreRoleSaveDto lctreRoleSaveDto,  // required false
            @PathVariable String mcd,
            HttpServletRequest request,
            Model model ) throws IOException {
        
        // S : 필요한 객체 setting
        
        // E : 필요한 객체 setting
        
        // 등록 처리
        
        // save
        lctreService.save( saveDto, request );
        
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
            HttpServletRequest request,
            Model model ) {
        
        // S : 필요한 객체 setting
        
        // target object 조회
        model.addAttribute( "targetDto", lctreService.findById( pk ) );
        
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
            @ModelAttribute LctreModDto modDto,
//            @ModelAttribute LctreRoleModDto lctreRoleModDto,  // required false
            @PathVariable String mcd,
            HttpServletRequest request,
            Model model ) throws IOException {
        
        // S : 필요한 객체 setting
        
        // E : 필요한 객체 setting
        
        
        // update 구현
        lctreService.update( modDto, request );
//        lctreService.update( modDto, lctreRoleModDto, request );
        
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
        lctreService.deleteAllById( pk );        // By 뒤에는 custom
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 삭제되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
        
        return "common/alert";
    }
    
    
}
