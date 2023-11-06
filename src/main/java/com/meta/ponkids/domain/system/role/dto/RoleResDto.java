package com.meta.ponkids.domain.system.role.dto;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class RoleResDto {
    
    @NotNull
    private String roleSn;      // 권한 일련번호
    
    @NotNull
    private String roleNm;      // 권한이름
    
    @Builder
    public RoleResDto( String roleSn, String roleNm ) {
        this.roleSn = roleSn;
        this.roleNm = roleNm;
    }
    
}
