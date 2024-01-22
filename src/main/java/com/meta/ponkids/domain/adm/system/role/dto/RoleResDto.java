package com.meta.ponkids.domain.adm.system.role.dto;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class RoleResDto {
    
    @NotNull
    private long roleSn;      // 권한 일련번호
    
    @NotNull
    private String roleNm;      // 권한이름
    
    @Builder
    public RoleResDto( long roleSn, String roleNm ) {
        this.roleSn = roleSn;
        this.roleNm = roleNm;
    }
    
}
