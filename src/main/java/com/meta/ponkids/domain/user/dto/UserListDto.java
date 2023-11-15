package com.meta.ponkids.domain.user.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserListDto {
    
    private String userId;
    
    private String userNm;
    
    private String userGender;
    
    private String userBrdtDate;
    
    private String userTelNo;
    
    private String resideArea;
    
    private String mngrYn;
    
    private String mngrConfmYn;
    
    @QueryProjection
    public UserListDto(String userId) {
    	this.userId = userId;
    }
    
    
    
}
