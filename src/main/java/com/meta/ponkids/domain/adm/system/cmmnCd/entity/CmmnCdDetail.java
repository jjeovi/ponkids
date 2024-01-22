package com.meta.ponkids.domain.adm.system.cmmnCd.entity;

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
        name = "SEQ_TB_CMMN_CD_DETAIL_SN",
        sequenceName = "SEQ_TB_CMMN_CD_DETAIL_SN",
        initialValue = 1,
        allocationSize = 1
)
@Where( clause = "del_yn = 'N'" ) // DEFAULT 로 WHERE DEL_YN = 'N' 문을 추가하여 조회
@SQLDelete( sql = "UPDATE tb_cmmn_cd_detail SET del_yn ='Y', updt_dt = now() WHERE cd_detail_sn = ?" )
// delelte 시 실행 (ex ) ~Repository.deleteById)
@Table( name = "TB_CMMN_CD_DETAIL" )
public class CmmnCdDetail extends BaseTimeEntity {
    
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_CMMN_CD_DETAIL_SN" )
    private Long cdDetailSn;
    
    private String cdNm;
    
    private Long cdDetailSeq;
    
    private String cdDetailNm;
    
    private String cdDetailDc;
    
    private String cdDetailVal1;
    
    private String cdDetailVal2;
    
    private String cdDetailVal3;
    
    private String cdDetailVal4;
    
    private String cdDetailVal5;
    
    private String useYn;
    
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
    private String delYn;
}
