package com.meta.ponkids.domain.bbs.controller;

import com.meta.ponkids.domain.bbs.dto.BbsSaveReqDto;
import com.meta.ponkids.domain.bbs.service.BbsService;
import com.meta.ponkids.domain.bbs.dto.BbsListDto;
import com.meta.ponkids.domain.bbs.dto.BbsModDto;
import com.meta.ponkids.domain.ntt.repository.NttRepository;
import com.meta.ponkids.domain.system.cmmnCd.service.CmmnCdDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;


/**
 * className      : BbsController
 * author         : ehlee
 * date           : 2023-12-01
 * description    : class of 게시판관리 Controller
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-12-01        ehlee             최초 생성
 */
@Controller
@RequiredArgsConstructor
public class BbsAdmController {
    
    private final static String BASIC_VIEW_PATH = "admin/bbs";
    private final static String BASIC_PATH = "/" + BASIC_VIEW_PATH;	// BASIC_VIEW_PATH 는  앞의 "/" 를 제거해야 함.
    private final BbsService bbsService;
    private final NttRepository nttRepository;
    
    private final CmmnCdDetailService cmmnCdDetailService;
    
    
    /**
     * methodName    : bbsList
     * date          : 23/12/11
     * description   : bbs list method
     */
    @GetMapping( BASIC_PATH + "/{mcd}/list" )
    public String list( @ModelAttribute BbsListDto bbsListDto,
                        @PageableDefault( size = 10 ) Pageable pageable,
                        @PathVariable String mcd,
                        Model model ) {
        // 목록 조회
        Page<BbsListDto> resultList = bbsService.getList( bbsListDto, pageable );
        model.addAttribute( "resultList", resultList );
        
        // 검색 dto setting
        model.addAttribute( "searchDTO", bbsListDto );
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        return BASIC_VIEW_PATH + "/list";
    }
    
    
    /**
     * methodName  : regist
     * date        : 23/12/02
     * description : bbs regist method
     */
    @GetMapping( BASIC_PATH + "/{mcd}/regist" )
    public String regist( Model model, @PathVariable String mcd ) {
        
        model.addAttribute( new BbsSaveReqDto() );
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        // 게시판 구분 코드 list ( 목록형, 포토형, ... )
        model.addAttribute( "bbsSeCdList", cmmnCdDetailService.getList( "BBS_SE_CD" ) );   // 게시판 구분 코드 리스트
        
        
        
        return BASIC_VIEW_PATH + "/regist";
    }
    
    
    /**
     * methodName    : insert
     * date          : 23/12/02
     * description   : bbs insert method
     */
    @Transactional
    @PostMapping( BASIC_PATH + "/{mcd}/insert" )
    public String insert(
            @ModelAttribute BbsSaveReqDto bbsSaveReqDto,
            @PathVariable String mcd,
            HttpServletRequest request,
            Model model ) {
        
        // 저장
        bbsService.save( bbsSaveReqDto, request );
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 등록되었습니다" );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
        
        return "common/alert";
    }
    
    
    /**
     * methodName    : modify
     * date          : 23/12/02
     * description   : bbs detail or bbs modify method
     */
    @GetMapping( value = {
            BASIC_PATH + "/{mcd}/detail",
            BASIC_PATH + "/{mcd}/modify" } )
    public String modify(
            @RequestParam( required = true ) Long bbsSn,
            @PathVariable String mcd,
            HttpServletRequest request,
            Model model ) {
        
        // target object 조회
        model.addAttribute( "targetDto", bbsService.findByBbsSn( bbsSn ) );
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        
        String urlPath = request.getServletPath();
        String remainPath = "";
        
        if ( urlPath.split( BASIC_PATH )[ 1 ].endsWith( "/detail" ) ) remainPath = "detail";
        if ( urlPath.split( BASIC_PATH )[ 1 ].endsWith( "/modify" ) ) remainPath = "modify";
        
        
        return BASIC_VIEW_PATH + "/" + remainPath;
    }
    
    
    /**
     * methodName    : update
     * date          : 23/12/02
     * description   : bbs update method
     */
    @Transactional
    @PostMapping( BASIC_PATH + "/{mcd}/update" )
    public String update(
            @ModelAttribute BbsModDto modDto,
            @PathVariable String mcd,
            HttpServletRequest request,
            Model model ) {
        
        bbsService.update( modDto, request );
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 수정되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
        
        return "common/alert";
    }
    
    
    /**
     * methodName    : delete
     * date          : 23/12/02
     * description   : bbs delete method
     */
    @Transactional
    @PostMapping( BASIC_PATH + "/{mcd}/delete" )
    public String delete(
            @RequestParam( required = true ) Long bbsSn,
            @PathVariable String mcd,
            Model model ) {
        
        String msg = "";
        
        // 해당 게시판에 게시물 있는지 조회 없으시 삭제 처리
        int count = nttRepository.getExistsNtt( bbsSn );
        
        if ( count == 0 ) {
            // 삭제 처리
            bbsService.deleteAllByBbsSn( bbsSn );
            msg = "정상적으로 삭제되었습니다.";
        } else {
            msg = "게시물이 존재합니다. 게시판을 삭제할 수 없습니다.";
        }
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", msg );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
        
        return "common/alert";
        
    }
}
