package com.meta.ponkids.domain.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.meta.ponkids.domain.payment.dto.depth2.Cancels;
import com.meta.ponkids.domain.payment.dto.depth2.Card;
import com.meta.ponkids.domain.payment.dto.depth2.Checkout;
import com.meta.ponkids.domain.payment.dto.depth2.Receipt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 토스 결제 취소 응답 DTO
 * <p>
 * 토스 결제 API에서 결제 취소 요청 후, 응답 데이터를 저장하는 DTO입니다.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TossCancelResDto {
    
    private String      country;                 // 결제 요청 국가
    private String      metadata;                // 메타데이터 (선택적 필드)
    private String      orderId;                 // 주문 ID
    private boolean     isPartialCancelable;     // 부분 취소 가능 여부
    private String      lastTransactionKey;      // 마지막 트랜잭션 키
    private Double      taxExemptionAmount;      // 세금 면세 금액
    private Double      suppliedAmount;          // 공급 금액
    private String      secret;                  // 비밀 키
    private String      type;                    // 결제 유형 (예: NORMAL)
    private boolean     cultureExpense;          // 문화비 지출 여부
    private Double      taxFreeAmount;           // 면세 금액
    private String      requestedAt;             // 요청 시각 (ISO 8601 형식)
    private String      currency;                // 통화 (예: KRW)
    private String      paymentKey;              // 결제 키
    
    private Checkout    checkout;                // 결제 확인 URL 정보
    
    private String      orderName;               // 주문 이름
    private String      method;                  // 결제 방법 (예: 카드)
    private boolean     useEscrow;               // 에스크로 사용 여부
    private Integer     vat;                     // 부가가치세
    private String      mId;                     // Merchant ID (상점 ID)
    private String      approvedAt;              // 결제 승인 시각
    private Integer     balanceAmount;           // 잔액 금액
    private String      version;                 // API 버전
    
    private Cancels[]   cancels;                 // 취소 정보 배열
    
    private Object      transfer;                // 송금 정보 (선택적 필드)
    private Object      mobilePhone;             // 모바일 전화 번호 (선택적 필드)
    private Object      failure;                 // 실패 정보 (선택적 필드)
    private Receipt     receipt;                 // 영수증 정보
    private Object      giftCertificate;         // 상품권 정보 (선택적 필드)
    private Object      cashReceipt;             // 현금 영수증 (선택적 필드)
    
    private Card        card;                    // 카드 결제 정보
    
    private String      status;                  // 결제 상태 (예: CANCELED)
}
