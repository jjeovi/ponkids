package com.meta.ponkids.domain.system.menu.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.meta.ponkids.domain.system.menu.dto.MenuListDto;
import com.meta.ponkids.domain.system.menu.dto.MenuModDto;
import com.meta.ponkids.domain.system.menu.dto.MenuSaveDto;
import com.meta.ponkids.domain.system.menu.repository.MenuRepository;
import com.meta.ponkids.domain.system.menu.service.MenuService;
import com.meta.ponkids.domain.system.role.dto.RoleListDto;
import com.meta.ponkids.domain.system.role.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MenuController {
	
	private final MenuService menuService;
	private final MenuRepository menuRepository;
	private final RoleRepository roleRepository;
	
	private final static String BASIC_PATH = "/admin/menu";
	
	
    @GetMapping( BASIC_PATH + "/list" )
    public String list( @ModelAttribute MenuListDto listDto,
//                        @PageableDefault( size = 10 ) Pageable pageable,
                        Model model ) {
    	
    	// S : 필요한 객체 setting
    	
    	// 목록 조회
        List<MenuListDto> resultList = menuService.getList( listDto );
        model.addAttribute( "resultList", resultList );
        
        // 검색 dto setting
        model.addAttribute( "searchDTO", listDto );
        
        // 카테고리 리스트 ( lv1 )
        model.addAttribute( "cateLv1List", roleRepository.getCateList() );
    	
    	// E : 필요한 객체 setting
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return BASIC_PATH + "/list";
    }
    
    @GetMapping( BASIC_PATH + "/regist" )
    public String regist( Model model ) {
        
    	// S : 필요한 객체 setting
    	
        // 가입 object 생성
        model.addAttribute( new MenuSaveDto() );
    	
    	// E : 필요한 객체 setting
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return BASIC_PATH + "/regist";
    }
    
    @Transactional
    @PostMapping( BASIC_PATH + "/insert" )
    public String insert (
            @ModelAttribute MenuSaveDto saveDto,
//            @ModelAttribute MenuRoleSaveDto menuRoleSaveDto,  // required false
            HttpServletRequest request,
            Model model ) throws IOException {
    	
    	// S : 필요한 객체 setting
    	
    	// E : 필요한 객체 setting
        
        // 등록 처리
        
        // save
        menuService.save( saveDto, request );
//        menuService.save( saveDto, menuRoleSaveDto, request );
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 등록되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/list" );
        
        return "common/alert";
    }
    
    @GetMapping( value = { 
    		BASIC_PATH + "/detail",
            BASIC_PATH + "/modify" } )
    public String detailOrModify(
            @RequestParam( required = true ) Long pk,	// 타입 체크
            Model model,
            HttpServletRequest request ) {
    	
    	// S : 필요한 객체 setting
    	
    	// target object 조회
    	model.addAttribute( "targetDto", menuService.findById( pk ) );
    	
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
            @RequestParam("file") MultipartFile files,		// 첨부파일 필요시
            @ModelAttribute MenuModDto modDto,
//            @ModelAttribute MenuRoleModDto menuRoleModDto,  // required false
            HttpServletRequest request,
            Model model ) throws IOException {
    	
    	// S : 필요한 객체 setting
    	
    	// E : 필요한 객체 setting
        
        // update 구현
    	menuService.update( modDto, request );
    	//	menuService.update( modDto, menuRoleModDto, request );
        
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
        menuService.deleteAllById( pk );		// By 뒤에는 custom
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 삭제되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/list" );
        
        return "common/alert";
    }

    @ResponseBody
    @GetMapping("/live/getMenuListAjax")
    public Map<String, Object> getMenuListAjax( @ModelAttribute MenuListDto listDto ) {
        // 해당 권한에 맞는 menuList 가져온 뒤 drawMenuTree 로 메뉴를 그린다.
        Map<String, Object> result = new HashMap<String, Object>();
        
        // 메뉴 list 출력
        result.put( "resultList", menuService.getList( listDto ) );
        
    	return result;
    }
    
    @ResponseBody
    @GetMapping("/live/getPossibleRoleListAjax")
    public Map<String, Object> getPossibleRoleListAjax( @ModelAttribute MenuListDto listDto ) {
    	// 메뉴 수정 시 체크로직
    	// 해당 메뉴가 다른 권한에도 연동할 수 있도록
        // (1) 해당 메뉴가 존재하는 권한 리스트 ( 이미 다른 권한에 있으면 '체크' 상태로 하기 위하여)
    	// (2) 해당 메뉴의 부모메뉴가 존재하는 권한 리스트
       
    	Map<String, Object> result = new HashMap<String, Object>();
        
        // (1) 해당 메뉴가 존재하는 권한 리스트 ( 이미 다른 권한에 있으면 '체크' 상태로 하기 위하여)
    	List<RoleListDto> checkedRoleList = menuService.getPossibleRoleListAjax( listDto );
    	result.put( "checkedRoleList", checkedRoleList );
        
        // (2) 해당 메뉴의 부모메뉴가 존재하는 권한 리스트 
        listDto.setMenuSn( listDto.getUpperMenuSn() );
        
        List<RoleListDto> possibleRoleList = menuService.getPossibleRoleListAjax( listDto );
        result.put( "possibleRoleList", possibleRoleList );
    	
    	return result;
    }
    
    @ResponseBody
    @GetMapping("/admin/live/menu/updateMenuAjax")
    public Map<String, Object> updateMenuAjax( 
    		@ModelAttribute MenuModDto modDto,
    		HttpServletRequest request,
    		Model model
    		) throws IOException {
    	
    	// 메뉴 수정 (ajax)
    	Map<String, Object> result = new HashMap<String, Object>();
    	
    	// update 구현
    	menuService.update( modDto, request );
    	
    	// 메시지 출력 및 url 이동 처리 (ajax)
    	result.put( "resultMsg", "정상적으로 수정되었습니다." );
    	return result;
    }
    

}
