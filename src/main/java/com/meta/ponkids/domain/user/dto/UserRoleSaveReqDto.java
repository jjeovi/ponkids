package com.meta.ponkids.domain.user.dto;

import com.meta.ponkids.domain.system.role.entity.Role;
import com.meta.ponkids.domain.user.entity.UserRole;
import com.meta.ponkids.domain.user.repository.UserRoleRepository;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
@Setter
public class UserRoleSaveReqDto {
    
    @NotNull
    private int roleSn;          // 아이디
    
    private String userId;
    
    private String registerId;
    
    private String registerIp;
    
    private String delYn;
    
    // builder 생성
    @Builder
    public UserRoleSaveReqDto( int roleSn, String userId, String registerId, String registerIp, String delYn ) {
        this.roleSn = roleSn;
        this.userId = userId;
        this.registerId = registerId;
        this.registerIp = registerIp;
        this.delYn = delYn;
    }
    
   
    // DTO to Entity 메소드는 DTO 내부에서 생성.
    public UserRole toEntity(){
        return UserRole.builder()
                .userId( userId )
                .roleSn( roleSn )
                .registerId( registerId )
                .registerIp( registerIp )
                .build();
    }
    
}
