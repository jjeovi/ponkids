package com.meta.ponkids.domain.payment.dto;

import com.meta.ponkids.domain.cls.entity.ClassReqst;
import com.meta.ponkids.domain.payment.PaymentProvider;
import com.meta.ponkids.domain.payment.entity.ClassPayment;
import com.meta.ponkids.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassPaymentSaveDto {
    
    private Long classPaymentSn;
    
    /**
     * ClassReqst 테이블과 다대일 관계 (N:1)
     */
    private ClassReqst classReqst;
    
    /**
     * 주문 ID
     */
    private String orderId;
    
    /**
     * 사용자 테이블과 연결(다대일관계 N:1) (FK) - 비회원 결제 고려하여 nullable 허용
     */
    private User user;
    
    /**
     * 고객 키 (비회원 결제 가능)
     */
    private String customerKey;
    
    /**
     * 결제 키 (외부 결제사 키) - NULL 허용 : 결제 승인 전 상태에서는 NULL 가능
     */
    private String paymentKey;
    
    /**
     * 결제 타입 : 일반(NORMAL) , 브랜드페이(BRANDPAY) , ...
     */
    private String paymentType;
    
    /**
     * 결제 수단 : 카드, 가상계좌, 간편결제, 휴대폰, 계좌이체, 문화상품권, 도서문화상품권, 게임문화상품권
     */
    private String paymentMethod;
    
    /**
     * 결제 제공업체 (TOSS, KAKAOPAY 등)
     */
    private PaymentProvider paymentProvider;
    
    /**
     * 결제 금액
     */
    private BigDecimal amount;
    
    /**
     * 결제 상태
     */
    private String paymentStatus;
    
    /**
     * 원인 (취소 사유 등)
     */
    private String reason;
    
    private LocalDateTime requestedAt;
    
    private LocalDateTime approvedAt;
    
    /**
     * 결제 취소 시간 : 사용자가 취소를 요청한 시간
     */
    private LocalDateTime cancelRequestedAt;
    
    /**
     * 취소가능한금액 : 취소 후 환불가능한 금액
     */
    private BigDecimal refundableAmount;
    
    /**
     * 결제 환불 시간 : 환불처리가 완료된 시간
     */
    private LocalDateTime canceledAt;
    
    /**
     * 취소 금액
     */
    private BigDecimal cancelAmount;
    
    
    /**
     * 취소 상태
     */
    private String cancelStatus;
    
    /**
     * 취소 사유
     */
    private String cancelReason;
    
    /**
     * 멱등키
     */
    private String idempotencyKey;
    
    /**
     * 멱등키 생성시간
     */
    private LocalDateTime idempotencyCreatedAt;
    
    private String registerId;
    
    private String registerIp;
    
    private String updusrId;
    
    private String updusrIp;
    
    public ClassPayment toEntity() {
        return ClassPayment.builder()
                .classPaymentSn( classPaymentSn )
                .classReqst( classReqst )
                .orderId( orderId )
                .user( user )
                .customerKey( customerKey )
                .paymentKey( paymentKey )
                .paymentType( paymentType )
                .paymentMethod( paymentMethod )
                .paymentProvider( paymentProvider )
                .amount( amount )
                .paymentStatus( paymentStatus )
                .reason( reason )
                .requestedAt( requestedAt )
                .approvedAt( approvedAt )
                .cancelRequestedAt( cancelRequestedAt )
                .refundableAmount( refundableAmount )
                .canceledAt( canceledAt )
                .cancelAmount( cancelAmount )
                .cancelStatus( cancelStatus )
                .cancelReason( cancelReason )
                .idempotencyKey( idempotencyKey )
                .idempotencyCreatedAt( idempotencyCreatedAt )
                .registerId( registerId )
                .registerIp( registerIp )
                .updusrId( updusrId )
                .updusrIp( updusrIp )
                .build();
    }
    
    // Entity to Dto 메소드는 DTO 내부에서 생성.
    public ClassPaymentSaveDto toDto( ClassPayment classPayment ) {
        return ClassPaymentSaveDto.builder()
                .classPaymentSn( classPayment.getClassPaymentSn() )
                .classReqst( classPayment.getClassReqst() )
                .orderId( classPayment.getOrderId() )
                .user( classPayment.getUser() )
                .customerKey( classPayment.getCustomerKey() )
                .paymentKey( classPayment.getPaymentKey() )
                .paymentType( classPayment.getPaymentType() )
                .paymentMethod( classPayment.getPaymentMethod() )
                .paymentProvider( classPayment.getPaymentProvider() )
                .amount( classPayment.getAmount() )
                .paymentStatus( classPayment.getPaymentStatus() )
                .reason( classPayment.getReason() )
                .requestedAt( classPayment.getRequestedAt() )
                .approvedAt( classPayment.getApprovedAt() )
                .cancelRequestedAt( classPayment.getCancelRequestedAt() )
                .refundableAmount( classPayment.getRefundableAmount() )
                .canceledAt( classPayment.getCanceledAt() )
                .cancelAmount( classPayment.getCancelAmount() )
                .cancelStatus( classPayment.getCancelStatus() )
                .cancelReason( classPayment.getCancelReason() )
                .idempotencyKey( classPayment.getIdempotencyKey() )
                .idempotencyCreatedAt( classPayment.getIdempotencyCreatedAt() )
                .registerId( classPayment.getRegisterId() )
                .registerIp( classPayment.getRegisterIp() )
                .updusrId( classPayment.getUpdusrId() )
                .updusrIp( classPayment.getUpdusrIp() )
                .build();
    }
    
}
