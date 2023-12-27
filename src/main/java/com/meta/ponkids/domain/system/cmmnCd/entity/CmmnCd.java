package com.meta.ponkids.domain.system.cmmnCd.entity;

import com.meta.ponkids.global.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert // insert 구문 시 null 이 아닌 값들만 insert
@DynamicUpdate // update 구문 시 null 이 아닌 값들만 update
@SequenceGenerator(    // TODO SEQUENCE setting
        name = "SEQ_TB_CMMN_CD_SN",
        sequenceName = "SEQ_TB_CMMN_CD_SN",
        initialValue = 1,
        allocationSize = 1
)
@Where( clause = "del_yn = 'N'" ) // DEFAULT 로 WHERE DEL_YN = 'N' 문을 추가하여 조회
@SQLDelete( sql = "UPDATE tb_cmmnCd SET del_yn ='Y', updt_dt = now() WHERE cd_sn = ?" )
// delelte 시 실행 (ex ) ~Repository.deleteById)
@Table( name = "TB_CMMN_CD" )
public class CmmnCd extends BaseTimeEntity {
    
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_CMMN_CD_SN" )
    private Long cdSn;
    
    private String cdNm;
    
    private String cdDc;
    
    private String cdDetailVal1_Dc;
    
    private String cdDetailVal2_Dc;
    
    private String cdDetailVal3_Dc;
    
    private String cdDetailVal4_Dc;
    
    private String cdDetailVal5_Dc;
    
    private String useYn;
    
    private String sysEssntlCmmnYn;
    
    private String clCd;
    
    private String remark;
    
    @Column( updatable = false )
    private String registerId;
    
    @Column( updatable = false )
    private String registerIp;
    
    
    //  @Column(insertable = false)   // 등록할 때도 수정일시에 시간 들어가게 변경
    private String updusrId;
    
    //  @Column(insertable = false)   // 등록할 때도 수정일시에 시간 들어가게 변경
    private String updusrIp;
    
    
    @ColumnDefault( "N" )                             // del_yn 컬럼에 공통으로 추가
    @Column( insertable = false, updatable = false )  // del_yn 컬럼에 공통으로 추가 (등록 시, 수정 시 해당컬럼 신경쓰지 않음.)
    private String delYn;                           // 삭제 여부
    
    
}
