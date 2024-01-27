package com.meta.ponkids.domain.system.banner.controller;

import com.meta.ponkids.domain.cls.dto.ClassListDto;
import com.meta.ponkids.domain.cls.service.ClassCategoryCl01Service;
import com.meta.ponkids.domain.system.banner.dto.BannerListDto;
import com.meta.ponkids.domain.system.banner.dto.BannerModDto;
import com.meta.ponkids.domain.system.banner.dto.BannerSaveDto;
import com.meta.ponkids.domain.system.banner.service.BannerService;
import com.meta.ponkids.domain.system.cmmnCd.dto.CmmnCdDetailListDto;
import com.meta.ponkids.domain.system.cmmnCd.dto.CmmnCdDetailModDto;
import com.meta.ponkids.domain.system.cmmnCd.service.CmmnCdDetailService;

import com.meta.ponkids.domain.system.file.service.AtchFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BannerAdmController {
    
    private final static String BASIC_PATH = "/admin/banner";
    private final BannerService bannerService;
    
    private final CmmnCdDetailService cmmnCdDetailService;

    private final ClassCategoryCl01Service classCategoryCl01Service;
    
    private final AtchFileService atchFileService;
    
    @GetMapping( BASIC_PATH + "/{mcd}/list" )
    public String list( @ModelAttribute BannerListDto listDto,
                        @PageableDefault( size = 10 ) Pageable pageable,
                        @PathVariable String mcd,
                        Model model ) {
        
        // S : 필요한 객체 setting
        
        // 목록 조회
        Page<BannerListDto> resultList = bannerService.getList( listDto, pageable );
        model.addAttribute( "resultList", resultList );
        
        // 검색 dto setting
        model.addAttribute( "searchDTO", listDto );
        
        // 카테고리 리스트 ( lv1 )
        // 배너 분류 코드 list ( 전체 )
        model.addAttribute( "cateLv1List", cmmnCdDetailService.getList( "BANNER_CL_CD" ) );   // 배너 분류 코드 리스트
        
        // E : 필요한 객체 setting
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return BASIC_PATH + "/list";
    }
    
    @GetMapping( BASIC_PATH + "/{mcd}/regist" )
    public String regist( @PathVariable String mcd,
                          Model model ) {
        
        // S : 필요한 객체 setting
    	
    	// 배너 분류 setting ( 메인상단배너, 메인클래스배너 )
    	model.addAttribute( "bannerClCdList", cmmnCdDetailService.getList( "BANNER_CL_CD" ) );   // 배너 분류 코드 리스트 
    	
        // 클래스 카테고리 분류1 list setting
        model.addAttribute( "classCategoryCl01List", classCategoryCl01Service.findAll() );
    	
        // 가입 object 생성
        model.addAttribute( new BannerSaveDto() );
        
        // E : 필요한 객체 setting
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return BASIC_PATH + "/regist";
    }
    
    @Transactional
    @PostMapping( BASIC_PATH + "/{mcd}/insert" )
    public String insert(
            @ModelAttribute BannerSaveDto saveDto,
            @PathVariable String mcd,
//            @ModelAttribute BannerRoleSaveDto bannerRoleSaveDto,  // required false
            @RequestParam( "thumbFile" ) MultipartFile thumbFile,
            HttpServletRequest request,
            Model model ) throws IOException {
        
        // S : 필요한 객체 setting
        
        
        // 썸네일 이미지 존재시 파일 저장
        if ( !thumbFile.isEmpty() ) {
            saveDto.setAtchFileSn( atchFileService.save( thumbFile ) );
        }
        
        // E : 필요한 객체 setting
        
        // 등록 처리
        
        // save
        try {
            bannerService.save( saveDto, request );
        }catch ( Exception e ) {
            
            // 메시지 출력 및 url 이동 처리
            model.addAttribute( "resultMsg", "등록 중 오류가 발생했습니다. " + e.getMessage() + "\n다시 시도해주세요." );
            model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
            
            return "common/alert";
            
        }
//        bannerService.save( saveDto, bannerRoleSaveDto, request );
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 등록되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
        
        return "common/alert";
    }
    
    @GetMapping( value = {
            BASIC_PATH + "/{mcd}/detail",
            BASIC_PATH + "/{mcd}/modify" } )
    public String detailOrModify(
            @RequestParam( required = true ) Long pk,    // 타입 체크
            @PathVariable String mcd,
            HttpServletRequest request,
            Model model ) {
        
        // S : 필요한 객체 setting
        
        // target object 조회
        model.addAttribute( "targetDto", bannerService.findById( pk ) );
        
        // E : 필요한 객체 setting
        
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        String urlPath = request.getServletPath();
        String remainPath = "";
        if ( urlPath.split( BASIC_PATH )[ 1 ].endsWith( "/detail" ) ) remainPath = "detail";
        if ( urlPath.split( BASIC_PATH )[ 1 ].endsWith( "/modify" ) ) remainPath = "modify";
        
        return BASIC_PATH + "/" + remainPath;
    }
    
    @Transactional
    @PostMapping( BASIC_PATH + "/{mcd}/update" )
    public String update(
            @RequestParam( "file" ) MultipartFile files,        // 첨부파일 필요시
            @PathVariable String mcd,
            @ModelAttribute BannerModDto modDto,
//            @ModelAttribute BannerRoleModDto bannerRoleModDto,  // required false
            HttpServletRequest request,
            Model model ) throws IOException {
        
        // S : 필요한 객체 setting
        
        // E : 필요한 객체 setting
        
        
        // update 구현
        bannerService.update( modDto, request );
//        bannerService.update( modDto, bannerRoleModDto, request );
        
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
        bannerService.deleteAllById( pk );        // By 뒤에는 custom
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 삭제되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
        
        return "common/alert";
    }
    
    
    // 배너 list 에서 카테고리 분류 클릭시 event (ajax)
    // 배너 분류 코드의 cdDetailSn 값으로 해당 코드 정보를 조회해 cdDetailVal2, cdDetailVal3 의 연계정보를 확인 후,
    // 있다면 연계코드의 리스트를 뿌리고,
    // 없으면 뿌리지 않음.
    @ResponseBody
    @GetMapping( BASIC_PATH + "/live/getBannerClDetailListByBannerClCdAjax" )
    public Map<String, Object> getBannerClDetailListByBannerClCdAjax(
            @ModelAttribute CmmnCdDetailListDto listDto
    ) {
        Map<String, Object> result = new HashMap<String, Object>();
        
//        result.put( "resultList", classService.getListByCrseSn( listDto ) );   // 커리큘럼 일련번호로 검색
        
        if (listDto.getCdDetailSn() == null ) {
            if (listDto.getCategory() != null && listDto.getCategory().getLv1Sn() != null ) {
                listDto.setCdDetailSn( listDto.getCategory().getLv1Sn() );
            } else {
                result.put("resultList", null ) ;
                return result;
            }
        }
        
        // cdDetailsn 값으로 코드 정보를 조회
        CmmnCdDetailModDto targetDto = cmmnCdDetailService.findById( listDto.getCdDetailSn() );
        
        if ( targetDto.getCdDetailVal2() != null && targetDto.getCdDetailVal2().equals("Y") ) {
            // 연계 여부 존재시
            
            // 연계 코드를 가져와 그 코드명의 리스트를 뿌립.
            if(targetDto.getCdDetailVal3() != null ) {
                // 연계 코드 는 cdDetailVal3 값에 존재함.
                result.put( "resultList", cmmnCdDetailService.getList( targetDto.getCdDetailVal3() ) );    // 요일리스트
                
            } else {
                result.put("resultList", null ) ;
            }
            
        } else {
            // 없으면 뿌리지 않음.
            result.put("resultList", null ) ;
        }

        return result;
    }
    
}
