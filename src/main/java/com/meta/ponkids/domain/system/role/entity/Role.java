package com.meta.ponkids.domain.system.role.entity;

import com.meta.ponkids.domain.user.entity.User;
import com.meta.ponkids.global.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Where( clause = "del_yn = 'N'")
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Table( name = "TB_ROLE" )
@SequenceGenerator(
        name = "SEQ_TB_ROLE_SN",
        sequenceName = "SEQ_TB_ROLE_SN",
        initialValue = 1,
        allocationSize = 1
)
public class Role extends BaseTimeEntity {
    
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_ROLE_SN" )
    private int roleSn;
    
    private String roleNm;
    
    private String roleDc;
    
    @NotNull
    private String registerId;          // 등록자 ID
    
    @NotNull
    private String registerIp;          // 등록자 IP
    
    private String updusrId;            // 수정자 ID
    
    private String updusrIp;            // 수정자 IP
    
    @ColumnDefault( "N" )
    private String delYn;               // 삭제 여부
    
    @ManyToMany
    private List<User> userList;
    
}