package com.meta.ponkids.domain.system.menu.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

import com.meta.ponkids.domain.system.menu.entity.MenuRole;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MenuRoleSaveDto {
	
    private Long menuRoleSn;
    
    private Long menuSn;
    
    private Long roleSn;
    
    private String registerId;
    
    private String registerIp;

    private String updusrId;
    
    private String updusrIp;
    
    private String delYn;

    @Builder
	public MenuRoleSaveDto(Long menuRoleSn, Long menuSn, Long roleSn, @NotNull String registerId,
			@NotNull String registerIp, String updusrId, String updusrIp) {
		super();
		this.menuRoleSn = menuRoleSn;
		this.menuSn = menuSn;
		this.roleSn = roleSn;
		this.registerId = registerId;
		this.registerIp = registerIp;
		this.updusrId = updusrId;
		this.updusrIp = updusrIp;
	}
    
    
    // DTO to Entity 메소드 : DTO 내부에 생성
    public MenuRole toEntity() {
    	return MenuRole.builder()
    			.menuRoleSn(menuRoleSn)
    			.menuSn(menuSn)
    			.roleSn(roleSn)
    			.registerId(registerId)
    			.registerIp(registerIp)
    			.updusrId(updusrId)
    			.updusrIp(updusrIp)
    			.build();
    			
    }
    
    
    

}
