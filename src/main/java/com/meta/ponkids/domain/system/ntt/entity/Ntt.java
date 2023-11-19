package com.meta.ponkids.domain.system.ntt.entity;


import com.meta.ponkids.global.common.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.*;

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
    @Column(insertable=false)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_NTT_SN" )
    private int nttSn;             // 게시판일련번호  
  
    @NotNull
    private int bbsSn;        // 게시판구분코드
    
    @NotNull
    private String nttSeq;             //게시판이름
                        
    private String nttNm;         // 게시판 안내문구   
                                      
    private String nttCn;          // 게시판 설명

    private String atchFileSn;   // 댓글 설정여부

    private int nttRdCnt;         //사용여부
     
    private String openYn;      //공개여부    
    
    private String noticeSetYn;      //공개여부     
    private String noticeSeq;      //공개여부     
    private String registerId;      //공개여부     

    @NotNull
    @ColumnDefault("N")
    private String delYn;     // 삭제 여부   


   // @ManyToMany
   // private List<Bbs> bbsList = new ArrayList<>();
    

}

