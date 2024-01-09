package com.meta.ponkids.domain.cls.controller;

import com.meta.ponkids.domain.cls.dto.ClassCategoryCl02ListDto;
import com.meta.ponkids.domain.cls.dto.ClassListDto;
import com.meta.ponkids.domain.cls.dto.ClassModDto;
import com.meta.ponkids.domain.cls.dto.ClassSaveDto;
import com.meta.ponkids.domain.cls.service.ClassCategoryCl01Service;
import com.meta.ponkids.domain.cls.service.ClassCategoryCl02Service;
import com.meta.ponkids.domain.cls.service.ClassService;
import com.meta.ponkids.domain.cls.service.ClassWeekService;
import com.meta.ponkids.domain.system.cmmnCd.service.CmmnCdDetailService;
import com.meta.ponkids.domain.system.file.entity.AtchFileDetail;
import com.meta.ponkids.domain.system.file.repository.AtchFileDetailRepository;
import com.meta.ponkids.domain.system.file.service.AtchFileDetailService;
import com.meta.ponkids.domain.system.file.service.AtchFileService;
import com.meta.ponkids.domain.system.menu.dto.MenuListDto;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ClassController {
    
    private final static String BASIC_PATH = "/admin/class";
    private final static String BASIC_DIR_PATH = "/admin/clas";
    
    private final ClassService classService;
    private final ClassWeekService classWeekService;
    private final ClassCategoryCl01Service classCategoryCl01Service;
    private final ClassCategoryCl02Service classCategoryCl02Service;
    private final CmmnCdDetailService cmmnCdDetailService;
    private final AtchFileService atchFileService;
    private final AtchFileDetailService atchFileDetailService;
    private final AtchFileDetailRepository atchFileDetailRepository;
    
    @GetMapping( BASIC_PATH + "/{mcd}/list" )
    public String list( @ModelAttribute ClassListDto listDto,
                        @PageableDefault( size = 10 ) Pageable pageable,
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
        
        return BASIC_DIR_PATH + "/list";
    }
    
    @GetMapping( BASIC_PATH + "/{mcd}/regist" )
    public String regist( @PathVariable String mcd,
                          Model model ) {
        
        // S : 필요한 객체 setting
        
        // 가입 object 생성
        model.addAttribute( "saveDto", new ClassSaveDto() );
        
        // 요일 List add
        model.addAttribute( "day7List", cmmnCdDetailService.getList( "DAY_7_CD" ) );    // 요일리스트
        
        // 클래스 카테고리 분류1 list setting
        model.addAttribute( "classCategoryCl01List", classCategoryCl01Service.findAll() );
        
        // E : 필요한 객체 setting
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return BASIC_DIR_PATH + "/regist";
    }
    
    @Transactional
    @PostMapping( BASIC_PATH + "/{mcd}/insert" )
    public String insert(
            @ModelAttribute ClassSaveDto saveDto,
            @PathVariable String mcd,
            HttpServletRequest request,
            @RequestParam( "thumbFile" ) MultipartFile thumbFile,
            @RequestParam( "atchFile" ) List<MultipartFile> atchFileList,
            Model model ) throws IOException {
        
        // S : 필요한 객체 setting
        
        // E : 필요한 객체 setting
        
        // 등록 처리
        
        // save
        
        // 썸네일 이미지 존재시 파일 저장
        if ( !thumbFile.isEmpty() ) {
            saveDto.setThumbAtchFileSn( atchFileService.save( thumbFile ) );
        }
        
        // 첨부파일  존재시 파일 저장
        if ( atchFileList.get( 0 ).getSize() != 0 ) {
            saveDto.setAtchFileSn( atchFileService.multifileSave( atchFileList, null ) );
        }
        
        // 클래스 저장
        try {
            saveDto = classService.save( saveDto, request );
        } catch ( Exception e ) {
            // 메시지 출력 및 url 이동 처리
            model.addAttribute( "resultMsg", "등록 중 오류가 발생했습니다. " + e.getMessage() + "\n다시 시도해주세요." );
            model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
            
            return "common/alert";
        }
        
        if ( saveDto.getClassWeek() != null ) {
            classWeekService.save( saveDto, request );
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
        ClassModDto targetDto = classService.findById( pk );
        model.addAttribute( "targetDto", targetDto );
        
        // 요일 List add
        model.addAttribute( "day7List", cmmnCdDetailService.getList( "DAY_7_CD" ) );    // 요일리스트
        
        // 클래스 카테고리 list setting
        model.addAttribute( "ctgryCdList", classCategoryCl01Service.findAll() );
        
        // 클래스 커리큘럼 list setting ( targetDto 의 ctgryCd 값으로 커리큘럼 list 를 구함. )
        model.addAttribute( "crseCdList", classCategoryCl02Service.findByParntsClSnOrderByClSeq( Long.parseLong( targetDto.getCtgryCd() ) ) );
        
        // 클래스 요일 List add
        // 클래스 요일 은 html 그리고 script로 ajax를 통해 불러온다. 처음에 불러오면 타임리프로 요일을 체크하는 로직이 너무 어렵기 때문에 html먼저 그린 뒤 ajax를 호출 하는 방식으로 정함.
//        model.addAttribute( "classWeekList",  classWeekService.findByClassSnOrderByClassWeekSn( targetDto.getClassSn() ));
        // 클래스sn model 에 추가
        model.addAttribute( "schClassSn",  targetDto.getClassSn() );
        
        
        // 첨부파일 존재시
        if ( targetDto.getAtchFileSn() != null ) {
            List<AtchFileDetail> atchFileList = atchFileDetailService.getList( targetDto.getAtchFileSn() );
            model.addAttribute( "atchFileList", atchFileList );
        }
        
        // E : 필요한 객체 setting
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        String urlPath = request.getServletPath();
        String remainPath = "";
        if ( urlPath.split( BASIC_PATH )[ 1 ].endsWith( "/detail" ) ) remainPath = "detail";
        if ( urlPath.split( BASIC_PATH )[ 1 ].endsWith( "/modify" ) ) remainPath = "modify";
        
        return BASIC_DIR_PATH + "/" + remainPath;
    }
    
    @Transactional
    @PostMapping( BASIC_PATH + "/{mcd}/update" )
    public String update(
            @RequestParam( "file" ) MultipartFile files,        // 첨부파일 (썸네일 이미지)
            @RequestParam( "atchFile" ) List<MultipartFile> atchFileList,   // 첨부파일 (여러개 파일 )
            @PathVariable String mcd,
            @ModelAttribute ClassModDto modDto,
            HttpServletRequest request,
            Model model ) throws IOException {
        
        // S : 필요한 객체 setting
        
        // 첨부파일 존재시 파일 저장
        if ( !files.isEmpty() ) {
            // 기존에 첨부파일 있을시 삭제
            if ( modDto.getThumbAtchFileSn() != null ) {
                atchFileService.delete( modDto.getThumbAtchFileSnOri() );
            }
            
            // 첨부파일 저장
            modDto.setThumbAtchFileSn( atchFileService.save( files ) );    // 파일 save (파일 개수 1개일 때 )
        } else {
            // 첨부파일 존재하지않을 때
            // 기존 첨부파일이 있었는데 삭제됬다면 삭제처리
            if ( modDto.getThumbAtchFileSnOri() != null && modDto.getThumbAtchFileSn() == null ) {
                atchFileService.delete( modDto.getThumbAtchFileSnOri() );
                modDto.setThumbAtchFileSn( null );
            }
        }
        
        Long atchFileSn = modDto.getAtchFileSn();
        
        // 첨부파일  존재시 파일 저장
        if ( atchFileSn != null ) { // 기존 첨부파일 있을시
            // 첨부파일  존재시 파일 저장
            if ( atchFileList.get( 0 ).getSize() != 0 ) {
                atchFileService.multifileSave( atchFileList, atchFileSn );    // 파일 save (파일여러개 ) + 추가 저장
            } else {
                // 기존 첨부파일 모두 삭제 됬을 경우?
                List<AtchFileDetail> atchFileDetailList = atchFileDetailService.getList( atchFileSn );
                if ( atchFileDetailList.isEmpty() ) {
                    atchFileDetailRepository.deleteByAtchFileDetailPk_AtchFileSn( atchFileSn ); // 부모 테이블 삭제 처리
                    modDto.setAtchFileSn( null );    // 파일 save (파일여러개 )
                }
            }
            
        } else { //기존 첨부파일 없을시 신규로 추가
            
            // 첨부파일  존재시 파일 저장
            if ( atchFileList.get( 0 ).getSize() != 0 ) {
                modDto.setAtchFileSn( atchFileService.multifileSave( atchFileList, null ) );
            }
            
        }
        
        // E : 필요한 객체 setting
        
        // update 구현
        classService.update( modDto, request );
        
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
        classService.deleteAllById( pk );        // By 뒤에는 custom
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 삭제되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
        
        return "common/alert";
    }
    
    // 카테고리 검색 (Ajax)
//    @ResponseBody
//    @GetMapping( BASIC_PATH + "/live/getListAjax" )
//    public Map<String, Object> getListAjax( @ModelAttribute ClassListDto listDto,
//                                            @PageableDefault( size = 10 ) Pageable pageable
//    ) {
//
//        Map<String, Object> result = new HashMap<String, Object>();
//
//        result.put( "resultList", classService.getList( listDto, pageable ) );
//
//        return result;
//    }
//
    // 클래스 요일 검색 (Ajax)
    @ResponseBody
    @GetMapping( BASIC_PATH + "/live/getClassWeekListAjax" )
    public Map<String, Object> getClassWeekListAjax( @ModelAttribute ClassListDto listDto
    ) {
        
        Map<String, Object> result = new HashMap<String, Object>();
        
        result.put( "resultList", classWeekService.findByClassSnOrderByClassWeekSn( listDto.getClassSn() ) );   // 클래스 요일 classSn으로 검색
        
        return result;
    }
    
    
}
