package com.meta.ponkids.domain.user.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * className      : UserListDto
 * author         : jjeoV
 * date           : 2023-11-19
 * description    : class of 회원 목록 Dto
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-11-19        jjeoV             최초 생성
 */
@NoArgsConstructor
@Data
public class UserListDto {
    
    private Long userSn;
    
    private String userId;
    
    private String userNm;
    
    private String gender;
    
    private String brdtDate;
    
    private String telNo;
    
    private String resideArea;
    
    private String mngrYn;
    
    private String mngrConfmYn;
    
    private String schOption;   // 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String schCntn;     // 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
    
    
    @QueryProjection
    public UserListDto( Long userSn, String userId, String userNm, String gender, String brdtDate, String telNo, String resideArea, String mngrYn, String mngrConfmYn ) {
        this.userSn = userSn;
        this.userId = userId;
        this.userNm = userNm;
        this.gender = gender;
        this.brdtDate = brdtDate;
        this.telNo = telNo;
        this.resideArea = resideArea;
        this.mngrYn = mngrYn;
        this.mngrConfmYn = mngrConfmYn;
    }
    
}
