package com.meta.ponkids.domain.system.file.service;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.mail.Multipart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.meta.ponkids.domain.system.file.dto.AtchFileDetailSaveDto;
import com.meta.ponkids.domain.system.file.entity.AtchFile;
import com.meta.ponkids.domain.system.file.entity.AtchFileDetail;
import com.meta.ponkids.domain.system.file.repository.AtchFileDetailRepository;
import com.meta.ponkids.domain.system.file.repository.AtchFileRepository;


/**
 * className      : FileService
 * author         : jjeoV
 * date           : 2023-11-19
 * description    : class of 파일 Service
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-11-19        jjeoV             최초 생성
 */
@Service
@RequiredArgsConstructor
public class AtchFileService {
	private final AtchFileRepository atchFileRepository;
	private final AtchFileDetailRepository atchFileDetailRepository;
	
	@Value("${upload.path}")
	private String uploadPath;
	
	@Transactional
	public Long save(MultipartFile file) throws IOException {
		// 파일 save 
		// 1. 물리업로드 (hardUpload)   : 지정된 경로에 파일 저장
		// 2. 소프트업로드 (softUpload)  : DB에 파일정보 저장
		
		
		// 1. 물리업로드 (hardUpload)   : 지정된 경로에 파일 저장
		// ------------------------------------------------
		
		String orignlFileNm = file.getOriginalFilename();
		String streFileNm = createSaveFileName(orignlFileNm);

		// 지정한 uploadPath 에 파일 저장
		file.transferTo(new File(getFullPath(streFileNm)));
		
	
		// 2. 소프트업로드 (softUpload)  : DB에 파일정보 저장
		// ------------------------------------------------

//		String contentType = file.getContentType();
		AtchFile newAtchFile = atchFileRepository.save(new AtchFile());	// 신규 파일 생성
		
		// atchFileDetailSaveDto 생성
		AtchFileDetailSaveDto atchFileDetailSaveDto = new AtchFileDetailSaveDto();
		
		
		// atchFileDetailSaveDto 항목 setting
		atchFileDetailSaveDto.setAtchFileSn(newAtchFile.getAtchFileSn());	// atchFileDetail > atchFileSn		첨부파일일련번호
		atchFileDetailSaveDto.setFileSeq((long)1);							// atchFileDetail > fileSeq			파일순번
		atchFileDetailSaveDto.setFileStrePath(getFullPath(streFileNm));		// atchFileDetail > fileStrePath	파일저장경로
		atchFileDetailSaveDto.setStreFileNm(streFileNm);					// atchFileDetail > streFileNm		저장파일이름
		atchFileDetailSaveDto.setOrignlFileNm(orignlFileNm);				// atchFileDetail > orignlFileNm	원파일명
		atchFileDetailSaveDto.setFileExtsn(extractExt(orignlFileNm));		// atchFileDetail > fileExtsn		파일확장자
		atchFileDetailSaveDto.setFileCn(orignlFileNm);						// atchFileDetail > fileCn			파일내용
		atchFileDetailSaveDto.setFileSize(file.getSize());					// atchFileDetail > fileSize		파일크기
		
		// atchFileDetail 로 dto to entity 
		AtchFileDetail atchFileDetail = atchFileDetailSaveDto.toEntity();
		
		// 파일 상세 save
		atchFileDetailRepository.save(atchFileDetail);
		
		return newAtchFile.getAtchFileSn();
	}
	
	
	// 파일 저장 이름 만들기
	// 서버상 저장되는 파일명은 중복의 위험을 줄이기 위해 랜덤난수를 생성하여 save
	private String createSaveFileName(String orignlFileNm) {
	    String ext = extractExt(orignlFileNm);
	    String uuid = UUID.randomUUID().toString();	// 랜덤 난수 생성
	    return new SimpleDateFormat("yyyyMMdd").format(new Date()).toString() + uuid + "." + ext;
	}
	
	// 확장자명 구하기
	private String extractExt(String originalFilename) {
	    int pos = originalFilename.lastIndexOf(".");
	    return originalFilename.substring(pos + 1);
	}
	
	// fullPath 만들기
	private String getFullPath(String filename) {
	    return uploadPath + filename;
	}
}
