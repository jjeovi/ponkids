package com.meta.ponkids.domain.mypage.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.meta.ponkids.domain.cls.dto.ClassReqstListDto;
import com.meta.ponkids.domain.cls.service.ClassReqstService;
import com.meta.ponkids.domain.lctre.service.LctreReqstService;
import com.meta.ponkids.domain.system.file.service.AtchFileService;
import com.meta.ponkids.domain.user.dto.UserChldrnModDto;
import com.meta.ponkids.domain.user.repository.UserChldrnRepository;
import com.meta.ponkids.domain.user.service.UserChldrnService;
import com.meta.ponkids.global.util.session.SessionUtils;

import lombok.RequiredArgsConstructor;

@RequestMapping("/mypage")
@Controller
@RequiredArgsConstructor
public class MypageController {
	
	public static String USER_VIEW_PATH;
	// path 경로 : pon
	@Value( "${key.default.directoryPath.user}" )
	public void setUserViewPath(String value) {
		USER_VIEW_PATH = value;
	}
	
	private final static String BASIC_DOMAIN = "mypage";
	private final static String BASIC_PATH = "/" + BASIC_DOMAIN;	// USER_VIEW_PATH + "/" + BASIC_DOMAIN 는  앞의 "/" 를 제거해야 함.
	
	
	private final ClassReqstService classReqstService;
	
	private final LctreReqstService lctreReqstService;
	
	
	private final UserChldrnRepository userChldrnRepository;
	
	private final UserChldrnService userChldrnService;
	private final AtchFileService atchFileService;
	
	// layout 관련 dataSet 처리는 
	// - MypageAop.java 에서 처리 ( 관심개수.. 등 ) 
	// - 로그인 체크 : AuthPreInterceptor.java 에서 처리 하여 return  
	
	@GetMapping( "/" )
	public String main( Model model ) {
		
		return "forward:/mypage/reqstHistory/list";   
	}
	
	@GetMapping( "/reqstHistory/list" )
	public String reqstHistoryList( 	@ModelAttribute ClassReqstListDto listDto,
								@PageableDefault( size = 8 ) Pageable pageable,
								Model model ) {
		
		// S : 필요한 객체 setting
		
		// session userSn setting
		listDto.setUserSn( SessionUtils.getAuthUserSn() );
		
		// 클래스 신청내역 리스트
		model.addAttribute("classReqstList", classReqstService.getList(listDto, pageable));
		
		
		// E : 필요한 객체 setting
		
		// 기본 경로 setting
		model.addAttribute( "basicPath", BASIC_PATH );
		// mypage용 mcd 
		model.addAttribute( "mypageMcd", "reqstHistory" );
		return USER_VIEW_PATH + BASIC_PATH + "/reqstHistory/list";
	}
	
	@GetMapping( "/reqstHistory/detail" )
	public String reqstHistoryDetail( @RequestParam( required = true ) Long pk,	// 타입 체크
										Model model) {
		
		// S : 필요한 객체 setting
		
		// 조회 process 
		// =================================================================================
		// 1. 클래스 신청 (TB_CLASS_REQST) 에서 조회  (1 건)
		// 2. 수업 신청 ( TB_LCTRE_REQST) 에서 조회  ( 여러건 가능 ) 
		// 3. 수업 신청 상세 ( TB_LCTRE_REQST_DETAIL ) 에서 조회 ( 2번 count 에서 추가로 여러건 또 가능 ) 
		// =================================================================================
		
		
		// 1. 클래스 신청 (TB_CLASS_REQST) 에서 조회  (1 건)
//		ClassReqstListDto targetDto = classReqstService.getByClassReqstSn(pk);
		model.addAttribute( "targetDto", classReqstService.getByClassReqstSn(pk) );

		// 2. 수업 신청 ( TB_LCTRE_REQST) 에서 조회  ( 여러건 가능 )
		// 3. 수업 신청 상세 ( TB_LCTRE_REQST_DETAIL ) 에서 조회 ( 2번 count 에서 추가로 여러건 또 가능 )
		
		// 2,3 번 동시에 수행.
//		List<LctreReqstListDto> targetLctreReqsts = lctreReqstService.getListByClassReqstSn(pk);
		model.addAttribute( "targetLctreReqsts", lctreReqstService.getListByClassReqstSn(pk) );
		
		
		// 기본 경로 setting
		model.addAttribute( "basicPath", BASIC_PATH );
		// mypage용 mcd 
		model.addAttribute( "mypageMcd", "reqstHistory" );
		return USER_VIEW_PATH + BASIC_PATH + "/reqstHistory/detail";
	}
	
	
	@GetMapping( "/reviewList" )
	public String reviewList( Model model ) {
		
		// S : 필요한 객체 setting
		
		
		// E : 필요한 객체 setting
		
		// 기본 경로 setting
		model.addAttribute( "basicPath", BASIC_PATH );
		// mypage용 mcd 
		model.addAttribute( "mypageMcd", "reviewList" );
		return USER_VIEW_PATH + BASIC_PATH + "/reviewList";
	}
	
	@GetMapping( "/myInfoModify" )
	public String myInfoModify( Model model ) {
		
		// S : 필요한 객체 setting
		
		
		// E : 필요한 객체 setting
		
		// 기본 경로 setting
		model.addAttribute( "basicPath", BASIC_PATH );
		// mypage용 mcd 
		model.addAttribute( "mypageMcd", "myInfoModify" );
		return USER_VIEW_PATH + BASIC_PATH + "/myInfoModify";
	}
	
