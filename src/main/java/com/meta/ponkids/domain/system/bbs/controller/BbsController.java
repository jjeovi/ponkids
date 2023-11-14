package com.meta.ponkids.domain.system.bbs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.meta.ponkids.domain.system.bbs.dto.BbsSaveReqDto;
import com.meta.ponkids.domain.system.bbs.repository.BbsRepository;
import com.meta.ponkids.domain.system.bbs.service.BbsService;
import com.meta.ponkids.domain.user.login.dto.UserSaveReqDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BbsController {
    
   private final BbsService bbsService;
   private final BbsRepository bbsRepository;

    
    /**
     * methodName    : bbsList
     * date           : 10/28/23
     * description    :
     */
    @GetMapping( "/admin/bbs/list" )
    public String bbsList() {
    	
    
        
        return "admin/bbs/list";
    }
    
    @GetMapping( "/admin/bbs/insert" )
    public String bbsInsert( Model model ) {
        
        model.addAttribute( new BbsSaveReqDto() );
        
        
        //model.addAttribute( "authList", roleRepository.findAll() );
        
        
        return "admin/bbs/insert";
    }
    
    
    @PostMapping("/admin/bbs/save")
    public String bbsSave( 
    		               @ModelAttribute BbsSaveReqDto bbsSaveReqDto, HttpServletRequest request){
      // getClientIp setting

      // TODO 프로필 있는지 확인하여 프로필 이미지 있으면 프로필 저장 후, atchFileSn 값 을 저장
      // TODO 자녀가 있으면 회원 등록 이후 자녀 정보의 등록도 필요

      // save
        bbsService.save(bbsSaveReqDto);

      // TODO message 생성하여 modal 에 저장 후 return
      return "admin/bbs/list";
  }

    

    
}
