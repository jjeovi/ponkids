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
        name = "SEQ_TB_CLASS_SN",
        sequenceName = "SEQ_TB_CLASS_SN",
        initialValue = 1,
        allocationSize = 1
)
@Where( clause = "del_yn = 'N'" ) // DEFAULT 로 WHERE DEL_YN = 'N' 문을 추가하여 조회
@SQLDelete( sql = "UPDATE tb_class SET del_yn ='Y', updt_dt = now() WHERE class_sn = ?" )
// delelte 시 실행 (ex ) ~Repository.deleteById)
@Table( name = "TB_CLASS" )
public class Class extends BaseTimeEntity {
    
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_CLASS_SN" )
    private Long classSn;                // 클래스일련번호
    
    private String ctgryCd;                // 카테고리코드
    
    private String crseCd;                // 커리큘럼코드
    
    private String classSj;                // 클래스제목
    
    private String classSumry;            // 클래스요약
    
    private String classDc;                // 클래스설명
    
    private String classAmt;            // 클래스금액
    
    private String classDscntBfeAmt;    // 클래스할인전금액
    
    private String classPdSetYn;        // 클래스기간설정여부
    
    private String classBeginDt;        // 클래스시작일시
    
    private String classEndDt;            // 클래스종료일시
    
    private Long thumbAtchFileSn;        // 썸네일첨부파일일련번호
    
    private Long atchFileSn;            // 첨부파일일련번호
    
    private String classExpsrYn;        // 클래스표시여부
    
    @Column( updatable = false )
    private String registerId;            // 등록자ID
    
    @Column( updatable = false )
    private String registerIp;            // 등록자IP
    
    private String updusrId;            // 수정자ID
    
    private String updusrIp;            // 수정자IP
    
    @ColumnDefault( "N" )                             // del_yn 컬럼에 공통으로 추가
    @Column( insertable = false, updatable = false )  // del_yn 컬럼에 공통으로 추가 (등록 시, 수정 시 해당컬럼 신경쓰지 않음.)
    private String delYn;                // 삭제여부
    
}
