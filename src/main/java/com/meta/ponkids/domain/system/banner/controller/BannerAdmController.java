package com.meta.ponkids.domain.system.banner.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ser.std.ClassSerializer;
import com.meta.ponkids.domain.cls.dto.ClassListDto;
import com.meta.ponkids.domain.cls.dto.ClassModDto;
import com.meta.ponkids.domain.cls.service.ClassCategoryCl01Service;
import com.meta.ponkids.domain.cls.service.ClassCategoryCl02Service;
import com.meta.ponkids.domain.cls.service.ClassService;
import com.meta.ponkids.domain.system.banner.dto.BannerListDto;
import com.meta.ponkids.domain.system.banner.dto.BannerModDto;
import com.meta.ponkids.domain.system.banner.dto.BannerSaveDto;
import com.meta.ponkids.domain.system.banner.service.BannerService;
import com.meta.ponkids.domain.system.cmmnCd.dto.CmmnCdDetailListDto;
import com.meta.ponkids.domain.system.cmmnCd.dto.CmmnCdDetailModDto;
import com.meta.ponkids.domain.system.cmmnCd.dto.CmmnCdModDto;
import com.meta.ponkids.domain.system.cmmnCd.service.CmmnCdDetailService;
import com.meta.ponkids.domain.system.cmmnCd.service.CmmnCdService;
import com.meta.ponkids.domain.system.file.service.AtchFileService;
import com.meta.ponkids.global.common.dto.CategoryDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BannerAdmController {
    
    private final static String BASIC_VIEW_PATH = "admin/banner";
    private final static String BASIC_PATH = "/" + BASIC_VIEW_PATH;	// BASIC_VIEW_PATH 는  앞의 "/" 를 제거해야 함.
    private final BannerService bannerService;
    
    private final CmmnCdService cmmnCdService;
    private final CmmnCdDetailService cmmnCdDetailService;

    private final ClassService classService;
    private final ClassCategoryCl01Service classCategoryCl01Service;
    private final ClassCategoryCl02Service classCategoryCl02Service;
    
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
        
        
        // 클래스 카테고리 분류2,3,4 list setting
        if ( listDto.getCategory() != null ) {
            
            // 클래스 카테고리 분류2 list setting
            if ( listDto.getCategory().getLv1Sn() != null ) {
                // lv2 li 리스트를 미리 만들어 뿌림
            	CmmnCdDetailListDto cmmnCdDetailListDto = new CmmnCdDetailListDto();
            	cmmnCdDetailListDto.setCategory(listDto.getCategory());
            	
                model.addAttribute( "cateLv2List", getBannerClDetailList(cmmnCdDetailListDto) );   // lv2 list 생성
            }
            
        }
        
        // E : 필요한 객체 setting
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return BASIC_VIEW_PATH + "/list";
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
        
        return BASIC_VIEW_PATH + "/regist";
    }
    
    
    @Transactional
    @PostMapping( BASIC_PATH + "/{mcd}/insert" )
    public String insert(
            @ModelAttribute BannerSaveDto saveDto,
            @PathVariable String mcd,
            @RequestParam( "atchFile" ) MultipartFile atchFile,
            HttpServletRequest request,
            Model model ) throws IOException {
        
        // S : 필요한 객체 setting
        
        // 썸네일 이미지 존재시 파일 저장
        if ( !atchFile.isEmpty() ) {
            saveDto.setAtchFileSn( atchFileService.save( atchFile ) );
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
    	BannerModDto targetDto = bannerService.findById( pk ); 
    			
        model.addAttribute( "targetDto", targetDto );
        
        if ( targetDto == null ) {
        	model.addAttribute( "resultMsg", "유효하지 않은 배너입니다. 다시 확인해 주세요." );
            model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
            
            return "common/alert";
        }
        
        
        // 배너 분류 setting ( 메인상단배너, 메인클래스배너 )
    	model.addAttribute( "bannerClCdList", cmmnCdDetailService.getList( "BANNER_CL_CD" ) );   // 배너 분류 코드 리스트
    	
    	// 배너 상세 분류 setting ( targetDto 의 bannerClCd 값을 체크 하여 bannerClDetailCd 리스트를 setting ) 
    	if ( StringUtils.hasText( targetDto.getBannerClCd() ) ) {
    		// 공통코드 상세 조회 
    		// 공통상세코드 1건 조회 
    		CmmnCdDetailModDto cmmnCdDetailModDto = cmmnCdDetailService.findTop1ByCdNmAndCdDetailVal1("BANNER_CL_CD", targetDto.getBannerClCd() );
    		
    		if ( cmmnCdDetailModDto != null && StringUtils.hasText( cmmnCdDetailModDto.getCdDetailVal2() ) ) {
    			if ( "Y".equals( cmmnCdDetailModDto.getCdDetailVal2()) ) {
    				if ( StringUtils.hasText(cmmnCdDetailModDto.getCdDetailVal3()) ) {
    					 // 배너 상세  분류 setting
    			    	model.addAttribute( "bannerClDetailCdList", cmmnCdDetailService.getList( "BANNER_CL_DETAIL_CD" ) );   // 배너 상세 분류 코드 리스트
    				}
    			}
    			
    		}
    	}
    	
    	// 클래스 카테고리 분류1 list setting
        model.addAttribute( "classCategoryCl01List", classCategoryCl01Service.findAll() );
    	
    	// 카테고리, 커리큘럼 setting 
    	if ( targetDto.getClassSn() != null && !( targetDto.getClassSn().equals("") ) ) {
    		// 클래스 조회
    		ClassModDto classModDto = classService.findById(targetDto.getClassSn());
    		
    		// ctgrySn setting
    		model.addAttribute("ctgrySn", classModDto.getCtgrySn());	// ctgrySn setting
    		
    		// crseList setting
    		model.addAttribute("crseList", classCategoryCl02Service.findByParntsClSnOrderByClSeq( classModDto.getCtgrySn() ));
    		

    		if ( classModDto.getCrseSn() != null && !(classModDto.getCrseSn().equals("")) ) {
    			// crseSn setting
    			model.addAttribute("crseSn", classModDto.getCrseSn());	// crseSn setting
    		} else {
    			// crseSn setting
    			model.addAttribute("crseSn", "");	// crseSn setting
    		}
    		
    		// classList setting
    		CategoryDto categoryDto = new CategoryDto();
    		categoryDto.setLv1Sn( classModDto.getCtgrySn());
    		categoryDto.setLv2Sn( classModDto.getCrseSn());
            ClassListDto classListDto = new ClassListDto();
            classListDto.setCategory( categoryDto );
            model.addAttribute( "classList", classService.getList( classListDto ) );  // lv3 list 생성
    		
    	}
        

    	
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
            @RequestParam( "atchFile" ) MultipartFile atchFile,
            @PathVariable String mcd,
            @ModelAttribute BannerModDto modDto,
            HttpServletRequest request,
            Model model ) throws IOException {
        
        // S : 필요한 객체 setting
    	
    	// 첨부파일 존재시 파일 저장
        if ( !atchFile.isEmpty() ) {
            // 기존에 첨부파일 있을시 삭제
            if ( modDto.getAtchFileSn() != null ) {
                atchFileService.delete( modDto.getAtchFileSnOri() );
            }
            
            // 첨부파일 저장
            modDto.setAtchFileSn( atchFileService.save( atchFile ) );    // 파일 save (파일 개수 1개일 때 )
        } else {
            // 첨부파일 존재하지않을 때
            // 기존 첨부파일이 있었는데 삭제됬다면 삭제처리
            if ( modDto.getAtchFileSnOri() != null && modDto.getAtchFileSn() == null ) {
                atchFileService.delete( modDto.getAtchFileSnOri() );
                modDto.setAtchFileSn( null );
            }
        }
    	
        // E : 필요한 객체 setting
        
        // update 구현
        bannerService.update( modDto, request );
        
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
        
        result.put("resultList", getBannerClDetailList(listDto)	);

        return result;
    }
    
    
    
    
  
    
    // ================================== UTIL ========================================
    // ================================== UTIL ========================================
    // ================================== UTIL ========================================
    
    // 배너 상세 분류 list (모듈화작업)
    private List<CmmnCdDetailListDto> getBannerClDetailList(CmmnCdDetailListDto listDto) {
    	
        if (listDto.getCdDetailSn() == null ) {
            if (listDto.getCategory() != null && listDto.getCategory().getLv1Sn() != null ) {
                listDto.setCdDetailSn( listDto.getCategory().getLv1Sn() );
            } else {
                return null;
            }
        }
        
        if ( listDto.getCdDetailSn().equals( (long) 0) ) {
        	CmmnCdModDto bannerClCdDto  = cmmnCdService.findByCdNm( "BANNER_CL_CD" );
            
            if ( bannerClCdDto.getClCd() != null ) {
            	return cmmnCdDetailService.getList( bannerClCdDto.getClCd() );
            } else {
            	return null;
            }
        	
        } else {
        	
        	// cdDetailsn 값으로 코드 정보를 조회
            CmmnCdDetailModDto targetDto = cmmnCdDetailService.findById( listDto.getCdDetailSn() );
            
            if ( targetDto.getCdDetailVal2() != null && targetDto.getCdDetailVal2().equals("Y") ) {
                // 연계 여부 존재시
                
                // 연계 코드를 가져와 그 코드명의 리스트를 뿌립.
                if(targetDto.getCdDetailVal3() != null ) {
                    // 연계 코드 는 cdDetailVal3 값에 존재함.
                	return cmmnCdDetailService.getList( targetDto.getCdDetailVal3() );
                    
                } else {
                	return null;
                }
                
            } else {
                // 없으면 뿌리지 않음.
            	return null;
            }
        }
    }
    
}
