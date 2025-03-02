package com.meta.ponkids.domain.payment.entity;

import com.meta.ponkids.domain.payment.PaymentProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * ClassPaymentHistory 엔티티
 * 결제 내역의 변경 이력을 관리하는 테이블
 */
@Entity
@Table( name = "tb_class_payment_history" )
@EntityListeners( AuditingEntityListener.class ) // 필수
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassPaymentHistory {
    
    /**
     * 이력 고유 번호 (자동 증가)
     */
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "class_payment_history_sn", updatable = false, nullable = false )
    private Long classPaymentHistorySn;
    
    /**
     * 이력 기록 시간
     */
    @Column( name = "created_at", nullable = false, updatable = false )
    @CreatedDate
    private LocalDateTime createdAt;
    
    /**
     * 동일한 결제에 대한 변경 순번
     */
    @Column( name = "history_seq", nullable = false )
    private Long historySeq;
    
    /**
     * 원본 결제 테이블과 연결 (FK)
     */
    private Long classPaymentSn;
    
    /**
     * 신청 마스터 테이블과 연결 (FK)
     */
    @Column( name = "class_reqst_sn", nullable = false )
    private Long classReqstSn;
    
    /**
     * 주문 ID
     */
    @Column( name = "order_id", length = 32, nullable = false )
    private String orderId;
    
    /**
     * 사용자 테이블과 연결 (회원 결제일 경우)
     */
    @Column( name = "user_sn" )
    private Long userSn;
    
    /**
     * 고객 식별키 (비회원 결제 고려)
     */
    @Column( name = "customer_key", length = 64, nullable = false )
    private String customerKey;
    
    /**
     * 결제 키 (외부 결제사 키)
     */
    @Column( name = "payment_key", length = 64, nullable = false )
    private String paymentKey;
    
    /** 결제 타입 : 일반(NORMAL) , 브랜드페이(BRANDPAY) , ... */
    @Column(name = "payment_type", length = 20 )
    private String paymentType;
    
    /** 결제 수단 : 카드, 가상계좌, ... */
    @Column(name = "payment_method", length = 64 )
    private String paymentMethod;
    
    /**
     * 결제 제공업체 (TOSS, KAKAOPAY 등)
     */
    @Enumerated( EnumType.STRING )
    @Column( name = "payment_provider", length = 32, nullable = false )
    private PaymentProvider paymentProvider;
    
    /**
     * 결제 금액 (소수점 3자리)
     */
    @Column( name = "amount", precision = 21, scale = 3, nullable = false )
    private BigDecimal amount;
    
    /**
     * 결제 상태 (PENDING, COMPLETED, CANCELED 등)
     */
    @Column( name = "payment_status", length = 20, nullable = false )
    private String paymentStatus;
    
    /**
     * 사유
     */
    @Column( name = "reason", length = 3000 )
    private String reason;
    
    /**
     * 결제 요청 시간
     */
    @Column( name = "requested_at" )
    private LocalDateTime requestedAt;
    
    /**
     * 결제 승인 시간
     */
    @Column( name = "approved_at" )
    private LocalDateTime approvedAt;
    
    /**
     * 결제 취소 시간
     */
    @Column( name = "canceled_at" )
    private LocalDateTime canceledAt;
    
    /**
     * 최초 등록한 사용자 ID : tb_class_payment의 updusr_id 로 설정
     */
    @Column( name = "register_id", length = 50, nullable = false )
    private String registerId;
    
    /**
     * 최초 등록한 사용자 IP : tb_class_payment의 updusr_ip 로 설정
     */
    @Column( name = "register_ip", length = 100, nullable = false )
    private String registerIp;
    
    
    /**
     * 등록일시 : tb_class_payment의 updt_dt 로 설정
     */
    @Comment( value = "등록일시" )
    @Column( name = "reg_dt", updatable = false )
    private LocalDateTime regDt;
    
    /**
     * `ClassPayment` 엔티티를 기반으로 `ClassPaymentHistory` 객체를 생성하는 Builder
     */
    @Builder
    public ClassPaymentHistory( ClassPayment classPayment, Long historySeq ) {
        
        // pioneerkids info field
        this.classPaymentSn     = classPayment.getClassPaymentSn();
//        this.createdAt          = LocalDateTime.now();    // 자동으로 생성된다. -> @EntityListeners( AuditingEntityListener.class ) 필수로 등록해야 @CreatedDate 가 동작
        this.historySeq         = historySeq;
        this.classReqstSn       = classPayment.getClassReqst().getClassReqstSn();
        this.userSn             = classPayment.getUser().getUserSn();
        this.customerKey        = classPayment.getCustomerKey();
        
        // toss payment info field
        this.orderId            = classPayment.getOrderId();
        this.paymentKey         = classPayment.getPaymentKey();
        this.paymentType        = classPayment.getPaymentType();
        this.paymentMethod      = classPayment.getPaymentMethod();
        this.paymentProvider    = classPayment.getPaymentProvider();
        this.amount             = classPayment.getAmount();
        this.paymentStatus      = classPayment.getPaymentStatus();
        this.reason             = classPayment.getReason();
        this.requestedAt        = classPayment.getRequestedAt();
        this.approvedAt         = classPayment.getApprovedAt();
        this.canceledAt         = classPayment.getCanceledAt();
        
        // default update info field
        this.registerId         = classPayment.getUpdusrId();   // tb_class_payment의 updusr_id 로 설정
        this.registerIp         = classPayment.getUpdusrIp();   // tb_class_payment의 updusr_ip 로 설정
        this.regDt              = classPayment.getUpdtDt();     // tb_class_payment의 updt_dt 로 설정
        
    }
    
}