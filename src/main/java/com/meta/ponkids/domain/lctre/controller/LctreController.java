package com.meta.ponkids.domain.lctre.controller;

import com.meta.ponkids.domain.cls.dto.ClassListDto;
import com.meta.ponkids.domain.cls.dto.ClassModDto;
import com.meta.ponkids.domain.cls.dto.ClassWeekListDto;
import com.meta.ponkids.domain.cls.service.ClassCategoryCl01Service;
import com.meta.ponkids.domain.cls.service.ClassCategoryCl02Service;
import com.meta.ponkids.domain.cls.service.ClassService;
import com.meta.ponkids.domain.cls.service.ClassWeekService;
import com.meta.ponkids.domain.lctre.dto.LctreListDto;
import com.meta.ponkids.domain.lctre.dto.LctreModDto;
import com.meta.ponkids.domain.lctre.dto.LctreSaveDto;
import com.meta.ponkids.domain.lctre.service.LctreService;
import com.meta.ponkids.domain.system.cmmnCd.service.CmmnCdDetailService;
import com.meta.ponkids.global.common.dto.CategoryDto;
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
public class LctreController {
    
    private final LctreService lctreService;
    
    private final ClassService classService;
    private final ClassWeekService classWeekService;
    private final ClassCategoryCl01Service classCategoryCl01Service;
    private final ClassCategoryCl02Service classCategoryCl02Service;
    
    private final CmmnCdDetailService cmmnCdDetailService;
    
    private final static String BASIC_PATH = "/admin/lctre";
    
    
    @GetMapping( value = { BASIC_PATH + "/{mcd}/list",
            BASIC_PATH + "/{mcd}/{classSn}/list" } )
    public String list( @ModelAttribute LctreListDto listDto,
                        @PathVariable String mcd,
                        @PathVariable( required = false ) Long classSn,
                        @PageableDefault( size = 10 ) Pageable pageable,
                        Model model ) {
        
        // S : 필요한 객체 setting
        
        // url 에 classSn 담겨 있을 시 classSn 유효성 체크
        if( classSn != null ) {
            // 공통 유효성 체크 함수
            Map<String, Object> classValidCheck = classValidCheck( classSn, mcd, model );
            boolean validResult = ( boolean ) classValidCheck.get( "validResult" ); // 체크 결과
            
            if ( !validResult ) {
                model.addAttribute( "resultMsg", ( String ) classValidCheck.get( "resultMsg" ) );
                model.addAttribute( "moveUrl", ( String ) classValidCheck.get( "moveUrl" ) );
                
                return "common/alert";
            } else {
                ClassModDto classDto = ( ClassModDto ) classValidCheck.get( "classDto" );
                
                if ( classDto != null ) {
                    // 클래스가 존재할 경우 분류 (lv1,lv2) 값 세팅을 미리 해줌
                    
                    // 분류 선택값 set 및 리스트 미리 setting 작업
                    if ( classDto.getCtgrySn() != null ) {
                        
                        CategoryDto categoryDto = new CategoryDto();
                        
                        // ------- S : 븐류 lv1 선택값 매핑 및 븐류 lv2 li 리스트 생성 작업
                        // ------- S : 분류 lv1 선택값 매핑 및 분류 lv2 li 리스트 생성 작업 : 커리큘럼 리스트 ( lv2 )
                        categoryDto.setLv1Sn( classDto.getCtgrySn() );    // searchDTO 에 lv1 Sn 매칭
                        // lv2 li 리스트를 미리 만들어 뿌림
                        model.addAttribute( "cateLv2List", classCategoryCl02Service.findByParntsClSnOrderByClSeq( categoryDto.getLv1Sn() ) );   // lv2 list 생성
                        
                        // ------- S : 분류 lv2 선택값 매핑 및 분류 lv3 li 리스트 생성 작업
                        // ------- S : 분류 lv2 선택값 매핑 및 분류 lv3 li 리스트 생성 작업 : 클래스 리스트 ( lv3 )
                        categoryDto.setLv2Sn( classDto.getCrseSn() );     // searchDTO 에 lv2 Sn 매칭
                        // lv3 li 리스트를 미리 만들어 뿌림
                        ClassListDto classListDto = new ClassListDto();
                        classListDto.setCategory( categoryDto );
                        model.addAttribute( "cateLv3List", classService.getList( classListDto ) );  // lv3 list 생성
                        
                        // ------- S : 분류 lv3 선택값 매핑 및 분류 lv4 li 리스트 생성 작업
                        // ------- S : 분류 lv3 선택값 매핑 및 분류 lv4 li 리스트 생성 작업 : 요일 리스트 ( lv4 )
                        categoryDto.setLv3Sn( classDto.getClassSn() );
                        // lv4 li 리스트를 미리 만들어 뿌림
                        model.addAttribute( "cateLv4List", classWeekService.getListByClassSn( categoryDto.getLv3Sn() ) );   // lv4 list 생성 (클래스 요일 classSn으로 검색 )
                        
                        listDto.setCategory( categoryDto );             // listDto 에 categoryDto setting
                    }
                }
            }
        }
        
        // 목록 조회
        Page<LctreListDto> resultList = lctreService.getList( listDto, pageable );
        model.addAttribute( "resultList", resultList );
        
        // 카테고리 리스트 ( lv1 )
        // 클래스 카테고리 분류1 list setting
        model.addAttribute( "cateLv1List", classCategoryCl01Service.findAll() );
        
        // 검색 dto setting
        model.addAttribute( "searchDTO", listDto );
        
        // E : 필요한 객체 setting
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return BASIC_PATH + "/list";
    }
    
