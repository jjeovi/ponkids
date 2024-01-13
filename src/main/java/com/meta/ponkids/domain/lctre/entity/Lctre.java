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
        name = "SEQ_TB_LCTRE_SN",
        sequenceName = "SEQ_TB_LCTRE_SN",
        initialValue = 1,
        allocationSize = 1
)
@Where( clause = "del_yn = 'N'") // DEFAULT 로 WHERE DEL_YN = 'N' 문을 추가하여 조회
// TODO SQLDelete setting
@SQLDelete(sql = "UPDATE tb_LCTRE SET del_yn ='Y', updt_dt = now() WHERE LCTRE_sn = ?") // delelte 시 실행 (ex ) ~Repository.deleteById)
//TODO TB name setting
@Table( name = "tb_lctre" )
public class Lctre extends BaseTimeEntity {
	
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_LCTRE_SN" )
	private Long 	lctreSn;				// 수업 일련번호
	
	private Long 	classSn;				// 클래스 일련번호
	
	private Long 	classWeekSn;			// 클래스 요일 일련번호
	
	private Long 	lctreSeq;				// 수업 순번
	
	private String 	lctreSj;				// 수업 제목
	
	private String 	lctreDc;				// 수업 설명
	
	private String 	lctreApplcntGuidance;	// 수업 신청자 안내
	
	private String 	rcritNmprSetYn;			// 모집 인원 설정 여부
	
	private Long 	rcritNmprCo;			// 모집 인원 수
	
	private String 	preparRcritNmprSetYn;	// 예비 모집 인원 설정 여부
	
	private Long 	preparRcritNmprCo;		// 예비 모집 인원 수
	
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
