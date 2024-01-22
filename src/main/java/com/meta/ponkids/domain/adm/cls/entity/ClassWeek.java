package com.meta.ponkids.domain.adm.cls.entity;

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
        name = "SEQ_TB_CLASS_WEEK_SN",
        sequenceName = "SEQ_TB_CLASS_WEEK_SN",
        initialValue = 1,
        allocationSize = 1
)
@Where( clause = "del_yn = 'N'" ) // DEFAULT 로 WHERE DEL_YN = 'N' 문을 추가하여 조회
@SQLDelete( sql = "UPDATE tb_class_week SET del_yn ='Y', updt_dt = now() WHERE class_week_sn = ?" )
// delelte 시 실행 (ex ) ~Repository.deleteById)
@Table( name = "TB_CLASS_WEEK" )
public class ClassWeek extends BaseTimeEntity {
    
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_CLASS_WEEK_SN" )
    private Long classWeekSn;               // 클래스 요일 일련번호
    
    private Long classSn;                   // 클래스 일련번호
    
    private String classDayCd;              // 클래스 요일 일련번호
    
    @Column( updatable = false )
    private String registerId;              // 등록자ID
    
    @Column( updatable = false )
    private String registerIp;              // 등록자IP
    
    private String updusrId;                // 수정자ID
    
    private String updusrIp;                // 수정자IP
    
    @ColumnDefault( "N" )                             // del_yn 컬럼에 공통으로 추가
    @Column( insertable = false, updatable = false )  // del_yn 컬럼에 공통으로 추가 (등록 시, 수정 시 해당컬럼 신경쓰지 않음.)
    private String delYn;                           // 삭제 여부
    
}
