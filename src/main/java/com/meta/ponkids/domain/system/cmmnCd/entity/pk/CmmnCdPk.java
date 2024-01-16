package com.meta.ponkids.domain.system.cmmnCd.entity.pk;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Embeddable    // 복합키 설정
@NoArgsConstructor
public class CmmnCdPk implements Serializable {
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long 	cdSn;        // 코드 일련번호
    
    private String 	cdNm;      // 코드 이름
    
    @Builder
    public CmmnCdPk( Long cdSn, String cdNm ) {
        this.cdSn = cdSn;
        this.cdNm = cdNm;
    }
    
}
