package com.meta.ponkids.domain.system.banner.entity;

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
        name = "SEQ_TB_BANNER_SN",
        sequenceName = "SEQ_TB_BANNER_SN",
        initialValue = 1,
        allocationSize = 1
)
@Where( clause = "del_yn = 'N'") // DEFAULT 로 WHERE DEL_YN = 'N' 문을 추가하여 조회
@SQLDelete(sql = "UPDATE tb_banner SET del_yn ='Y', updt_dt = now() WHERE banner_sn = ?") // delelte 시 실행 (ex ) ~Repository.deleteById)
@Table( name = "TB_BANNER" )
public class Banner extends BaseTimeEntity {
	
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_BANNER_SN" )
	private Long bannerSn;
	
	private String bannerClCd;          	// 배너 분류 코드
	
	private String bannerClDetailCd;     	// 배너 분류 상세 코드
	
	private Long bannerExpsrOrdr;         	// 배너 노출 순서
	
	private String bannerNm;          		// 배너 이름
	
	private String bannerDc;          		// 배너 설명
	
	private Long atchFileSn;          		// 첨부 파일 일련번호
	
	private String url;          			// 클릭시 이동 URL
	
	private String classMapngYn;          	// 클래스 매핑 여부
	
	private String classSn;          		// 클래스 일련번호
	
	private Long useYn;          			// 사용 여부
	
	private String bannerPdSetYn;        	// 배너 기간 설정 여부
	
	private String bannerBeginDt;         	// 배너 시작 일시
	
	private String bannerEndDt;          	// 배너 종료 일시
	
	@Column(updatable = false)
	private String registerId;          	// 등록자 ID
	
	@Column(updatable = false)
	private String registerIp;          	// 등록자 IP
	
//  @Column(insertable = false)   // 등록할 때도 수정일시에 시간 들어가게 변경
	private String updusrId;            	// 수정자 ID
	
//  @Column(insertable = false)   // 등록할 때도 수정일시에 시간 들어가게 변경
	private String updusrIp;           	 	// 수정자 IP
	
	@ColumnDefault("N")                             // del_yn 컬럼에 공통으로 추가
	@Column(insertable = false, updatable = false)  // del_yn 컬럼에 공통으로 추가 (등록 시, 수정 시 해당컬럼 신경쓰지 않음.)
	private String delYn;                           // 삭제 여부

}