    @GetMapping( BASIC_PATH + "/{mcd}/{classSn}/regist" )
    public String regist( @PathVariable String mcd,
                          @PathVariable Long classSn,
                          Model model ) {
        
        // S : 필요한 객체 setting
        
        // url 에 classSn 담겨 있을 시 classSn 유효성 체크
        Map<String, Object> classValidCheck = classValidCheck( classSn, mcd, model );
        boolean validResult = (boolean)classValidCheck.get("validResult");
        if( !validResult ) {
            model.addAttribute( "resultMsg",  (String)classValidCheck.get("resultMsg"));
            model.addAttribute( "moveUrl", (String)classValidCheck.get("moveUrl") );
            
            return "common/alert";
        }
        
        ClassModDto classDto = (ClassModDto) classValidCheck.get("classDto");
        
        // 등록 classSn 정보 add
        model.addAttribute( "classDto", classDto );
        
        
        // 가입 object 생성
        model.addAttribute( new LctreSaveDto() );
        
        
        
        // 클래스 요일 List add
        model.addAttribute( "classWeekList", classWeekService.getListByClassSn( classSn ) );    // 요일리스트
        
        // E : 필요한 객체 setting
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return BASIC_PATH + "/regist";
    }
    
    @Transactional
    @PostMapping( BASIC_PATH + "/{mcd}/{classSn}/insert" )
    public String insert(
            @ModelAttribute LctreSaveDto saveDto,
//            @ModelAttribute LctreRoleSaveDto lctreRoleSaveDto,  // required false
            @PathVariable String mcd,
            @PathVariable Long classSn,
            HttpServletRequest request,
            Model model ) throws IOException {
        
        // S : 필요한 객체 setting
        // url 에 classSn 담겨 있을 시 classSn 유효성 체크
        Map<String, Object> classValidCheck = classValidCheck( classSn, mcd, model );
        boolean validResult = (boolean)classValidCheck.get("validResult");
        if( !validResult ) {
            model.addAttribute( "resultMsg",  (String)classValidCheck.get("resultMsg"));
            model.addAttribute( "moveUrl", (String)classValidCheck.get("moveUrl") );
            
            return "common/alert";
        }
        
        ClassModDto classDto = (ClassModDto) classValidCheck.get("classDto");
        
        // 등록 classSn 정보 add
        model.addAttribute( "classDto", classDto );
        
        
        // E : 필요한 객체 setting
        
        // 등록 처리
        
        // save
        lctreService.save( saveDto, request );
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 등록되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/" + classSn + "/list" );
        
        return "common/alert";
    }
    
