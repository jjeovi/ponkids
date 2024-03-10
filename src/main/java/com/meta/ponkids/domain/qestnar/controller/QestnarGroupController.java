package com.meta.ponkids.domain.qestnar.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meta.ponkids.domain.qestnar.dto.QestnarGroupListDto;
import com.meta.ponkids.domain.qestnar.dto.QestnarQestnDetailListDto;
import com.meta.ponkids.domain.qestnar.service.QestnarGroupService;
import com.meta.ponkids.domain.qestnar.service.QestnarQestnDetailService;
import com.meta.ponkids.domain.qestnar.service.QestnarQestnService;
import com.meta.ponkids.domain.system.cmmnCd.service.CmmnCdDetailService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class QestnarGroupController {
	
	private final QestnarGroupService qestnarGroupService;
	private final QestnarQestnService qestnarQestnService;
	private final QestnarQestnDetailService qestnarQestnDetailService;
	
	private final CmmnCdDetailService cmmnCdDetailService;
	
	private final static String BASIC_VIEW_PATH = "qestnarGroup";
	private final static String BASIC_PATH = "/" + BASIC_VIEW_PATH;	// BASIC_VIEW_PATH 는  앞의 "/" 를 제거해야 함.
	
	
    // 코드명으로 설문조사 그룹 조회 
    @ResponseBody
    @GetMapping( BASIC_PATH + "/live/getByQestnarGroupCdAjax" )
    public Map<String, Object> getByQestnarGroupCdAjax( @RequestParam( "qestnarGroupCd" ) String qestnarGroupCd
    ) {
        
        Map<String, Object> result = new HashMap<String, Object>();
        
        // 설문조사 그룹 조회 process 
        // ==================================================
        // 1. 설문조사 그룹 조회 : targetDto : TB_QESTNAR_GROUP 에서 qestnarGroupCd 로 조회 => 
        // 2. 설문조사 질문 조회 : targetQestnarQestnList : 설문조사 질문 list : TB_QESTNAR_QESTN 에서 1에서 조회한 qestnarGroupSn값으로 조회 
        // 3. 설문조사 질문 상세 (선택지) 조회 : targetQestnarQestnDetailList : 설문조사 질문 상세 list(선택형 문항중 '선택지'에 해당하는 list) : TB_QESTNAR_QESTN_DETAIL 에서 1에서 조회한 qestnarGroupSn값으로 조회 ) 
        
        // target object 조회\
    	QestnarGroupListDto targetDto = qestnarGroupService.findByQestnarGroupCd( qestnarGroupCd );
    	if ( targetDto == null ) {
    		result.put("flag", "E");
    		result.put("msg", "설문 정보가 존재하지 않습니다. 다시 시도해주세요.");
    		return result;
    	}
    	result.put( "targetDto", targetDto );
    	
        // 설문조사 질문 list
    	result.put("targetQestnarQestnList", qestnarQestnService.findByQestnarGroupSn( targetDto.getQestnarGroupSn() ));
    	
    	// 설문조사 질문 상세 list
    	result.put("targetQestnarQestnDetailList", qestnarQestnDetailService.getListByQestnarGroupSnOrderByQestnarQestnSnAsc( targetDto.getQestnarGroupSn() ));
    	
        // 설문조사 질문 항목 유형 코드 리스트 
    	result.put( "qestnarQestnItemTyCdList", cmmnCdDetailService.getList( "QESTNAR_QESTN_ITEM_TY_CD" ) );   // 설문조사 질문 항목 유형 코드 리스트 
        
        
    	result.put("flag", "S");
        return result;
    }
	

}
