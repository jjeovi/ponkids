package com.meta.ponkids.domain.lctre.controller;

import com.meta.ponkids.domain.lctre.dto.LctreListDto;
import com.meta.ponkids.domain.lctre.dto.LctreReqstListDto;
import com.meta.ponkids.domain.lctre.repository.LctreReqstRepository;
import com.meta.ponkids.domain.lctre.service.LctreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class LctreController {
    
    private final LctreService lctreService;
    
    private final LctreReqstRepository lctreReqstRepository;
    
    private final static String BASIC_VIEW_PATH = "lctre";
    private final static String BASIC_PATH = "/" + BASIC_VIEW_PATH;    // BASIC_VIEW_PATH 는  앞의 "/" 를 제거해야 함.
    
    // 수업리스트 검색 ( classSn, ClassDayCd : 클래스sn과 , 요일로 검색 )
    @ResponseBody
    @GetMapping( BASIC_PATH + "/live/getLctreListByClassSnAndClassDayCdAjax" )
    public Map<String, Object> getLctreListByClassSnAndClassDayCdAjax( @ModelAttribute LctreListDto listDto
    ) {
        
        Map<String, Object> result = new HashMap<String, Object>();
        
        result.put( "resultList", lctreService.findByClassSnAndClassDayCdAjax( listDto ) );   // 클래스 요일 classSn으로 검색
        
        return result;
    }
    
    // 이미 신청한 수업이 있는지 확인
    // 수업 , 자녀로 검색
    @ResponseBody
    @GetMapping( "/lctreReqst/live/existsByLctreSnAndChldrnSnAjax" )
    public boolean existsByLctreSnAndChldrnSn( @ModelAttribute LctreReqstListDto listDto
    ) {
        return lctreReqstRepository.existsLctreReqstWithPaidStatus( listDto.getLctreSn(), listDto.getChldrnSn() );
    }
}
