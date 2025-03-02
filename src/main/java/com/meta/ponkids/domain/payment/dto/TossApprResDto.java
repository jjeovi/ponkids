package com.meta.ponkids.domain.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 토스 결제 승인 응답 DTO (TossApprResDto)
 *
 * 토스 결제 승인 응답을 매핑하는 DTO 클래스입니다.
 * payment 객체 정보 : 37개 필드(2025/02/17 기준)를 포함하고 있으며,
 * nullable 필드는 null 이 될 수 있음.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TossApprResDto {
    /** 상점 아이디 */
    private String mId;
    
    /** 마지막 거래 키값 (nullable) */
    private String lastTransactionKey;
    
    /** 결제 키값 (필수) */
    private String paymentKey;
    
    /** 주문 번호 */
    private String orderId;
    
    /** 주문명 (구매상품명) */
    private String orderName;
    
    /** 과세 제외 금액 */
    private Integer taxExemptionAmount;
    
    /** 결제 상태 (READY, DONE, CANCELED 등) */
    private String status;
    
    /** 결제 요청 시간 (ISO 8601 형식) */
    private String requestedAt;
    
    /** 결제 승인 시간 (nullable, ISO 8601 형식) */
    private String approvedAt;
    
    /** 에스크로 사용 여부 */
    private Boolean useEscrow;
    
    /** 문화비 지출 여부 */
    private Boolean cultureExpense;
    
    /** 카드 결제 정보 (nullable) */
    private Map<String, Object> card;
    
    /** 가상계좌 정보 (nullable) */
    private Map<String, Object> virtualAccount;
    
    /** 계좌이체 정보 (nullable) */
    private Map<String, Object> transfer;
    
    /** 휴대폰 결제 정보 (nullable) */
    private Map<String, Object> mobilePhone;
    
    /** 상품권 결제 정보 (nullable) */
    private Map<String, Object> giftCertificate;
    
    /** 현금영수증 정보 (nullable) */
    private Map<String, Object> cashReceipt;
    
    /** 현금영수증 발행 내역 (nullable) */
    private Map<String, Object> cashReceipts;
    
    /** 할인 정보 (nullable) */
    private Map<String, Object> discount;
    
    /** 결제 취소 이력 (nullable) */
    private Map<String, Object> cancels;
    
    /** 웹훅 검증값 (nullable) */
    private String secret;
    
    /** 결제 유형 (NORMAL, BILLING 등) */
    private String type;
    
    /** 간편결제 정보 (nullable) */
    private Map<String, Object> easyPay;
    
    /** 결제 국가 */
    private String country;
    
    /** 결제 실패 정보 (nullable) */
    private Map<String, Object> failure;
    
    /** 부분 취소 가능 여부 */
    private Boolean isPartialCancelable;
    
    /** 영수증 정보 (nullable) */
    private Map<String, Object> receipt;
    
    /** 결제창 정보 (nullable) */
    private Map<String, Object> checkout;
    
    /** 결제 통화 (KRW 등) */
    private String currency;
    
    /** 총 결제 금액 */
    private Long totalAmount;           // totalAmount값만 Long 으로 저장
    
    /** 취소 가능 금액 */
    private Integer balanceAmount;
    
    /** 공급가액 */
    private Integer suppliedAmount;
    
    /** 부가세 */
    private Integer vat;
    
    /** 면세 금액 */
    private Integer taxFreeAmount;
    
    /** 결제 수단 (카드, 계좌이체 등) */
    private String method;
    
    /** API 버전 */
    private String version;
    
    /** 추가 메타데이터 (nullable) */
    private Map<String, Object> metadata;
}

