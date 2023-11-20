package com.meta.ponkids.domain.system.file.dto;

import javax.validation.constraints.NotNull;

import com.meta.ponkids.domain.system.file.entity.AtchFileDetail;
import com.meta.ponkids.domain.system.file.entity.pk.AtchFileDetailPk;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AtchFileDetailSaveDto {
	
		@NotNull
	 	private Long atchFileSn;						// 첨부 파일 일련번호
	 	
		@NotNull
	 	private Long fileSeq;							// 파일 순번
	
		@NotNull
	  	private String fileStrePath;					// 파일저장경로
	    
		@NotNull
	    private String streFileNm;						// 저장파일이름
	    
		@NotNull
	    private String orignlFileNm;					// 원파일명
	    
		@NotNull
	    private String fileExtsn;						// 파일확장자
	    
		
	    private String fileCn;							// 파일내용
	    
	    @NotNull
	    private Long fileSize;						// 파일크기

	    @Builder
		public AtchFileDetailSaveDto(@NotNull Long atchFileSn, @NotNull Long fileSeq, @NotNull String fileStrePath,
				@NotNull String streFileNm, @NotNull String orignlFileNm, @NotNull String fileExtsn, String fileCn,
				@NotNull Long fileSize) {
			this.atchFileSn = atchFileSn;
			this.fileSeq = fileSeq;
			this.fileStrePath = fileStrePath;
			this.streFileNm = streFileNm;
			this.orignlFileNm = orignlFileNm;
			this.fileExtsn = fileExtsn;
			this.fileCn = fileCn;
			this.fileSize = fileSize;
		}
	    
	    // DTO to Entity 메소드는 DTO 내부에서 생성.
	    public AtchFileDetail toEntity() {
	    	return AtchFileDetail.builder()
	    			.atchFileDetailPk(new AtchFileDetailPk(atchFileSn,fileSeq) )
	    			.fileStrePath(fileStrePath)
	    			.streFileNm(streFileNm)
	    			.orignlFileNm(orignlFileNm)
	    			.fileExtsn(fileExtsn)
	    			.fileCn(fileCn)
	    			.fileSize(fileSize)
	    			.build();
	    }
	    
	
}
