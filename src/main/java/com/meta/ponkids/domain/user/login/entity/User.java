package com.meta.ponkids.domain.user.login.entity;

import com.meta.ponkids.domain.system.role.entity.Role;
import com.meta.ponkids.global.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Where( clause = "del_yn = 'N'")
@Table( name = "TB_USER" )
public class  User extends BaseTimeEntity {
    
    /* USER_SN 컬럼 insert 시 사용하지 않음 */
    @Column(insertable=false)
    private int userSn;
    
    @Id
    @Column( unique = true )
    @Email
    private String userId;              // 사용자 아이디
    
    @NotNull
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
    
    private Integer atchFileSn;         // 첨부파일 일련번호
    
    @NotNull
    private String mngrYn;              // 관리자 여부
    
    @NotNull
    @ColumnDefault("N")
    private String mngrConfmYn;         // 관리자 승인 여부
    
    private String confmerId;           // 승인자 ID
    
    private String confmerIp;           // 승인자 IP
    
    private LocalDateTime confmDt;      // 승인 일시
    
    private String cntnSns;             // 연계
    
    private LocalDateTime lastLoginDt;  // 마지막 로그인한 일시
    
    @NotNull
    private String registerIp;          // 등록자 IP
    
    private String updusrId;            // 수정자 ID
    
    private String updusrIp;            // 수정자 IP
    
    @NotNull
    @ColumnDefault("N")
    private String delYn;               // 삭제 여부
    
    @ManyToMany
    @JoinTable(
            name = "tb_user_role",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_sn"))
    private List<Role> roles = new ArrayList<>();
}

