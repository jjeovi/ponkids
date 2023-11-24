package com.meta.ponkids.domain.system.ntt.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import com.meta.ponkids.domain.system.ntt.dto.NttListDto;
import com.meta.ponkids.domain.system.ntt.dto.NttReplyListDto;
import com.meta.ponkids.domain.system.ntt.dto.NttReplySaveReqDto;
import com.meta.ponkids.domain.system.ntt.dto.NttSaveReqDto;
import com.meta.ponkids.domain.system.ntt.service.NttReplyService;
import com.meta.ponkids.domain.system.ntt.service.NttService;

import javax.servlet.http.HttpServletRequest;


import java.util.List;

@Controller
@RequiredArgsConstructor
public class NttController {
    

   private final NttService nttService;
   private final NttReplyService nttReplyService;


   
   private final static String BASIC_PATH = "/admin/ntt";
 
    /**
     * methodName    : nttList
     * date           : 11/17/23
     * description    :
     */
    @GetMapping( BASIC_PATH + "/list" )
    public String nttList(
    		 @RequestParam(required = true) int bbsSn,
    		 @ModelAttribute NttListDto nttListDto, 
    					@PageableDefault( size = 10 ) Pageable pageable,
    					Model model ) {
        
        
        // target object 조회
        model.addAttribute("bbsSn", bbsSn);
        
    	//게시물 조회 
        //Page<NttListDto> nttList = nttService.getNttList( nttListDto);
       /// model.addAttribute( "nttList", nttList );
        
        
    	// 목록 조회
        Page<NttListDto> resultList = nttService.getList( nttListDto, pageable );
        model.addAttribute( "resultList", resultList );
        
        // 검색 dto setting
        model.addAttribute( "searchDTO", nttListDto );
        
        // 기본 경로 setting
        model.addAttribute("basicPath", BASIC_PATH);

        return BASIC_PATH + "/list";
    }
    
    
    
    @GetMapping(  BASIC_PATH  + "/regist" )
    public String nttRegist( Model model ) {
        
       // model.addAttribute( new BbsSaveReqDto() );
        
        // 기본 경로 setting
        model.addAttribute("basicPath", BASIC_PATH);
        
        return BASIC_PATH + "/regist";
    }
    
    
    @PostMapping(BASIC_PATH  + "/insert")
    public String nttInsert( 
    		               @ModelAttribute NttSaveReqDto nttSaveReqDto, HttpServletRequest request
    		               , Model model) {
      // save
        nttService.save(nttSaveReqDto);
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 등록되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH +"/list" );

        return "common/alert";
  }
    
    
    
    @GetMapping(value= {  BASIC_PATH + "/modify" } )	 
      public String modify( @RequestParam(required = true) int nttSn,  Model model, 
    		  HttpServletRequest request ) {
      
      // 권한 리스트
     // model.addAttribute( "authList", roleRepository.findAll() );
      
      // target object 조회
      model.addAttribute("targetDto", nttService.findByNttSn(nttSn));
      
      
  	 // 댓글 목록 조회
      List<NttReplyListDto> replyList = nttReplyService.getList(nttSn);
      model.addAttribute( "replyList", replyList );
      

      // 기본 경로 setting
      model.addAttribute("basicPath", BASIC_PATH);
      
      
      String urlPath = request.getServletPath();
      String remainPath = ""; 

      if ( urlPath.split(BASIC_PATH)[1].startsWith("/modify") ) remainPath = "modify";
      
      
      return BASIC_PATH + "/" + remainPath;
      }  
    
    
    
    @PostMapping(BASIC_PATH  + "/nttReplyInsert")
    public String nttReplyInsert( 
    		               @ModelAttribute NttReplySaveReqDto nttReplySaveReqDto, HttpServletRequest request
    		               , Model model) {
      // save
         nttReplyService.save(nttReplySaveReqDto);
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 댓글이 등록되었습니다." );
        ///model.addAttribute( "moveUrl", BASIC_PATH +"/list" );

        return "common/alert";
  }
    
    
    /**
     * methodName    : answerReplyList
     * date           : 11/24/23
     * description    : id 답글조회 ajax
     */
    @ResponseBody
    @RequestMapping( value = "/reply/answerReplyList", method = { RequestMethod.GET } )
    public Model answerReplyList( @RequestParam( "nttReplySn" ) int nttReplySn , Model model) {
        
    	
    	 // 댓글 목록 조회
        List<NttReplyListDto> answerReplyList = nttReplyService.getAnswerReplyList(nttReplySn);
        model.addAttribute( "answerReplyList", answerReplyList );
        
        return model;
    }  
    
    
}
