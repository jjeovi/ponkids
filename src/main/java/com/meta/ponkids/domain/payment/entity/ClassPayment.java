package com.meta.ponkids.domain.payment.entity;

import com.meta.ponkids.domain.cls.entity.ClassReqst;
import com.meta.ponkids.domain.payment.PaymentProvider;
import com.meta.ponkids.domain.user.entity.User;
import com.meta.ponkids.global.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table( name = "tb_class_payment" )
public class ClassPayment extends BaseTimeEntity {
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "class_payment_sn" )
    private Long classPaymentSn;
    
    /** ClassReqst 테이블과 다대일 관계 (N:1) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_reqst_sn", nullable = false)
    private ClassReqst classReqst;
    
    /** 주문 ID */
    @Column(name = "order_id", length = 32, nullable = false)
    private String orderId;
    
    /** 사용자 테이블과 연결(다대일관계 N:1) (FK) - 비회원 결제 고려하여 nullable 허용 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_sn", nullable = true)
    private User user;
    
    /** 고객 키 (비회원 결제 가능) */
    @Column(name = "customer_key", length = 64, nullable = false)
    private String customerKey;
    
    /** 결제 키 (외부 결제사 키) - NULL 허용 */
    @Column(name = "payment_key", length = 64, unique = true )
    private String paymentKey;
    
    /** 결제 타입 : 일반(NORMAL) , 브랜드페이(BRANDPAY) , ... */
    @Column(name = "payment_type", length = 20 )
    private String paymentType;
    
    /** 결제 수단 : 카드, 가상계좌, ... */
    @Column(name = "payment_method", length = 64 )
    private String paymentMethod;
    
    /** 결제 제공업체 (TOSS, KAKAOPAY 등) */
    @Enumerated( EnumType.STRING )
    @Column( name = "payment_provider", length = 32, nullable = false )
    private PaymentProvider paymentProvider;
    
    /** 결제 금액 */
    @Column( name = "amount", nullable = false, precision = 21, scale = 3 )
    private BigDecimal amount;
    
    @Column( name = "payment_status", length = 32, nullable = false )
    private String paymentStatus;
    
    @Column(name = "reason", length = 3000 )
    private String reason;
    
    @Column( name = "requested_at" )
    private LocalDateTime requestedAt;
    
    @Column( name = "approved_at" )
    private LocalDateTime approvedAt;
    
    /**
     * 결제 취소 시간 : 사용자가 취소를 요청한 시간
     */
    @Column( name = "cancel_requested_at" )
    private LocalDateTime cancelRequestedAt;
    
    /**
     * 취소가능한금액 : 취소 후 환불가능한 금액
     */
    @Column( name = "refundable_amount" )
    private BigDecimal refundableAmount;
    
    /**
     * 결제 환불 시간 : 환불처리가 완료된 시간
     */
    @Column( name = "canceled_at" )
    private LocalDateTime canceledAt;
    
    /**
     * 취소 금액
     */
    @Column( name = "cancel_amount" )
    private BigDecimal cancelAmount;
    
    /**
     * 취소 상태
     */
    @Column( name = "cancel_status" )
    private String cancelStatus;
    
    /**
     * 취소 사유
     */
    @Column( name = "cancel_reason" )
    private String cancelReason;
    
    /**
     * 멱등키
     */
    @Column( name = "idempotency_key" )
    private String idempotencyKey;
    
    /**
     * 멱등키 생성시간
     */
    @Column( name = "idempotency_created_at" )
    private LocalDateTime idempotencyCreatedAt;
    
    @Column( name = "register_id", length = 50, nullable = false, updatable = false )
    private String registerId;
    
    @Column( name = "register_ip", length = 100, nullable = false, updatable = false )
    private String registerIp;
    
    @Column( name = "updusr_id", length = 50 )
    private String updusrId;
    
    @Column( name = "updusr_ip", length = 100 )
    private String updusrIp;
    
    /**
     * 환불 사유 로그를 기록하기 위한 도메인 메서드
     *
     * @param reason 환불 내역 로그 문자열
     */
    public void updateReason( String reason ) {
        this.reason = reason;
    }
    
    /**
     * 환불 금액을 설정하는 도메인 메서드
     *
     * @param cancelAmount 환불 금액
     */
    public void setCancelAmount( BigDecimal cancelAmount ) {
        this.cancelAmount = cancelAmount;
    }
    
    // 멱등키 생성
    public void setIdempotencyKey( String idempotencyKey ) {
        this.idempotencyKey = idempotencyKey;
    }
    
    // 멱등키 생성 시간
    public void setIdempotencyCreatedAt( LocalDateTime idempotencyCreatedAt ) {
        this.idempotencyCreatedAt = idempotencyCreatedAt;
    }
 
//    @Comment( value = "등록일시" )
//    @Column( name="reg_dt", updatable = false )
//    @CreatedDate
//    private LocalDateTime regDt;
//
//    @Comment( value = "수정일시" )
////    @Column(insertable = false)   // 등록할 때도 수정일시에 시간 들어가게 변경
//    @LastModifiedDate
//    private LocalDateTime updtDt;
    
}
