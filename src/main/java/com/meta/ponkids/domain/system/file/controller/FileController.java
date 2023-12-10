package com.meta.ponkids.domain.system.file.controller;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.http.MediaType;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import com.google.gson.JsonObject;
import com.meta.ponkids.domain.system.file.entity.AtchFileDetail;
import com.meta.ponkids.domain.system.file.repository.AtchFileDetailRepository;
import com.meta.ponkids.domain.system.file.service.AtchFileService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FileController {

	private final AtchFileDetailRepository atchFileDetailRepository;
	private final AtchFileService    atchFileService;
	

	/**
     * methodName    : list
     * date           : 2023-11-20
     * description    : 이미지 다운로드  
     */
	@GetMapping( "/getImage" )
	public ResponseEntity<byte[]> getImage(
			@RequestParam(required = true ) Long atchFileSn,
			@RequestParam(required = false, defaultValue="1" ) Long fileSeq,
			Model model
			) {
		
		ResponseEntity<byte[]> result = null;
		try {
            
            // 파일 정보 가져오기
            AtchFileDetail atchFileDetail = atchFileDetailRepository.getTarget( atchFileSn,fileSeq );
            
            // 실제 파일 가져오기
            File file = new File(atchFileDetail.getFileStrePath());

            HttpHeaders header = new HttpHeaders();

            //MIME타입 처리
            header.add("Content-Type", Files.probeContentType(file.toPath()));

            //파일 데이터 처리
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
		 
		 return result;
	}
	

	/**
	 * methodName : uploadSummernoteImageFile 
	 *       date : 2023-11-20 
	 *description : 에디터 이미지 업로드
	 * 
	 */
	@PostMapping(value = "/uploadSummernoteImageFile", produces = "application/json")
	@ResponseBody
	public JsonObject uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {

		JsonObject jsonObject = new JsonObject();

		String fileRoot = "C:\\summernote_image\\"; // 저장될 외부 파일 경로
		String originalFileName = multipartFile.getOriginalFilename(); // 오리지날 파일명
		String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); // 파일 확장자

		String savedFileName = UUID.randomUUID() + extension; // 저장될 파일 명

		File targetFile = new File(fileRoot + savedFileName);

		try {
			InputStream fileStream = multipartFile.getInputStream();
			FileUtils.copyInputStreamToFile(fileStream, targetFile); // 파일 저장

			jsonObject.addProperty("url", "/summernoteImage/" + savedFileName);
			jsonObject.addProperty("responseCode", "success");

		} catch (IOException e) {
			FileUtils.deleteQuietly(targetFile); // 저장된 파일 삭제
			jsonObject.addProperty("responseCode", "error");
			e.printStackTrace();
		}

		return jsonObject;
	}

	/**
	 * methodName : fileDownload 
	 *       date : 2023-11-20 
	 *description : 첨부파일 다운로드
	 * 
	 */
	@GetMapping("/fileDownload")
    public ResponseEntity<Resource>  fileDownload(@RequestParam(required = true ) Long atchFileSn,
			                                     @RequestParam(required = true  ) Long fileSeq,
			                                     Model model) throws IOException {
			
		
        
        // 파일 정보 가져오기
        AtchFileDetail atchFileDetail = atchFileDetailRepository.getTarget( atchFileSn,fileSeq );
        
        UrlResource resource;
        
        try{
            resource = new UrlResource("file:"+ atchFileDetail.getFileStrePath());
        }catch (MalformedURLException e){
          
            e.getStackTrace();
            throw new RuntimeException("the given URL path is not valid");
        }
        
        String originalFileName   = atchFileDetail.getOrignlFileNm();
        //Long fileSize = atchFileDetail.getFileSize();
        
        String encodedOriginalFileName = UriUtils.encode(originalFileName, StandardCharsets.UTF_8);

        String contentDisposition = "attachment; filename=\"" + encodedOriginalFileName + "\"";
        
   			  
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,contentDisposition)
                .body(resource);

	
	}
	
	
	
	/**
	 * methodName : fileDownload 
	 *       date : 2023-12-09 
	 *description : 첨부파일 삭제
	 * 
	 */
	@GetMapping("/fileDelete")
    public  String fileDelete(@RequestParam(required = true ) Long atchFileSn,
			                @RequestParam(required = true  ) Long fileSeq,
			                Model model) throws IOException {
		
		   atchFileService.deleteFile(atchFileSn,fileSeq);
		   
		   model.addAttribute("resultMsg", "정상적으로 파일이 삭제 되었습니다." );
	       model.addAttribute("moveUrl", "/admin/ntt/modify?nttSn=100051&bbsSeCd=01" );

	       return "common/alert";
	
	}

    	

}
