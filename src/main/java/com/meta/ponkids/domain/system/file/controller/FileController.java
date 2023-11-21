package com.meta.ponkids.domain.system.file.controller;

import java.io.File;
import java.nio.file.Files;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.meta.ponkids.domain.system.file.entity.AtchFileDetail;
import com.meta.ponkids.domain.system.file.repository.AtchFileDetailRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FileController {
	
	private final AtchFileDetailRepository atchFileDetailRepository;
	
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

}
