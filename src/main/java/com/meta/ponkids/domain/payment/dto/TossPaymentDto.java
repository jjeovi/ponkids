package com.meta.ponkids.domain.payment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
/**
 * Toss 결제 정보를 담는 DTO 클래스
 * - 결제 요청 및 승인 후 반환되는 데이터를 포함
 * - Lombok을 활용하여 Getter 및 Setter 자동 생성
 */
@Getter
@Setter
class TossPaymentDto {
    
    /** 결제 승인 후 반환되는 결제 키 */
    private String paymentType;
    
    /** 주문 ID */
    private String orderId;
    
    /** 고객 식별 키 */
    private String customerKey;
    
    /** 결제 서비스 제공자 (예: Toss, 카드사 등) */
    private String paymentProvider;
    
    /** 결제 금액 */
    private double amount;
    
    /** 결제 승인 시간 */
    private LocalDateTime approvedAt;
}