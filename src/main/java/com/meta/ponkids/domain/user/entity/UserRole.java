package com.meta.ponkids.domain.user.entity;

import com.meta.ponkids.global.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
        initialValue = 1, allocationSize = 1)
@Where( clause = "del_yn = 'N'")
@Table( name = "TB_USER_ROLE" )
public class UserRole extends BaseTimeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_USER_ROLE_SN")
    private int userRoleSn;
  
    @NotNull
    private String userId;
    
    @NotNull
    private int roleSn;
    
    @NotNull
    private String registerId;
    
    @NotNull
    private String registerIp;
    
    @ColumnDefault("N")
    private String delYn;
    
}
