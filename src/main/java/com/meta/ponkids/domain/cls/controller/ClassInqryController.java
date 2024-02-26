package com.meta.ponkids.domain.cls.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.meta.ponkids.domain.cls.dto.ClassInqryListDto;
import com.meta.ponkids.domain.cls.dto.ClassInqryModDto;
import com.meta.ponkids.domain.cls.dto.ClassInqrySaveDto;
import com.meta.ponkids.domain.cls.repository.ClassInqryRepository;
import com.meta.ponkids.domain.cls.service.ClassInqryService;
import com.meta.ponkids.domain.system.login.dto.LoginDto;
import com.meta.ponkids.global.util.session.SessionUtils;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ClassInqryController {
	
	private final ClassInqryService classInqryService;
	private final ClassInqryRepository classInqryRepository;
	
	private final static String BASIC_PATH = "/classInqry";
	private final static String BASIC_VIEW_PATH = "pon/cls";
	
	
	@GetMapping( BASIC_PATH + "/{mcd}/list" )
	public String list( @ModelAttribute ClassInqryListDto listDto,
						@PathVariable String mcd,
						@PageableDefault( size = 10 ) Pageable pageable,
						Model model ) {
		
		// S : 필요한 객체 setting
		
		// 목록 조회
		Page<ClassInqryListDto> resultList = classInqryService.getList( listDto, pageable );
		model.addAttribute( "resultList", resultList );
		
		// 검색 dto setting
		model.addAttribute( "searchDTO", listDto );
		
		// E : 필요한 객체 setting
		
		// 기본 경로 setting
		model.addAttribute( "basicPath", BASIC_PATH );
		
		return BASIC_PATH + "/list";
	}
	
	
	@GetMapping( BASIC_PATH + "/{mcd}/regist" )
	public String regist( @PathVariable String mcd, Model model ) {
		
		// S : 필요한 객체 setting
		
		// 가입 object 생성
		model.addAttribute( new ClassInqrySaveDto() );
		
		// E : 필요한 객체 setting
		
		// 기본 경로 setting
		model.addAttribute( "basicPath", BASIC_PATH );
		
		return BASIC_PATH + "/regist";
	}
	
	
	@Transactional
	@PostMapping( BASIC_PATH + "/{mcd}/insert" )
	public String insert (
			@ModelAttribute ClassInqrySaveDto saveDto,
			@PathVariable String mcd,
			HttpServletRequest request,
			Model model ) throws IOException {
		
		// S : 필요한 객체 setting
		
		// 1-1. classSn 체크
		if ( saveDto == null || saveDto.getClassSn() == null ) {
			// 메시지 출력 및 url 이동 처리
			model.addAttribute( "resultMsg", "등록 중 문제가 발생하였습니다. 다시 시도해주세요." );
			model.addAttribute( "moveUrl", "/class/" + mcd + "/list" );
			
			return "common/alert";
		}
		
		// 로그인 안되어 있으면 return 
		LoginDto loginDto = SessionUtils.getAuthentication(); 
		if ( loginDto == null || loginDto.getUserSn() == null ) {
			// 메시지 출력 및 url 이동 처리
			model.addAttribute( "resultMsg", "로그인 세션을 확인해주세요." );
			model.addAttribute( "moveUrl", "/class/" + mcd + "/detail?pk=" + saveDto.getClassSn());
			
			return "common/alert";
		}
		
		// 로그인 userSn 값 setting 
		saveDto.setUserSn( loginDto.getUserSn() );
		
		// step은 1로 setting
		saveDto.setStep("1");		// 문의글은 1, 답변은 2
		
		// E : 필요한 객체 setting
		
		// 등록 처리
		// save
		classInqryService.save( saveDto, request );
		
		// 메시지 출력 및 url 이동 처리
		model.addAttribute( "resultMsg", "정상적으로 등록되었습니다." );
		model.addAttribute( "moveUrl", "/class/" + mcd + "/detail?pk=" + saveDto.getClassSn());
		
		return "common/alert";
	}

	
	@GetMapping( value = { 
			BASIC_PATH + "/{mcd}/detail",
			BASIC_PATH + "/{mcd}/modify" } )
	public String detailOrModify(
			@RequestParam( required = true ) Long pk,	// 타입 체크
			@PathVariable String mcd,
			HttpServletRequest request,
			Model model ) {
		
		// S : 필요한 객체 setting
		
		// target object 조회
		model.addAttribute( "targetDto", classInqryService.findById( pk ) );
		
		// E : 필요한 객체 setting
		
		// 기본 경로 setting
		model.addAttribute( "basicPath", BASIC_PATH );
		
		String urlPath = request.getServletPath();
		String remainPath = "";
		if ( urlPath.split( BASIC_PATH )[ 1 ].endsWith( "/detail" ) ) remainPath = "detail";
		if ( urlPath.split( BASIC_PATH )[ 1 ].endsWith( "/modify" ) ) remainPath = "modify";
		
		return BASIC_PATH + "/" + remainPath;
	}
	
	
	@Transactional
	@PostMapping( BASIC_PATH + "/{mcd}/update" )
	public String update(
			@RequestParam("file") MultipartFile files,		// 첨부파일 필요시
			@ModelAttribute ClassInqryModDto modDto,
//			@ModelAttribute ClassInqryRoleModDto classInqryRoleModDto,  // required false
			@PathVariable String mcd,
			HttpServletRequest request,
			Model model ) throws IOException {
		
		// S : 필요한 객체 setting
		
		// E : 필요한 객체 setting
		
		// update 구현
		classInqryService.update( modDto, request );
		
		// 메시지 출력 및 url 이동 처리
		model.addAttribute( "resultMsg", "정상적으로 수정되었습니다." );
		model.addAttribute( "moveUrl", BASIC_PATH + "/" + mcd + "/list" );
		
		return "common/alert";
	}

	
	// 클래스 문의 단건 상세조회 ( by classInqrySn )
	@Transactional
	@ResponseBody
	@GetMapping( BASIC_PATH + "/live/detailClassInqryAjax" )
	public Map<String, Object> detailClassInqryAjax (
			@ModelAttribute ClassInqryListDto listDto,
			Model model ) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		result.put( "resultOne" , classInqryRepository.getByClassInqrySn(listDto) );   // 커리큘럼 일련번호로 검색
		
		return result;
	}
	
}
