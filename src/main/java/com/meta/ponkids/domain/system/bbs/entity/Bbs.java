package com.meta.ponkids.domain.system.bbs.entity;


import com.meta.ponkids.global.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Where( clause = "del_yn = 'N'")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Table( name = "TB_BBS" )
@SequenceGenerator(
        name = "SEQ_TB_BBS_SN",
        sequenceName = "SEQ_TB_BBS_SN",
        initialValue = 1,
        allocationSize = 1
)
public class  Bbs extends BaseTimeEntity {
    
    @Id
    @Column(insertable=false)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_BBS_SN" )
    private int bbsSn;             // 게시판일련번호  
  
    @NotNull
    private String bbsSeCd;        // 게시판구분코드
    
    @NotNull
    private String bbsNm;             //게시판이름
                        
    private String bbsGdcc;         // 게시판 안내문구   
                                      
    private String bbsDc;          // 게시판 설명

    private String answerSetYn;   // 댓글 설정여부

    private String useYn;         //사용여부
     
    private String openYn;      //공개여부    
    
    private String registerId;      //공개여부     

    @NotNull
    @ColumnDefault("N")
    private String delYn;     // 삭제 여부   

    
   // @ManyToMany
   // private List<Bbs> bbsList = new ArrayList<>();
    

}

