package com.meta.ponkids.domain.payment.dto;

import lombok.Data;

/**
 * Toss 결제 승인 요청 DTO 클래스
 * - 결제 요청 및 승인 후 반환되는 데이터를 포함
 */
@Data
public class TossApprReqDto {
    
    /**
     * 결제 승인 후 반환되는 결제 키
     */
    private String paymentType;
    
    /**
     * 주문 ID
     */
    private String orderId;
    
    /**
     * paymentKey
     */
    private String paymentKey;
    
    /**
     * 결제 금액
     */
    private double amount;
    
}
