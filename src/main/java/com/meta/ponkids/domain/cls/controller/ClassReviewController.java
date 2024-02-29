package com.meta.ponkids.domain.cls.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
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

import com.meta.ponkids.domain.cls.dto.ClassReviewListDto;
import com.meta.ponkids.domain.cls.dto.ClassReviewModDto;
import com.meta.ponkids.domain.cls.dto.ClassReviewSaveDto;
import com.meta.ponkids.domain.cls.service.ClassReviewService;
import com.meta.ponkids.global.util.session.SessionUtils;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ClassReviewController {
	

	public static String USER_VIEW_PATH;
    // path 경로 : pon
    @Value( "${key.default.directoryPath.user}" )
    public void setUserViewPath(String value) {
    	USER_VIEW_PATH = value;
    }
	
	private final ClassReviewService classReviewService;
	
	private final static String BASIC_DOMAIN = "classReview";
    private final static String BASIC_PATH = "/" + BASIC_DOMAIN;	// USER_VIEW_PATH + "/" + BASIC_DOMAIN 는  앞의 "/" 를 제거해야 함.
	
	
    @GetMapping( BASIC_PATH + "/{mcd}/list" )
    public String list( @ModelAttribute ClassReviewListDto listDto,
    					@PathVariable String mcd,
                        @PageableDefault( size = 8 ) Pageable pageable,
                        Model model ) {
    	
    	// S : 필요한 객체 setting
    	
    	// 목록 조회
        Page<ClassReviewListDto> resultList = classReviewService.getList( listDto, pageable );
        model.addAttribute( "resultList", resultList );
        
        // 검색 dto setting
        model.addAttribute( "searchDTO", listDto );
    	
    	// E : 필요한 객체 setting
        
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return USER_VIEW_PATH + BASIC_PATH + "/list";
    }
    
    @GetMapping( BASIC_PATH + "/{mcd}/regist" )
    public String regist( @PathVariable String mcd, Model model ) {
        
    	// S : 필요한 객체 setting
    	
        // 가입 object 생성
        model.addAttribute( new ClassReviewSaveDto() );
    	
    	// E : 필요한 객체 setting
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return USER_VIEW_PATH + BASIC_PATH + "/regist";
    }
    
    @Transactional
    @PostMapping( BASIC_PATH + "/insert" )
    public String insert (
            @ModelAttribute ClassReviewSaveDto saveDto,
            HttpServletRequest request,
            Model model ) throws IOException {
    	
    	// S : 필요한 객체 setting
    	
    	// 저장 후 이동할 url setting
    	String moveUrl = request.getHeader("referer");
    	
    	
    	// userSn setting
    	saveDto.setUserSn( SessionUtils.getAuthUserSn());
    	
    	// step setting
    	saveDto.setStep("1");	// 원글 :1 / 댓글 : 2
    	
    	// E : 필요한 객체 setting
    	
        
        // 등록 처리
        
        // save
        classReviewService.save( saveDto, request );
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 등록되었습니다." );
        model.addAttribute( "moveUrl", moveUrl );
        
        return "common/alert";
    }
    
    @GetMapping( value = { 
    		BASIC_PATH + "/{mcd}/detail",
            BASIC_PATH + "/{mcd}/modify" } )
    public String detailOrModify(
            @RequestParam( required = true ) Long pk,	// 타입 체크
            @PathVariable String mcd,
            HttpServletRequest request,
            Model model ) {
    	
    	// S : 필요한 객체 setting
    	
    	// target object 조회
    	model.addAttribute( "targetDto", classReviewService.findById( pk ) );
    	
    	// E : 필요한 객체 setting
        
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        String urlPath = request.getServletPath();
        String remainPath = "";
        if ( urlPath.split( BASIC_PATH )[ 1 ].endsWith( "/detail" ) ) remainPath = "detail";
        if ( urlPath.split( BASIC_PATH )[ 1 ].endsWith( "/modify" ) ) remainPath = "modify";
        
        return USER_VIEW_PATH + BASIC_PATH + "/" + remainPath;
    }
    
    @Transactional
    @PostMapping( BASIC_PATH + "/{mcd}/update" )
    public String update(
            @RequestParam("file") MultipartFile files,		// 첨부파일 필요시
            @ModelAttribute ClassReviewModDto modDto,
//            @ModelAttribute ClassReviewRoleModDto classReviewRoleModDto,  // required false
            @PathVariable String mcd,
            HttpServletRequest request,
            Model model ) throws IOException {
    	
    	// S : 필요한 객체 setting
    	
    	// E : 필요한 객체 setting
        
        
        // update 구현
    	classReviewService.update( modDto, request );
//        classReviewService.update( modDto, classReviewRoleModDto, request );
        
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
        classReviewService.deleteAllById( pk );		// By 뒤에는 custom
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 삭제되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
        
        return "common/alert";
    }
	

}
