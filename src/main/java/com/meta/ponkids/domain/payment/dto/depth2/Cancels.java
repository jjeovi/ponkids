package com.meta.ponkids.domain.payment.dto.depth2;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 결제 취소 정보
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties( ignoreUnknown = true )
public class Cancels {
    private Double refundableAmount;        // 환불 가능 금액
    private Double taxFreeAmount;           // 면세 금액
    private String canceledAt;              // 취소 시각 (ISO 8601 형식)
    private Double cancelAmount;            // 취소 금액
    private String cancelStatus;            // 취소 상태 (예: DONE)
    private String cancelReason;            // 취소 사유
    private String cancelRequestId;         // 취소 요청 ID
    private String transactionKey;          // 트랜잭션 키
    private Double taxExemptionAmount;      // 세금 면세 금액
    private Double transferDiscountAmount;  // 송금 할인 금액
    private String receiptKey;              // 영수증 키
    private Double easyPayDiscountAmount;   // EasyPay 할인 금액
}
