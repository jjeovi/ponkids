package com.meta.ponkids.domain.cls.dto;

import com.meta.ponkids.domain.cls.entity.ClassWeek;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassWeekSaveDto {
    
    private Long classWeekSn;        // 클래스 요일 일련번호
    
    private Long classSn;           // 클래스 일련번호
    
    private String classDayCd;      // 클래스 요일 코드
    
    private String registerId;      // 등록자 id
    
    private String registerIp;      // 등록자 ip
    
    private String updusrId;        // 수정자 id
    
    private String updusrIp;        // 수정자 ip
    
    @Builder
    public ClassWeekSaveDto( Long classWeekSn, Long classSn, String classDayCd, String registerId, String registerIp, String updusrId, String updusrIp ) {
        this.classWeekSn = classWeekSn;
        this.classSn = classSn;
        this.classDayCd = classDayCd;
        this.registerId = registerId;
        this.registerIp = registerIp;
        this.updusrId = updusrId;
        this.updusrIp = updusrIp;
    }
    
    // Dto to Entity 메소드 생성
    public ClassWeek toEntity() {
        return ClassWeek.builder()
                .classWeekSn( classWeekSn )
                .classSn( classSn )
                .classDayCd( classDayCd )
                .registerId( registerId )
                .registerIp( registerIp )
                .updusrId( updusrId )
                .updusrIp( updusrIp )
                .build();
    }
}
