package com.meta.ponkids.domain.cls.controller;

import com.meta.ponkids.domain.cls.dto.ClassCategoryCl02ListDto;
import com.meta.ponkids.domain.cls.dto.ClassListDto;
import com.meta.ponkids.domain.cls.dto.ClassModDto;
import com.meta.ponkids.domain.cls.dto.ClassSaveDto;
import com.meta.ponkids.domain.cls.service.ClassCategoryCl01Service;
import com.meta.ponkids.domain.cls.service.ClassCategoryCl02Service;
import com.meta.ponkids.domain.cls.service.ClassService;
import com.meta.ponkids.domain.cls.service.ClassWeekService;
import com.meta.ponkids.domain.system.cmmnCd.service.CmmnCdDetailService;
import com.meta.ponkids.domain.system.file.service.AtchFileService;
import com.meta.ponkids.domain.system.menu.dto.MenuListDto;
import com.meta.ponkids.domain.system.menu.service.MenuService;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ClassController {
    
    private final static String BASIC_PATH = "/admin/class";
    private final static String BASIC_DIR_PATH = "/admin/clas";
    
    private final ClassService classService;
    private final ClassWeekService classWeekService;
    private final ClassCategoryCl01Service classCategoryCl01Service;
    private final ClassCategoryCl02Service classCategoryCl02Service;
    private final CmmnCdDetailService cmmnCdDetailService;
    private final AtchFileService atchFileService;
    private final MenuService menuService;
    
    @GetMapping( BASIC_PATH + "/{mcd}/list" )
    public String list( @ModelAttribute ClassListDto listDto,
                        @PageableDefault( size = 10 ) Pageable pageable,
                        @PathVariable String mcd,
                        Model model ) {
        
        // S : 필요한 객체 setting
        
        // 목록 조회
        Page<ClassListDto> resultList = classService.getList( listDto, pageable );
        model.addAttribute( "resultList", resultList );
        
        // 검색 dto setting
        model.addAttribute( "searchDTO", listDto );
        
        // 카테고리 리스트 ( lv1 )
        // 클래스 카테고리 분류1 list setting
        model.addAttribute( "cateLv1List", classCategoryCl01Service.findAll() );
        
        if(listDto.getCategory() != null && listDto.getCategory().getLv1Sn() != null  ) {
        	
        	ClassCategoryCl02ListDto categoryCl02ListDto = new ClassCategoryCl02ListDto();
        	
        	// 부모clSn 값 setting ( ajax의 categorySn 을 대입해준다.)
        	categoryCl02ListDto.setParntsClSn( listDto.getCategory().getLv1Sn() );
        	
        	model.addAttribute( "cateLv2List", classCategoryCl02Service.findByParntsClSnOrderByClSeq( categoryCl02ListDto ) );
        }
        
        // E : 필요한 객체 setting
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return BASIC_DIR_PATH + "/list";
    }
    
    @GetMapping( BASIC_PATH + "/{mcd}/regist" )
    public String regist( @PathVariable String mcd,
                          Model model ) {
        
        // S : 필요한 객체 setting
        
        // 가입 object 생성
        model.addAttribute( "saveDto", new ClassSaveDto() );
        
        // 요일 List add
        model.addAttribute( "day7List", cmmnCdDetailService.getList( "DAY_7_CD" ) );    // 요일리스트
        
        // 클래스 카테고리 분류1 list setting
        model.addAttribute( "classCategoryCl01List", classCategoryCl01Service.findAll() );
        
        // E : 필요한 객체 setting
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return BASIC_DIR_PATH + "/regist";
    }
    
    @Transactional
    @PostMapping( BASIC_PATH + "/{mcd}/insert" )
    public String insert(
            @ModelAttribute ClassSaveDto saveDto,
            @PathVariable String mcd,
            HttpServletRequest request,
            @RequestParam( "thumbFile" ) MultipartFile thumbFile,
            @RequestParam( "atchFile" ) List<MultipartFile> atchFileList,
            Model model ) throws IOException {
        
        // S : 필요한 객체 setting
        
        // E : 필요한 객체 setting
        
        // 등록 처리
        
        // save
        
        // 썸네일 이미지 존재시 파일 저장
        if ( !thumbFile.isEmpty() ) {
            saveDto.setThumbAtchFileSn( atchFileService.save( thumbFile ) );
        }
        
        // 첨부파일  존재시 파일 저장
        if ( atchFileList.get( 0 ).getSize() != 0 ) {
            saveDto.setAtchFileSn( atchFileService.multifileSave( atchFileList, null ) );
        }
        
        // 클래스 저장
        try {
            saveDto = classService.save( saveDto, request );
        } catch ( Exception e ) {
            // 메시지 출력 및 url 이동 처리
            model.addAttribute( "resultMsg", "등록 중 오류가 발생했습니다. " + e.getMessage() + "\n다시 시도해주세요." );
            model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
            
            return "common/alert";
        }
        
        
        if ( saveDto.getClassWeek() != null ) {
            classWeekService.save( saveDto, request );
        }
        
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
        model.addAttribute( "targetDto", classService.findById( pk ) );
        
        // E : 필요한 객체 setting
        
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        String urlPath = request.getServletPath();
        String remainPath = "";
        if ( urlPath.split( BASIC_PATH )[ 1 ].endsWith( "/detail" ) ) remainPath = "detail";
        if ( urlPath.split( BASIC_PATH )[ 1 ].endsWith( "/modify" ) ) remainPath = "modify";
        
        return BASIC_DIR_PATH + "/" + remainPath;
    }
    
    @Transactional
    @PostMapping( BASIC_PATH + "/{mcd}/update" )
    public String update(
            @RequestParam( "file" ) MultipartFile files,        // 첨부파일 필요시
            @PathVariable String mcd,
            @ModelAttribute ClassModDto modDto,
            HttpServletRequest request,
            Model model ) throws IOException {
        
        // S : 필요한 객체 setting
        
        // E : 필요한 객체 setting
        
        
        // update 구현
        classService.update( modDto, request );
        
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
        classService.deleteAllById( pk );        // By 뒤에는 custom
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 삭제되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
        
        return "common/alert";
    }
    
    // 카테고리 검색 (Ajax)
    @ResponseBody
    @GetMapping( BASIC_PATH + "/live/getListAjax")
    public Map<String, Object> getListAjax( @ModelAttribute ClassListDto listDto ,
    		@PageableDefault( size = 10 ) Pageable pageable
    		) {
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	
    	MenuListDto mListDto = new MenuListDto();
//    	result.put( "resultList", menuService.getUserMenuList( mListDto ) );
    	result.put( "resultList", classService.getList( listDto, pageable ) );
    	
    	return result;
    }
    
    
}
