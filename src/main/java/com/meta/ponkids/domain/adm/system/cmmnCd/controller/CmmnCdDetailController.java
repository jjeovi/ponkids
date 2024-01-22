package com.meta.ponkids.domain.adm.system.cmmnCd.controller;

import com.meta.ponkids.domain.adm.system.cmmnCd.dto.CmmnCdDetailListDto;
import com.meta.ponkids.domain.adm.system.cmmnCd.dto.CmmnCdDetailModDto;
import com.meta.ponkids.domain.adm.system.cmmnCd.dto.CmmnCdDetailSaveDto;
import com.meta.ponkids.domain.adm.system.cmmnCd.service.CmmnCdDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class CmmnCdDetailController {
    
    private final static String BASIC_PATH = "/admin/cmmnCdDetail";
    private final CmmnCdDetailService cmmnCdDetailService;
    
    @GetMapping( BASIC_PATH + "/list" )
    public String list( @ModelAttribute CmmnCdDetailListDto listDto,
                        @PageableDefault( size = 10 ) Pageable pageable,
                        Model model ) {
        
        // S : 필요한 객체 setting
        
        // 목록 조회
        Page<CmmnCdDetailListDto> resultList = cmmnCdDetailService.getList( listDto, pageable );
        model.addAttribute( "resultList", resultList );
        
        // 검색 dto setting
        model.addAttribute( "searchDTO", listDto );
        
        // E : 필요한 객체 setting
        
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return BASIC_PATH + "/list";
    }
    
    @GetMapping( BASIC_PATH + "/regist" )
    public String regist( Model model ) {
        
        // S : 필요한 객체 setting
        
        // 가입 object 생성
        model.addAttribute( new CmmnCdDetailSaveDto() );
        
        // E : 필요한 객체 setting
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return BASIC_PATH + "/regist";
    }
    
    @Transactional
    @PostMapping( BASIC_PATH + "/insert" )
    public String insert(
            @ModelAttribute CmmnCdDetailSaveDto saveDto,
//            @ModelAttribute CmmnCdDetailRoleSaveDto cmmnCdDetailRoleSaveDto,  // required false
            HttpServletRequest request,
            Model model ) throws IOException {
        
        // S : 필요한 객체 setting
        
        // E : 필요한 객체 setting
        
        // 등록 처리
        
        // save
        cmmnCdDetailService.save( saveDto, request );
//        cmmnCdDetailService.save( saveDto, cmmnCdDetailRoleSaveDto, request );
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 등록되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/list" );
        
        return "common/alert";
    }
    
    @GetMapping( value = {
            BASIC_PATH + "/detail",
            BASIC_PATH + "/modify" } )
    public String detailOrModify(
            @RequestParam( required = true ) Long pk,    // 타입 체크
            Model model,
            HttpServletRequest request ) {
        
        // S : 필요한 객체 setting
        
        // target object 조회
        model.addAttribute( "targetDto", cmmnCdDetailService.findById( pk ) );
        
        // E : 필요한 객체 setting
        
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        String urlPath = request.getServletPath();
        String remainPath = "";
        if ( urlPath.split( BASIC_PATH )[ 1 ].startsWith( "/detail" ) ) remainPath = "detail";
        if ( urlPath.split( BASIC_PATH )[ 1 ].startsWith( "/modify" ) ) remainPath = "modify";
        
        return BASIC_PATH + "/" + remainPath;
    }
    
    @Transactional
    @PostMapping( BASIC_PATH + "/update" )
    public String update(
            @RequestParam( "file" ) MultipartFile files,        // 첨부파일 필요시
            @ModelAttribute CmmnCdDetailModDto modDto,
//            @ModelAttribute CmmnCdDetailRoleModDto cmmnCdDetailRoleModDto,  // required false
            HttpServletRequest request,
            Model model ) throws IOException {
        
        // S : 필요한 객체 setting
        
        // E : 필요한 객체 setting
        
        
        // update 구현
        cmmnCdDetailService.update( modDto, request );
//        cmmnCdDetailService.update( modDto, cmmnCdDetailRoleModDto, request );
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 수정되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/list" );
        
        return "common/alert";
    }
    
    
    @Transactional
    @PostMapping( BASIC_PATH + "/delete" )
    public String delete(
            @RequestParam( required = true ) Long pk,
            Model model ) {
        
        // 삭제 처리
        cmmnCdDetailService.deleteAllById( pk );        // By 뒤에는 custom
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 삭제되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/list" );
        
        return "common/alert";
    }
    
    
}
