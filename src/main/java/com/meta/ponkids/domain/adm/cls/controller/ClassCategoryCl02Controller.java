package com.meta.ponkids.domain.adm.cls.controller;

import com.meta.ponkids.domain.adm.cls.dto.*;
import com.meta.ponkids.domain.adm.cls.service.ClassCategoryCl01Service;
import com.meta.ponkids.domain.adm.cls.service.ClassCategoryCl02Service;
import com.meta.ponkids.domain.adm.system.menu.service.MenuService;
import com.meta.ponkids.domain.adm.cls.repository.ClassCategoryCl02Repository;
import com.meta.ponkids.global.common.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ClassCategoryCl02Controller {
    
    private final static String BASIC_PATH = "/admin/classCategoryCl02";
    private final ClassCategoryCl02Service classCategoryCl02Service;
    private final ClassCategoryCl01Service classCategoryCl01Service;
    private final ClassCategoryCl02Repository classCategoryCl02Repository;
    private final MenuService menuService;
    
    @GetMapping( BASIC_PATH + "/{mcd}/{parntsClSn}/list" )
    public String list( @ModelAttribute ClassCategoryCl02ListDto listDto,
                        @PageableDefault( size = 10 ) Pageable pageable,
                        @PathVariable String mcd,
                        @PathVariable Long parntsClSn,
                        Model model ) {
        
        // S : 필요한 객체 setting
        
        // parntsClSn setting
        if ( parntsClSn != null ) {
            
            
            // parntsClsn
            listDto.setParntsClSn( parntsClSn );
            // category set
            categorySet( listDto, parntsClSn );
            
        } else {
            // TODO : return error
            
        }
        
        // 목록 조회
        Page<ClassCategoryCl02ListDto> resultList = classCategoryCl02Service.getList( listDto, pageable );
        model.addAttribute( "resultList", resultList );
        
        // 검색 dto setting
        model.addAttribute( "searchDTO", listDto );
        
        // 카테고리 리스트 ( lv1 )
        // 클래스 카테고리 분류1 list setting
        model.addAttribute( "cateLv1List", classCategoryCl01Service.findAll() );
        
        // E : 필요한 객체 setting
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        // 경로 추가
        model.addAttribute( "parntsClSn", parntsClSn );
        
        return BASIC_PATH + "/list";
    }
    
    @GetMapping( BASIC_PATH + "/{mcd}/{parntsClSn}/regist" )
    public String regist( @PathVariable String mcd,
                          @PathVariable Long parntsClSn,
                          Model model ) {
        
        // S : 필요한 객체 setting
        
        // 가입 object 생성
        model.addAttribute( "saveDto", new ClassSaveDto() );
        
        // 부모 카테고리 값 가져오기
        ClassCategoryCl01ModDto classCategoryCl01ModDto = classCategoryCl01Service.findById( parntsClSn );
        
        // 부모 카테고리 값 ( sn, nm ) model 에 setting
        if ( classCategoryCl01ModDto != null ) {
            model.addAttribute( "parntsClSn", parntsClSn );
            model.addAttribute( "parntsClNm", classCategoryCl01ModDto.getClNm() );
            
        } else {
            // TODO : return error
            
        }
        
        // E : 필요한 객체 setting
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return BASIC_PATH + "/regist";
    }
    
    @Transactional
    @PostMapping( BASIC_PATH + "/{mcd}/{parntsClSn}/insert" )
    public String insert(
            @ModelAttribute ClassCategoryCl02SaveDto saveDto,
            @PathVariable String mcd,
            @PathVariable Long parntsClSn,
            HttpServletRequest request,
            Model model ) throws IOException {
        
        // S : 필요한 객체 setting
        
        // E : 필요한 객체 setting
        
        // 등록 처리
        
        // save
        classCategoryCl02Service.save( saveDto, request );
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 등록되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/" + parntsClSn + "/list" );
        
        return "common/alert";
    }
    
    @GetMapping( value = {
            BASIC_PATH + "/{mcd}/{parntsClSn}/detail",
            BASIC_PATH + "/{mcd}/{parntsClSn}/modify" } )
    public String detailOrModify(
            @RequestParam( required = true ) Long pk,    // 타입 체크
            @PathVariable String mcd,
            @PathVariable Long parntsClSn,
            HttpServletRequest request,
            Model model ) {
        
        // S : 필요한 객체 setting
        
        // target object 조회
        ClassCategoryCl02ModDto targetDto = classCategoryCl02Service.findById( pk );
        model.addAttribute( "targetDto", targetDto );
        
        // 부모 카테고리 값 가져오기
        if ( parntsClSn == 0 && targetDto != null && targetDto.getParntsClSn() != null ) {
            ClassCategoryCl01ModDto classCategoryCl01ModDto = classCategoryCl01Service.findById( targetDto.getParntsClSn() );
            
            // 부모 카테고리 값 ( sn, nm ) model 에 setting
            if ( classCategoryCl01ModDto != null ) {
                model.addAttribute( "parntsClSn", parntsClSn );
                model.addAttribute( "parntsClNm", classCategoryCl01ModDto.getClNm() );
                
            } else {
                // TODO : return error
                
            }
            
            
        } else {
            ClassCategoryCl01ModDto classCategoryCl01ModDto = classCategoryCl01Service.findById( parntsClSn );
            
            // 부모 카테고리 값 ( sn, nm ) model 에 setting
            if ( classCategoryCl01ModDto != null ) {
                model.addAttribute( "parntsClSn", parntsClSn );
                model.addAttribute( "parntsClNm", classCategoryCl01ModDto.getClNm() );
                
            } else {
                // TODO : return error
                
            }
        }
        
        // E : 필요한 객체 setting
        
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        // 경로 추가
        model.addAttribute( "parntsClSn", parntsClSn );
        
        String urlPath = request.getServletPath();
        String remainPath = "";
        if ( urlPath.split( BASIC_PATH )[ 1 ].endsWith( "/detail" ) ) remainPath = "detail";
        if ( urlPath.split( BASIC_PATH )[ 1 ].endsWith( "/modify" ) ) remainPath = "modify";
        
        return BASIC_PATH + "/" + remainPath;
    }
    
    @Transactional
    @PostMapping( BASIC_PATH + "/{mcd}/{parntsClSn}/update" )
    public String update(
            @PathVariable String mcd,
            @PathVariable Long parntsClSn,
            @ModelAttribute ClassCategoryCl02ModDto modDto,
            HttpServletRequest request,
            Model model ) throws IOException {
        
        // S : 필요한 객체 setting
        
        // E : 필요한 객체 setting
        
        
        // update 구현
        classCategoryCl02Service.update( modDto, request );
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 수정되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/" + parntsClSn + "/list" );
        
        return "common/alert";
    }
    
    @Transactional
    @PostMapping( BASIC_PATH + "/{mcd}/{parntsClSn}/delete" )
    public String delete(
            @RequestParam( required = true ) Long pk,
            @PathVariable String mcd,
            @PathVariable Long parntsClSn,
            Model model ) {
        
        // 삭제 처리
        classCategoryCl02Service.deleteAllById( pk );        // By 뒤에는 custom
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 삭제되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/" + parntsClSn + "/list" );
        
        return "common/alert";
    }
    
    
    // 카테고리 setting method
    public void categorySet( ClassCategoryCl02ListDto listDto, long parntsClSn ) {
        
        // 전체 검색 일 경우 전체로 setting
        if ( parntsClSn == 0 ) {
            if ( listDto.getCategory() != null ) {
                
                listDto.getCategory().setLv1Sn( parntsClSn );
                listDto.getCategory().setLv1Nm( "전체" );
                
            } else {
                CategoryDto categoryDto = new CategoryDto();
                
                categoryDto.setLv1Sn( parntsClSn );
                categoryDto.setLv1Nm( "전체" );
                listDto.setCategory( categoryDto );
                
            }
        }
        
        
        ClassCategoryCl01ModDto classCategoryCl01ModDto = classCategoryCl01Service.findById( parntsClSn );
        
        if ( classCategoryCl01ModDto != null ) {
            
            if ( listDto.getCategory() != null ) {
                
                listDto.getCategory().setLv1Sn( parntsClSn );
                listDto.getCategory().setLv1Nm( classCategoryCl01ModDto.getClNm() );
                
            } else {
                CategoryDto categoryDto = new CategoryDto();
                
                categoryDto.setLv1Sn( parntsClSn );
                categoryDto.setLv1Nm( classCategoryCl01ModDto.getClNm() );
                listDto.setCategory( categoryDto );
            }
        }
    }
    
    @ResponseBody
    @GetMapping( BASIC_PATH + "/live/getListByParntsClSnAjax" )
    public Map<String, Object> getListByParntsClSnAjax( @ModelAttribute ClassCategoryCl02ListDto listDto ) {
        // 카테고리(parntsClSn) 로 커리큘럼 검색
        
        Map<String, Object> result = new HashMap<String, Object>();
        
        // 분류에서 설정된 categorySn값을 검색키워드에 맞게 검색조건값 setting ( ajax의 categorySn 을 대입 )
        // target = parntsClSn
        Long targetPk = listDto.getParntsClSn();
        if ( targetPk == null ) {
            if ( listDto.getCategory().getCategorySn() != null ) {
                listDto.setParntsClSn( listDto.getCategory().getCategorySn() );
            }
        }
        
        // 부모 클래스 일련번호로 커리큘럼검색
        List<ClassCategoryCl02ListDto> listDtos = null;
        
        if( listDto.getParntsClSn() != null && listDto.getParntsClSn() == 0  ) {
        	// 부모클래스 일련번호가 0 일 때 : 전체 검색
        	listDtos = classCategoryCl02Service.findAllByOrderByClSeq();
        } else if ( listDto.getParntsClSn() != null ) {
        	// 부모클래스 일련번호가 0 이 아닐 때 : 부모클래스일련번호로 검색 
        	listDtos = classCategoryCl02Service.findByParntsClSnOrderByClSeq( listDto.getParntsClSn() );
        }
        
        // list put
        result.put( "resultList", listDtos );
        
        return result;
    }
    
}
