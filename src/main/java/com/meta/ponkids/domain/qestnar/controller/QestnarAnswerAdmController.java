package com.meta.ponkids.domain.qestnar.controller;

import java.io.IOException;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.meta.ponkids.domain.qestnar.dto.QestnarAnswerListDto;
import com.meta.ponkids.domain.qestnar.dto.QestnarAnswerModDto;
import com.meta.ponkids.domain.qestnar.dto.QestnarGroupListDto;
import com.meta.ponkids.domain.qestnar.service.QestnarAnswerDetailService;
import com.meta.ponkids.domain.qestnar.service.QestnarAnswerReplyService;
import com.meta.ponkids.domain.qestnar.service.QestnarAnswerService;
import com.meta.ponkids.domain.qestnar.service.QestnarGroupService;
import com.meta.ponkids.domain.qestnar.service.QestnarQestnDetailService;
import com.meta.ponkids.domain.qestnar.service.QestnarQestnService;
import com.meta.ponkids.domain.system.cmmnCd.service.CmmnCdDetailService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class QestnarAnswerAdmController {
	
	private final QestnarAnswerService qestnarAnswerService;
	private final QestnarAnswerDetailService qestnarAnswerDetailService;
	private final QestnarAnswerReplyService qestnarAnswerReplyService;
	private final QestnarGroupService qestnarGroupService;
	private final QestnarQestnService qestnarQestnService;
	private final QestnarQestnDetailService qestnarQestnDetailService;
	
	private final CmmnCdDetailService cmmnCdDetailService;
	
	private final static String BASIC_VIEW_PATH = "admin/qestnarAnswer";
	private final static String BASIC_PATH = "/" + BASIC_VIEW_PATH;	// BASIC_VIEW_PATH 는  앞의 "/" 를 제거해야 함.
	
    @GetMapping( value = { BASIC_PATH + "/{mcd}/list",
            BASIC_PATH + "/{mcd}/{qestnarAnswerSn}/list" } )
    public String list( @ModelAttribute QestnarAnswerListDto listDto,
    					@PathVariable String mcd,
                        @PageableDefault( size = 10 ) Pageable pageable,
                        Model model ) {
    	
    	// S : 필요한 객체 setting
    	
    	// 목록 조회
        Page<QestnarAnswerListDto> resultList = qestnarAnswerService.getList( listDto, pageable );
        model.addAttribute( "resultList", resultList );
        
        // 검색 dto setting
        model.addAttribute( "searchDTO", listDto );
        
        // 카테고리 리스트 ( lv1 )
        // 클래스 카테고리 분류1 list setting
        model.addAttribute( "cateLv1List", qestnarGroupService.getList( new QestnarGroupListDto() ) );
    	
    	// E : 필요한 객체 setting
        
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return BASIC_VIEW_PATH + "/list";
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
    	model.addAttribute( "targetDto", qestnarAnswerService.findById( pk ) );
    	
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
            @ModelAttribute QestnarAnswerModDto modDto,
//            @ModelAttribute QestnarAnswerRoleModDto qestnarAnswerRoleModDto,  // required false
            @PathVariable String mcd,
            HttpServletRequest request,
            Model model ) throws IOException {
    	
    	// S : 필요한 객체 setting
    	
    	// E : 필요한 객체 setting
        
        
        // update 구현
    	qestnarAnswerService.update( modDto, request );
//        qestnarAnswerService.update( modDto, qestnarAnswerRoleModDto, request );
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 수정되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
        
        return "common/alert";
    }
    
    
//    @Transactional
//    @PostMapping( BASIC_PATH + "/{mcd}/delete" )
//    public String delete(
//            @RequestParam( required = true ) Long pk,
//            @PathVariable String mcd,
//            Model model ) {
//        
//        // 삭제 처리
//        qestnarAnswerService.deleteAllById( pk );		// By 뒤에는 custom
//        
//        // 메시지 출력 및 url 이동 처리
//        model.addAttribute( "resultMsg", "정상적으로 삭제되었습니다." );
//        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
//        
//        return "common/alert";
//    }
    

	// 상세 조회 Ajax
    @ResponseBody
    @GetMapping( BASIC_PATH + "/live/detailAjax" )
    public Map<String, Object> detailAjax( @ModelAttribute QestnarAnswerListDto listDto ) {
        // 해당 권한에 맞는 menuList 가져온 뒤 drawMenuTree 로 메뉴를 그린다.
        Map<String, Object> result = new HashMap<String, Object>();
        
        // 설문조사 그룹 조회
        
        // 설문조사 질문 목록 조회
        // target object 조회\
    	QestnarGroupListDto targetDto = qestnarGroupService.findByQestnarGroupCd( listDto.getQestnarGroupCd() );
    	
    	if ( targetDto == null ) {
    		result.put("flag", "E");
    		result.put("msg", "설문 정보가 존재하지 않습니다. 다시 시도해주세요.");
    		return result;
    	}
    	
    	result.put( "targetQestnarGroupDto", targetDto );
    	
        // 설문조사 질문 list
    	result.put( "targetQestnarQestnList", qestnarQestnService.findByQestnarGroupSn( targetDto.getQestnarGroupSn() ));
    	
    	// 설문조사 질문 상세 list
    	result.put( "targetQestnarQestnDetailList", qestnarQestnDetailService.getListForQestnarAnswerDetail( targetDto.getQestnarGroupSn(), listDto.getQestnarAnswerSn() ));
    	
        // 설문조사 질문 항목 유형 코드 리스트 
    	result.put( "qestnarQestnItemTyCdList", cmmnCdDetailService.getList( "QESTNAR_QESTN_ITEM_TY_CD" ) );   // 설문조사 질문 항목 유형 코드 리스트 
        
        
        // 설문조사 작성답안 조회 
        result.put( "targetDto", qestnarAnswerService.findById( listDto.getQestnarAnswerSn() ) );
        
        // 설문조사 작성답안 상세 조회 : 주관식 답변만 가져옴.
        result.put( "targetQestnarAnswerDetailList", qestnarAnswerDetailService.getListByQestnarAnswerSn( listDto.getQestnarAnswerSn() ) );
        
        // 설문조사 답변 (reply) 조회
        result.put( "targetQestnarAnswerReplyList", qestnarAnswerReplyService.getListByQestnarAnswerSn( listDto.getQestnarAnswerSn() ) );
        
        result.put("flag", "S");
        return result;
    }
	

}
