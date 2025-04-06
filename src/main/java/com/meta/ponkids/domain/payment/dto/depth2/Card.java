package com.meta.ponkids.domain.payment.dto.depth2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 카드 결제 정보
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties( ignoreUnknown = true )
public class Card {
    private String  ownerType;              // 카드 소유자 유형 (예: 개인)
    private String  number;                 // 카드 번호 (마스킹된 형태)
    private Double  amount;                 // 결제 금액
    private String  acquireStatus;          // 카드 수취 상태 (예: READY)
    private boolean isInterestFree;         // 무이자 여부
    private String  cardType;               // 카드 종류 (예: 신용카드)
    private String  approveNo;              // 승인 번호
    private Integer installmentPlanMonths;  // 할부개월수
    private String  interestPayer;          // 이자 지불자
    private String  issuerCode;             // 카드 발급사 코드
    private String  acquirerCode;           // 카드 수취사 코드
    private boolean useCardPoint;           // 카드 포인트 사용 여부
}
