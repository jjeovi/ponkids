package com.meta.ponkids.domain.system.ntt.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.meta.ponkids.domain.system.bbs.dto.BbsModDto;
import com.meta.ponkids.domain.system.bbs.service.BbsService;
import com.meta.ponkids.domain.system.file.service.AtchFileService;
import com.meta.ponkids.domain.system.ntt.dto.NttListDto;
import com.meta.ponkids.domain.system.ntt.dto.NttModDto;
import com.meta.ponkids.domain.system.ntt.dto.NttReplyListDto;
import com.meta.ponkids.domain.system.ntt.dto.NttReplyModDto;
import com.meta.ponkids.domain.system.ntt.dto.NttReplySaveReqDto;
import com.meta.ponkids.domain.system.ntt.dto.NttSaveReqDto;
import com.meta.ponkids.domain.system.ntt.service.NttReplyService;
import com.meta.ponkids.domain.system.ntt.service.NttService;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class NttController {
    

   private final NttService nttService;
   private final BbsService bbsService;
   private final NttReplyService nttReplyService;
   private final AtchFileService atchFileService;


   
   private final static String BASIC_PATH = "/admin/ntt";
 
    /**
     * methodName    : nttList
     * date           : 11/17/23
     * description    :
     */
    @GetMapping( BASIC_PATH + "/list" )
    public String nttList(
    		 @RequestParam(required = true) Long bbsSn,
    		 @ModelAttribute NttListDto nttListDto, 
    					@PageableDefault( size = 10 ) Pageable pageable,
    					Model model ) {
        
        
        // target object 조회
        model.addAttribute("bbsSn", bbsSn);
        
        nttListDto.setBbsSn(bbsSn);
        
    	// 게시물 조회 
        //Page<NttListDto> nttList = nttService.getNttList( nttListDto);
       /// model.addAttribute( "nttList", nttList );
       
        // 공지설정 목록 조회 
        List<NttListDto> noticeList = nttService.getNoticeList(bbsSn);
        model.addAttribute( "noticeList", noticeList );
        
    	// 목록 조회
        Page<NttListDto> resultList = nttService.getList( nttListDto, pageable );
        model.addAttribute( "resultList", resultList );
        
        // 검색 dto setting
        model.addAttribute( "searchDTO", nttListDto );
        
        // 기본 경로 setting
        model.addAttribute("basicPath", BASIC_PATH);
        
        //리스트형, 포토형 화면 다름 .
        String bbsSeCd = bbsService.getBbsSeCd(bbsSn);
        String screen  = "";
        if( bbsSeCd.equals("01")) { // 포토형
        	screen = "/photoList.html";
        } else {
        	screen = "/list";
        }
        

        return BASIC_PATH + screen;
    }
    
    
    
    @GetMapping(  BASIC_PATH  + "/regist" )
    public String nttRegist(  @RequestParam(required = true) Long bbsSn,
    	                      @RequestParam(required = true) String bbsSeCd, Model model ) {
        
       // model.addAttribute( new BbsSaveReqDto() );
        model.addAttribute("bbsSn", bbsSn);
        model.addAttribute("bbsSeCd", bbsSeCd);
        // 기본 경로 setting
        model.addAttribute("basicPath", BASIC_PATH);
        
        return BASIC_PATH + "/regist";
    }
    
    
    @PostMapping(BASIC_PATH  + "/insert")
    public String nttInsert( 
    	                   @RequestParam("file") MultipartFile files,
    		               @ModelAttribute NttSaveReqDto nttSaveReqDto,
    		               HttpServletRequest request , Model model ) throws IOException {
    	
    	
    	  // 첨부파일 존재시 파일 저장
        if(!files.isEmpty()){
        	nttSaveReqDto.setAtchFileSn(atchFileService.save(files));	// 파일 save (파일 개수 1개일 때 ) 
        }
    	
    	// save
        nttService.save(nttSaveReqDto,request);
        
        Long bbsSn = nttSaveReqDto.getBbsSn();
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 등록되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH +"/list?bbsSn="+ bbsSn);

        return "common/alert";
  }
    
    
    
    @GetMapping(value= {  BASIC_PATH + "/modify" } )	 
      public String modify( @RequestParam(required = true) Long nttSn,   @RequestParam(required = true) String bbsSeCd, Model model, 
    		  HttpServletRequest request ) throws IOException {
      
      // 권한 리스트
     // model.addAttribute( "authList", roleRepository.findAll() );
    	
     NttModDto targetDto = nttService.findByNttSn(nttSn);
    	
      // target object 조회
      model.addAttribute("targetDto", targetDto);
      
  	  //댓글 설정여부
  	  String replySetYn = bbsService.getSetReplySetYn(targetDto.getBbsSn());
  	  
  	  model.addAttribute("replySetYn", replySetYn);
  	  model.addAttribute("nttSn", nttSn);
  	  model.addAttribute("bbsSeCd", bbsSeCd);
  	  
  	 //댓글 설정 Y일 경우 
      if(replySetYn.equals("Y")) {
     	 // 댓글 목록 조회
    	  List<NttReplyListDto> replyList = nttReplyService.getList(nttSn);
      	  model.addAttribute( "replyList", replyList );
       }
      
      // 조회수 업데이트 
      nttService.update(nttSn ,request);
      

      // 기본 경로 setting
      model.addAttribute("basicPath", BASIC_PATH);
      
      
      String urlPath = request.getServletPath();
      String remainPath = ""; 

      if ( urlPath.split(BASIC_PATH)[1].startsWith("/modify") ) remainPath = "modify";
      
      
      return BASIC_PATH + "/" + remainPath;
      }
    
    
    @PostMapping(BASIC_PATH + "/update")
    public String update(
    		@RequestParam("file") MultipartFile files,
    		@ModelAttribute  NttModDto modDto, HttpServletRequest request
            ,
    		Model model ) throws IOException {
    	
  	    // 첨부파일 존재시 파일 저장
        if(!files.isEmpty()){
        	 // 기존에 첨부파일 있을시 삭제
            if( modDto.getAtchFileSnOri() != null ) {
                atchFileService.delete(modDto.getAtchFileSnOri());
            }
            
        	modDto.setAtchFileSn(atchFileService.save(files));	// 파일 save (파일 개수 1개일 때 ) 
        }
    	
    	
    	   nttService.nttUpdate(modDto,request);
    	   
    	   Long bbsSn = modDto.getBbsSn();
           
           // 메시지 출력 및 url 이동 처리
           model.addAttribute( "resultMsg", "정상적으로 수정되었습니다." );
           model.addAttribute( "moveUrl", BASIC_PATH +"/list?bbsSn="+ bbsSn);
    

           return "common/alert";
    }
    
    
    
    @PostMapping(BASIC_PATH  + "/nttReplyInsert")
    public String nttReplyInsert( 
    		               @ModelAttribute NttReplySaveReqDto nttReplySaveReqDto, HttpServletRequest request
    		               , Model model) {
      // save
         nttReplyService.save(nttReplySaveReqDto,request);
         
         Long nttSn = nttReplySaveReqDto.getNttSn();
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 댓글이 등록되었습니다." );
    	model.addAttribute( "moveUrl", BASIC_PATH +"/modify?nttSn="+ nttSn);

        return "common/alert";
  }
    
    
    /**
     * methodName    : answerReplyList
     * date           : 11/24/23
     * description    : id 답글조회 ajax
     */
    @ResponseBody
    @RequestMapping( value = "/reply/answerReplyList", method = { RequestMethod.GET } )
    public List answerReplyList( @RequestParam( "nttReplySn" ) Long nttReply) {
        
    	 // 댓글 목록 조회
        List<NttReplyListDto> answerReplyList = nttReplyService.getAnswerReplyList(nttReply);
        
        return answerReplyList;
    } 
    
    
    
    
    /**
     * methodName    : delete
     * date           : 11/24/23
     * description    : user delete method
     */
    @Transactional 
    @PostMapping( BASIC_PATH + "/delete" )
    public String delete(
            @RequestParam(required = true) Long nttSn,
            Model model ) {
     
    	// 해당 게시판에 게시물 있는지 조회 없으시 삭제 처리 
    	
    	
     // 삭제 처리
      nttService.deleteAllByNttSn( nttSn );
      
      // 메시지 출력 및 url 이동 처리
      model.addAttribute( "resultMsg", "정상적으로 삭제되었습니다." );
      model.addAttribute( "moveUrl", BASIC_PATH + "/list" );
      
      return "common/alert";

    }
    
    
    
    /**
     * methodName    : nttReplyUpdate
     * date           : 11/26/23
     * description    : id 답글조회 ajax
     */
    @ResponseBody
    @RequestMapping( value = "/reply/nttReplyUpdate", method = { RequestMethod.GET } )
    public String nttReplyUpdate( @RequestParam( "nttReplySn" ) Long nttReplySn , @RequestParam( "nttReplyCn" ) String nttReplyCn 
    		    ,@RequestParam( "nttSn" ) Long nttSn
    		    ,HttpServletRequest request ,Model model ) {
          
    	
    	 NttReplyModDto modDto =   new NttReplyModDto();
    	 
    	 modDto.setNttReplySn(nttReplySn);
    	 modDto.setNttReplyCn(nttReplyCn);
    	 
    	 // 댓글 수정
          nttReplyService.update(modDto,request);
           
 	    String resultMsg = "정상적으로 수정되었습니다.";
    	
    	return resultMsg;
      
    } 
    
    /**
     * methodName    : nttReplyDelete
     * date           : 11/26/23
     * description    : id 댓글 삭제
     */
    @ResponseBody
    @RequestMapping( value = "/reply/nttReplyDelete", method = { RequestMethod.GET } )
    public String nttReplyUpdate( @RequestParam( "nttReplySn" ) Long nttReplySn  
    		,@RequestParam( "nttSn" ) Long nttSn
    		,HttpServletRequest request ,Model model ) {
    	

    	// 댓글 수정
    	nttReplyService.deleteAllByNttReplySn(nttReplySn);
    	
    	// 메시지 출력 및 url 이동 처리
    	//model.addAttribute( "resultMsg", "정상적으로 삭제되었습니다." );
    	//model.addAttribute( "moveUrl", BASIC_PATH +"/modify?nttSn="+ nttSn);
    	String resultMsg = "정상적으로 삭제되었습니다.";
    	
    	return resultMsg;
    	
    	
    } 
    
    
    
}
