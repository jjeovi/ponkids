package com.meta.ponkids.domain.system.file.service;

import com.meta.ponkids.domain.system.file.dto.AtchFileDetailSaveDto;
import com.meta.ponkids.domain.system.file.entity.AtchFile;
import com.meta.ponkids.domain.system.file.entity.AtchFileDetail;
import com.meta.ponkids.domain.system.file.repository.AtchFileDetailRepository;
import com.meta.ponkids.domain.system.file.repository.AtchFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


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
@Log4j2
@RequiredArgsConstructor
public class AtchFileService {
    private final AtchFileRepository atchFileRepository;
    private final AtchFileDetailRepository atchFileDetailRepository;
    
    @Value( "${key.upload.path}" )
    private String UPLOAD_PATH;
    
    /**
     * methodName    : save
     * date           : 11/21/23
     * description    : 파일 생성 및 db 저장
     */
    @Transactional
    public Long save( MultipartFile file ) throws IOException {
        // 파일 save
        // 1. 물리업로드 (hardUpload)   : 지정된 경로에 파일 저장
        // 2. 소프트업로드 (softUpload)  : DB에 파일정보 저장
        
        
        // 1. 물리업로드 (hardUpload)   : 지정된 경로에 파일 저장
        // ------------------------------------------------
        
        String orignlFileNm = file.getOriginalFilename();
        String streFileNm = createSaveFileName( orignlFileNm );
        
        // 지정한 uploadPath 에 파일 저장
        file.transferTo( new File( getFullPath( streFileNm ) ) );
        
        
        // 2. 소프트업로드 (softUpload)  : DB에 파일정보 저장
        // ------------------------------------------------

//		String contentType = file.getContentType();
        AtchFile newAtchFile = atchFileRepository.saveAndFlush( new AtchFile() );    // 신규 파일 생성
        
        // atchFileDetailSaveDto 생성
        AtchFileDetailSaveDto atchFileDetailSaveDto = new AtchFileDetailSaveDto();
        
        
        // atchFileDetailSaveDto 항목 setting
        atchFileDetailSaveDto.setAtchFileSn( newAtchFile.getAtchFileSn() );    // atchFileDetail > atchFileSn		첨부파일일련번호
        atchFileDetailSaveDto.setFileSeq( ( long ) 1 );                            // atchFileDetail > fileSeq			파일순번
        atchFileDetailSaveDto.setFileStrePath( getFullPath( streFileNm ) );        // atchFileDetail > fileStrePath	파일저장경로
        atchFileDetailSaveDto.setStreFileNm( streFileNm );                    // atchFileDetail > streFileNm		저장파일이름
        atchFileDetailSaveDto.setOrignlFileNm( orignlFileNm );                // atchFileDetail > orignlFileNm	원파일명
        atchFileDetailSaveDto.setFileExtsn( extractExt( orignlFileNm ) );        // atchFileDetail > fileExtsn		파일확장자
        atchFileDetailSaveDto.setFileCn( orignlFileNm );                        // atchFileDetail > fileCn			파일내용
        atchFileDetailSaveDto.setFileSize( file.getSize() );                    // atchFileDetail > fileSize		파일크기
        
        // atchFileDetail 로 dto to entity
        AtchFileDetail atchFileDetail = atchFileDetailSaveDto.toEntity();
        
        // 파일 상세 save
        atchFileDetailRepository.saveAndFlush( atchFileDetail );
        
        return newAtchFile.getAtchFileSn();
        
    }
    
    /**
     * methodName    : delete
     * date           : 11/21/23
     * description    : 파일 삭제 ( 서버상의 파일을 물리적 삭제 처리 + db 정보 삭제 )
     */
    @Transactional
    public void delete( Long atchFileSn ) throws EmptyResultDataAccessException {
        
        // S : 서버상 파일 물리적 삭제 처리
        List<AtchFileDetail> atchFileDetailList = atchFileDetailRepository.getList( atchFileSn );
        
        for ( AtchFileDetail atchFileDetail : atchFileDetailList ) {
            
            File file = new File( atchFileDetail.getFileStrePath() );
            
            if ( file.exists() ) { //파일존재여부확인
                
                if ( file.isDirectory() ) { //파일이 디렉토리인지 확인
                    
                    File[] files = file.listFiles();
                    
                    for ( int i = 0; i < files.length; i++ ) {
                        if ( files[ i ].delete() ) {
                            log.info( files[ i ].getName() + " 삭제성공" );
                        } else {
                            log.info( files[ i ].getName() + " 삭제실패" );
                        }
                    }
                    
                }
                if ( file.delete() ) {
                    log.info( "파일삭제 성공" );
                } else {
                    log.info( "파일삭제 실패" );
                }
                
            } else {
                log.info( "파일이 존재하지 않습니다." );
            }
            
        }
        // E : 서버상 파일 물리적 삭제 처리
        
        try {
            // DB상의 파일 삭제
            atchFileDetailRepository.deleteByAtchFileDetailPk_AtchFileSn( atchFileSn );
            atchFileRepository.deleteById( atchFileSn );
        } catch ( EmptyResultDataAccessException e ) {
            log.info( "error 발생", e );
        }
    }
    
    
    // 파일 저장 이름 만들기
    // 서버상 저장되는 파일명은 중복의 위험을 줄이기 위해 랜덤난수를 생성
    private String createSaveFileName( String orignlFileNm ) {
        String ext = extractExt( orignlFileNm );
        String uuid = UUID.randomUUID().toString();    // 랜덤 난수 생성
        return new SimpleDateFormat( "yyyyMMdd" ).format( new Date() ) + uuid + "." + ext;
    }
    
