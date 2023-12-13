package com.meta.ponkids.domain.system.ntt.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.meta.ponkids.domain.system.bbs.service.BbsService;
import com.meta.ponkids.domain.system.file.entity.AtchFileDetail;
import com.meta.ponkids.domain.system.file.repository.AtchFileDetailRepository;
import com.meta.ponkids.domain.system.file.service.AtchFileDetailService;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    

   private final NttService nttService;
   private final BbsService bbsService;
   private final NttReplyService nttReplyService;
   private final AtchFileService atchFileService;
   private final AtchFileDetailService atchFileDetailService;
   private final AtchFileDetailRepository  atchFileDetailRepository;

   
   
   private final static String BASIC_PATH = "/admin/ntt";
 
    /**
     * methodName    : nttList
     * date          : 23/12/04
     * description   : ntt list method
     */
    @GetMapping( BASIC_PATH + "/{mcd}/list" )
    public String nttList( @RequestParam(required = true) Long bbsSn,
    		               @ModelAttribute NttListDto nttListDto, 
    		               @PathVariable String mcd,
    					   @PageableDefault( size = 10 ) Pageable pageable,
    					  Model model ) {
        
        // target object 조회
        model.addAttribute("bbsSn", bbsSn);
        
        nttListDto.setBbsSn(bbsSn);
       
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
    
    
    /**
     * methodName  : regist
     * date        : 23/12/02
     * description : ntt regist method
     */
    @GetMapping(  BASIC_PATH  + "/{mcd}/regist" )
    public String nttRegist(  @RequestParam(required = true) Long bbsSn,
    	                      @RequestParam(required = true) String bbsSeCd,
    	                      Model model ) {
        
        model.addAttribute("bbsSn", bbsSn);
        model.addAttribute("bbsSeCd", bbsSeCd);
        
        // 기본 경로 setting
        model.addAttribute("basicPath", BASIC_PATH);
        
        return BASIC_PATH + "/regist";
    }
    
    
    
    /**
     * methodName    : insert
     * date          : 23/12/02
     * description   : ntt insert method
     */
    @Transactional
    @PostMapping(BASIC_PATH  + "/{mcd}/insert")
    public String nttInsert( 
    	                   //@RequestParam("file") MultipartFile files,
    	                   //@RequestParam("multiFile") List<MultipartFile> multiFileList,
    	                   
    	                   @RequestParam(required = false , defaultValue= "") MultipartFile file,
    	                   @RequestParam(required = false , defaultValue= "") List<MultipartFile> multiFile,
    		               @ModelAttribute NttSaveReqDto nttSaveReqDto,
    		               @PathVariable String mcd,
    		               HttpServletRequest request , Model model ) throws IOException {
    	
    	
    	// 썸네일 이미지 존재시 파일 저장
        if(!file.isEmpty()){
        	nttSaveReqDto.setAtchFileSn(atchFileService.save(file));	// 파일 save (파일 개수 1개일 때 ) 
        }
        
        // 첨부파일  존재시 파일 저장
        if(!multiFile.isEmpty()){
        	
        	nttSaveReqDto.setCnAtchFileSn(atchFileService.multifileSave(multiFile,null));	// 파일 save (파일여러개 ) 
        }
    	
    	// save
        nttService.save(nttSaveReqDto,request);
        
        Long bbsSn = nttSaveReqDto.getBbsSn();
        
        // 메시지 출력 및 url 이동 처리
        model.addAttribute( "resultMsg", "정상적으로 등록되었습니다." );
        model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd +"/list?bbsSn="+ bbsSn);

        return "common/alert";
     }
    
    
    
    /**
     * methodName    : modify
     * date          : 23/12/02
     * description   : ntt detail or ntt modify method
     */
    @GetMapping(value= {
    		BASIC_PATH + "/{mcd}/detail",
            BASIC_PATH + "/{mcd}/modify" } )
      public String modify( @RequestParam(required = true)  Long nttSn, 
    		                @RequestParam(required = true) String bbsSeCd, 
    		                @PathVariable String mcd,
    		                Model model, 
    		                HttpServletRequest request ) throws IOException {
      
      // target object 조회
      NttModDto targetDto = nttService.findByNttSn(nttSn);
      
  	  //댓글 설정여부
  	  String replySetYn = bbsService.getSetReplySetYn(targetDto.getBbsSn());
  	  
  	  model.addAttribute("targetDto", targetDto);
  	  model.addAttribute("replySetYn", replySetYn);
  	  model.addAttribute("nttSn", nttSn);
  	  model.addAttribute("bbsSeCd", bbsSeCd);
  	  
  	 //댓글 설정 Y일 경우 
      if(replySetYn.equals("Y")) {
     	 // 댓글 목록 조회
    	  List<NttReplyListDto> replyList = nttReplyService.getList(nttSn);
      	  model.addAttribute( "replyList", replyList );
       }
      
     // List<AtchFileDetail>  atchFileList = null;
      //첨부파일 존재시 
      if(targetDto.getCnAtchFileSn() != null) {
    	  List<AtchFileDetail>  atchFileList = atchFileDetailService.getList(targetDto.getCnAtchFileSn());
          model.addAttribute("atchFileList", atchFileList);
      }

      
      // 조회수 업데이트 
      nttService.update(nttSn ,request);

      // 기본 경로 setting
      model.addAttribute("basicPath", BASIC_PATH);
      
      
      String urlPath = request.getServletPath();
      String remainPath = ""; 

      if ( urlPath.split( BASIC_PATH )[ 1 ].endsWith( "/detail" ) ) remainPath = "detail";
      if ( urlPath.split( BASIC_PATH )[ 1 ].endsWith( "/modify" ) ) remainPath = "modify";
      
      
      return BASIC_PATH + "/" + remainPath;
      
      }
    
    
    /**
     * methodName    : update
     * date          : 23/12/02
     * description   : bbs update method
     */
    @Transactional
    @PostMapping(BASIC_PATH + "/{mcd}/update")
    public String update(
    		             @RequestParam("file") MultipartFile files,
    	                 @RequestParam("multiFile") List<MultipartFile> multiFileList,
                         @PathVariable String mcd,
    		             @ModelAttribute  NttModDto modDto, HttpServletRequest request,
    		             Model model ) throws IOException {
    	
        // 첨부파일 존재시 파일 저장
        if(!files.isEmpty()){
            // 기존에 첨부파일 있을시 삭제
            if( modDto.getAtchFileSnOri() != null ) {
                atchFileService.delete(modDto.getAtchFileSnOri());
            }
            
            // 첨부파일 저장
            modDto.setAtchFileSn(atchFileService.save(files));	// 파일 save (파일 개수 1개일 때 )
        } else {
            // 첨부파일 존재하지않을 때
            // 기존 첨부파일이 있었는데 삭제됬다면 삭제처리
            if( modDto.getAtchFileSnOri()!= null && modDto.getAtchFileSn() == null ) {
                atchFileService.delete(modDto.getAtchFileSnOri());
                modDto.setAtchFileSn( null );
            }
        }
        
        
        Long cnAtchFileSn = modDto.getCnAtchFileSn();
     	
  	    // 첨부파일  존재시 파일 저장
        if( cnAtchFileSn != null){ // 기존 첨부파일 있을시
        	 // 첨부파일  존재시 파일 저장
        	 if(  multiFileList.get(0).getSize() != 0){
            	atchFileService.multifileSave(multiFileList,cnAtchFileSn);	// 파일 save (파일여러개 ) + 추가 저장
            } else {
            	// 기존 첨부파일 모두 삭제 됬을 경우?
              	List<AtchFileDetail>  atchFileList = atchFileDetailService.getList(cnAtchFileSn);
                if(atchFileList.isEmpty()) {
                	atchFileDetailRepository.deleteByAtchFileDetailPk_AtchFileSn(cnAtchFileSn); // 부모 테이블 삭제 처리
                   	modDto.setCnAtchFileSn(null);	// 파일 save (파일여러개 ) 
                }
            }

        } else { //기존 첨부파일 없을시 신규로 추가
        	
            // 첨부파일  존재시 파일 저장
        	 if(  multiFileList.get(0).getSize() != 0){
            	modDto.setCnAtchFileSn(atchFileService.multifileSave(multiFileList,null));
            }
  
        }
    	  // 게시물 업데이트
    	   nttService.nttUpdate(modDto,request);
    	   
    	   Long bbsSn = modDto.getBbsSn();
           
           // 메시지 출력 및 url 이동 처리
           model.addAttribute( "resultMsg", "정상적으로 수정되었습니다." );
           model.addAttribute( "moveUrl", BASIC_PATH  + "/" + mcd +"/list?bbsSn="+ bbsSn);
    

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
            @RequestParam(required = true) Long nttSn,
            @PathVariable String mcd,
            Model model ) {
    	
     NttModDto targetDto = nttService.findByNttSn(nttSn);
     // 삭제 처리
      nttService.deleteAllByNttSn( nttSn );
      

      Long bbsSn  = targetDto.getBbsSn();
      
      // 메시지 출력 및 url 이동 처리
      model.addAttribute( "resultMsg", "정상적으로 삭제되었습니다." );
      model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list?bbsSn=" + bbsSn );
      
      return "common/alert";

    }
    

    /**
     * methodName    : fnReplyInsert
     * date           : 11/26/23
     * description    : id 댓글등록 ajax
     */
    @ResponseBody
    @RequestMapping( value = "/reply/nttReplyInsert" )
    public List nttReplyInsert(@RequestParam( "nttSn" ) Long nttSn,
    		                   @RequestParam( "parntsReplySn" ) Long parntsReplySn,         
    		                   @RequestParam( "nttReplyCn" ) String nttReplyCn,  
    		                   @RequestParam( "gubun" ) String gubun, 
    		                   HttpServletRequest request ) {
    	
    	NttReplySaveReqDto nttReplySaveReqDto =   new NttReplySaveReqDto();
    	nttReplySaveReqDto.setNttSn(nttSn);
    	nttReplySaveReqDto.setNttReplyCn(nttReplyCn);
 
     	
    	if(gubun.equals("A")){ // A : 댓글 등록 
    	
    		 nttReplySaveReqDto.setParntsReplySn((long) 0);
    		 nttReplySaveReqDto.setStep(1);
    	     // 댓글 등록 
    	     nttReplyService.save(nttReplySaveReqDto,request);
    	     // 댓글 목록 조회
    	  	 List<NttReplyListDto> replyList = nttReplyService.getList(nttSn);
    	  	 
    	  	 return replyList;
    	} else {    //  B: 답글 등록
    		
    		 nttReplySaveReqDto.setParntsReplySn(parntsReplySn);
    		 nttReplySaveReqDto.setStep(2);
    		 //답글 등록 
 	    	 nttReplyService.save(nttReplySaveReqDto,request);
    		 // 답글 목록 조회
    	     List<NttReplyListDto> answerReplyList = nttReplyService.getAnswerReplyList(parntsReplySn);
    	        
    	    return answerReplyList;
    	}
        
      
      
    } 
    
    
    
    /**
     * methodName    : nttReplyUpdate
     * date           : 11/26/23
     * description    : id 답글조회 ajax
     */
    @ResponseBody
    @RequestMapping( value = "/reply/nttReplyUpdate", method = { RequestMethod.GET } )
    public List nttReplyUpdate( @RequestParam( "nttReplySn" ) Long nttReplySn,
    		                    @RequestParam( "nttReplyCn" ) String nttReplyCn, 
    		                    @RequestParam( "nttSn" ) Long nttSn,
    		                    HttpServletRequest request  ) {
          
    	
    	 NttReplyModDto modDto =   new NttReplyModDto();
    	 
    	 modDto.setNttReplySn(nttReplySn);
    	 modDto.setNttReplyCn(nttReplyCn);
    	 
    	 // 댓글 OR 답글 수정
          nttReplyService.update(modDto,request);
          
     	 // 댓글 목록 조회
    	 List<NttReplyListDto> replyList = nttReplyService.getList(nttSn);

    	
    	 return replyList;
      
    } 
    
    /**
     * methodName    : nttReplyDelete
     * date           : 11/26/23
     * description    : id 댓글 삭제
     */
    @ResponseBody
    @RequestMapping( value = "/reply/nttReplyDelete", method = { RequestMethod.GET } )
    public List nttReplyUpdate( @RequestParam( "nttReplySn" ) Long nttReplySn,  
    		                    @RequestParam( "nttSn" ) Long nttSn,
    		                    HttpServletRequest request  ) {

    	// 댓글 삭제
    	nttReplyService.deleteAllByNttReplySn(nttReplySn);

    	List<NttReplyListDto> replyList = nttReplyService.getList(nttSn);
    	
    	return replyList;
    	
    	
    }   
    
    
    /**
     * methodName    : answerReplyList
     * date           : 11/24/23
     * description    : id 답글조회 ajax
     */
    @ResponseBody
    @RequestMapping( value = "/reply/answerReplyList", method = { RequestMethod.GET } )
    public List answerReplyList(@RequestParam( "nttReplySn" ) Long nttReplySn) {

    	// 답글 목록 조회
        List<NttReplyListDto> answerReplyList = nttReplyService.getAnswerReplyList(nttReplySn); //부모 키 
          
        return answerReplyList;
      
    } 
    
}
