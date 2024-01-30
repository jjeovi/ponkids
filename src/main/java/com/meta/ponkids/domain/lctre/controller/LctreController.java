package com.meta.ponkids.domain.lctre.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meta.ponkids.domain.cls.service.ClassCategoryCl01Service;
import com.meta.ponkids.domain.cls.service.ClassCategoryCl02Service;
import com.meta.ponkids.domain.cls.service.ClassService;
import com.meta.ponkids.domain.cls.service.ClassWeekService;
import com.meta.ponkids.domain.lctre.dto.LctreListDto;
import com.meta.ponkids.domain.lctre.service.LctreService;
import com.meta.ponkids.domain.system.cmmnCd.service.CmmnCdDetailService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LctreController {
    
    private final LctreService lctreService;
    
    private final ClassService classService;
    private final ClassWeekService classWeekService;
    private final ClassCategoryCl01Service classCategoryCl01Service;
    private final ClassCategoryCl02Service classCategoryCl02Service;
    
    private final CmmnCdDetailService cmmnCdDetailService;
    
    private final static String BASIC_PATH = "/lctre";
    
    // 수업리스트 검색 ( classSn, ClassDayCd : 클래스sn과 , 요일로 검색 ) 
    @ResponseBody
    @GetMapping( BASIC_PATH + "/live/getLctreListByClassSnAndClassDayCdAjax" )
    public Map<String, Object> getLctreListByClassSnAndClassDayCdAjax( @ModelAttribute LctreListDto listDto
    ) {
        
        Map<String, Object> result = new HashMap<String, Object>();
        
        result.put( "resultList", lctreService.findByClassSnAndClassDayCdAjax( listDto ) );   // 클래스 요일 classSn으로 검색
        
        return result;
    }
    
    
}