	@GetMapping( "/myChldrnInfo" )
	public String myChldrnInfo( @RequestParam( required = false ) Long userChldrnSeq,	// 자녀 순번  *기본값 1
								@RequestParam( required = false ) String type,			// 타입 ( mod : 수정(기존자녀수정), add : 등록(신규자녀등록) ) *기본값 mod 
									Model model ) {
		
		// S : 필요한 객체 setting
		
		// 자녀순번 없을시 기본값 1 로 setting
		if( userChldrnSeq == null ) { 
			userChldrnSeq = (long)1 ;
		}
		
		// 페이지유형 없을시 기본값 "mod"
		if ( !StringUtils.hasText( type ) ) {
			type = "mod";
		}
		model.addAttribute( type );
		
		// default : 자녀 불러오기 ( userChldrnSeq 번째 자녀 ) 
		model.addAttribute( "targetDto", userChldrnRepository.getByUserSnAndUserChldrnSeq( SessionUtils.getAuthUserSn(), userChldrnSeq ) );
		
		// default : 자식 list  
		model.addAttribute( "userChldrnListDto", userChldrnRepository.getListByUserSn( SessionUtils.getAuthUserSn() ) );
		
		
		// E : 필요한 객체 setting
		
		// 기본 경로 setting
		model.addAttribute( "basicPath", BASIC_PATH );
		
		// mypage용 mcd 
		model.addAttribute( "mypageMcd", "myChldrnInfo" );
		
		return USER_VIEW_PATH + BASIC_PATH + "/myChldrnInfo";
	}
	
	
	@PostMapping( "/myChldrnInfo/update" )
	public String myChldrnInfoUpdate(
			@ModelAttribute UserChldrnModDto modDto,
			@RequestParam( "file" ) MultipartFile files,
			HttpServletRequest request,
			Model model ) throws IOException {
		
		
		Long userSn = SessionUtils.getAuthUserSn();
		
		if( userSn == null ) { 
			model.addAttribute( "resultMsg",	"수정 중 오류가 발생하였습니다. 세션을 확인해주세요." );
			model.addAttribute( "moveUrl",		"/" );
			return "common/alert";
		}
		
		modDto.setUserSn( userSn );
		
		
		// 첨부파일 존재시 파일 저장
		if ( !files.isEmpty() ) {
			// 기존에 첨부파일 있을시 삭제
			if ( modDto.getAtchFileSnOri() != null ) {
				atchFileService.delete( modDto.getAtchFileSnOri() );
			}
			
			// 첨부파일 저장
			modDto.setAtchFileSn( atchFileService.save( files ) );	// 파일 save (파일 개수 1개일 때 )
		} else {
			// 첨부파일 존재하지않을 때
			// 기존 첨부파일이 있었는데 삭제됬다면 삭제처리
			if ( modDto.getAtchFileSnOri() != null && modDto.getAtchFileSn() == null ) {
				atchFileService.delete( modDto.getAtchFileSnOri() );
				modDto.setAtchFileSn( null );
			}
		}
		
		// 수정 처리
		userChldrnService.update( modDto, request ) ;
		
		// 메시지 출력 및 url 이동 처리
		model.addAttribute( "resultMsg", "정상적으로 수정되었습니다." );
		model.addAttribute( "moveUrl",	 BASIC_PATH + "/myChldrnInfo" );
		
		return "common/alert";
	}
	
	
	@PostMapping( "/myChldrnInfo/deleteChldrn" )
	public String deleteChldrn(
			@RequestParam( required = true ) Long pk,
			Model model ) {
		
		
		Long userSn = SessionUtils.getAuthUserSn();
		
		if( userSn == null ) { 
			model.addAttribute( "resultMsg",	"삭제 중 오류가 발생하였습니다. 세션을 확인해주세요." );
			model.addAttribute( "moveUrl",		"/" );
			return "common/alert";
		}
		
		// 삭제 처리
		userChldrnRepository.deleteByChldrnSnAndUserSn( pk, userSn );
		
		// 메시지 출력 및 url 이동 처리
		model.addAttribute( "resultMsg", "정상적으로 삭제되었습니다." );
		model.addAttribute( "moveUrl",	 BASIC_PATH + "/myChldrnInfo" );
		
		return "common/alert";
	}
	
	
	@GetMapping( "/inqryList" )
	public String inqryList( Model model ) {
		
		// S : 필요한 객체 setting
		
		
		// E : 필요한 객체 setting
		
		// 기본 경로 setting
		model.addAttribute( "basicPath", BASIC_PATH );
		// mypage용 mcd 
		model.addAttribute( "mypageMcd", "inqryList" );
		return USER_VIEW_PATH + BASIC_PATH + "/inqryList";
	}
	
	
	@GetMapping( "/questionList" )
	public String questionList( Model model ) {
		
		// S : 필요한 객체 setting
		
		
		// E : 필요한 객체 setting
		
		// 기본 경로 setting
		model.addAttribute( "basicPath", BASIC_PATH );
		// mypage용 mcd 
		model.addAttribute( "mypageMcd", "questionList" );
		return USER_VIEW_PATH + BASIC_PATH + "/questionList";
	}
	
	
}
