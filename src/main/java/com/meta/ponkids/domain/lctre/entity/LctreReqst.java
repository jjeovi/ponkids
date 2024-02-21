package com.meta.ponkids.domain.lctre.entity;

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
@SequenceGenerator(	// TODO SEQUENCE setting
        name = "SEQ_TB_LCTRE_REQST_SN",
        sequenceName = "SEQ_TB_LCTRE_REQST_SN",
        initialValue = 1,
        allocationSize = 1
)
@Where( clause = "del_yn = 'N'") // DEFAULT 로 WHERE DEL_YN = 'N' 문을 추가하여 조회
@SQLDelete(sql = "UPDATE {h-schema}tb_lctre_reqst SET del_yn ='Y', updt_dt = now() WHERE lctre_reqst_sn = ?") // delelte 시 실행 (ex ) ~Repository.deleteById)
@Table( name = "tb_lctre_reqst" )
public class LctreReqst extends BaseTimeEntity {

	
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_LCTRE_REQST_SN" )
	private Long lctreReqstSn;			// 수업 신청 일련번호
	
	private Long classReqstSn;			// 클래스 신청 일련번호
	
	private Long lctreSn;				// 수업 일련번호
	
	private Long chldrnSn;				// 자녀 일련번호
	
	@Column(updatable = false)
	private String registerId;          // 등록자 ID
	
	@Column(updatable = false)
	private String registerIp;          // 등록자 IP
	
//  @Column(insertable = false)   // 등록할 때도 수정일시에 시간 들어가게zㅋ 변경
	private String updusrId;            // 수정자 ID
	
//  @Column(insertable = false)   // 등록할 때도 수정일시에 시간 들어가게 변경
	private String updusrIp;            // 수정자 IP

    @ColumnDefault("N")                             // del_yn 컬럼에 공통으로 추가
    @Column(insertable = false, updatable = false)  // del_yn 컬럼에 공통으로 추가 (등록 시, 수정 시 해당컬럼 신경쓰지 않음.)
    private String delYn;                           // 삭제 여부

}