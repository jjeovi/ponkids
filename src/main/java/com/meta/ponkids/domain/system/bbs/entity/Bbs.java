package com.meta.ponkids.domain.system.bbs.entity;


import com.meta.ponkids.global.common.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;



@SequenceGenerator(
        name = "SEQ_TB_BBS_SN",
        sequenceName = "SEQ_TB_BBS_SN",
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
@SQLDelete(sql = "UPDATE tb_bbs SET del_yn ='Y', updt_dt = now() WHERE bbsSn = ?")
@Table( name = "TB_BBS" )
public class  Bbs extends BaseTimeEntity {
    
    @Id
    @Column(insertable=false)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_BBS_SN" )
    private Long bbsSn;         // 게시판 일련번호    
  
    @NotNull
    private String bbsSeCd;     // 게시판 구분코드 : 01 - 포토형 , 02 - 리스트형   
    
    @NotNull
    private String bbsNm;       // 게시판 이름      
                        
    private String bbsGdcc;     // 게시판 안내문구    
                                      
    private String bbsDc;       //게시판 설명 

    private String replySetYn;  // 댓글 설정여부  

    private String useYn;       // 사용여부 
      
    private String openYn;      // 공개여부       
    
    @Column(updatable = false) 
    private String registerId;  // 등록자 ID     
    
    @Column(updatable = false)
    private String registerIp;   // 등록자 IP
    
    @Column(updatable = false)
    private LocalDateTime regDt; // 등록일 

    private String updusrId;      // 수정자 ID
    
    private String updusrIp;      // 수정자 IP
    
    private LocalDateTime updtDt; // 수정일


    @ColumnDefault("N")                             
    @Column(insertable = false, updatable = false)  // del_yn 컬럼에 공통으로 추가
    private String delYn;                           // 삭제 여부

    

}

