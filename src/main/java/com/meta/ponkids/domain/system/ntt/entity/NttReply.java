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
        name = "SEQ_TB_NTT_REPLY_SN",
        sequenceName = "SEQ_TB_NTT_REPLY_SN",
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
@SQLDelete(sql = "UPDATE tb_ntt_reply SET del_yn ='Y', updt_dt = now() WHERE nttReplySn = ?")
@Table( name = "TB_NTT_REPLY" )
public class  NttReply extends BaseTimeEntity {
    
	@Id
    @Column(insertable=false)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_NTT_REPLY_SN" )
    private int nttReplySn;
	
    @NotNull
	private int nttSn;
	private int step;
	private int parntsReplySn;
	private int nttReplySeq;
	private String nttReplyCn;
	private String openYn;
	private int nttReplyGroup;
    @Column(updatable = false)
	private String registerId;
    @Column(updatable = false)
	private String registerIp;
    @Column(updatable = false)
	private LocalDateTime regDt;
    @Column(insertable = false)
	private String upduserId;
    @Column(insertable = false)
	private String upduserIp;
    @Column(insertable = false)
	private LocalDateTime updtDt;

    @ColumnDefault("N")                             // del_yn 컬럼에 공통으로 추가
    @Column(insertable = false, updatable = false)  // del_yn 컬럼에 공통으로 추가
    private String delYn;                           // 삭제 여부
    
    
    


}

