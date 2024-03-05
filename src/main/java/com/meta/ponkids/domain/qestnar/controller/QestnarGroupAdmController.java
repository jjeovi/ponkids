package com.meta.ponkids.domain.qestnar.controller;

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

import com.meta.ponkids.domain.qestnar.dto.QestnarGroupListDto;
import com.meta.ponkids.domain.qestnar.dto.QestnarGroupModDto;
import com.meta.ponkids.domain.qestnar.dto.QestnarGroupSaveDto;
import com.meta.ponkids.domain.qestnar.service.QestnarGroupService;
import com.meta.ponkids.domain.qestnar.service.QestnarQestnDetailService;
import com.meta.ponkids.domain.qestnar.service.QestnarQestnService;
import com.meta.ponkids.domain.system.cmmnCd.service.CmmnCdDetailService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class QestnarGroupAdmController {
	
	private final QestnarGroupService qestnarGroupService;
	private final QestnarQestnService qestnarQestnService;
	private final QestnarQestnDetailService qestnarQestnDetailService;
	
	private final CmmnCdDetailService cmmnCdDetailService;
	
	private final static String BASIC_VIEW_PATH = "admin/qestnarGroup";
	private final static String BASIC_PATH = "/" + BASIC_VIEW_PATH;	// BASIC_VIEW_PATH 는  앞의 "/" 를 제거해야 함.
	
	
    @GetMapping( BASIC_PATH + "/{mcd}/list" )
    public String list( @ModelAttribute QestnarGroupListDto listDto,
    					@PathVariable String mcd,
                        @PageableDefault( size = 10 ) Pageable pageable,
                        Model model ) {
    	
    	// S : 필요한 객체 setting
    	
    	// 목록 조회
        Page<QestnarGroupListDto> resultList = qestnarGroupService.getList( listDto, pageable );
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
        model.addAttribute( new QestnarGroupSaveDto() );
        
        
        // 설문조사 질문 항목 유형 코드 리스트 
        model.addAttribute( "qestnarQestnItemTyCdList", cmmnCdDetailService.getList( "QESTNAR_QESTN_ITEM_TY_CD" ) );   // 클래 상세 항목 유형 코드 리스트
    	
    	// E : 필요한 객체 setting
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return BASIC_VIEW_PATH + "/regist";
    }
    
    @Transactional
    @PostMapping( BASIC_PATH + "/{mcd}/insert" )
    public String insert (
            @ModelAttribute QestnarGroupSaveDto saveDto,
//            @ModelAttribute QestnarGroupRoleSaveDto qestnarGroupRoleSaveDto,  // required false
            @PathVariable String mcd,
            HttpServletRequest request,
            Model model ) throws IOException {
    	
    	// S : 필요한 객체 setting
    	
    	// E : 필요한 객체 setting
        
        // 등록 처리
        
    	// 클래스 저장
        try {
            saveDto = qestnarGroupService.save( saveDto, request );
        } catch ( Exception e ) {
            // 메시지 출력 및 url 이동 처리
            model.addAttribute( "resultMsg", "등록 중 오류가 발생했습니다. " + e.getMessage() + "\n다시 시도해주세요." );
            model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
            
            return "common/alert";
        }
        
        // 입력항목 존재하면 등록
        if ( saveDto != null && saveDto.getQestnarQestns() != null && saveDto.getQestnarQestns().size() != 0 ) {
        	qestnarQestnService.save( saveDto, request );
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
            @RequestParam( required = true ) Long pk,	// 타입 체크
            @PathVariable String mcd,
            HttpServletRequest request,
            Model model ) {
    	
    	// S : 필요한 객체 setting
    	
    	// target object 조회\
    	QestnarGroupModDto targetDto = qestnarGroupService.findById( pk );
    	model.addAttribute( "targetDto", targetDto );
    	
        // 설문조사 질문 list
    	model.addAttribute("targetQestnarQestnList", qestnarQestnService.findByQestnarGroupSn( targetDto.getQestnarGroupSn() ));
    	
    	// 설문조사 질문 상세 list
    	model.addAttribute("targetQestnarQestnDetailList", qestnarQestnDetailService.getListByQestnarGroupSnOrderByQestnarQestnSnAsc( targetDto.getQestnarGroupSn() ));
    	
        // 설문조사 질문 항목 유형 코드 리스트 
        model.addAttribute( "qestnarQestnItemTyCdList", cmmnCdDetailService.getList( "QESTNAR_QESTN_ITEM_TY_CD" ) );   // 클래 상세 항목 유형 코드 리스트
        
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
            @ModelAttribute QestnarGroupModDto modDto,
            @PathVariable String mcd,
            HttpServletRequest request,
            Model model ) throws IOException {
    	
    	// S : 필요한 객체 setting
    	
    	// E : 필요한 객체 setting
        
        
        // update 구현
    	try {
            // update 구현
    		qestnarGroupService.update( modDto, request );
        } catch ( Exception e ) {
            // 메시지 출력 및 url 이동 처리
            model.addAttribute( "resultMsg", "수정 중 오류가 발생했습니다. " + e.getMessage() + "\n다시 시도해주세요." );
            model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
            
            return "common/alert";
        }
    	
    	
    	 // 입력항목 update (입력항목 존재시) : 입력항목 전부 삭제 후 새로 save
    	qestnarQestnService.deleteAllByQestnarGroupSn( modDto.getQestnarGroupSn() );
    	
        if ( modDto != null && modDto.getQestnarQestns() != null && modDto.getQestnarQestns().size() != 0 ) {
            qestnarQestnService.save( modDto, request );
        }
        
    	
        
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
        qestnarGroupService.deleteAllById( pk );		// By 뒤에는 custom
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 삭제되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
        
        return "common/alert";
    }
	

}
