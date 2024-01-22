package com.meta.ponkids.domain.system.banner.dto;

import com.meta.ponkids.global.common.dto.CategoryDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BannerListDto {
    
    private Long bannerSn;                    // 배너 일련번호
    
    private String bannerClCd;            // 배너 분류 코드
    
    private String bannerClDetailCd;        // 배너 분류 상세 코드
    
    private Long bannerExpsrOrdr;            // 배너 노출 순서
    
    private String bannerNm;                // 배너 이름
    
    private String bannerDc;                // 배너 설명
    
    private Long atchFileSn;                // 첨부 파일 일련번호
    
    private String url;                    // 클릭시 이동 URL
    
    private String classMapngYn;            // 클래스 매핑 여부
    
    private String classSn;                // 클래스 일련번호
    
    private Long useYn;                    // 사용 여부
    
    private String bannerPdSetYn;            // 배너 기간 설정 여부
    
    private String bannerBeginDt;            // 배너 시작 일시
    
    private String bannerEndDt;            // 배너 종료 일시
    
    private String schOption;   // 검색 옵션 *( A,B,C,... ) : 생성자에는 추가하지 않음!
    
    private String schCntn;     // 검색 내용 *( 검색어 내용 ) : 생성자에는 추가하지 않음!
    
    private CategoryDto category;    // 카테고리 검색 : 생성자에는 추가하지 않음!
    
    @QueryProjection
    public BannerListDto( Long bannerSn, String bannerClCd, String bannerClDetailCd, Long bannerExpsrOrdr,
                          String bannerNm, String bannerDc, Long atchFileSn, String url, String classMapngYn, String classSn,
                          Long useYn, String bannerPdSetYn, String bannerBeginDt, String bannerEndDt ) {
        this.bannerSn = bannerSn;
        this.bannerClCd = bannerClCd;
        this.bannerClDetailCd = bannerClDetailCd;
        this.bannerExpsrOrdr = bannerExpsrOrdr;
        this.bannerNm = bannerNm;
        this.bannerDc = bannerDc;
        this.atchFileSn = atchFileSn;
        this.url = url;
        this.classMapngYn = classMapngYn;
        this.classSn = classSn;
        this.useYn = useYn;
        this.bannerPdSetYn = bannerPdSetYn;
        this.bannerBeginDt = bannerBeginDt;
        this.bannerEndDt = bannerEndDt;
    }
    
    
}