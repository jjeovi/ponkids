package com.meta.ponkids.domain.system.file.entity;

import java.time.LocalDateTime;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.meta.ponkids.domain.system.file.entity.pk.AtchFileDetailPk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * className      : AtchFileDetail
 * author         : jjeoV
 * date           : 2023-11-19
 * description    : class of 파일상세 Entity
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-11-19        jjeoV             최초 생성
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "tb_atch_file_detail" )
public class AtchFileDetail {
	
	
	// 복합키 생성 ( AtchFileDetailPk.java 에서 2개 키를 미리 생성)
	// private Long atchFileSn;						// 첨부 파일 일련번호
	// private Long fileSeq;						// 파일 순번
    @EmbeddedId
    private AtchFileDetailPk atchFileDetailPk;		// 복합키 setting
    
    private String fileStrePath;					// 파일저장경로
    
    private String streFileNm;						// 저장파일이름
    
    private String orignlFileNm;					// 원파일명
    
    private String fileExtsn;						// 파일확장자
    
    private String fileCn;							// 파일내용
    
    private Long fileSize;						// 파일크기

}
