package com.meta.ponkids.domain.system.bbs.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.meta.ponkids.domain.system.bbs.dto.BbsListDto;
import com.meta.ponkids.domain.system.bbs.dto.BbsModDto;
import com.meta.ponkids.domain.system.bbs.dto.BbsSaveReqDto;
import com.meta.ponkids.domain.system.bbs.repository.BbsRepository;
import com.meta.ponkids.domain.system.bbs.service.BbsService;
import com.meta.ponkids.domain.system.ntt.repository.NttRepository;
import com.meta.ponkids.domain.system.ntt.service.NttService;
import com.querydsl.jpa.impl.JPAQuery;

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
 * 2023-12-01        jjeoV             최초 생성
 */
@Controller
@RequiredArgsConstructor
public class BbsController {
    
   private final BbsService bbsService;
   private final NttRepository nttRepository;
   
   private final static String BASIC_PATH = "/admin/bbs";
    
   /**
    * methodName    : bbsList
    * date          : 23/12/02
    * description   : bbs list method
    */
   @GetMapping( BASIC_PATH + "/list" )
   public String list( @ModelAttribute BbsListDto bbsListDto, 
   					@PageableDefault( size = 10 ) Pageable pageable,
   					Model model ) {
       // 목록 조회
       Page<BbsListDto> resultList = bbsService.getList( bbsListDto, pageable );
       model.addAttribute( "resultList", resultList );
       
       // 검색 dto setting
       model.addAttribute( "searchDTO", bbsListDto );
       
       // 기본 경로 setting
       model.addAttribute("basicPath", BASIC_PATH);

       return BASIC_PATH + "/list";
   }
   
   
   /**
    * methodName  : regist
    * date        : 23/12/02
    * description : bbs regist method
    */
    @GetMapping(  BASIC_PATH  + "/regist" )
    public String regist( Model model ) {
        
        model.addAttribute( new BbsSaveReqDto() );
        
        // 기본 경로 setting
        model.addAttribute("basicPath", BASIC_PATH);
        
        return BASIC_PATH + "/regist";
    }
    
    
    
    /**
     * methodName    : insert
     * date          : 23/12/02
     * description   : bbs insert method
     */
    @Transactional
    @PostMapping("/admin/bbs/insert")
    public String insert( 
    	   @ModelAttribute BbsSaveReqDto bbsSaveReqDto, 
    	   HttpServletRequest request,
    	   Model model ) {
      
      // 저장
      bbsService.save(bbsSaveReqDto, request);
        
      // 메시지 출력 및 url 이동 처리
      model.addAttribute( "resultMsg", "정상적으로 등록되었습니다." );
      model.addAttribute( "moveUrl", BASIC_PATH +"/list" );

      return "common/alert";
     }
    
   
    /**
     * methodName    : modify
     * date          : 23/12/02
     * description   : bbs detail or bbs modify method
     */
    @GetMapping(value= { 
    		BASIC_PATH + "/detail" ,
            BASIC_PATH + "/modify" } ) 
    public String modify( 
    	   @RequestParam(required = true) Long bbsSn,
    	   HttpServletRequest request, 
    	   Model model ) {
      
      // target object 조회
      model.addAttribute("targetDto", bbsService.findByBbsSn(bbsSn));
      
      // 기본 경로 setting
      model.addAttribute("basicPath", BASIC_PATH);
      
      
      String urlPath = request.getServletPath();
      String remainPath = ""; 

      if ( urlPath.split(BASIC_PATH)[1].startsWith("/modify") ) remainPath = "modify";
      
      
      return BASIC_PATH + "/" + remainPath;
    }

    
    /**
     * methodName    : update
     * date          : 23/12/02
     * description   : user update method
     */
    @PostMapping(BASIC_PATH + "/update")
    public String update(
    	   @ModelAttribute  BbsModDto modDto,
    	   HttpServletRequest request,
    	   Model model ) {
    	
      bbsService.update(modDto,request);
           
      // 메시지 출력 및 url 이동 처리
      model.addAttribute( "resultMsg", "정상적으로 수정되었습니다." );
      model.addAttribute( "moveUrl", BASIC_PATH +"/list" );

      return "common/alert";
    }
    
    
    /**
     * methodName    : delete
     * date          : 23/12/02
     * description   : bbs delete method
     */
    @Transactional
    @PostMapping( BASIC_PATH + "/delete" )
    public String delete(
            @RequestParam(required = true) Long bbsSn,
            Model model ) {
    	
     String msg = "";
    
     // 해당 게시판에 게시물 있는지 조회 없으시 삭제 처리 
     int count = nttRepository.getExistsNtt(bbsSn);
    
     if( count == 0 ) {
         // 삭제 처리
         bbsService.deleteAllByBbsSn( bbsSn );
         msg = "정상적으로 삭제되었습니다.";
     } else {
    	 msg = "게시물이 존재합니다. 게시판을 삭제 할수 없습니다.";
     }
      
      // 메시지 출력 및 url 이동 처리
      model.addAttribute( "resultMsg", msg);
      model.addAttribute( "moveUrl", BASIC_PATH + "/list" );
      
      return "common/alert";

    }
}
