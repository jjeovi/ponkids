package com.meta.ponkids.domain.adm.user.dto;

import com.meta.ponkids.domain.adm.user.entity.UserRole;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * className      : UserRoleModDto
 * author         : jjeoV
 * date           : 2023-11-19
 * description    : class of 회원 권한 수정 Dto
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-11-19        jjeoV             최초 생성
 */
@NoArgsConstructor
@Data
@Setter
public class UserRoleModDto {
    
    private Long userRoleSn;
    //    @NotNull
    private Long roleSn;          // 아이디
    
    private Long userSn;
    
    private String updusrId;
    
    private String updusrIp;
    
    private String delYn;
    
    // builder 생성
    @Builder
    public UserRoleModDto( Long userRoleSn, Long roleSn, Long userSn, String updusrId, String updusrIp, String delYn ) {
        this.userRoleSn = userRoleSn;
        this.roleSn = roleSn;
        this.userSn = userSn;
        this.updusrId = updusrId;
        this.updusrIp = updusrIp;
        this.delYn = delYn;
    }
    
    // DTO to Entity 메소드는 DTO 내부에서 생성.
    public UserRole toEntity() {
        return UserRole.builder()
                .userRoleSn( userRoleSn )
                .userSn( userSn )
                .roleSn( roleSn )
                .updusrId( updusrId )
                .updusrIp( updusrIp )
                .build();
    }
    
    // Entity to Dto 메소드는 DTO 내부에서 생성.
    public UserRoleModDto toDto( UserRole userRole ) {
        return UserRoleModDto.builder()
                .userRoleSn( userRole.getUserRoleSn() )
                .userSn( userRole.getUserSn() )
                .roleSn( userRole.getRoleSn() )
                .updusrId( userRole.getUpdusrId() )
                .updusrIp( userRole.getUpdusrIp() )
                .build();
    }
    
}
