package com.meta.ponkids.domain.system.banner.controller;


import com.meta.ponkids.domain.system.banner.dto.BannerListDto;
import com.meta.ponkids.domain.system.banner.service.BannerService;
import com.meta.ponkids.domain.system.cmmnCd.service.CmmnCdDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BannerController {
    
    private final static String BASIC_VIEW_PATH = "banner";
    private final static String BASIC_PATH = "/" + BASIC_VIEW_PATH;	// BASIC_VIEW_PATH 는  앞의 "/" 를 제거해야 함.
    
    private final static String BANNER_MAIN_TOP = "MAIN_TOP";
    private final static String BANNER_MAIN_CLASS = "MAIN_CLASS";
    
    private final BannerService bannerService;
    
    private final CmmnCdDetailService cmmnCdDetailService;
    
    
    @ResponseBody
    @GetMapping( BASIC_PATH + "/live/getMainListAjax" )
    public Map<String, Object> getPonBannerListAjax() {
        // 사용자 배너 리스트 가져오기
        Map<String, Object> result = new HashMap<String, Object>();
        
        // 배너 메인 상단 영역 리스트 조회
        List<BannerListDto> bannerMainTopList = bannerService.getMainList( BANNER_MAIN_TOP );
        result.put( "bannerMainTopList", bannerMainTopList );
        
        // 배너 메인 클래스 영역 리스트 조회
        List<BannerListDto> bannerMainClassList = bannerService.getMainList( BANNER_MAIN_CLASS );
        result.put( "bannerMainClassList", bannerMainTopList );
        
        return result;
    }
    
}
