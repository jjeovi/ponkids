package com.meta.ponkids.domain.system.role.dto;

import com.meta.ponkids.domain.system.role.entity.Role;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;

import javax.validation.constraints.Email;
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
    private int roleSn;          // 아이디
    
    // builder 생성
    @Builder
    public RoleSaveReqDto( int roleSn ) {
        this.roleSn = roleSn;
    }
    
    // DTO to Entity 메소드는 DTO 내부에서 생성.
    public Role toEntity(){
        return Role.builder()
                .roleSn( roleSn )
                .build();
    }
    
    
}
