package com.meta.ponkids.domain.system.role.dto;

import com.meta.ponkids.domain.system.role.entity.Role;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;


/**
 * className    : RoleSaveReqDto
 * author         : jjeoV
 * date           : 11/10/23
 * description    : class of role save . 권한 저장 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 11/10/23        jjeoV       최초 생성
 */
@NoArgsConstructor
@Data
@Setter
public class RoleSaveReqDto {
    
    @NotNull
    private long roleSn;          // 아이디
    
    private String userId;
    
    private String registerId;
    
    private String registerIp;
    
    private String delYn;
    
    // builder 생성
    @Builder
    public RoleSaveReqDto( long roleSn, String registerId, String registerIp, String delYn ) {
        this.roleSn = roleSn;
        this.registerId = registerId;
        this.registerIp = registerIp;
        this.delYn = delYn;
    }
    
    // DTO to Entity 메소드는 DTO 내부에서 생성.
    public Role toEntity() {
        return Role.builder()
                .roleSn( roleSn )
                .registerId( registerId )
                .registerIp( registerIp )
                .build();
    }
    
    
}