    // 확장자명 구하기
    private String extractExt( String originalFilename ) {
        int pos = originalFilename.lastIndexOf( "." );
        return originalFilename.substring( pos + 1 );
    }
    
    // fullPath 만들기
    private String getFullPath( String filename ) {
        return UPLOAD_PATH + filename;
    }
    
    /**
     * methodName    : saves
     * date           : 12/07/23
     * description    : 다중 파일 생성 및 db 저장
     */
    @Transactional
    public Long multifileSave( List<MultipartFile> multiFileList, Long cnAtchFileSn ) throws IOException {
        // 파일 save
        // 1. 물리업로드 (hardUpload)   : 지정된 경로에 파일 저장
        // 2. 소프트업로드 (softUpload)  : DB에 파일정보 저장
        // ------------------------------------------------
        
        File fileCheck = new File( UPLOAD_PATH );
        if ( !fileCheck.exists() ) fileCheck.mkdirs();
        
        List<Map<String, String>> fileList = new ArrayList<>();
        
        String newAtchFileYn = "N"; // 신규파일 여부
        if ( cnAtchFileSn == null ) {
            AtchFile newAtchFile = atchFileRepository.save( new AtchFile() );    // 신규 파일 생성
            cnAtchFileSn = newAtchFile.getAtchFileSn();
            newAtchFileYn = "Y";
        }
        
        // atchFileDetailSaveDto 생성
        AtchFileDetailSaveDto atchFileDetailSaveDto = new AtchFileDetailSaveDto();
        
        // atchFileDetailSaveDto 항목 setting
        for ( int i = 0; i < multiFileList.size(); i++ ) {
            String originFile = multiFileList.get( i ).getOriginalFilename();
            String ext = originFile.substring( originFile.lastIndexOf( "." ) );
            String changeFile = UUID.randomUUID() + ext;
            
            
            Map<String, String> map = new HashMap<>();
            map.put( "originFile", originFile );
            map.put( "changeFile", changeFile );
            
            fileList.add( map );
            
            atchFileDetailSaveDto.setAtchFileSn( cnAtchFileSn );                        // atchFileDetail > atchFileSn		첨부파일일련번호
            
            if ( newAtchFileYn == "Y" ) {
                atchFileDetailSaveDto.setFileSeq( ( long ) i + 1 );                        // atchFileDetail > fileSeq			파일순번
            } else {
                atchFileDetailSaveDto.setFileSeq( atchFileDetailRepository.maxFileSeq( cnAtchFileSn ) );
            }
            
            atchFileDetailSaveDto.setFileStrePath( getFullPath( changeFile ) );        // atchFileDetail > fileStrePath	파일저장경로
            atchFileDetailSaveDto.setStreFileNm( changeFile );                    // atchFileDetail > streFileNm		저장파일이름
            atchFileDetailSaveDto.setOrignlFileNm( originFile );                    // atchFileDetail > orignlFileNm	원파일명
            atchFileDetailSaveDto.setFileExtsn( extractExt( originFile ) );            // atchFileDetail > fileExtsn		파일확장자
            atchFileDetailSaveDto.setFileCn( originFile );                        // atchFileDetail > fileCn			파일내용
            atchFileDetailSaveDto.setFileSize( multiFileList.get( i ).getSize() );     // atchFileDetail > fileSize		파일크기
            
            // atchFileDetail 로 dto to entity
            AtchFileDetail atchFileDetail = atchFileDetailSaveDto.toEntity();
            
            // 파일 상세 save
            atchFileDetailRepository.save( atchFileDetail );
            
        }
        
        // 파일업로드
        try {
            for ( int i = 0; i < multiFileList.size(); i++ ) {
                File uploadFile = new File( UPLOAD_PATH + "\\" + fileList.get( i ).get( "changeFile" ) );
                multiFileList.get( i ).transferTo( uploadFile );
            }
            
            System.out.println( "다중 파일 업로드 성공!" );
            
        } catch ( IllegalStateException | IOException e ) {
            System.out.println( "다중 파일 업로드 실패 ㅠㅠ" );
            // 만약 업로드 실패하면 파일 삭제
            for ( int i = 0; i < multiFileList.size(); i++ ) {
                new File( UPLOAD_PATH + "\\" + fileList.get( i ).get( "changeFile" ) ).delete();
            }
            
            
            e.printStackTrace();
        }
        
        
        
        return cnAtchFileSn;
        
    }
    
    
    /**
     * methodName    : delete
     * date           : 11/21/23
     * description    : 파일 삭제 ( 서버상의 파일을 물리적 삭제 처리 + db 정보 삭제 )
     */
    @Transactional
    public void deleteFile( Long atchFileSn, Long fileSeq ) throws EmptyResultDataAccessException {
        
        
        
        try {
            // DB상의 파일 삭제
            atchFileDetailRepository.deleteByAtchFileDetailPk( atchFileSn, fileSeq );
            //atchFileDetailRepository.deleteByAtchFileDetailPk_AtchFileSn(atchFileSn);
            //모든 파일 다 삭제시 부모 테이블도 삭제
            //atchFileRepository.deleteById( atchFileSn );
        } catch ( EmptyResultDataAccessException e ) {
            log.info( "error 발생", e );
        }
    }
    
}
