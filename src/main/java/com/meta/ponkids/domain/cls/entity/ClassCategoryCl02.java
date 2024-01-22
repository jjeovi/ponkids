package com.meta.ponkids.domain.cls.entity;

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
@SequenceGenerator(
        name = "SEQ_TB_CLASS_CATEGORY_CL02_SN",
        sequenceName = "SEQ_TB_CLASS_CATEGORY_CL02_SN",
        initialValue = 1,
        allocationSize = 1
)
@Where( clause = "del_yn = 'N'" ) // DEFAULT 로 WHERE DEL_YN = 'N' 문을 추가하여 조회
@SQLDelete( sql = "UPDATE tb_class_category_cl02 SET del_yn ='Y', updt_dt = now() WHERE cl_sn = ?" )
// delelte 시 실행 (ex ) ~Repository.deleteById)
@Table( name = "tb_class_category_cl02" )
public class ClassCategoryCl02 extends BaseTimeEntity {
    
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_CLASS_CATEGORY_CL02_SN" )
    private Long clSn;                            // 분류2 일련번호
    
    private Long parntsClSn;                            // 분류1 일련번호
    
    private String clNm;                            // 분류2 이름
    
    private Long clSeq;                            // 분류2 순번
    
    @Column( updatable = false )
    private String registerId;                        // 등록자ID
    
    @Column( updatable = false )
    private String registerIp;                        // 등록자IP
    
    private String updusrId;                        // 수정자ID
    
    private String updusrIp;                        // 수정자IP
    
    @ColumnDefault( "N" )                             // del_yn 컬럼에 공통으로 추가
    @Column( insertable = false, updatable = false )  // del_yn 컬럼에 공통으로 추가 (등록 시, 수정 시 해당컬럼 신경쓰지 않음.)
    private String delYn;                            // 삭제여부
    
}
