package com.meta.ponkids.domain.cls.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.meta.ponkids.global.common.BaseTimeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert // insert 구문 시 null 이 아닌 값들만 insert
@DynamicUpdate // update 구문 시 null 이 아닌 값들만 update
@SequenceGenerator(	// TODO SEQUENCE setting
        name = "SEQ_TB_class_inqry_SN",
        sequenceName = "SEQ_TB_class_inqry_SN",
        initialValue = 1,
        allocationSize = 1
)
@Where( clause = "del_yn = 'N'") // DEFAULT 로 WHERE DEL_YN = 'N' 문을 추가하여 조회
// TODO SQLDelete setting
@SQLDelete(sql = "UPDATE tb_class_inqry SET del_yn ='Y', updt_dt = now() WHERE class_inqry_sn = ?") // delelte 시 실행 (ex ) ~Repository.deleteById)
//TODO TB name setting
@Table( name = "TB_class_inqry" )
public class ClassInqry extends BaseTimeEntity {
	
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_class_inqry_SN" )
	private Long	classInqrySn;		// 클래스 문의 일련번호
	
	private Long	classSn;			// 클래스 일련번호
	
	private String	step;				// 계층
	
	private Long	parntsInqrySn;		// 부모 문의 일련번호
	
	private Long	userSn;				// 사용자 일련번호
	
	private String	inqrySj;			// 문의 제목
	
	private String	inqryCn;			// 문의 내용
	
	private String	openYn;				// 공개 여부 ( Y / N ) 
	
	@Column(updatable = false)
	private String	registerId;			// 등록자 ID
	
	@Column(updatable = false)
	private String	registerIp;			// 등록자 IP
	
//  @Column(insertable = false)			// 등록할 때도 수정일시에 시간 들어가게 변경
	private String	updusrId;			// 수정자 ID
	
//  @Column(insertable = false)			// 등록할 때도 수정일시에 시간 들어가게 변경
	private String	updusrIp;			// 수정자 IP

	@ColumnDefault("N")					// del_yn 컬럼에 공통으로 추가
	@Column(insertable = false, updatable = false)  // del_yn 컬럼에 공통으로 추가 (등록 시, 수정 시 해당컬럼 신경쓰지 않음.)
	private String delYn;				// 삭제 여부

}
