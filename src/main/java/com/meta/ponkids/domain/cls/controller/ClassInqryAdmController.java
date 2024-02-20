package com.meta.ponkids.domain.cls.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.meta.ponkids.domain.cls.dto.ClassInqryListDto;
import com.meta.ponkids.domain.cls.dto.ClassInqryModDto;
import com.meta.ponkids.domain.cls.dto.ClassInqrySaveDto;
import com.meta.ponkids.domain.cls.dto.ClassModDto;
import com.meta.ponkids.domain.cls.dto.ClassWeekListDto;
import com.meta.ponkids.domain.cls.service.ClassInqryService;
import com.meta.ponkids.domain.cls.service.ClassService;
import com.meta.ponkids.domain.cls.service.ClassWeekService;
import com.meta.ponkids.global.util.common.CommonUtils;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ClassInqryAdmController {
	
	private final ClassInqryService classInqryService;
	
	private final ClassService classService;
	private final ClassWeekService classWeekService;
	
    
    private final static String BASIC_VIEW_PATH = "admin/classInqry";
    private final static String BASIC_PATH = "/" + BASIC_VIEW_PATH;	// BASIC_VIEW_PATH 는  앞의 "/" 를 제거해야 함.
	
	
    @GetMapping( value = { BASIC_PATH + "/{mcd}/list",
    				BASIC_PATH + "/{mcd}/{classSn}/list" } )
    public String list( @ModelAttribute ClassInqryListDto listDto,
    					@PathVariable String mcd,
    					@PathVariable( required = false ) Long classSn,
    					@PageableDefault( size = 10 ) Pageable pageable,
    					Model model ) {
    	
    	// S : 필요한 객체 setting
    	
    	 
        // url 에 classSn 담겨 있을 시 classSn 유효성 체크
        if ( classSn != null && classSn != 0 ) {
        	// 1. class 정보가 있을 경우 : classDto 의 정보로 categoryhDto 의 lv1~lv3 까지 setting . ( lv1 : 카테고리, lv2 : 커리큘럼, lv3 : 클래스명 ) , lv4 는 listDto 에서 존재여부 체크하여 있으면 setting

            // 공통 유효성 체크 함수
            Map<String, Object> classValidCheck = classValidCheck( classSn, mcd, model );
            boolean validResult = ( boolean ) classValidCheck.get( "validResult" ); // 체크 결과
            
            if ( !validResult ) {
                model.addAttribute( "resultMsg", classValidCheck.get( "resultMsg" ) );
                model.addAttribute( "moveUrl", classValidCheck.get( "moveUrl" ) );
                
                return "common/alert";
            } else {
                ClassModDto classDto = ( ClassModDto ) classValidCheck.get( "classDto" );
                
                if ( classDto != null ) {
                    // 클래스가 존재할 경우 분류 (lv1,lv2..) 값 세팅을 미리 해줌
//                	listDto.setCategory( createCategory(listDto,classDto) );
                    listDto.setCategory( CommonUtils.createCategory( listDto.getCategory(), classDto) );
                }
            }
        } else {
        	// 2. class 정보가 없을 경우 : listDto의 lv1,lv2만 체크하면 됨 (lv3 or lv4 가 만약 있다면 classDto 가 있는 url 로 redirect 되었을 테니, 이 경우는 생각하지 않아도 됨.)
        	//    listDto의 lv1,lv2 값이 있다면 체크하여 categoryDto 에 setting
        	
//        	listDto.setCategory( createCategory(listDto, null ) );
        	listDto.setCategory( CommonUtils.createCategory( listDto.getCategory(), null ) );
        	
        }
        
        
        CommonUtils.schConditionCombineForResetUrl( listDto.getCategory(), classSn, BASIC_PATH, mcd, model );
    	
    	// 목록 조회
        Page<ClassInqryListDto> resultList = classInqryService.getList( listDto, pageable );
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
        model.addAttribute( new ClassInqrySaveDto() );
    	
    	// E : 필요한 객체 setting
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return BASIC_VIEW_PATH + "/regist";
    }
    
    @Transactional
    @PostMapping( BASIC_PATH + "/{mcd}/insert" )
    public String insert (
            @ModelAttribute ClassInqrySaveDto saveDto,
//            @ModelAttribute ClassInqryRoleSaveDto classInqryRoleSaveDto,  // required false
            @PathVariable String mcd,
            HttpServletRequest request,
            Model model ) throws IOException {
    	
    	// S : 필요한 객체 setting
    	
    	// E : 필요한 객체 setting
        
        // 등록 처리
        
        // save
        classInqryService.save( saveDto, request );
//        classInqryService.save( saveDto, classInqryRoleSaveDto, request );
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 등록되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
        
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
    	model.addAttribute( "targetDto", classInqryService.findById( pk ) );
    	
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
            @RequestParam("file") MultipartFile files,		// 첨부파일 필요시
            @ModelAttribute ClassInqryModDto modDto,
//            @ModelAttribute ClassInqryRoleModDto classInqryRoleModDto,  // required false
            @PathVariable String mcd,
            HttpServletRequest request,
            Model model ) throws IOException {
    	
    	// S : 필요한 객체 setting
    	
    	// E : 필요한 객체 setting
        
        
        // update 구현
    	classInqryService.update( modDto, request );
//        classInqryService.update( modDto, classInqryRoleModDto, request );
        
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
        classInqryService.deleteAllById( pk );		// By 뒤에는 custom
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 삭제되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
        
        return "common/alert";
    }
    
    
    
    // ========================= Util method =========================
    // ========================= Util method =========================
    // ========================= Util method =========================
    
    
    // url 변수 classSn 유효성 체크 로직
    private Map<String, Object> classValidCheck( Long classSn, String mcd, Model model ) {
        
        Map<String, Object> result = new HashMap<String, Object>();
        
        if ( classSn != null ) {
            // 클래스 정보 add ( classSn으로 검색 )
            ClassModDto classDto = classService.findById( classSn );
            // 클래스 요일 list 정보 add ( classSn으로 검색 )
            List<ClassWeekListDto> classWeekListDtos = classWeekService.getListByClassSn( classSn );
            
            // 클래스가 존재 하지 않을 시
            if ( classDto == null ) {
                // 메시지 출력 및 url 이동 처리
                result.put( "validResult", false );
                
                result.put( "resultMsg", "클래스의 정보를 확인해주세요." );
                result.put( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
                
                return result;
            } else if ( classWeekListDtos.size() == 0 ) {
                // 메시지 출력 및 url 이동 처리
                result.put( "validResult", false );
                
                result.put( "resultMsg", "클래스에 요일 등록이 되어있지 않습니다.\n적어도 1개의 요일을 선택해 주세요." );
                result.put( "moveUrl", "/admin/class/" + mcd + "/modify?pk=" + classSn );
                
                return result;
            } else {
                result.put( "validResult", true );
                
                // 클래스 정보 add
                result.put( "classDto", classDto );
            }
        }
        return result;
    }
	

}
