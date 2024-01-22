package com.meta.ponkids.domain.user.dto;

import com.meta.ponkids.domain.user.entity.UserRole;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * className      : UserRoleSaveDto
 * author         : jjeoV
 * date           : 2023-11-19
 * description    : class of 회원 권한 등록 Dto
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-11-19        jjeoV             최초 생성
 */
@NoArgsConstructor
@Data
@Setter
public class UserRoleSaveDto {
    
    //    @NotNull
    private Long roleSn;          // 아이디
    
    private Long userSn;
    
    private String registerId;
    
    private String registerIp;
    
    private String delYn;
    
    // builder 생성
    @Builder
    public UserRoleSaveDto( Long roleSn, Long userSn, String registerId, String registerIp, String delYn ) {
        this.roleSn = roleSn;
        this.userSn = userSn;
        this.registerId = registerId;
        this.registerIp = registerIp;
        this.delYn = delYn;
    }
    
    // DTO to Entity 메소드는 DTO 내부에서 생성.
    public UserRole toEntity() {
        return UserRole.builder()
                .userSn( userSn )
                .roleSn( roleSn )
                .registerId( registerId )
                .registerIp( registerIp )
                .build();
    }
    
}
