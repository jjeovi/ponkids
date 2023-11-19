package com.meta.ponkids.domain.user.entity;

import com.meta.ponkids.global.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
 * className      : UserChldrn
 * author         : jjeoV
 * date           : 2023-11-19
 * description    : class of 자녀 Entity
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-11-19        jjeoV             최초 생성
 */
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@SQLDelete( sql = "UPDATE tb_user_chldrn SET del_yn ='Y', updt_dt = now() WHERE user_id = ?" )
@Where( clause = "del_yn = 'N'" )
@Table( name = "tb_user_chldrn" )
public class UserChldrn extends BaseTimeEntity {
    
    @Id
    @Column( unique = true )
    @GeneratedValue
    private int chldrnSn;              // 자녀 일련번호
    
    private String userId;
    
    private int userChldrnSeq;
    
    private String chldrnNm;            // 자녀 이름
    
    private String chldrnGender;        // 자녀 성별
    
    private String chldrnBrdtDate;      // 자녀 생년월일
    
    private String chldrnEmail;         // 자녀 이메일
    
    private String chldrnTelNo;         // 자녀 연락처
    
    private String atchFileSn;          // 첨부 파일 일련번호
    
    @NotNull
    private String registerId;          // 등록자 ID
    
    @NotNull
    private String registerIp;          // 등록자 IP
    
    private String updusrId;            // 수정자 ID
    
    private String updusrIp;            // 수정자 IP
    
    @ColumnDefault( "N" )
    private String delYn;               // 삭제 여부
    
}
