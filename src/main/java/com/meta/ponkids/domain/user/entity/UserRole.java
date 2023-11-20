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
import javax.validation.constraints.NotNull;

/**
 * className    : UserRole
 * author         : jjeoV
 * date           : 2023-11-19
 * description    : class of 회원권한 Entity
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
@DynamicInsert
@DynamicUpdate
@SequenceGenerator(
        name = "SEQ_TB_USER_ROLE_SN",
        sequenceName = "SEQ_TB_USER_ROLE_SN", //매핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 1 )
@SQLDelete( sql = "UPDATE tb_user_role SET del_yn ='Y', updt_dt = now() WHERE user_sn = ?" )
@Where( clause = "del_yn = 'N'" )
@Table( name = "tb_user_role" )
public class UserRole extends BaseTimeEntity {
    
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_USER_ROLE_SN" )
    private Long userRoleSn;
    
    @NotNull
    private Long userSn;
    
    //    @NotNull
    private Long roleSn;
    
    @NotNull
    private String registerId;
    
    @NotNull
    private String registerIp;
    
    @ColumnDefault( "N" )
    private String delYn;
    
}
