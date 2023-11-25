package com.meta.ponkids.domain.system.ntt.entity;


import com.meta.ponkids.global.common.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.persistence.CascadeType;
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
    private Long nttSn;           
  
    
    @Column(updatable = false) 
    private Long bbsSn;       
    
    @NotNull
    private int nttSeq;           
                        
    private String nttNm;        
                                      
    private String nttCn;         

    private String atchFileSn;   

    private int nttRdcnt;         
    
    @Column(updatable = false) 
    private String openYn;     
    
    private String noticeSetYn; 
    
    @Column(updatable = false) 
    private String noticeSeq;  
    
    
    @Column(updatable = false)
    private String registerId;      
    
    @Column(updatable = false)
    private String registerIp; 
    
    @Column(updatable = false)
    private LocalDateTime regDt;  

    private String updusrId;      
    private String updusrIp; 
    private LocalDateTime updtDt;  


    @ColumnDefault("N")                             // del_yn 컬럼에 공통으로 추가
    @Column(insertable = false, updatable = false)  // del_yn 컬럼에 공통으로 추가
    private String delYn;                           // 삭제 여부
    


   // @ManyToMany
   // private List<Bbs> bbsList = new ArrayList<>();
    

}

