package com.meta.ponkids.domain.ntt.controller;

import com.meta.ponkids.domain.bbs.dto.BbsModDto;
import com.meta.ponkids.domain.bbs.service.BbsService;
import com.meta.ponkids.domain.ntt.dto.*;
import com.meta.ponkids.domain.ntt.service.NttReplyService;
import com.meta.ponkids.domain.ntt.service.NttService;
import com.meta.ponkids.domain.system.cmmnCd.dto.CmmnCdDetailModDto;
import com.meta.ponkids.domain.system.cmmnCd.service.CmmnCdDetailService;
import com.meta.ponkids.domain.system.file.entity.AtchFileDetail;
import com.meta.ponkids.domain.system.file.repository.AtchFileDetailRepository;
import com.meta.ponkids.domain.system.file.service.AtchFileDetailService;
import com.meta.ponkids.domain.system.file.service.AtchFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * className      : NttController
 * author         : ehlee
 * date           : 2023-12-01
 * description    : class of 게시물관리 Controller
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-12-01        ehlee             최초 생성
 */
@Controller
@RequiredArgsConstructor
public class NttController {
    
    public static String USER_VIEW_PATH;
    
    // path 경로 : pon
    @Value( "${key.default.directoryPath.user}" )
    public void setUserViewPath(String value) {
        USER_VIEW_PATH = value;
    }
    
    private final static String BASIC_DOMAIN = "ntt";
    private final static String BASIC_PATH = "/" + BASIC_DOMAIN;	// USER_VIEW_PATH + "/" + BASIC_DOMAIN 는  앞의 "/" 를 제거해야 함.
    
    private final NttService nttService;
    private final BbsService bbsService;
    private final NttReplyService nttReplyService;
    private final AtchFileService atchFileService;
    private final AtchFileDetailService atchFileDetailService;
    private final AtchFileDetailRepository atchFileDetailRepository;
    
    private final CmmnCdDetailService cmmnCdDetailService;
    
    /**
     * methodName    : nttList
     * date          : 23/12/04
     * description   : ntt list method
     */
    @GetMapping( BASIC_PATH + "/{mcd}/{bbsSn}/list" )
    public String nttList( @PathVariable Long bbsSn,
                           @ModelAttribute NttListDto nttListDto,
                           @PathVariable String mcd,
                           @PageableDefault( size = 8 ) Pageable pageable,
                           Model model ) {
        
        // target object 조회
        model.addAttribute( "bbsSn", bbsSn );
        
        nttListDto.setBbsSn( bbsSn );
        
        // 게시판 구분 코드 찾기
        BbsModDto bbsModDto = bbsService.findByBbsSn( bbsSn );
        
        // 게시판 이 존재하지 않을 경우
        if ( bbsModDto == null || bbsModDto.getBbsSeCd() == null || bbsModDto.getBbsSeCd().equals("") ) {
            // 메시지 출력 및 url 이동 처리
            model.addAttribute( "resultMsg", "게시판 ID를 다시 확인해주세요." );
            model.addAttribute( "moveUrl", "/" );
            return "common/alert";
        }
        
        // 게시판 구분 코드 상세 조회
        CmmnCdDetailModDto bbsSeCdDto = cmmnCdDetailService.findTop1ByCdNmAndCdDetailVal1( "BBS_SE_CD", bbsModDto.getBbsSeCd() );
        
        int defaultPageSize = 8;
        // 게시판 bbsSeCdDto 가 없을 시.
        if ( bbsSeCdDto == null ) {
            // 메시지 출력 및 url 이동 처리
            model.addAttribute( "resultMsg", "게시판 타입 정보를 찾을 수 없습니다. 관리자에게 문의해주세요." );
            model.addAttribute( "moveUrl", "/" );
            return "common/alert";
        } else {
        	String cdDetailVal2 = bbsSeCdDto.getCdDetailVal2();
        	if ( cdDetailVal2 != null && cdDetailVal2.matches("[+-]?\\d*(\\.\\d+)?")) {
        		defaultPageSize = Integer.parseInt( cdDetailVal2 );
        		
        	}
        	
        }
        Pageable customPageable = PageRequest.of(pageable.getPageNumber(), defaultPageSize );
        
        // 공지설정 목록 조회
        List<NttListDto> noticeList = nttService.getNoticeList( bbsSn );
        model.addAttribute( "noticeList", noticeList );
        
        // 목록 조회
        Page<NttListDto> resultList = nttService.getList( nttListDto, customPageable );
        model.addAttribute( "resultList", resultList );
        
        // 검색 dto setting
        model.addAttribute( "searchDTO", nttListDto );
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        String viewName = bbsModDto.getBbsSeCd();
        return USER_VIEW_PATH + "/" + BASIC_DOMAIN + "/" + viewName;
    }
    
    /**
     * methodName    : modify
     * date          : 23/12/02
     * description   : ntt detail or ntt modify method
     */
    @GetMapping( value = {
            BASIC_PATH + "/{mcd}/{bbsSn}/detail",
            BASIC_PATH + "/{mcd}/{bbsSn}/modify" } )
    public String modify( @RequestParam( required = true ) Long pk,
                          @PathVariable Long bbsSn,
                          @PathVariable String mcd,
                          Model model,
                          HttpServletRequest request ) throws IOException {
        
        // target object 조회
        NttModDto targetDto = nttService.findByNttSn( pk );
        
        //댓글 설정여부
        String replySetYn = bbsService.getSetReplySetYn( targetDto.getBbsSn() );
        
        model.addAttribute( "bbsSn", bbsSn );
        model.addAttribute( "targetDto", targetDto );
        model.addAttribute( "replySetYn", replySetYn );
        model.addAttribute( "nttSn", pk );
        // 게시판 구분 코드 찾기
        BbsModDto bbsModDto = bbsService.findByBbsSn( bbsSn );
        
        
        //댓글 설정 Y일 경우
        if ( replySetYn.equals( "Y" ) ) {
            // 댓글 목록 조회
            List<NttReplyListDto> replyList = nttReplyService.getList( pk );
            model.addAttribute( "replyList", replyList );
        }
        
        // List<AtchFileDetail>  atchFileList = null;
        //첨부파일 존재시
        if ( targetDto.getCnAtchFileSn() != null ) {
            List<AtchFileDetail> atchFileList = atchFileDetailService.getList( targetDto.getCnAtchFileSn() );
            model.addAttribute( "atchFileList", atchFileList );
        }
        
        // 조회수 업데이트
        nttService.update( pk, request );
        
        // 기본 경로 setting
        model.addAttribute( "basicPath", BASIC_PATH );
        
        String urlPath = request.getServletPath();
        String remainPath = "";
        
        if ( urlPath.split( BASIC_PATH )[ 1 ].endsWith( "/detail" ) ) remainPath = "detail";
        if ( urlPath.split( BASIC_PATH )[ 1 ].endsWith( "/modify" ) ) remainPath = "modify";
        
        return USER_VIEW_PATH + "/" + BASIC_DOMAIN + "/" + remainPath;
    }
    
    
}
