package com.meta.ponkids.domain.cls.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.meta.ponkids.domain.cls.dto.ClassCategoryCl02ListDto;
import com.meta.ponkids.domain.cls.dto.ClassListDto;
import com.meta.ponkids.domain.cls.service.ClassCategoryCl01Service;
import com.meta.ponkids.domain.cls.service.ClassCategoryCl02Service;
import com.meta.ponkids.domain.cls.service.ClassDetailService;
import com.meta.ponkids.domain.cls.service.ClassService;
import com.meta.ponkids.domain.cls.service.ClassWeekService;
import com.meta.ponkids.domain.system.cmmnCd.service.CmmnCdDetailService;
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
    public String detail( @ModelAttribute ClassListDto listDto,
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
    	
    	if ( listDto.getCategory() != null && listDto.getCategory().getLv1Sn() != null ) {
    		
    		ClassCategoryCl02ListDto categoryCl02ListDto = new ClassCategoryCl02ListDto();
    		
    		// 부모clSn 값 setting ( ajax의 categorySn 을 대입해준다.)
    		categoryCl02ListDto.setParntsClSn( listDto.getCategory().getLv1Sn() );
    		
    		model.addAttribute( "cateLv2List", classCategoryCl02Service.findByParntsClSnOrderByClSeq( categoryCl02ListDto.getParntsClSn() ) );
    	}
    	
    	// E : 필요한 객체 setting
    	
    	// 기본 경로 setting
    	model.addAttribute( "basicPath", BASIC_PATH );
    	
    	return BASIC_DIR_PATH + "/detail";
    }
    
	
	

}
