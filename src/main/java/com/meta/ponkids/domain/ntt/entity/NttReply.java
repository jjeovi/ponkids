package com.meta.ponkids.domain.ntt.entity;

import com.meta.ponkids.global.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


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
@SQLDelete( sql = "UPDATE {h-schema}tb_ntt_reply SET del_yn ='Y', updt_dt = now() WHERE nttReplySn = ?" )
@Table( name = "TB_NTT_REPLY" )
public class NttReply extends BaseTimeEntity {
    
    @Id
    @Column( insertable = false )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_NTT_REPLY_SN" )
    private Long nttReplySn;
    
    @NotNull
    private Long nttSn;
    private int step;
    private Long parntsReplySn;
    private int nttReplySeq;
    private String nttReplyCn;
    
    
    @ColumnDefault( "Y" )
    @Column( updatable = false )
    private String openYn;
    @Column( updatable = false )
    private String registerId;
    @Column( updatable = false )
    private String registerIp;
    
    @Column( updatable = false )
    private LocalDateTime regDt;
    
    private String updusrId;
    private String updusrIp;
    private LocalDateTime updtDt;
    
    
    @ColumnDefault( "N" )
    @Column( insertable = false, updatable = false )  // del_yn 컬럼에 공통으로 추가
    private String delYn;                           // 삭제 여부
    
    
}

