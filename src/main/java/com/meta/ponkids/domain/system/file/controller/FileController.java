package com.meta.ponkids.domain.system.file.controller;

import java.io.File;
import java.net.URLDecoder;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FileController {
	
	
//	private final static String BASIC_PATH = "/file";
	
	@Value("${upload.path}")
	private String uploadPath;
	
	/**
     * methodName    : list
     * date           : 2023-11-20
     * description    : 이미지 다운로드  
     */
	@GetMapping( "/getImage" )
	public ResponseEntity<byte[]> getImage(
//			@PathVariable Long atchFileSn, 
			@RequestParam(required = true ) Long atchFileSn,
			@RequestParam(required = false, defaultValue="1" ) Long fileSeq,
			Model model
			) {
		
		ResponseEntity<byte[]> result = null;
		
		String fileName = "";
		String size = "";
		System.out.println("tttttttt");
		try {

            String srcFileName = URLDecoder.decode(fileName,"UTF-8");

            File file = new File(uploadPath + File.separator + srcFileName);

            if(size != null && size.equals("1")){
                file = new File(file.getParent(),file.getName().substring(2));
            }

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
	
	
    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName, String size){

        ResponseEntity<byte[]> result = null;

        try {

            String srcFileName = URLDecoder.decode(fileName,"UTF-8");

            File file = new File(uploadPath + File.separator + srcFileName);

            if(size != null && size.equals("1")){
                file = new File(file.getParent(),file.getName().substring(2));
            }

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
	

}
