package com.meta.ponkids.domain.ntt.entity;


import com.meta.ponkids.global.common.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;



@SequenceGenerator(
        name = "SEQ_TB_NTT_SN",
        sequenceName = "SEQ_TB_NTT_SN",
        initialValue = 1,
        allocationSize = 1
)
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Where( clause = "del_yn = 'N'")
@SQLDelete(sql = "UPDATE tb_ntt SET del_yn ='Y', updt_dt = now() WHERE nttSn = ?")
@Table( name = "TB_NTT" )
public class  Ntt extends BaseTimeEntity {
    
    @Id
    @Column(insertable=true)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_NTT_SN" )
    private Long nttSn;        // 게시물 일력번호       
  
    
    @Column(updatable = false) 
    private Long bbsSn;        // 게시판 일련번호
    
    @NotNull
    private int nttSeq;       // 게시물 순번    
                        
    private String nttNm;     // 게시물 이름    
                                      
    private String nttCn;     // 게시물 내용    

    private Long atchFileSn;  // 썸네일 첨부파일
    
    private Long cnAtchFileSn;  // 첨부파일


    private int nttRdcnt;     // 조회수  
    
    @Column(updatable = false) 
    private String openYn;      // 공개여부
    
    private String noticeSetYn;  // 공지여부
    
    @Column(updatable = false) 
    private String noticeSeq;    // 공지 순번
    
    
    @Column(updatable = false)
    private String registerId;    // 등록일자   
    
    @Column(updatable = false)
    private String registerIp;    // 등록자 IP
    
    @Column(updatable = false)
    private LocalDateTime regDt;  // 등록일 

    private String updusrId;       // 수정자 ID
    private String updusrIp;       // 수정자 IP
    private LocalDateTime updtDt;  // 수정일


    @ColumnDefault("N")                             // del_yn 컬럼에 공통으로 추가
    @Column(insertable = false, updatable = false)  // del_yn 컬럼에 공통으로 추가
    private String delYn;                           // 삭제 여부


}

