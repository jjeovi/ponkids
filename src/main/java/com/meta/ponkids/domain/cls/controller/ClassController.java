package com.meta.ponkids.domain.cls.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.meta.ponkids.domain.cls.dto.ClassCategoryCl02ListDto;
import com.meta.ponkids.domain.cls.dto.ClassListDto;
import com.meta.ponkids.domain.cls.dto.ClassModDto;
import com.meta.ponkids.domain.cls.service.ClassCategoryCl01Service;
import com.meta.ponkids.domain.cls.service.ClassCategoryCl02Service;
import com.meta.ponkids.domain.cls.service.ClassDetailService;
import com.meta.ponkids.domain.cls.service.ClassService;
import com.meta.ponkids.domain.cls.service.ClassWeekService;
import com.meta.ponkids.domain.system.cmmnCd.service.CmmnCdDetailService;
import com.meta.ponkids.domain.system.file.entity.AtchFileDetail;
import com.meta.ponkids.domain.system.file.repository.AtchFileDetailRepository;
import com.meta.ponkids.domain.system.file.service.AtchFileDetailService;
import com.meta.ponkids.domain.system.file.service.AtchFileService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class ClassController {
	
    
    private final static String BASIC_PATH = "/class";
    private final static String BASIC_DIR_PATH = "/pon/cls";
    
    private final ClassService classService;
    private final ClassWeekService classWeekService;
    private final ClassDetailService classDetailService;
    private final ClassCategoryCl01Service classCategoryCl01Service;
    private final ClassCategoryCl02Service classCategoryCl02Service;
    
    private final CmmnCdDetailService cmmnCdDetailService;
    
    private final AtchFileService atchFileService;
    private final AtchFileDetailService atchFileDetailService;
    private final AtchFileDetailRepository atchFileDetailRepository;
    
    

    @GetMapping( BASIC_PATH + "/{mcd}/list" )
    public String list( @ModelAttribute ClassListDto listDto,
                        @PageableDefault( size = 8 ) Pageable pageable,
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
        
//        if ( listDto.getCategory() != null && listDto.getCategory().getLv1Sn() != null ) {
//            
//            ClassCategoryCl02ListDto categoryCl02ListDto = new ClassCategoryCl02ListDto();
//            
//            // 부모clSn 값 setting ( ajax의 categorySn 을 대입해준다.)
//            categoryCl02ListDto.setParntsClSn( listDto.getCategory().getLv1Sn() );
//            
//            model.addAttribute( "cateLv2List", classCategoryCl02Service.findAllByOrderByParntsClSnAscClSeqAsc() );
//        }
        model.addAttribute( "cateLv2List", classCategoryCl02Service.findAllByOrderByParntsClSnAscClSeqAsc() );
        
        // E : 필요한 객체 setting
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return BASIC_DIR_PATH + "/list";
    }
	
    
    
    
    @GetMapping( BASIC_PATH + "/{mcd}/detail" )
    public String detail( 
    		@RequestParam( required = true ) Long pk,    // 타입 체크
            @PathVariable String mcd,
            HttpServletRequest request,
            Model model ) {
    	
// S : 필요한 객체 setting
        
        // target object 조회
        ClassModDto targetDto = classService.findById( pk );
        model.addAttribute( "targetDto", targetDto );
        
        // 요일 List add
        model.addAttribute( "day7List", cmmnCdDetailService.getList( "DAY_7_CD" ) );    // 요일리스트
        
        // 클래스 카테고리 list setting
        model.addAttribute( "ctgrySnList", classCategoryCl01Service.findAll() );
        
        // 클래스 커리큘럼 list setting ( targetDto 의 ctgrySn 값으로 커리큘럼 list 를 구함. )
        model.addAttribute( "crseSnList", classCategoryCl02Service.findByParntsClSnOrderByClSeq( targetDto.getCtgrySn() ) );
        
        // 클래스 요일 List add
        // 클래스 요일 은 html 그리고 script로 ajax를 통해 불러온다. 처음에 불러오면 타임리프로 요일을 체크하는 로직으로는 타임리프의 값을 script에서 읽는데 한계가 있기 때문에
        // html먼저 그린 뒤 ajax를 호출 하는 방식으로 정함. -> /live/getClassWeekListAjax 에서 구현
        // model.addAttribute( "classWeekList",  classWeekService.findByClassSnOrderByClassWeekSn( targetDto.getClassSn() ));
        
        // chldrn target object 조회
        model.addAttribute( "targetClsDtlList", classDetailService.findByClassSnOrderByClassDetailSeq( targetDto.getClassSn() ) );
        
        // 클래스sn model 에 추가
        model.addAttribute( "schClassSn", targetDto.getClassSn() );
        
        
        // 첨부파일 존재시
        if ( targetDto.getAtchFileSn() != null ) {
            List<AtchFileDetail> atchFileList = atchFileDetailService.getList( targetDto.getAtchFileSn() );
            model.addAttribute( "atchFileList", atchFileList );
        }
        
        // E : 필요한 객체 setting
    	
    	// 기본 경로 setting
    	model.addAttribute( "basicPath", BASIC_PATH );
    	
    	return BASIC_DIR_PATH + "/detail";
    }
    
	
	

}
