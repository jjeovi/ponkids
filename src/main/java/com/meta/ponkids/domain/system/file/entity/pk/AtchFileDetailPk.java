package com.meta.ponkids.domain.system.file.entity.pk;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;


@Data
@Embeddable    // 복합키 설정
@NoArgsConstructor
public class AtchFileDetailPk implements Serializable {
    
    
    private Long atchFileSn;        // 첨부 파일 일련번호
    
    private Long fileSeq;            // 파일 순번
    
    @Builder
    public AtchFileDetailPk( Long atchFileSn, Long fileSeq ) {
        this.atchFileSn = atchFileSn;
        this.fileSeq = fileSeq;
    }
    
    
}
