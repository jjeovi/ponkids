package com.meta.ponkids.domain.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserListResDto {
    
    private String userId;
    
    private String userNm;
    
    private String userGender;
    
    private String userBrdtDate;
    
    private String userTelNo;
    
    private String resideArea;
    
    private String mngrYn;
    
}
