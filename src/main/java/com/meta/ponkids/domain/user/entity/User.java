package com.meta.ponkids.domain.user.entity;

import com.meta.ponkids.global.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * className      : User
 * author         : jjeoV
 * date           : 2023-11-19
 * description    : class of 회원 Entity
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-11-19        jjeoV             최초 생성
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert // insert 구문 시 null 이 아닌 값들만 insert
@DynamicUpdate // update 구문 시 null 이 아닌 값들만 update
@SequenceGenerator(
        name = "SEQ_TB_USER_SN",
        sequenceName = "SEQ_TB_USER_SN",
        initialValue = 1,
        allocationSize = 1
)
@Where( clause = "del_yn = 'N'" ) // DEFAULT 로 WHERE DEL_YN = 'N' 문을 추가하여 조회
@SQLDelete( sql = "UPDATE tb_user SET del_yn ='Y', updt_dt = now() WHERE user_sn = ?" )
// delelte 시 실행 (ex ) ~Repository.deleteById)
@Table( name = "TB_USER" )
public class User extends BaseTimeEntity {
    
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_USER_SN" )
//	/* USER_SN 컬럼 insert 시 사용하지 않음 */
//    @Column(insertable=false, updatable = false)
    private Long userSn;
    
    @Column( unique = true )
    @Email
    private String userId;              // 사용자 아이디
    
    private String password;            // 비밀번호
    
    @NotNull
    private String userNm;              // 사용자 이름
    
    @NotNull
    private String gender;              // 성별
    
    private String brdtDate;            // 생년월일
    
    @NotNull
    private String telNo;               // 연락처
    
    @NotNull
    private String resideArea;          // 거주지역
    
    private String zip;                 // 우편번호
    
    private String rdnmAdr;             // 도로명 주소
    
    private String detailAdr;           // 상세 주소
    
    private Long atchFileSn;         // 첨부파일 일련번호
    
    private String mngrYn;              // 관리자 여부
    
    @ColumnDefault( "N" )
    private String mngrConfmYn;         // 관리자 승인 여부
    
    private String confmerId;           // 승인자 ID
    
    private String confmerIp;           // 승인자 IP
    
    private LocalDateTime confmDt;      // 승인 일시
    
    private String          snsKakaoCntnYn;     // SNS Kakao 연계 여부
    
    private LocalDateTime   snsKakaoCntnDt;     // SNS Kakao 연계 일시
    
    private String          snsGoogleCntnYn;    // SNS Google 연계 여부
    
    private LocalDateTime   snsGoogleCntnDt;    // SNS Google 연계 일시
    
    private String          snsNaverCntnYn;     // SNS Naver 연계 여부
    
    private LocalDateTime   snsNaverCntnDt;     // SNS Naver 연계 일시
    
    private String          snsFacebookCntnYn;  // SNS Facebook 연계 여부
    
    private LocalDateTime   snsFacebookCntnDt;  // SNS Facebook 연계 일시
    
    private String          snsAppleCntnYn;     // SNS Apple 연계 여부
    
    private LocalDateTime   snsAppleCntnDt;     // SNS Apple 연계 일시
    
    private LocalDateTime lastLoginDt;  // 마지막 로그인한 일시
    
    @Column( updatable = false )
    private String registerIp;          // 등록자 IP
    
    private String updusrId;            // 수정자 ID
    
    private String updusrIp;            // 수정자 IP
    
    @ColumnDefault( "N" )                             // del_yn 컬럼에 공통으로 추가
    @Column( insertable = false, updatable = false )  // del_yn 컬럼에 공통으로 추가 (등록 시, 수정 시 해당컬럼 신경쓰지 않음.)
    private String delYn;                           // 삭제 여부
    
    
}

