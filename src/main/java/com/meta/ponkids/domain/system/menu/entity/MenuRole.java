package com.meta.ponkids.domain.system.menu.entity;

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
        name = "SEQ_TB_MENU_ROLE_SN",
        sequenceName = "SEQ_TB_MENU_ROLE_SN", //매핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 1 )
@SQLDelete( sql = "UPDATE tb_menu_role SET del_yn ='Y', updt_dt = now() WHERE menu_role_sn = ?" )
@Where( clause = "del_yn = 'N'" )
@Table( name = "tb_menu_role" )
public class MenuRole extends BaseTimeEntity {
    
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_MENU_ROLE_SN" )
    private Long menuRoleSn;
    
    @NotNull
    private Long menuSn;
    
    //    @NotNull
    private Long roleSn;
    
    @Column(updatable = false)
    private String registerId;
    
    @Column(updatable = false)
    private String registerIp;

//  @Column(insertable = false)   // 등록할 때도 수정일시에 시간 들어가게 변경
    private String updusrId;
    
//  @Column(insertable = false)   // 등록할 때도 수정일시에 시간 들어가게 변경
    private String updusrIp;
    
    @ColumnDefault( "N" )
    @Column(insertable = false, updatable = false)  // del_yn 컬럼에 공통으로 추가 (등록 시, 수정 시 해당컬럼 신경쓰지 않음.)
    private String delYn;
    
}
