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

import javax.servlet.http.HttpServletRequest;


@Controller
@RequiredArgsConstructor
public class BbsController {
    
   private final BbsService bbsService;
   private final BbsRepository bbsRepository;
   
   private final static String BASIC_PATH = "/admin/bbs";
    
   /**
    * methodName    : bbsList
    * date           : 11/17/23
    * description    :
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
   
    
    
    @GetMapping(  BASIC_PATH  + "/regist" )
    public String regist( Model model ) {
        
        model.addAttribute( new BbsSaveReqDto() );
        
        // 기본 경로 setting
        model.addAttribute("basicPath", BASIC_PATH);
        
        return BASIC_PATH + "/regist";
    }
    
    
    @PostMapping("/admin/bbs/insert")
    public String insert( 
    		               @ModelAttribute BbsSaveReqDto bbsSaveReqDto, HttpServletRequest request
    		               , Model model) {
      // save
        bbsService.save(bbsSaveReqDto);
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 등록되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH +"/list" );

        return "common/alert";
  }
    
    
    @GetMapping(value= {  BASIC_PATH + "/modify" } )	 
      public String modify( @RequestParam(required = true) int bbsSn,  Model model, 
    		  HttpServletRequest request ) {
      
      // 권한 리스트
     // model.addAttribute( "authList", roleRepository.findAll() );
      
      // target object 조회
      model.addAttribute("targetDto", bbsService.findByBbsSn(bbsSn));
      
      // 기본 경로 setting
      model.addAttribute("basicPath", BASIC_PATH);
      
      
      String urlPath = request.getServletPath();
      String remainPath = ""; 

      if ( urlPath.split(BASIC_PATH)[1].startsWith("/modify") ) remainPath = "modify";
      
      
      return BASIC_PATH + "/" + remainPath;
      }
    
    @PostMapping(BASIC_PATH + "/update")
    public String update(
    		@ModelAttribute  BbsModDto modDto,
    		Model model ) {
    	
    	   bbsService.update(modDto);
           
           // 메시지 출력 및 url 이동 처리
           model.addAttribute( "resultMsg", "정상적으로 수정되었습니다." );
           model.addAttribute( "moveUrl", BASIC_PATH +"/list" );

           return "common/alert";
    }
    

    

}
