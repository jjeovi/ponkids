package com.meta.ponkids.domain.system.popup.entity;

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
@SequenceGenerator(
        name = "SEQ_TB_popup_SN",
        sequenceName = "SEQ_TB_popup_SN",
        initialValue = 1,
        allocationSize = 1
)
@Where( clause = "del_yn = 'N'") // DEFAULT 로 WHERE DEL_YN = 'N' 문을 추가하여 조회
@SQLDelete(sql = "UPDATE {h-schema}tb_popup SET del_yn ='Y', updt_dt = now() WHERE popup_sn = ?") // delelte 시 실행 (ex ) ~Repository.deleteById)
@Table( name = "TB_popup" )
public class Popup extends BaseTimeEntity {
	
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_popup_SN" )
	private Long 	popupSn;
	
	private String 	popupNm;
	
	private String 	popupCn;
	
	private String	useYn;
	
	private String	popupBeginDt;
	
	private String	popupEndDt;
	
	private Long	atchFileSn;
	
	private String	url;
	
	@Column(updatable = false)
	private String registerId;          // 등록자 ID
	
	@Column(updatable = false)
	private String registerIp;          // 등록자 IP
	
//  @Column(insertable = false)   // 등록할 때도 수정일시에 시간 들어가게 변경
	private String updusrId;            // 수정자 ID
	
//  @Column(insertable = false)   // 등록할 때도 수정일시에 시간 들어가게 변경
	private String updusrIp;            // 수정자 IP

    @ColumnDefault("N")                             // del_yn 컬럼에 공통으로 추가
    @Column(insertable = false, updatable = false)  // del_yn 컬럼에 공통으로 추가 (등록 시, 수정 시 해당컬럼 신경쓰지 않음.)
    private String delYn;                           // 삭제 여부

}