    @GetMapping( value = {
            BASIC_PATH + "/{mcd}/{classSn}/detail",
            BASIC_PATH + "/{mcd}/{classSn}/modify" } )
    public String detailOrModify(
            @RequestParam( required = true ) Long pk,    // 타입 체크
            @PathVariable String mcd,
            @PathVariable Long classSn,
            HttpServletRequest request,
            Model model ) {
        
        // S : 필요한 객체 setting
        // url 에 classSn 담겨 있을 시 classSn 유효성 체크
        Map<String, Object> classValidCheck = classValidCheck( classSn, mcd, model );
        boolean validResult = (boolean)classValidCheck.get("validResult");
        if( !validResult ) {
            model.addAttribute( "resultMsg",  (String)classValidCheck.get("resultMsg"));
            model.addAttribute( "moveUrl", (String)classValidCheck.get("moveUrl") );
            
            return "common/alert";
        }
        
        ClassModDto classDto = (ClassModDto) classValidCheck.get("classDto");
        
        // 등록 classSn 정보 add
        model.addAttribute( "classDto", classDto );
        
        // target object 조회
        model.addAttribute( "targetDto", lctreService.findById( pk ) );
        
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
    @PostMapping( BASIC_PATH + "/{mcd}/{classSn}/update" )
    public String update(
            @RequestParam( "file" ) MultipartFile files,        // 첨부파일 필요시
            @ModelAttribute LctreModDto modDto,
//            @ModelAttribute LctreRoleModDto lctreRoleModDto,  // required false
            @PathVariable String mcd,
            @PathVariable Long classSn,
            HttpServletRequest request,
            Model model ) throws IOException {
        
        // S : 필요한 객체 setting
        // url 에 classSn 담겨 있을 시 classSn 유효성 체크
        Map<String, Object> classValidCheck = classValidCheck( classSn, mcd, model );
        boolean validResult = (boolean)classValidCheck.get("validResult");
        if( !validResult ) {
            model.addAttribute( "resultMsg",  (String)classValidCheck.get("resultMsg"));
            model.addAttribute( "moveUrl", (String)classValidCheck.get("moveUrl") );
            
            return "common/alert";
        }
        
        ClassModDto classDto = (ClassModDto) classValidCheck.get("classDto");
        
        // 등록 classSn 정보 add
        model.addAttribute( "classDto", classDto );
        
        // E : 필요한 객체 setting
        
        
        // update 구현
        lctreService.update( modDto, request );
//        lctreService.update( modDto, lctreRoleModDto, request );
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 수정되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
        
        return "common/alert";
    }
    
    
    @Transactional
    @PostMapping( value = { BASIC_PATH + "/{mcd}/delete",
            BASIC_PATH + "/{mcd}/{classSn}/delete" } )
    public String delete(
            @RequestParam( required = true ) Long pk,
            @PathVariable String mcd,
            @PathVariable Long classSn,
            Model model ) {
        
        // url 에 classSn 담겨 있을 시 classSn 유효성 체크
        if( classSn != null ) {
            Map<String, Object> classValidCheck = classValidCheck( classSn, mcd, model );
            boolean validResult = ( boolean ) classValidCheck.get( "validResult" );
            if ( !validResult ) {
                model.addAttribute( "resultMsg", ( String ) classValidCheck.get( "resultMsg" ) );
                model.addAttribute( "moveUrl", ( String ) classValidCheck.get( "moveUrl" ) );
                
                return "common/alert";
            }
            
            ClassModDto classDto = ( ClassModDto ) classValidCheck.get( "classDto" );
            
            // 등록 classSn 정보 add
            model.addAttribute( "classDto", classDto );
        }
        
        
        // 삭제 처리
        lctreService.deleteAllById( pk );        // By 뒤에는 custom
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 삭제되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
        
        return "common/alert";
    }
    
    
    
    
    
    
    
    
    
    
    
    private Map<String, Object> classValidCheck( Long classSn, String mcd, Model model ) {
        
        Map<String, Object> result = new HashMap<String, Object>();
        
        if ( classSn != null ) {
            // 클래스 정보 add ( classSn으로 검색 )
            ClassModDto classDto = classService.findById( classSn );
            // 클래스 요일 list 정보 add ( classSn으로 검색 )
            List<ClassWeekListDto> classWeekListDtos =  classWeekService.getListByClassSn( classSn );
            
            // 클래스가 존재 하지 않을 시
            if ( classDto == null ) {
                // 메시지 출력 및 url 이동 처리
                result.put("validResult", false);
                
                result.put( "resultMsg", "클래스의 정보를 확인해주세요." );
                result.put( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
                
                return result;
            } else if ( classWeekListDtos.size() == 0 ) {
                // 메시지 출력 및 url 이동 처리
                result.put("validResult", false);
                
                
                result.put( "resultMsg", "클래스에 요일 등록이 되어있지 않습니다.\n적어도 1개의 요일을 선택해 주세요." );
                result.put( "moveUrl",   "/admin/class/" + mcd + "/modify?pk=" + classSn );
                
                return result;
            } else {
                result.put("validResult", true);
                
                // 클래스 정보 add
                model.addAttribute( "classDto", classDto );
                
            }
        }
        
        
        
        return result;
    }
    
    
}
