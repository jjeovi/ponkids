package com.meta.ponkids.domain.user.entity;

import com.meta.ponkids.global.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
@DynamicInsert
@DynamicUpdate
@Where( clause = "del_yn = 'N'")
@SQLDelete(sql = "UPDATE tb_user SET del_yn ='Y', updt_dt = now() WHERE user_id = ?")
@Table( name = "TB_USER" )
public class  User extends BaseTimeEntity {
    
    /* USER_SN 컬럼 insert 시 사용하지 않음 */
    @Column(insertable=false, updatable = false)
    private int userSn;
    
    @Id
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
    
    private Integer atchFileSn;         // 첨부파일 일련번호
    
    private String mngrYn;              // 관리자 여부
    
    @ColumnDefault("N")
    private String mngrConfmYn;         // 관리자 승인 여부
    
    private String confmerId;           // 승인자 ID
    
    private String confmerIp;           // 승인자 IP
    
    private LocalDateTime confmDt;      // 승인 일시
    
    private String cntnSns;             // 연계
    
    private LocalDateTime lastLoginDt;  // 마지막 로그인한 일시
    
    @Column(updatable = false)
    private String registerIp;          // 등록자 IP
    
    private String updusrId;            // 수정자 ID
    
    private String updusrIp;            // 수정자 IP
    
    @ColumnDefault("N")
    @Column(insertable = false, updatable = false)
    private String delYn;               // 삭제 여부
    
//    @ManyToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE})
//    @JoinTable(
//            name = "tb_user_role",
//            joinColumns = @JoinColumn(name="userId"),
//            inverseJoinColumns = @JoinColumn(name="roleSn"))
//    private final List<Role> roles = new ArrayList<>();
    
}

