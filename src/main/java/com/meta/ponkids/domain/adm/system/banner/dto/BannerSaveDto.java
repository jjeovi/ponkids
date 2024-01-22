package com.meta.ponkids.domain.adm.system.banner.dto;

import com.meta.ponkids.domain.adm.system.banner.entity.Banner;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BannerSaveDto {
    
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
    
    private String registerId;            // 등록자 id
    
    private String registerIp;            // 등록자 ip
    
    private String updusrId;                // 수정자 ID
    
    private String updusrIp;                // 수정자 IP
    
    @Builder
    public BannerSaveDto( Long bannerSn, String bannerClCd, String bannerClDetailCd, Long bannerExpsrOrdr,
                          String bannerNm, String bannerDc, Long atchFileSn, String url, String classMapngYn, String classSn,
                          Long useYn, String bannerPdSetYn, String bannerBeginDt, String bannerEndDt, String registerId,
                          String registerIp, String updusrId, String updusrIp ) {
        super();
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
        this.registerId = registerId;
        this.registerIp = registerIp;
        this.updusrId = updusrId;
        this.updusrIp = updusrIp;
    }
    
    // Dto to Entity 메소드 생성
    public Banner toEntity() {
        return Banner.builder()
                .bannerSn( bannerSn )
                .bannerClCd( bannerClCd )
                .bannerClDetailCd( bannerClDetailCd )
                .bannerExpsrOrdr( bannerExpsrOrdr )
                .bannerNm( bannerNm )
                .bannerDc( bannerDc )
                .atchFileSn( atchFileSn )
                .url( url )
                .classMapngYn( classMapngYn )
                .classSn( classSn )
                .useYn( useYn )
                .bannerPdSetYn( bannerPdSetYn )
                .bannerBeginDt( bannerBeginDt )
                .bannerEndDt( bannerEndDt )
                .registerId( registerId )
                .registerIp( registerIp )
                .updusrId( updusrId )
                .updusrIp( updusrIp )
                .build();
    }
}
