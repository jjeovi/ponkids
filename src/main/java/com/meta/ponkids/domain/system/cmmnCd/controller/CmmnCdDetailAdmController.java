package com.meta.ponkids.domain.system.cmmnCd.controller;

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

import com.meta.ponkids.domain.system.cmmnCd.dto.CmmnCdDetailListDto;
import com.meta.ponkids.domain.system.cmmnCd.dto.CmmnCdDetailModDto;
import com.meta.ponkids.domain.system.cmmnCd.dto.CmmnCdDetailSaveDto;
import com.meta.ponkids.domain.system.cmmnCd.dto.CmmnCdModDto;
import com.meta.ponkids.domain.system.cmmnCd.service.CmmnCdDetailService;
import com.meta.ponkids.domain.system.cmmnCd.service.CmmnCdService;
import com.meta.ponkids.global.common.dto.CategoryDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CmmnCdDetailAdmController {
    
    private final static String BASIC_PATH = "/admin/cmmnCdDetail";
    private final CmmnCdDetailService cmmnCdDetailService;
    private final CmmnCdService cmmnCdService;
    
    @GetMapping( BASIC_PATH + "/{mcd}/{cdSn}/list" )
    public String list( @ModelAttribute CmmnCdDetailListDto listDto,
                        @PageableDefault( size = 10 ) Pageable pageable,
                        @PathVariable String mcd,
                        @PathVariable Long cdSn,
                        Model model ) {
        
        // S : 필요한 객체 setting
    	
    	// cdSn setting
        if ( cdSn != 0 && cdSn != null ) {
            
            // cdSn Setting
        	// cdSn 으로 cdNm 구하기
        	CmmnCdModDto cmmnCdDto  = cmmnCdService.findById(cdSn);
        	
        	if ( cmmnCdDto != null && cmmnCdDto.getCdNm() != null ) {
        		listDto.setCdNm(cmmnCdDto.getCdNm());
        		// category set
        		categorySet( listDto, cdSn );
        	}
        } else if ( cdSn == 0 ) { 
        	
    		// category set
    		categorySet( listDto, cdSn );
        	
        	
        } else {
        	// 메시지 출력 및 url 이동 처리
            model.addAttribute( "resultMsg", "url의 부모코드 일련번호를 다시 확인해주세요." );
            model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/0/list" );
            return "common/alert";
        }
        
        // 목록 조회
        Page<CmmnCdDetailListDto> resultList = cmmnCdDetailService.getList( listDto, pageable );
        model.addAttribute( "resultList", resultList );
        
        // 검색 dto setting
        model.addAttribute( "searchDTO", listDto );
        
        // 카테고리 리스트 ( lv1 )
        // 카테고리 분류1 list setting
        model.addAttribute( "cateLv1List", cmmnCdService.findAll() );
        
        // E : 필요한 객체 setting
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        // 부모 sn 값 setting
        model.addAttribute( "cdSn", cdSn );
        
        return BASIC_PATH + "/list";
    }
    
    @GetMapping( BASIC_PATH + "/{mcd}/{cdSn}/regist" )
    public String regist( 
    		@PathVariable String mcd,
            @PathVariable Long cdSn,
    		Model model ) {
        
        // S : 필요한 객체 setting
        
        // 가입 object 생성
        model.addAttribute( new CmmnCdDetailSaveDto() );
        
        // 부모 코드 값 가져오기
        CmmnCdModDto cmmnCdModDto = cmmnCdService.findById( cdSn );
        
        // 부모 코드 값 ( sn, nm ) model 에 setting
        if ( cmmnCdModDto != null ) {
            model.addAttribute( "cdSn", cdSn );
            model.addAttribute( "cdNm", cmmnCdModDto.getCdNm() );
            model.addAttribute( "cdDc", cmmnCdModDto.getCdDc() );
            
        } else {
            // TODO : return error
        }
        
        // E : 필요한 객체 setting
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return BASIC_PATH + "/regist";
    }
    
    @Transactional
    @PostMapping( BASIC_PATH + "/{mcd}/{cdSn}/insert" )
    public String insert(
            @ModelAttribute CmmnCdDetailSaveDto saveDto,
//            @ModelAttribute CmmnCdDetailRoleSaveDto cmmnCdDetailRoleSaveDto,  // required false
            @PathVariable String mcd,
            @PathVariable Long cdSn,
            HttpServletRequest request,
            Model model ) throws IOException {
        
        // S : 필요한 객체 setting
        
        // E : 필요한 객체 setting
        
        // 등록 처리
        
        // save
        cmmnCdDetailService.save( saveDto, request );
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 등록되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/" + cdSn + "/list" );
        
        return "common/alert";
    }
    
    @GetMapping( value = {
            BASIC_PATH + "/{mcd}/{cdSn}/detail",
            BASIC_PATH + "/{mcd}/{cdSn}/modify" } )
    public String detailOrModify(
            @RequestParam( required = true ) Long pk,    // 타입 체크
            @PathVariable String mcd,
            @PathVariable Long cdSn,
            Model model,
            HttpServletRequest request ) {
        
        // S : 필요한 객체 setting
        
        // target object 조회
    	CmmnCdDetailModDto targetDto = cmmnCdDetailService.findById( pk );
        model.addAttribute( "targetDto", targetDto );
        
        // targetDto 의 cdNm 값으로 조회한 부모코드의 cdSn 값이 url로 들어온 cdSn 값과 같은지 비교 ( 굳이 의미없긴 하지만 검증작업 )
        if(targetDto != null && targetDto.getCdNm() != null ) {
        	CmmnCdModDto compareDto = cmmnCdService.findByCdNm( targetDto.getCdNm() );
        	if( !( compareDto != null && compareDto.getCdSn() != null && cdSn.equals(compareDto.getCdSn()) ) ) {
        		
                // 메시지 출력 및 url 이동 처리
                model.addAttribute( "resultMsg", "url의 부모코드 일련번호를 확인해주세요." );
                model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/0/list" );
                return "common/alert";
        	}
        }
        
        // 부모 코드 값 가져오기
        if ( cdSn == 0 && targetDto != null && targetDto.getCdNm() != null ) {
        	
            CmmnCdModDto cmmnCdModDto = cmmnCdService.findByCdNm( targetDto.getCdNm() );
            
            // 부모 코드 값 ( sn, nm ) model 에 setting
            if ( cmmnCdModDto != null ) {
                model.addAttribute( "cdSn", cmmnCdModDto.getCdSn() );
                model.addAttribute( "cdNm", cmmnCdModDto.getCdNm() );
                model.addAttribute( "cdDc", cmmnCdModDto.getCdDc() );
                
            } else {
                // TODO : return error
            }
            
        } else {
        	CmmnCdModDto cmmnCdModDto = cmmnCdService.findById( cdSn );
            
            // 부모 코드 값 ( sn, nm ) model 에 setting
            if ( cmmnCdModDto != null ) {
                model.addAttribute( "cdSn", cmmnCdModDto.getCdSn() );
                model.addAttribute( "cdNm", cmmnCdModDto.getCdNm() );
                model.addAttribute( "cdDc", cmmnCdModDto.getCdDc() );
                
            } else {
                // TODO : return error
            }
        }
        
        // E : 필요한 객체 setting
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        // 부모 sn 값 setting
        model.addAttribute( "cdSn", cdSn );
        
        String urlPath = request.getServletPath();
        String remainPath = "";
        if ( urlPath.split( BASIC_PATH )[ 1 ].endsWith( "/detail" ) ) remainPath = "detail";
        if ( urlPath.split( BASIC_PATH )[ 1 ].endsWith( "/modify" ) ) remainPath = "modify";
        
        return BASIC_PATH + "/" + remainPath;
    }
    
    @Transactional
    @PostMapping( BASIC_PATH + "/{mcd}/{cdSn}/update" )
    public String update(
            @ModelAttribute CmmnCdDetailModDto modDto,
            @PathVariable String mcd,
            @PathVariable Long cdSn,
            HttpServletRequest request,
            Model model ) throws IOException {
        
        // S : 필요한 객체 setting
        
        // E : 필요한 객체 setting
        
        // update 구현
        cmmnCdDetailService.update( modDto, request );
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 수정되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/" + cdSn + "/list" );
        
        return "common/alert";
    }
    
    
    @Transactional
    @PostMapping( BASIC_PATH + "/{mcd}/{cdSn}/delete" )
    public String delete(
            @RequestParam( required = true ) Long pk,
            @PathVariable String mcd,
            @PathVariable Long cdSn,
            Model model ) {
        
        // 삭제 처리
        cmmnCdDetailService.deleteAllById( pk );        // By 뒤에는 custom
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 삭제되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/" + cdSn + "/list" );
        
        return "common/alert";
    }
    
    
    
    // ========================= Util method =========================
    // ========================= Util method =========================
    // ========================= Util method =========================
    
    // 카테고리 setting method
    public void categorySet( CmmnCdDetailListDto listDto, Long cdSn ) {
        
        // 전체 검색 일 경우 전체로 setting
        if ( cdSn == 0 ) {
            if ( listDto.getCategory() != null ) {
                
                listDto.getCategory().setLv1Sn( cdSn );
                listDto.getCategory().setLv1Nm( "전체" );
            } else {
                CategoryDto categoryDto = new CategoryDto();
                
                categoryDto.setLv1Sn( cdSn );
                categoryDto.setLv1Nm( "전체" );
                listDto.setCategory( categoryDto );
            }
        }
        
        CmmnCdModDto cmmnCdModDto = cmmnCdService.findById(cdSn);
        
        if ( cmmnCdModDto != null ) {
            
            if ( listDto.getCategory() != null ) {
                
                listDto.getCategory().setLv1Sn( cdSn );
                listDto.getCategory().setLv1Nm( cmmnCdModDto.getCdDc() );
            } else {
                CategoryDto categoryDto = new CategoryDto();
                
                categoryDto.setLv1Sn( cdSn );
                categoryDto.setLv1Nm( cmmnCdModDto.getCdDc() );
                listDto.setCategory( categoryDto );
            }
        }
    }
    
    
}
